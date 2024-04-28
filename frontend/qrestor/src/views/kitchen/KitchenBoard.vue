<script setup>
import { onBeforeMount, ref } from 'vue'
import OrdersService from '@/service/OrdersService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'
import KithchenBoardSSEservice from '@/service/KithchenBoardSSEservice.js'
import { useWaiterCallsStore } from '@/store.js'


const waiterCallsStore = useWaiterCallsStore()
const toast = useToast()
const { t } = useI18n()
const orders = ref([])
const waiterRequests = ref(new Set())
const selectedOrderId = ref(null)

const setContextOrderId = (orderId) => {
  selectedOrderId.value = orderId
}

const getSeverity = (status) => {
  if (status === 'PAYMENT_IN_PROGRESS') {
    return 'info'
  } else if (status === 'PENDING') {
    return 'warning'
  } else if (status === 'IN_PROGRESS') {
    return 'help'
  } else if (status === 'COMPLETED') {
    return 'success'
  } else if (status === 'CANCELLED') {
    return 'danger'
  }
}

const ordersService = new OrdersService()
const boardSSEservice = new KithchenBoardSSEservice()

const onSSEmessage = (data, event) => {
  console.log('SSE message', data)

  switch (event) {
    case 'INIT_ORDERS':
      orders.value.unshift(data)
      break
    case 'NEW_ORDER':
      orders.value.push(data)
      toast.add({
        severity: 'success',
        summary: 'New order received',
        detail: 'Order ID: ' + data.publicId,
        life: 6000
      })
      break
    case 'ORDER_STATUS_CHANGED':
      var order = orders.value.find(order => order.publicId === data.publicId)
      if (order) {
        order.status = data.status
      }
      break
    case 'WAITER_CALL':
      waiterRequests.value.add(data.tableNr)
      waiterCallsStore.addWaiterCall(data.tableNr)
      break
  }
}

onBeforeMount(async () => {
  orders.value = []
  waiterRequests.value = waiterCallsStore.getCalls()
  boardSSEservice.connect(onSSEmessage)
})

const acceptWaiterCall = (tableNrr) => {
  console.log('waiter call accepted', tableNrr)
  waiterRequests.value = waiterCallsStore.remove(tableNrr)
  toast.add({
    severity: 'success',
    summary: 'Waiter Call accepted',
    detail: 'Table: ' + tableNrr,
    life: 3000
  })
}

const rejectOrder = (orderId) => {
  ordersService.changeOrderStatus(orderId, 'CANCELLED')
  orders.value = orders.value.filter((val) => val.publicId !== orderId)
  toast.add({
    severity: 'success',
    summary: 'Order rejected',
    detail: 'Order was rejected',
    life: 3000
  })
}

const makeIdShorter = (id) => {
  return id.substring(0, 8) + ' ...'
}

const statuses = [
  {
    label: 'In progress',
    icon: 'pi pi-angle-double-right',
    command: (event) => {
      console.log(event)
      changeStatus('IN_PROGRESS')
    }
  },
  {
    label: 'Completed',
    icon: 'pi pi-check',
    command: () => {
      changeStatus('COMPLETED')
    }
  }
]

const changeStatus = (status) => {
  ordersService.changeOrderStatus(selectedOrderId.value, status)
  orders.value = orders.value.map((val) => {
    if (val.publicId === selectedOrderId.value) {
      val.status = status
    }
    return val
  })
  toast.add({
    severity: 'success',
    summary: 'Order status changed',
    detail: 'Order status was changed',
    life: 3000
  })
}
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <h1 class="text-4xl text-center">Kitchen Board</h1>

      <Card
        class="mb-5 bg-orange-600 fadein animation-duration-2000 animation-iteration-infinite"
        v-if="!(waiterRequests.size === 0)"
      >
        <template #content>
          <div class="flex flex-wrap align-items-center justify-content-center	text-6xl">
            Waiter request to tables:
            <Tag
              @click=acceptWaiterCall(waiterRequest)
              class="text-6xl ml-4 cursor-pointer"
              severity="info"
              :value=waiterRequest
              v-for="waiterRequest in waiterRequests"
              :key="waiterRequest" />

          </div>
        </template>

      </Card>

      <div class="card">
        <h1 v-if="orders.length < 1" class="text-xl text-center">No active orders</h1>
        <Toast />
        <div class="flex align-content-start flex-wrap">
          <div class="w-3 min-w-3"
               v-for="order in orders"
               :key="order.publicId">
            <Card
              class="ml-3 mt-3 surface-50  "
            >
              <template #title>Order ID: {{ makeIdShorter(order.publicId) }}</template>
              <template #content>
                <p class="m-0 p-0 text-xl">
                  STATUS:
                  <span>
                  <Tag
                    class="text-lg ml-2"
                    :severity=getSeverity(order.status)
                    :value=t(order.status)
                  />
                </span>
                </p>

                <p class="m-0 p-0 text-xl">
                  Table number:
                  <span class="text-base"
                  ><Tag class="m-0" :value="order.tableNumber" severity="info"
                  /></span>
                </p>
                <p class="m-0 p-0 text-xl">
                  Items count:
                  <Tag class="m-0" :value="order.items.length" severity="info" />
                </p>
                <p class="m-0 p-0 text-xl">
                  Online payment:
                  <i
                    class="pi ml-2"
                    :class="{
                    'text-green-500 pi-check-circle': order.paymentSelected,
                    'text-pink-500 pi-times-circle': !order.paymentSelected
                  }"
                  ></i>
                </p>
                <Divider class="mb-3 mt-0 p-0"></Divider>
                <p class="text-xl">Order items:</p>
                <div class=" w-full">
                  <div class=" m-0 p-0 text-xl" v-for="item in order.items" :key="item.menuItemId">
                    <p class="m-2"> Title: {{ item.menuItemTitle }} </p>
                    <p class="m-2"> Quantity: {{ item.quantity }} </p>
                    <p class="m-2"> Note: {{ item.specialInstructions }}</p>
                    <Divider class="m-0 p-0"></Divider>
                    <p class="m-2"> Options:</p>
                    <div class="list">
                      <li class=" ml-3" v-for="option in item.menuItemOptions" :key="option.publicId">
                        {{ option.optionTitle }}
                        <li class=" ml-4 mt-2 " v-for="position in option.optionPositions" :key="position.publicId">
                          <span class="mt-2"> {{ position.optionTitle }}</span>
                        </li>
                      </li>
                    </div>

                  </div>
                </div>

              </template>
              <template #footer>
                <Divider class="mb-3 mt-0 p-0"></Divider>

                <div class="grid">
                  <div class=" col-6">
                    <Button class="w-auto" severity="danger" label="Reject" icon="pi pi-times"
                            @click="rejectOrder(order.publicId)" />
                  </div>

                  <div class="flex col-6">
                    <SplitButton
                      class="m-0 ml-auto p-0"
                      severity="info"
                      label="Status"
                      :model="statuses"
                      @click="setContextOrderId(order.publicId)"
                    ></SplitButton>
                  </div>

                </div>
              </template>
            </Card>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
