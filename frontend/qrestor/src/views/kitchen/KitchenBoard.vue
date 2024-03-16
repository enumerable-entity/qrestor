<script setup>
import { onBeforeMount, ref } from 'vue'
import OrdersService from '@/service/OrdersService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'
import KithchenBoardSSEservice from '@/service/KithchenBoardSSEservice.js'
import {useWaiterCallsStore} from '@/store.js'


const waiterCallsStore = useWaiterCallsStore()
const toast = useToast()
const { t } = useI18n()
const orders = ref([])
const orderDetailsDialog = ref(false)
const selectedOrder = ref(null)
const waiterRequests = ref([])

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
  if(event !== 'INIT_ORDERS' && event !== 'WAITER_CALL') {
    toast.add({
      severity: 'success',
      summary: 'New order received',
      detail: 'Order ID: ' + data.publicId,
      life: 6000
    })
  }
  if(event !== 'WAITER_CALL'){
    orders.value.unshift(data)
  }else{
    waiterRequests.value.push(data)
    waiterCallsStore.addWaiterCall(data)
  }
}

onBeforeMount(async () => {
  orders.value = []
  waiterRequests.value = waiterCallsStore.getCalls()
  boardSSEservice.connect(onSSEmessage)
})

const acceptWaiterCall = (tableNrr) => {
  console.log("waiter call accepted", tableNrr)
  waiterRequests.value = waiterRequests.value.filter(x => x.tableNr !== tableNrr)
  waiterCallsStore.remove(tableNrr)
  toast.add({
    severity: 'success',
    summary: 'Waiter Call accepted',
    detail: 'Table: ' + tableNrr,
    life: 3000
  })
}

const generateNodesFromItems = () => {
  const nodes = []
  selectedOrder.value.items.forEach((menuItem) => {
    const menuItemNode = {
      key: menuItem.menuItemId,
      label: menuItem.menuItemTitle,
      icon: 'pi pi-fw pi-inbox',
      children: []
    }
    const optionsNodes = []
    menuItemNode.children = menuItem.menuItemOptions.forEach((option) => {
      const optionNode = {
        key: option.publicId,
        label: option.optionTitle,
        icon: 'pi pi-fw pi-inbox',
        children: []
      }
      const positionsNodes = []
      option.optionPositions.forEach((position) => {
        const optionPositionNode = {
          key: position.publicId,
          label: position.optionTitle,
          icon: 'pi pi-fw pi-inbox'
        }
        positionsNodes.push(optionPositionNode)
      })
      optionNode.children = positionsNodes
      optionsNodes.push(optionNode)
    })
    menuItemNode.children = optionsNodes
    nodes.push(menuItemNode)
  })
  return nodes
}

const orderItemsNodes = ref()

const rejectOrder = () => {
  ordersService.changeOrderStatus(selectedOrder.value.publicId, 'CANCELLED')
  orders.value = orders.value.filter((val) => val.publicId !== selectedOrder.value.publicId)
  orderDetailsDialog.value = false
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
    command: () => {
      changeStatus('IN_PROGRESS')
    }
  },
  {
    label: 'Completed',
    icon: 'pi pi-check',
    command: () => {
      changeStatus('COMPLETED')
    }
  },
  {
    label: 'Canceled',
    icon: 'pi pi-times',
    command: () => {
      changeStatus('CANCELLED')
    }
  }
]

const changeStatus = (status) => {
  ordersService.changeOrderStatus(selectedOrder.value.publicId, status)
  orders.value = orders.value.map((val) => {
    if (val.publicId === selectedOrder.value.publicId) {
      val.status = status
    }
    return val
  })
  orderDetailsDialog.value = false
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

        <Card
          class ="mb-5 bg-orange-600 fadein animation-duration-2000 animation-iteration-infinite"
          v-if="!(waiterRequests.length === 0)"
        >
          <template #content>
            <div class="flex flex-wrap align-items-center justify-content-center	text-6xl" >
              Waiter request to tables:
                <Tag
                  @click=acceptWaiterCall(waiterRequest.tableNr)
                  class="text-6xl ml-4 cursor-pointer"
                  severity="info"
                  :value=waiterRequest.tableNr
                  v-for="waiterRequest in waiterRequests"
                  :key="waiterRequest.tableNr"/>

            </div>
          </template>

        </Card>

      <div class="card">

        <Toast />
        <div class="grid">
          <Card
            class="col-3"
            v-for="order in orders"
            :key="order.publicId"
          >
            <template #header>
              <div class="flex justify-content-center w-full" >
                <img alt="user header" src="https://picsum.photos/200/200" />
              </div>

            </template>
            <template #title>Order ID: {{order.publicId}}</template>
            <template #subtitle>Card subtitle</template>
            <template #content>
              <p class="m-0">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore sed consequuntur error repudiandae numquam deserunt quisquam repellat libero asperiores earum nam nobis, culpa ratione quam perferendis esse, cupiditate neque
                quas!
              </p>
            </template>
            <template #footer>
              <div class="flex gap-3 mt-1">
                <Button label="Cancel" severity="secondary" outlined class="w-full" />
                <Button label="Save" class="w-full" />
              </div>
            </template>
          </Card>
        </div>

        <Dialog
          v-model:visible="orderDetailsDialog"
          :style="{ width: '500px' }"
          :header="
            'Order details with ID: ' +
            (selectedOrder ? ' - ' + makeIdShorter(selectedOrder.publicId) : '')
          "
          :modal="true"
          class="p-fluid"
        >
          <Card>
            <template #content>
              <p class="m-0 p-0 text-xl">
                ID: <span class="text-xl">{{ selectedOrder.publicId }}</span>
              </p>
            </template>
          </Card>
          <Divider></Divider>
          <Card>
            <template #title>Selling Point Details</template>
            <template #content>
              <p class="m-0 p-0 text-xl">
                Name: <span class="text-base">{{ selectedOrder.restaurantName }}</span>
              </p>
              <p class="m-0 p-0 text-xl">
                Title: <span class="text-base">{{ selectedOrder.restaurantTitle }}</span>
              </p>
            </template>
          </Card>
          <Divider></Divider>
          <Card>
            <template #title>Order Details</template>
            <template #content>
              <p class="m-0 p-1 text-xl">
                Table number:
                <span class="text-base"
                  ><Tag class="m-0" :value="selectedOrder.tableNumber" severity="info"
                /></span>
              </p>
              <p class="m-0 p-1 text-xl">
                Status:
                <Tag
                  class="m-0"
                  :value="t(selectedOrder.status)"
                  :severity="getSeverity(selectedOrder.status)"
                />
              </p>
              <p class="m-0 p-1 text-xl">
                Items count:
                <Tag class="m-0" :value="selectedOrder.items.length" severity="info" />
              </p>
              <p class="m-0 p-1 text-xl">
                Online payment:
                <i
                  class="pi"
                  :class="{
                    'text-green-500 pi-check-circle': selectedOrder.paymentSelected,
                    'text-pink-500 pi-times-circle': !selectedOrder.paymentSelected
                  }"
                ></i>
              </p>
            </template>
          </Card>
          <Divider></Divider>
          <Card>
            <template #title>Order items</template>
            <template #content>
              <Tree :value="orderItemsNodes" class="w-full md:w-30rem"></Tree>
            </template>
          </Card>

          <template #footer>
            <div class="formgrid grid">
              <div class="field col-3">
                <Button severity="danger" label="Reject" icon="pi pi-times" @click="rejectOrder" />
              </div>
              <div class="field col-9">
                <SplitButton
                  severity="info"
                  label="Change status"
                  :model="statuses"
                  @click="saveMenu"
                ></SplitButton>
              </div>
            </div>
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
