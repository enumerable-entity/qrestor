<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import OrdersService from '@/service/OrdersService.js'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'
import { useRoute } from 'vue-router'

const route = useRoute()
const toast = useToast()
const { t } = useI18n()
const orders = ref(null)
const orderDetailsDialog = ref(false)
const deleteMenuDialog = ref(false)
const menu = ref({})
const selectedOrder = ref(null)
const dt = ref(null)
const submitted = ref(false)

const datesFromTo = ref([new Date(), new Date()])

const autoValueSellPoint = ref(null)
const selectedAutoValueSellPoint = ref(null)
const autoFilteredValueSellPoint = ref([])

const searchSellingPoint = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueSellPoint.value = [...autoValueSellPoint.value]
    } else {
      autoFilteredValueSellPoint.value = autoValueSellPoint.value.filter((restaurant) => {
        return restaurant.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const filters1 = ref(null)
const loading1 = ref(null)

const initFilters1 = () => {
  filters1.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    name: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    description: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    restaurantId: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },

    isActive: { value: null, matchMode: FilterMatchMode.EQUALS }
  }
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

const isHistoryWindow = ref(route.params.history)

const ordersService = new OrdersService()
const sellingPointService = new SellingPointsService()

onBeforeMount(async () => {
  initFilters1()
  const { data } = await ordersService.getOrdersHistoryByDates(
    datesFromTo.value[0].toISOString().slice(0, 10),
    datesFromTo.value[1].toISOString().slice(0, 10)
  )
  //const dictResponse = await sellingPointService.getSellingPointsDictionary()
  //autoValueSellPoint.value = dictResponse.data
  orders.value = data.content
  loading1.value = false
})

const clearFilter1 = () => {
  initFilters1()
}

const hideDialog = () => {
  orderDetailsDialog.value = false
  submitted.value = false
}

const saveMenu = async () => {
  submitted.value = true
  menu.value.restaurantId = selectedAutoValueSellPoint.value.id

  if (menu.value.publicId) {
    const { data } = await ordersService.updateMenu(menu.value)
    orderDetailsDialog.value = false
    const index = orders.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      orders.value[index] = data
    }
  } else {
    const { data } = await ordersService.addMenu(menu.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Menu saved',
      life: 3000
    })
    orderDetailsDialog.value = false
    orders.value.push(data)
  }
  menu.value = {}
  selectedAutoValueSellPoint.value = null
}

const confirmDeleteMenu = (editMenu) => {
  menu.value = editMenu
  deleteMenuDialog.value = true
}

const deleteMenu = async () => {
  const { status } = await ordersService.deleteMenu(menu.value.publicId)
  if (status === 204) {
    orders.value = orders.value.filter((val) => val.publicId !== menu.value.publicId)
    deleteMenuDialog.value = false
    menu.value = {}
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Menu deleted',
      life: 3000
    })
  }
}

const makeIdShorter = (id) => {
  return id.substring(0, 8) + ' ...'
}

const onRowSelect = (event) => {
  orderDetailsDialog.value = true
  selectedOrder.value = { ...event.data }
}

const statuses = [
  { label: 'Payment in progress', icon: 'pi pi-check', command: () => {} },
  { label: 'Confirmed', icon: 'pi pi-check', command: () => {
      console.log('Confirmed')
    } },
  { label: 'Canceled', icon: 'pi pi-check', command: () => {} }
]

const onDateSelect = (event) => {
  if (event !== undefined) {
    const selectedDateFrom = event[0]
    const selectedDateTo = event[1]
    if (selectedDateFrom && selectedDateTo) {
      datesFromTo.value[0] = selectedDateFrom
      datesFromTo.value[1] = selectedDateTo
      ordersService
        .getOrdersHistoryByDates(
          datesFromTo.value[0].toISOString().slice(0, 10),
          datesFromTo.value[1].toISOString().slice(0, 10)
        )
        .then((response) => {
          orders.value = response.data.content
        })
    }
  }
}
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <DataTable
          ref="dt"
          :value="orders"
          v-model:selection="selectedOrder"
          selectionMode="single"
          @rowSelect="onRowSelect"
          v-model:filters="filters1"
          dataKey="publicId"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          :rows="10"
          :globalFilterFields="['name', 'description', 'restaurantId']"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} menus"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">Orders history</h5>
              <Calendar
                v-model="datesFromTo"
                @update:modelValue="onDateSelect"
                selectionMode="range"
                :manualInput="false"
                hideOnRangeSelection
                showIcon
                dateFormat="dd/mm/yy"
              />
              <Button
                type="button"
                icon="pi pi-filter-slash"
                label="Clear"
                class="p-button-outlined mb-2"
                @click="clearFilter1()"
              />
              <span class="block mt-2 md:mt-0 p-input-icon-left">
                <i class="pi pi-search" />
                <InputText v-model="filters1['global'].value" placeholder="Search..." />
              </span>
            </div>
          </template>
          <template #empty> No orders found.</template>
          <template #loading> Loading orders data. Please wait.</template>

          <Column field="publicId" header="Id" headerStyle="width:10%; min-width:10rem;">
            <template #body="slotProps">
              <span class="p-column-title">Id</span>
              {{ makeIdShorter(slotProps.data.publicId) }}
            </template>
          </Column>
          <Column
            field="restaurantName"
            header="Selling point name"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Selling point name</span>
              {{ slotProps.data.restaurantName }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by name"
              />
            </template>
          </Column>
          <Column
            field="restaurantTitle"
            header="Selling point title"
            :sortable="true"
            headerStyle="width:12%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.restaurantTitle }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by name"
              />
            </template>
          </Column>
          <Column
            field="tableNumber"
            header="Table number"
            :sortable="true"
            headerStyle="width:9%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Table number</span>
              {{ slotProps.data.tableNumber }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by description"
              />
            </template>
          </Column>
          <Column
            field="status"
            header="Status"
            :sortable="true"
            headerStyle="width:9%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Table number</span>
              <Tag :value="t(slotProps.data.status)" :severity="getSeverity(slotProps.data.status)" />
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by description"
              />
            </template>
          </Column>

          <Column
            field="items"
            header="Items count"
            :sortable="true"
            headerStyle="width:9%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Items count</span>
              {{ slotProps.data.items.length }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by description"
              />
            </template>
          </Column>

          <Column
            field="paymentSelected"
            :sortable="true"
            header="Online payment"
            dataType="boolean"
            style="min-width: 3rem; width: 13%"
          >
            <template #body="{ data }">
              <i
                class="pi"
                :class="{
                  'text-green-500 pi-check-circle': data.paymentSelected,
                  'text-pink-500 pi-times-circle': !data.paymentSelected
                }"
              ></i>
            </template>
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>
        </DataTable>

        <Dialog
          v-model:visible="orderDetailsDialog"
          :style="{ width: '450px' }"
          :header="
            'Order details with ID: ' +
            (selectedOrder ? ' - ' + makeIdShorter(selectedOrder.publicId) : '')
          "
          :modal="true"
          class="p-fluid"
        >
          <Card>
            <template #content>
              <p class="m-0 p-0 text-xl">ID: <span class="text-xl">{{selectedOrder.publicId}}</span></p>
            </template>
          </Card>
          <Divider></Divider>
          <Card>
            <template #title>Selling Point Details</template>
            <template #content >
              <p class="m-0 p-0 text-xl">Name: <span class="text-base">{{selectedOrder.restaurantName}}</span></p>
              <p class="m-0 p-0 text-xl">Title: <span class="text-base">{{selectedOrder.restaurantTitle}}</span></p>
            </template>
          </Card>
          <Divider></Divider>
          <Card>
            <template #title>Order Details</template>
            <template #content>
              <p class="m-0 p-1 text-xl">Table number: <span class="text-base"><Tag class="m-0" :value="selectedOrder.tableNumber" severity="info"/></span></p>
              <p class="m-0 p-1 text-xl">Status: <Tag class="m-0" :value="t(selectedOrder.status)" :severity="getSeverity(selectedOrder.status)"/></p>
              <p class="m-0 p-1 text-xl">Items count: <Tag class="m-0" :value="selectedOrder.items.length" severity="info"/></p>
              <p class="m-0 p-1 text-xl">Online payment: <i class="pi" :class="{'text-green-500 pi-check-circle': selectedOrder.paymentSelected, 'text-pink-500 pi-times-circle': !selectedOrder.paymentSelected}"></i></p>
            </template>
          </Card>

          <template #footer>
            <div class="formgrid grid">
              <div class="field col-3">
                <Button severity="danger" label="Reject" icon="pi pi-times" @click="hideDialog" />
              </div>
              <div class="field col-9">
                <SplitButton severity="info" label="Change status" :model="statuses" @click="saveMenu"></SplitButton>
              </div>
            </div>
          </template>
        </Dialog>

        <Dialog
          v-model:visible="deleteMenuDialog"
          :style="{ width: '450px' }"
          header="Confirm"
          :modal="true"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span v-if="menu"
            >Are you sure you want to delete <b>{{ menu.name }}</b
            >?</span
            >
          </div>
          <template #footer>
            <Button
              label="No"
              icon="pi pi-times"
              class="p-button-text"
              @click="deleteMenuDialog = false"
            />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteMenu" />
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
