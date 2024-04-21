package com.qrestor.paymentor.service;

import com.qrestor.models.Pair;
import com.qrestor.models.dto.AbstractPublicDTO;
import com.qrestor.models.dto.auth.UserDescriptorDTO;
import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.paymentor.client.AuthHttpClient;
import com.qrestor.paymentor.client.MenuHttpClient;
import com.qrestor.paymentor.client.RestaurantHttpClient;
import com.qrestor.paymentor.entity.PaymentRequestDetails;
import com.qrestor.paymentor.kafka.producers.KafkaProducer;
import com.qrestor.paymentor.repository.PaymentDetailsRepository;
import com.qrestor.paymentor.systemuser.enitity.SyncUser;
import com.qrestor.paymentor.systemuser.repository.SyncUserRepository;
import com.qrestor.security.SecurityUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.qrestor.models.EmailRequestType.ORDER_SUCCESS_CONFIRMATION;
import static com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO.USER_NAME_PARAM;

@Service
@Slf4j
public class PaymentService {
    private final AuthHttpClient authHttpClient;
    private final RestaurantHttpClient restaurantHttpClient;
    private final MenuHttpClient menuHttpClient;

    private final PaymentDetailsRepository repository;

    private final SyncUserRepository syncUserRepository;

    private final KafkaProducer<KafkaEmailSendRequestDTO> mailerProducer;

    public PaymentService(AuthHttpClient authHttpClient,
                          SyncUserRepository syncUserRepository,
                          RestaurantHttpClient restaurantHttpClient,
                          MenuHttpClient menuHttpClient,
                          PaymentDetailsRepository repository,
                          @Value("${app.stripe.secret}") String stripeApiKey,
                          KafkaProducer<KafkaEmailSendRequestDTO> mailerProducer) {
        this.repository = repository;
        this.mailerProducer = mailerProducer;
        Stripe.apiKey = stripeApiKey;
        this.authHttpClient = authHttpClient;
        this.syncUserRepository = syncUserRepository;
        this.restaurantHttpClient = restaurantHttpClient;
        this.menuHttpClient = menuHttpClient;
    }

    private static List<SessionCreateParams.LineItem> mapOrderToLineItems(OrderDTO order,
                                                                          CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsPriceMapF,
                                                                          CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsOptionsPriceMapF) {
        Map<UUID, Pair<String, Long>> uuidPairMap = null;
        Map<UUID, Pair<String, Long>> menuItemsOptionsPriceMap = null;
        try {
            menuItemsOptionsPriceMap = menuItemsOptionsPriceMapF.get();
            uuidPairMap = menuItemsPriceMapF.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Map<UUID, Pair<String, Long>> finalUuidPairMap = uuidPairMap;
        List<SessionCreateParams.LineItem> menuItems = order.getItems().stream()
                .map(menuItem -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) menuItem.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(finalUuidPairMap.get(menuItem.getMenuItemId()).right() * 10)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(finalUuidPairMap.get(menuItem.getMenuItemId()).left())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());

        Map<UUID, Pair<String, Long>> finalMenuItemsOptionsPriceMap = menuItemsOptionsPriceMap;
        List<SessionCreateParams.LineItem> menuItemOptions = order.getItems().stream()
                .flatMap(itemOrderDetails -> itemOrderDetails.getMenuItemOptions().stream())
                .flatMap(menuItemOptions1 -> menuItemOptions1.getOptionPositions().stream())
                .map(menuItemOptionPositionId -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(finalMenuItemsOptionsPriceMap.get(menuItemOptionPositionId.getPublicId()).right() * 10)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(finalMenuItemsOptionsPriceMap.get(menuItemOptionPositionId.getPublicId()).left())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());
        menuItems.addAll(menuItemOptions);
        return menuItems;
    }

    @Transactional
    public void performStripeAccountCreation() throws StripeException {
        UserDescriptorDTO userInfo = authHttpClient.getUserInfo();
        AccountCreateParams params = prepareNewSubContractorAccount(userInfo);
        Account account = Account.create(params);
        syncUserRepository.getByUuid(SecurityUtils.getPrincipalUUID())
                .ifPresent(syncUser -> saveUserStripeAccountId(syncUser, account));
        log.info("Stripe Account created: {}", account);
    }

    private void saveUserStripeAccountId(SyncUser syncUser,
                                         Account account) {
        syncUser.setStripeAccountId(account.getId());
        syncUserRepository.save(syncUser);
    }

    private AccountCreateParams prepareNewSubContractorAccount(UserDescriptorDTO userInfo) {
        return AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.STANDARD)
                .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                .setCountry(userInfo.address().country())
                .setEmail(userInfo.user().email())
                .setCompany(getBasicUserFiels(userInfo))
                .build();
    }

    private AccountCreateParams.Company getBasicUserFiels(UserDescriptorDTO userInfo) {
        return AccountCreateParams.Company.builder()
                .setName(userInfo.information().businessName())
                .setPhone(userInfo.information().phone())
                .setAddress(getUserAddress(userInfo))
                .build();
    }

    private AccountCreateParams.Company.Address getUserAddress(UserDescriptorDTO userInfo) {
        return AccountCreateParams.Company.Address.builder()
                .setCity(userInfo.address().city())
                .setCountry(userInfo.address().country())
                .setLine1(userInfo.address().address())
                .setPostalCode(userInfo.address().zip())
                .setState(userInfo.address().state())
                .build();
    }

    public String getStripeOnboardRedirectUrl() throws StripeException {
        String stripeAccountId = getStripeAccId();
        AccountLinkCreateParams params =
                AccountLinkCreateParams.builder()
                        .setAccount(stripeAccountId)
                        .setRefreshUrl("http://localhost:8080/paymentor/reauth")
                        .setReturnUrl("http://localhost:8080/paymentor/return")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build();

        AccountLink accountLink = AccountLink.create(params);
        return accountLink.getUrl();
    }

    private String getStripeAccId() {
        Optional<SyncUser> user = syncUserRepository.getByUuid(SecurityUtils.getPrincipalUUID());
        if (user.isPresent()) {
            return user.get().getStripeAccountId();
        } else {
            throw new RuntimeException("User not found");
        }

    }

    public String getRedirectUrlForOrder(UUID orderId) throws StripeException {
        return repository.findById(orderId).map(PaymentRequestDetails::getPaymentUrl).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private String getPaymentRedirectUrl(OrderDTO order) throws StripeException {
        CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsPriceMap = getMenuItemsPriceMap(order.getItems());
        CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsOptionsPriceMap = getMenuItemsOptionsPriceMap(order.getItems());

        UUID restaurantOwnerId = restaurantHttpClient.getRestaurantOwnerId(order.getRestaurantId());
        Optional<SyncUser> user = syncUserRepository.getByUuid(restaurantOwnerId);
        if (user.isPresent()) {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addAllLineItem(mapOrderToLineItems(order, menuItemsPriceMap, menuItemsOptionsPriceMap))
                            //.setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                            //        .setApplicationFeeAmount(123L)
                            //        .build())
                            .setSuccessUrl("http://localhost:8080/paymentor/payment/success")
                            .setCancelUrl("http://localhost:8080/paymentor/payment/failure")
                            .build();

            RequestOptions requestOptions =
                    RequestOptions.builder().setStripeAccount(user.get().getStripeAccountId()).build();

            Session session = Session.create(params, requestOptions);
            return session.getUrl();
        }
        throw new RuntimeException("User not found");
    }

    private CompletableFuture<Map<UUID, Pair<String, Long>>> getMenuItemsOptionsPriceMap(List<ItemOrderDetails> items) {
        var menuItemOptionPositionsIds = items.stream()
                .flatMap(item -> item.getMenuItemOptions().stream())
                .flatMap(menuOptions -> menuOptions.getOptionPositions().stream())
                .map(AbstractPublicDTO::getPublicId)
                .collect(Collectors.toSet());
        return CompletableFuture.supplyAsync(() -> menuHttpClient.getMenuItemOptionsPositionsPricesMap(menuItemOptionPositionsIds));
    }

    private CompletableFuture<Map<UUID, Pair<String, Long>>> getMenuItemsPriceMap(List<ItemOrderDetails> orderItems) {
        Set<UUID> menuItemsUUIDs = orderItems.stream().map(ItemOrderDetails::getMenuItemId).collect(Collectors.toSet());
        return CompletableFuture.supplyAsync(() -> menuHttpClient.getMenuItemsPriceMap(menuItemsUUIDs));
    }

    public void processPayment(OrderDTO message) throws StripeException {
        String paymentUrl = getPaymentRedirectUrl(message);
        repository.save(new PaymentRequestDetails(message.getPublicId(), paymentUrl));
    }

    public void processPaymentSuccessAfterActions(Session session) {
        mailerProducer.send(new KafkaEmailSendRequestDTO(
                session.getCustomerDetails().getEmail(),
                ORDER_SUCCESS_CONFIRMATION,
                prepareParams(session)));
    }

    Map<String, String> prepareParams(Session session) {
        Map<String, String> params = new HashMap<>();
        params.put(USER_NAME_PARAM, session.getCustomerDetails().getName());
        params.put("amount", session.getAmountTotal().toString());
        params.put("currency", session.getCurrency());
        return params;
    }
}
