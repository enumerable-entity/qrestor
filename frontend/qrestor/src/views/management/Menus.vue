<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import MenuService from '@/service/MenuService.js'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'
import router from '@/router/index.js'

const toast = useToast()

const menus = ref(null)
const menuDialog = ref(false)
const deleteMenuDialog = ref(false)
const deleteMenusDialog = ref(false)
const menu = ref({})
const selectedMenu = ref(null)
const dt = ref(null)
const submitted = ref(false)

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
    }
  }
}

const menuService = new MenuService()
const sellingPointService = new SellingPointsService()

onBeforeMount(async () => {
  initFilters1();
  const { data } = await menuService.getMenus()
  const dictResponse = await sellingPointService.getSellingPointsDictionary()
  autoValueSellPoint.value = dictResponse.data
  menus.value = data
  loading1.value = false;
})

const clearFilter1 = () => {
  initFilters1();
};

const openNew = () => {
  menu.value = {}
  menu.value.isActive = false
  submitted.value = false
  menuDialog.value = true
}

const hideDialog = () => {
  menuDialog.value = false
  submitted.value = false
}

const saveMenu = async () => {
  submitted.value = true
  menu.value.restaurantId = selectedAutoValueSellPoint.value.id

  if (menu.value.publicId) {
    const { data } = await menuService.updateMenu(menu.value)
    menuDialog.value = false
    const index = menus.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      menus.value[index] = data
    }
  } else {
    const { data } = await menuService.addMenu(menu.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Menu saved',
      life: 3000
    })
    menuDialog.value = false
    menus.value.push(data)
  }
  menu.value = {}
  selectedAutoValueSellPoint.value = null
}
const editMenu = (editMenu) => {
  menu.value = { ...editMenu }
  menuDialog.value = true
}

const confirmDeleteMenu = (editMenu) => {
  menu.value = editMenu
  deleteMenuDialog.value = true
}

const deleteMenu = async () => {
  const { status } = await menuService.deleteMenu(menu.value.publicId)
  if (status === 204) {
    menus.value = menus.value.filter((val) => val.publicId !== menu.value.publicId)
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

const exportCSV = () => {
  dt.value.exportCSV()
}

const deleteSelectedMenus = () => {
  menus.value = menus.value.filter((val) => !selectedMenu.value.includes(val))
  deleteMenusDialog.value = false
  selectedMenu.value = null
  toast.add({
    severity: 'success',
    summary: 'Successful',
    detail: 'Menus was deleted',
    life: 3000
  })
}

// ROW SELECT
const onRowSelect = (event) => {
  router.push("/management/menus/" + event.data.publicId + "/menu-items")
  selectedMenu.value = {}
}

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <Toolbar >
          <template v-slot:start>
            <div class="my-2">
              <Button
                label="New"
                icon="pi pi-plus"
                class="p-button-success mr-2"
                @click="openNew"
              />
            </div>
          </template>
          <template v-slot:end>
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
          :value="menus"
          v-model:selection="selectedMenu"
          selectionMode="single"
          @rowSelect="onRowSelect"
          v-model:filters="filters1"
          dataKey="publicId"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          :rows="5"
          :globalFilterFields="['name', 'description']"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} menus"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">Manage menus</h5>
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
          <template #empty> No menus found. </template>
          <template #loading> Loading menus data. Please wait. </template>
          <Column
            field="publicId"
            header="Id"
            headerStyle="width:10%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Id</span>
              {{ makeIdShorter(slotProps.data.publicId) }}
            </template>
          </Column>
          <Column
            field="name"
            header="Title"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.name }}
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
          <Column
            field="description"
            header="Description"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Description</span>
              {{ slotProps.data.description }}
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
            field="restaurantId"
            header="Selling Point ID"
            headerStyle="width:23%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.restaurantId }}
            </template>
          </Column>
          <Column
            field="isActive"
            :sortable="true"
            header="Is Active"
            dataType="boolean"
            style="min-width: 3rem"
          >
            <template #body="{ data }">
              <i
                class="pi"
                :class="{
                  'text-green-500 pi-check-circle': data.isActive,
                  'text-pink-500 pi-times-circle': !data.isActive
                }"
              ></i>
            </template>
          </Column>
          <Column headerStyle="min-width:10rem;" header="Actions">
            <template #body="slotProps">
              <Button
                text
                icon="pi pi-pencil"
                class="p-button-rounded p-button-success mr-2"
                @click="editMenu(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-trash"
                class="p-button-rounded p-button-warning mt-2"
                @click="confirmDeleteMenu(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>

        <Dialog
          v-model:visible="menuDialog"
          :style="{ width: '450px' }"
          header="Menu details"
          :modal="true"
          class="p-fluid"
        >
          <div class="field">
            <label for="name">Title</label>
            <InputText
              id="name"
              v-model.trim="menu.name"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !menu.name }"
            />
            <small class="p-invalid" v-if="submitted && !menu.name">Name is required.</small>
          </div>
          <div class="field">
            <label for="name">Description</label>
            <InputText
              id="name"
              v-model.trim="menu.description"
              required="true"
              autofocus
            />

          </div>
          <div class="field ">
            <label for="currency">Select selling point for this Menu</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !menu.restaurantId }"
              required="true"
              autofocus
              placeholder="Search"
              id="currency"
              :dropdown="true"
              :multiple="false"
              v-model="selectedAutoValueSellPoint"
              :suggestions="autoFilteredValueSellPoint"
              @complete="searchSellingPoint($event)"
              field="name"
            />
            <small class="p-invalid" v-if="submitted && !menu.restaurantId">Selling point is required.</small>
          </div>
         
              <div class="field-checkbox mb-0">
                <Checkbox id="terms" name="option" value=true v-model="menu.isActive"  :binary="true" />
                <label for="terms">Is Active</label>
              </div>
            

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveMenu" />
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
