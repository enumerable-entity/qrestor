package com.qrestor.paymentor.service;

import com.qrestor.models.Pair;
import com.qrestor.models.dto.AbstractPublicDTO;
import com.qrestor.models.dto.auth.UserDescriptorDTO;
import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.paymentor.client.AuthHttpClient;
import com.qrestor.paymentor.client.MenuHttpClient;
import com.qrestor.paymentor.client.RestaurantHttpClient;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {
    private final AuthHttpClient authHttpClient;
    private final RestaurantHttpClient restaurantHttpClient;
    private final MenuHttpClient menuHttpClient;

    private final SyncUserRepository syncUserRepository;
    @Value("${app.stripe.secret}")
    private String stripeApiKey;

    public PaymentService(AuthHttpClient authHttpClient, SyncUserRepository syncUserRepository, RestaurantHttpClient restaurantHttpClient,
                          MenuHttpClient menuHttpClient) {
        Stripe.apiKey = stripeApiKey;
        this.authHttpClient = authHttpClient;
        this.syncUserRepository = syncUserRepository;
        this.restaurantHttpClient = restaurantHttpClient;
        this.menuHttpClient = menuHttpClient;
    }

    private static List<SessionCreateParams.LineItem> mapOrderToLineItems(OrderDTO order,
                                                                          CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsPriceMap,
                                                                          CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsOptionsPriceMap) {
        List<SessionCreateParams.LineItem> menuItems = order.getItems().stream()
                .map(menuItem -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) menuItem.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(menuItemsPriceMap.join().get(menuItem.getMenuItemId()).right())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(menuItemsPriceMap.join().get(menuItem.getMenuItemId()).left())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());

        List<SessionCreateParams.LineItem> menuItemOptions = order.getItems().stream()
                .map(ItemOrderDetails::getMenuItemOptions)
                .flatMap(Collection::stream)
                .map(menuItemOption -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(menuItemsOptionsPriceMap.join().get(menuItemOption.getPublicId()).right())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(menuItemsOptionsPriceMap.join().get(menuItemOption.getPublicId()).left())
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
        AccountCreateParams params =
                AccountCreateParams.builder()
                        .setType(AccountCreateParams.Type.STANDARD)
                        .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                        .setCountry(userInfo.address().country())
                        .setEmail(userInfo.user().email())
                        .setCompany(AccountCreateParams.Company.builder()
                                .setName(userInfo.information().businessName())
                                .setPhone(userInfo.information().phone())
                                .setAddress(AccountCreateParams.Company.Address.builder()
                                        .setCity(userInfo.address().city())
                                        .setCountry(userInfo.address().country())
                                        .setLine1(userInfo.address().address())
                                        .setPostalCode(userInfo.address().zip())
                                        .setState(userInfo.address().state())
                                        .build())
                                .build())
                        .build();
        Account account = Account.create(params);
        syncUserRepository.findByUuid(SecurityUtils.getPrincipalUUID())
                .ifPresent(syncUser -> {
                    syncUser.setStripeAccountId(account.getId());
                    syncUserRepository.save(syncUser);
                });
        log.info("Stripe Account created: {}", account);
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
        Optional<SyncUser> user = syncUserRepository.findByUuid(SecurityUtils.getPrincipalUUID());
        if (user.isPresent()) {
            return user.get().getStripeAccountId();
        } else {
            throw new RuntimeException("User not found");
        }

    }

    public String getPaymentRedirectUrl(OrderDTO order) throws StripeException {
        CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsPriceMap = getMenuItemsPriceMap(order.getItems());
        CompletableFuture<Map<UUID, Pair<String, Long>>> menuItemsOptionsPriceMap = getMenuItemsOptionsPriceMap(order.getItems());

        UUID restaurantOwnerId = restaurantHttpClient.getRestaurantOwnerId(order.getRestaurantId());
        Optional<SyncUser> user = syncUserRepository.findByUuid(restaurantOwnerId);
        if (user.isPresent()) {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addAllLineItem(mapOrderToLineItems(order, menuItemsPriceMap, menuItemsOptionsPriceMap))
                            //.setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                            //        .setApplicationFeeAmount(123L)
                            //        .build())
                            .setSuccessUrl("https://localhost:8080/paymentor/successPayment")
                            .setCancelUrl("https://localhost:8080/paymentor/cancelPayment")
                            .build();

            RequestOptions requestOptions =
                    RequestOptions.builder().setStripeAccount(user.get().getStripeAccountId()).build();

            Session session = Session.create(params, requestOptions);
            return session.getUrl();
        }
        throw new RuntimeException("User not found");
    }

    private CompletableFuture<Map<UUID, Pair<String, Long>>> getMenuItemsOptionsPriceMap(List<ItemOrderDetails> items) {
        Set<UUID> menuItemsUUIDs = items.stream()
                .flatMap(item -> item.getMenuItemOptions().stream().map(AbstractPublicDTO::getPublicId))
                .collect(Collectors.toSet());
        return CompletableFuture.supplyAsync(() -> menuHttpClient.getMenuItemsOptionsPriceMap(menuItemsUUIDs));
    }

    private CompletableFuture<Map<UUID, Pair<String, Long>>> getMenuItemsPriceMap(List<ItemOrderDetails> orderItems) {
        Set<UUID> menuItemsUUIDs = orderItems.stream().map(ItemOrderDetails::getMenuItemId).collect(Collectors.toSet());
        return CompletableFuture.supplyAsync(() -> menuHttpClient.getMenuItemsPriceMap(menuItemsUUIDs));
    }
}
