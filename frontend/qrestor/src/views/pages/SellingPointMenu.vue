<script setup>
import { useRoute } from 'vue-router'
import AppFooter from '@/layout/AppFooter.vue'
import QrService from '@/service/QrService.js'
import MenuService from '@/service/MenuService.js'
import { computed, onBeforeMount, ref } from 'vue'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'
import OrdersService from '@/service/OrdersService.js'
import router from '@/router/index.js'

const toast = useToast()
const route = useRoute()
const qrCodeId = route.params.code
const tableNumber = ref(null)
const pointInfo = ref({})

const qrService = new QrService()
const menuService = new MenuService()
const sellingPointsService = new SellingPointsService()
const ordersService = new OrdersService()
const getImageURL = (path) => {
  return import.meta.env.VITE_ROOT_API + '/files/' + path
}

const formatCurrency = (value) => {
  const price = value / 10
  return price.toLocaleString('en', { style: 'currency', currency: 'PLN' })
}

const nestedRouteItems = ref([
  {
    label: 'Pizza',
    command: (event) => {
      console.log(event)
    }
  },
  {
    label: 'Main'
  },
  {
    label: 'Drinks'
  },
  {
    label: 'Deserts'
  }
])

const selectedItems = ref([])
const selectedOptionsIds = ref(null)
const orderSummaryDialog = ref(false)
const onlinePayment = ref(false)

const selectedItemsPriceSum = computed(() => {
  return selectedItems.value.reduce((acc, item) => {
    return (
      acc +
      (item.price * item.quantity +
        item.menuItemOptions.reduce((acc, option) => {
          return (
            acc +
            option.optionPositions.reduce((acc, position) => {
              return acc + position.price
            }, 0)
          )
        }, 0))
    )
  }, 0)
})

const showOrderSummary = () => {
  orderSummaryDialog.value = true
  console.log('showOrderSummary', selectedItems.value)
}

const placeOrderAndGetPaymentUrl = async () => {
  const orderModel = {
    restaurantId: pointInfo.value.publicId,
    restaurantName: pointInfo.value.name,
    restaurantTitle: pointInfo.value.title,
    tableNumber: tableNumber.value,

    items: selectedItems.value,
    paymentSelected: onlinePayment.value
  }

  console.log('placeOrderAndGetPaymentUrl', orderModel)
  // const { data, status } = await ordersService.placeOrderAndPay(orderModel)
  // if (onlinePayment.value === true) {
  //   const url = await ordersService.getPaymentUrl(data.publicId)
  //   if (url) {
  //     await router.push(url)
  //   }
  // }
}
  const selectedItemModel = ref({
    count: 1
  })
  const addTOCartDialog = ref(false)
  const callWaiterDialog = ref(false)

  const selectedItemCount = ref(1)

  const addItemToCartDialog = async (item) => {
    const { data: itemOptionsInfo } = await menuService.getMenuItemOptionsInfo(item.itemId)
    addTOCartDialog.value = true
    selectedItemModel.value = item
    console.log('selected item options', itemOptionsInfo)
    selectedItemModel.value.options = itemOptionsInfo
  }

  const layout = ref('grid')



  const selectedCategoryItems = ref([])
  const restaurantBackgroundUrl = ref('')
  onBeforeMount(async () => {
    const qrCodeMappings = await qrService.getQrCodeData(qrCodeId)
    tableNumber.value = qrCodeMappings.data.tableId
    const { data: menuAggregate } = await menuService.getMenuAggregate(
      qrCodeMappings.data.sellingPointId
    )
    const { data: sellPointInfo } = await sellingPointsService.getSellingPointInfo(
      qrCodeMappings.data.sellingPointId
    )
    pointInfo.value = sellPointInfo
    restaurantBackgroundUrl.value = getImageURL(sellPointInfo.settings.backgroundImageUrl)
    selectedCategoryItems.value = menuAggregate.find(
      (element) => element.categoryNlsKey === 'PIZZA'
    ).items
  })

  const backgroundStyle = computed(() => {
    return {
      backgroundImage: `url(${restaurantBackgroundUrl.value})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center'
    }
  })

  const hideDialog = () => {
    addTOCartDialog.value = false
  }

  const addItemToCart = (count, specialInstructions) => {
    const itemToAddToCart = {
      imageUrl: selectedItemModel.value.imageUrl,
      menuItemId: selectedItemModel.value.itemId,
      menuItemTitle: selectedItemModel.value.title,
      price: selectedItemModel.value.price,
      specialInstructions: selectedItemModel.value.specInstr,
      menuItemOptions: mergeOptionPositions(selectedOptionsIds.value),
      quantity: selectedItemModel.value.count
    }

    console.log(selectedOptionsIds.value)
    selectedItems.value.push(itemToAddToCart)
    console.log(selectedItems.value)
    addTOCartDialog.value = false
    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Item has been added to cart',
      life: 3000
    })
    selectedItemModel.value = null
    selectedOptionsIds.value = null
  }

  const formatAddress = (address) => {
    return `${address.zip}, \u00A0 ${address.city}, \u00A0 ${address.address}`
  }

  const showCallWaiterDialog = () => {
    callWaiterDialog.value = true
  }

  const callWaiterRequest = () => {
    console.log(
      `callWaiterRequest to table ${tableNumber.value} and point ${pointInfo.value.publicId}`
    )
    sellingPointsService.callWaiter(tableNumber.value, pointInfo.value.publicId)
    callWaiterDialog.value = false
    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Waiter has been called',
      life: 3000
    })
  }

  const getSelectedValueObj = (option, position) => {
    return {
      publicId: option.publicId,
      optionTitle: option.title,
      optionPositions: [
        {
          publicId: position.publicId,
          optionTitle: position.title,
          price: position.price
        }
      ]
    }
  }

  function mergeOptionPositions(options) {
    const result = {}
    if (options) {
      options.forEach((option) => {
        if (result[option.publicId]) {
          result[option.publicId].optionPositions.push(...option.optionPositions)
        } else {
          result[option.publicId] = {
            publicId: option.publicId,
            optionTitle: option.optionTitle,
            optionPositions: [...option.optionPositions]
          }
        }
      })

      return Object.values(result)
    } else {
      return []
    }
  }

</script>

<template>
  <div class="layout-wrapper containerClass" :style="backgroundStyle">
    <div class="layout-main-container pt-3">
      <div class="card">
        <div class="text-900 font-bold text-3xl mb-4 text-center">
          Welcome to {{ pointInfo.title }}
        </div>
        <div class="text-2xl mb-4 text-center line-height-3">{{ pointInfo.description }}</div>
        <div class="text-l text-center line-height-3">{{ formatAddress(pointInfo.address) }}</div>
      </div>
      <div class="layout-main">
        <div class="grid">
          <div class="col-12">
            <div class="card">
              <h5>Menu</h5>
              <div>
                <TabMenu :model="nestedRouteItems" class="mb-0 pb-0"></TabMenu>
                <DataView :value="selectedCategoryItems" :layout="layout" :rows="9">
                  <template #grid="slotProps">
                    <div class="col-12 md:col-3">
                      <div class="card border-1 surface-border mt-2">
                        <div class="text-center">
                          <Image
                            preview
                            :src="getImageURL(slotProps.data.imageUrl)"
                            :alt="slotProps.data.name"
                            class="w-9 my-3 mx-0"
                            height="200"
                          />
                          <div class="text-2xl font-bold">{{ slotProps.data.title }}</div>
                          <div class="mb-3">{{ slotProps.data.description }}</div>
                        </div>
                        <div class="flex align-items-center justify-content-between">
                          <div class="flex align-items-center">
                            <span
                              v-for="ingr in slotProps.data.ingredients" :key="ingr.publicId"
                              class="font-semibold mr-2"
                              ><i class="pi pi-tag mr-2"></i>{{ ingr }}</span
                            >
                          </div>
                        </div>
                        <div class="flex align-items-center justify-content-between mt-2">
                          <span class="text-2xl font-semibold">{{
                            formatCurrency(slotProps.data.price)
                          }}</span>
                          <Button
                            icon="pi pi-shopping-cart"
                            @click="addItemToCartDialog(slotProps.data)"
                          ></Button>
                        </div>
                      </div>
                    </div>
                  </template>
                </DataView>
                <Toast></Toast>
                <Dialog
                  v-model:visible="callWaiterDialog"
                  header="Do You want to call Waiter to table?"
                  :modal="true"
                  class="p-fluid w-auto"
                >
                  <template #footer>
                    <Button
                      label="Call waiter"
                      icon="pi pi-bell"
                      class="p-button-text"
                      @click="callWaiterRequest"
                    />
                  </template>
                </Dialog>
                <Dialog
                  v-model:visible="addTOCartDialog"
                  header="Add to cart"
                  :modal="true"
                  class="p-fluid w-auto"
                >
                  <div class="text-center">
                    <Image
                      preview
                      :src="getImageURL(selectedItemModel.imageUrl)"
                      :alt="selectedItemModel.name"
                      class=""
                      height="200"
                    />
                  </div>
                  <div class="field">
                    <h4>{{ selectedItemModel.title }}</h4>
                  </div>

                  <div class="field">
                    <h5>{{ selectedItemModel.description }}</h5>
                  </div>
                  <div class="flex align-items-center">
                    <span v-for="ingr in selectedItemModel.ingredients" :key="ingr.publicId" class="font-semibold mr-2"
                      ><i class="pi pi-tag mr-2"></i>{{ ingr }}</span
                    >
                  </div>

                  <div class="field mt-2">
                    <span class="text-2xl font-semibold">{{
                      formatCurrency(selectedItemModel.price)
                    }}</span>
                  </div>

                  <div class="field">
                    <label for="itemCount">Count</label>
                    <InputNumber
                      v-model="selectedItemModel.count"
                      id="itemCount"
                      inputId="itemCount"
                      min="1"
                      showButtons
                    />
                  </div>
                  <div v-for="option in selectedItemModel.options" :key="option.publicId">
                    <h5>{{ option.title }}</h5>
                    <div
                      class="field-checkbox mb-2"
                      v-for="optionValue in option.menuItemOptionPositions"
                      :key="optionValue.publicId"
                    >
                      <Checkbox
                        :inputId="optionValue.publicId"
                        name="optionValue"
                        :value="getSelectedValueObj(option, optionValue)"
                        v-model="selectedOptionsIds"
                      />
                      <label :for="optionValue.publicId">
                        {{
                          optionValue.title +
                          '  \u00A0 \u00A0 \u00A0 \u00A0  ' +
                          formatCurrency(optionValue.price)
                        }}
                      </label>
                    </div>
                  </div>
                  <div class="field mt-3">
                    <label for="spec">Special Instructions</label>
                    <InputText id="spec" placeholder="..." v-model="selectedItemModel.specInstr" />
                  </div>

                  <template #footer>
                    <Button
                      label="Cancel"
                      icon="pi pi-times"
                      class="p-button-text"
                      @click="hideDialog"
                    />
                    <Button
                      label="Add to cart"
                      icon="pi pi-check"
                      class="p-button-text"
                      @click="addItemToCart"
                    />
                  </template>
                </Dialog>
                <Dialog
                  v-model:visible="orderSummaryDialog"
                  header="Your order"
                  :modal="true"
                  class="p-fluid w-9"
                >
                  <div class="field">
                    <h4>Selected items</h4>
                  </div>
                  <div v-for="sitem in selectedItems" :key="sitem.menuItemId" class="mb-5">
                    <div class="grid">
                      <div class="col-3 md:col-3">
                        <Image
                          preview
                          :src="getImageURL(sitem.imageUrl)"
                          :alt="sitem.menuItemTitle"
                          class=""
                          height="50"
                        ></Image>
                      </div>
                      <div class="field col-9 md:col-9">
                        <h5>{{ sitem.menuItemTitle }}</h5>
                        <span class="text-xl font-semibold"
                          >{{ formatCurrency(sitem.price) }} x {{ sitem.quantity }}</span
                        >
                      </div>
                    </div>

                    <div class="field" v-if="!(sitem.menuItemOptions.length === 0)">
                      <h5 class="text-900 font-bold text-2xl">Selected options:</h5>
                    </div>
                    <div v-for="option in sitem.menuItemOptions" :key="option.publicId">
                      <h5 class="text-xl">{{ option.optionTitle }}</h5>
                      <div
                        class="flex mb-2"
                        v-for="optionValue in option.optionPositions"
                        :key="optionValue.publicId"
                      >
                        <Tag
                          class="flex align-items-center justify-content-center ml-2 text-sm"
                          severity="success"
                          :value="optionValue.optionTitle"
                        ></Tag>
                        <span class="text-xl font-semibold ml-2"
                          >+ {{ formatCurrency(optionValue.price) }}</span
                        >
                      </div>
                    </div>
                    <Divider></Divider>
                  </div>
                  <div class="field-checkbox">
                    <Checkbox
                      id="paymentSelected"
                      name="paymentSelected"
                      value="paymentSelected"
                      v-model="onlinePayment"
                      :binary="true"
                    />
                    <label for="paymentSelected">Online payment</label>
                  </div>
                  <template #footer>
                    <Button
                      label="Cancel"
                      icon="pi pi-times"
                      class="p-button-text"
                      @click="hideDialog"
                    />
                    <Button
                      :label="formatCurrency(selectedItemsPriceSum)"
                      icon="pi pi-send"
                      class="p-button-text"
                      @click="placeOrderAndGetPaymentUrl"
                    />
                  </template>
                </Dialog>
                <Button
                  class="p-button-rounded p-button-success p-button-icon-only floating-button"
                  icon="pi pi-shopping-cart"
                  @click="showOrderSummary"
                ></Button>
                <Button
                  class="p-button-rounded p-button-danger p-button-icon-only floating-button-waiter"
                  icon="pi pi-user"
                  @click="showCallWaiterDialog"
                ></Button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <app-footer></app-footer>
    </div>
    <div class="layout-mask"></div>
  </div>
</template>

<style scoped>
.floating-button {
  position: fixed;
  right: 20px;
  bottom: 450px;
  z-index: 1000; /* Ensure the button is above other elements */
}

.floating-button-waiter {
  position: fixed;
  right: 20px;
  bottom: 400px;
  z-index: 1000; /* Ensure the button is above other elements */
}
</style>
