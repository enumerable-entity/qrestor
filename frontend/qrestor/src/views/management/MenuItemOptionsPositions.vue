<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import OptionsPositionsService from '@/service/OptionsPositionsService.js'
import MenuItemsOptionsService from '@/service/MenuItemsOptionsService.js'
import { useToast } from 'primevue/usetoast'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useUserStore } from '@/store.js'

const { t } = useI18n()
const toast = useToast()
const route = useRoute()

const menuItemOptions = ref(null)
const optionPosition = ref({})
const menuDialog = ref(false)
const deleteMenuDialog = ref(false)
const selectedMenu = ref(null)
const dt = ref(null)
const submitted = ref(false)

const autoValueOption = ref(null)
const selectedAutoValueSMenu = ref(null)
const autoFilteredValueMenu = ref([])

const searchMenuItem = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueMenu.value = [...autoValueOption.value]
    } else {
      autoFilteredValueMenu.value = autoValueOption.value.filter((menuItem) => {
        return menuItem.title.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const itemOptionContextId = ref(route.params.optionId)
const optionContextName = ref(null)

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

// SERVICES INSTANCES
const menuItemsOptionsService = new MenuItemsOptionsService()
const optionPositionsService = new OptionsPositionsService()

onBeforeMount(async () => {
  initFilters1()

  if (itemOptionContextId.value) {
    const { data } = await optionPositionsService.getOptionsPositionsForOptionId(itemOptionContextId.value)
    menuItemOptions.value = data
    optionContextName.value = data.length > 0 ? data[0].itemOptionTitle : ''
  } else {
    const { data } = await optionPositionsService.getAllOptionsPositions()
    const { data: positionData } = await menuItemsOptionsService.getAllMenuItemsOptions()
    menuItemOptions.value = data
    autoValueOption.value = positionData

  }
   loading1.value = false
})

const clearFilter1 = () => {
  initFilters1()
}

const openNew = () => {
  optionPosition.value = {
    title: '',
    price: 0,
    menuItemOptionPositions: [],
    itemOptionId: { publicId: itemOptionContextId.value },
    enabled: false

  }

  submitted.value = false
  menuDialog.value = true
}

const hideDialog = () => {
  menuDialog.value = false
  submitted.value = false
}

const routemenu = ref(null)

const items = [
  {label: "Empty context", route: 'MenuItemsOptions', icon: 'pi pi-fw pi-pencil'}
]

const onRowContextMenu = (event) => {
  routemenu.value.show(event.originalEvent)
  console.log(event)
}

const saveMenuItemOption = async () => {
  submitted.value = true
  optionPosition.value.itemOptionId = selectedAutoValueSMenu.value.publicId || itemOptionContextId.value

  if (optionPosition.value.publicId) {
    const { data } = await optionPositionsService.updateOptionPosition(optionPosition.value)
    menuDialog.value = false
    const index = menuItemOptions.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      menuItemOptions.value[index] = data
    }
  } else {
    const { data } = await optionPositionsService.addOptionPosition(optionPosition.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'New option position saved',
      life: 3000
    })
    menuDialog.value = false
    menuItemOptions.value.push(data)
  }
  optionPosition.value = {}
  selectedAutoValueSMenu.value = null
  submitted.value = false
}
const editMenuItem = (editPosition) => {
  selectedAutoValueSMenu.value = {publicId: editPosition.itemOptionId, title: editPosition.itemOptionTitle}
  optionPosition.value = { ...editPosition }
  menuDialog.value = true
}

const confirmDeleteMenuItem = (editMenu) => {
  optionPosition.value = editMenu
  deleteMenuDialog.value = true
}

const openDetailsDialog = (menuItem) => {
  menuItem.value = menuItem
  menuDialog.value = true
}

const deleteMenuItem = async () => {
  const { status } = await optionPositionsService.deleteOptionPosition(optionPosition.value.publicId)
  if (status === 204) {
    menuItemOptions.value = menuItemOptions.value.filter((val) => val.publicId !== optionPosition.value.publicId)
    deleteMenuDialog.value = false
    optionPosition.value = {}
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Option position deleted',
      life: 3000
    })
  }
}

const makeIdShorter = (id) => {
  return id.substring(0, 8) + ' ...'
}

const exportCSV = () => {
  dt.value.exportCSV()
}

// ROW SELECT
const onMenuRowSelect = (event) => {
  toast.add({
    severity: 'info',
    summary: 'Menu Selected',
    detail: event.data.name,
    life: 3000
  })
}

const formatCurrencyForAPI = (value) => {
  return value * 10
}
const formatCurrency = (value) => {
  const price = value / 10
  return price.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
}

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <Toolbar class="mb-4">
          <template v-slot:start>
            <div class="my-2">
              <Button
                :label="t('button.new')"
                icon="pi pi-plus"
                class="p-button-success mr-2"
                @click="openNew"
              />
            </div>
          </template>
          <template v-slot:end>
            <FileUpload
              mode="basic"
              accept="image/*"
              url="localhost:8080/menu/management/menu-items"
              :maxFileSize="1000000"
              label="Import"
              chooseLabel="Import"
              class="mr-2 inline-block"
            />
            <Button
              label="Export"
              icon="pi pi-upload"
              class="p-button-help"
              @click="exportCSV($event)"
            />
          </template>
        </Toolbar>

        <DataTable
          ref="dt"
          :value="menuItemOptions"
          v-model:selection="selectedMenu"
          v-model:contextMenuSelection="selectedMenu"
          selectionMode="single"
          @rowSelect="onMenuRowSelect"
          v-model:filters="filters1"
          dataKey="publicId"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          @row-contextmenu="onRowContextMenu"
          contextMenu
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
              <h5 class="m-0">
                Manage options positions <span v-if="itemOptionContextId">for option: </span>
                <Tag
                  v-if="itemOptionContextId"
                  class="ml-2 text-lg"
                  severity="info"
                  :value="optionContextName"
                ></Tag>
              </h5>
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
          <template #empty> No options positions found.</template>
          <template #loading> Loading options positions data. Please wait.</template>

          <Column field="publicId" header="Id" headerStyle="width:20%; min-width:10rem;">
            <template #body="slotProps">
              <span class="p-column-title">Id</span>
              {{ makeIdShorter(slotProps.data.publicId) }}
            </template>
          </Column>

          <Column
            field="title"
            header="Title"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Title</span>
              {{ slotProps.data.title }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by title"
              />
            </template>
          </Column>

          <Column field="price" header="Price" headerStyle="width:10%; min-width:10rem;">
            <template #body="slotProps">
              <span class="p-column-title">Price</span>
              {{ formatCurrency(slotProps.data.price) }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by price"
              />
            </template>
          </Column>

          <Column
            field="isActive"
            :sortable="true"
            header="Is Active"
            dataType="boolean"
            headerStyle="width:10%;"
            style="min-width: 3rem"
          >
            <template #body="{ data }">
              <i
                class="pi"
                :class="{
                  'text-green-500 pi-check-circle': data.enabled,
                  'text-pink-500 pi-times-circle': !data.enabled
                }"
              ></i>
            </template>
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>
          <Column
            field="itemOptionTitle"
            header="Item option title"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Item option title</span>
              {{ slotProps.data.itemOptionTitle }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by title"
              />
            </template>
          </Column>

          <Column headerStyle="min-width:12rem; width:25%;" header="Actions">
            <template #body="slotProps">
              <Button
                text
                icon="pi pi-pencil"
                class="p-button-rounded p-button-success mr-2"
                @click="editMenuItem(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-trash"
                class="p-button-rounded p-button-warning mt-2 mr-2"
                @click="confirmDeleteMenuItem(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-info-circle"
                class="p-button-rounded p-button-warning mt-2"
                @click="openDetailsDialog(slotProps.data)"
              />
            </template>
          </Column>

        </DataTable>

        <ContextMenu ref="routemenu" :model="items" @hide="selectedMenu = null" class="w-2">
          <template #item="{ item, props }">
            <router-link v-if="item.route" v-slot="{ href, navigate }" :to="item.route" custom>
              <a v-ripple :href="href" v-bind="props.action" @click="navigate">
                <span :class="item.icon" />
                <span class="ml-2">{{ item.label }}</span>
              </a>
            </router-link>
            <a v-else v-ripple :href="item.url" :target="item.target" v-bind="props.action">
              <span :class="item.icon" />
              <span class="ml-2">{{ item.label }}</span>
            </a>
          </template>
        </ContextMenu>


        <Dialog
          v-model:visible="menuDialog"
          :style="{ width: '450px' }"
          header="Option position details"
          :modal="true"
          class="p-fluid"
        >
          <div class="field">
            <label for="title">Title</label>
            <InputText
              id="title"
              v-model.trim="optionPosition.title"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !optionPosition.title }"
            />
            <small class="p-invalid" v-if="submitted && !optionPosition.title">Title is required.</small>
          </div>

          <div class="field">
            <label for="price">Price</label>
            <InputNumber v-model="optionPosition.price"
                         id = "price"
                         inputId="currency-us"
                         mode="currency"
                         showButtons
                         :currency="useUserStore().getUserCurrency()"
                         :locale="useUserStore().getUserLocale()" />
          </div>

          <div class="field" v-if="!itemOptionContextId">
            <label for="currency">Select Option for this position</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !optionPosition.itemOptionId }"
              required="true"
              autofocus
              placeholder="Search"
              id="currency"
              :dropdown="true"
              :multiple="false"
              v-model="selectedAutoValueSMenu"
              :suggestions="autoFilteredValueMenu"
              @complete="searchMenuItem($event)"
              field="title"
            />
            <small class="p-invalid" v-if="submitted && !optionPosition.itemOptionId"
              >Selling point is required.</small>
          </div>
          <div class="field-checkbox mb-0">
            <Checkbox
              id="active"
              name="active"
              value="true"
              v-model="optionPosition.enabled"
              :binary="true"
            />
            <label for="active" class = "mr-3">Is Active</label>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveMenuItemOption" />
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
            <span v-if="optionPosition"
              >Are you sure you want to delete <b>{{ optionPosition.name }}</b
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
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteMenuItem" />
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
