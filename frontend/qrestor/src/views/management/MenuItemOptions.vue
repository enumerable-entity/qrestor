<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import MenuItemsOptionsService from '@/service/MenuItemsOptionsService.js'
import MenuItemsService from '@/service/MenuItemsService.js'
import IngredientsService from '@/service/IngredientsService.js'
import { useToast } from 'primevue/usetoast'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
const toast = useToast()
const route = useRoute()

const menuItemOptions = ref(null)
const qrMapping = ref({})
const menuDialog = ref(false)
const deleteMenuDialog = ref(false)
const selectedMenu = ref(null)
const dt = ref(null)
const submitted = ref(false)

const autoValueMenu = ref(null)
const selectedAutoValueSMenu = ref(null)
const autoFilteredValueMenu = ref([])

const searchMenuItem = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueMenu.value = [...autoValueMenu.value]
    } else {
      autoFilteredValueMenu.value = autoValueMenu.value.filter((menuItem) => {
        return menuItem.title.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const autoValueIngredient = ref(null)
const selectedAutoValueIngredients = ref([])
const autoFilteredValueIngredient= ref([])

const searchIngredients = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueIngredient.value = [...autoValueIngredient.value]
    } else {
      autoFilteredValueIngredient.value = autoValueIngredient.value.filter((ingredient) => {
        return ingredient.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}


const menuItemContextId = ref(route.params.menuItemId)
const menuContextName = ref(null)

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
const menuItemsService = new MenuItemsService()
const ingredientsService = new IngredientsService()
const menuItemsOptionsService = new MenuItemsOptionsService()

onBeforeMount(async () => {
  initFilters1()

  if (menuItemContextId.value) {
    const { data } = await menuItemsOptionsService.getMenuItemsOptionsForMenuItemId(menuItemContextId.value)
    menuItemOptions.value = data
    menuContextName.value = data.length > 0 ? data[0].menuItemTitle : ''
  } else {
    const { data } = await menuItemsOptionsService.getAllMenuItemsOptions()
    const { data: menuData } = await menuItemsService.getAllMenuItems()
    menuItemOptions.value = data
    autoValueMenu.value = menuData

  }
   const dictResponse = await ingredientsService.getIngredients()
   autoValueIngredient.value = dictResponse.data
   loading1.value = false
})

const clearFilter1 = () => {
  initFilters1()
}

const openNew = () => {
  menuItemOption.value = {
    title: '',
    menuItemOptionPositions: [],
    menuItemId: { publicId: menuItemContextId.value },
    required: false,
    enabled: false,
    multiSelect: false
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
  {label: "Manage option positions", route: 'menu-item-options-positions', icon: 'pi pi-fw pi-pencil'}
]

const onRowContextMenu = (event) => {
  routemenu.value.show(event.originalEvent)
  console.log(event)
}

const saveMenuItemOption = async () => {
  submitted.value = true
  menuItemOption.value.menuItemOptionPositions = selectedAutoValueIngredients.value
  menuItemOption.value.menuItemId = selectedAutoValueSMenu.value.publicId || menuItemContextId.value

  if (menuItemOption.value.publicId) {
    const { data } = await menuItemsOptionsService.updateMenuItemOption(menuItemOption.value)
    menuDialog.value = false
    const index = menuItemOptions.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      menuItemOptions.value[index] = data
    }
  } else {
    const { data } = await menuItemsOptionsService.addMenuItemOption(menuItemOption.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'New Menu Item Option saved',
      life: 3000
    })
    menuDialog.value = false
    menuItemOptions.value.push(data)
  }
  menuItemOption.value = {}
  selectedAutoValueSMenu.value = null
  selectedAutoValueIngredients.value = null
  submitted.value = false
}
const editMenuItem = (editMenu) => {
  selectedAutoValueIngredients.value = editMenu.menuItemOptionPositions
  selectedAutoValueSMenu.value = {publicId: editMenu.menuItemId, title: editMenu.menuItemTitle}

  menuItemOption.value = { ...editMenu }
  menuDialog.value = true
}

const confirmDeleteMenuItem = (editMenu) => {
  menuItemOption.value = editMenu
  deleteMenuDialog.value = true
}

const openDetailsDialog = (menuItem) => {
  menuItem.value = menuItem
  menuDialog.value = true
}

const deleteMenuItem = async () => {
  const { status } = await menuItemsOptionsService.deleteMenuItemOption(menuItemOption.value.publicId)
  if (status === 204) {
    menuItemOptions.value = menuItemOptions.value.filter((val) => val.publicId !== menuItemOption.value.publicId)
    deleteMenuDialog.value = false
    menuItemOption.value = {}
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
                Manage menu items options <span v-if="menuItemContextId">for menu item: </span>
                <Tag
                  v-if="menuItemContextId"
                  class="ml-2 text-lg"
                  severity="info"
                  :value="menuContextName"
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
          <template #empty> No menu items options found.</template>
          <template #loading> Loading menu items options data. Please wait.</template>

          <Column field="publicId" header="Id" headerStyle="width:10%; min-width:10rem;">
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
            field="required"
            :sortable="true"
            header="Is required"
            dataType="boolean"
            headerStyle="width:11%"
            style="min-width: 4rem"
          >
            <template #body="{ data }">
              <i
                class="pi"
                :class="{
                  'text-green-500 pi-check-circle': data.required,
                  'text-pink-500 pi-times-circle': !data.required
                }"
              ></i>
            </template>
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>

          <Column
            field="multiSelect"
            :sortable="true"
            header="Is multiselect"
            dataType="boolean"
            headerStyle="width:12%;"
            style="min-width: 3rem"
          >
            <template #body="{ data }">
              <i
                class="pi"
                :class="{
                  'text-green-500 pi-check-circle': data.multiSelect,
                  'text-pink-500 pi-times-circle': !data.multiSelect
                }"
              ></i>
            </template>
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>


          <Column
            field="menuItemOptionPositions"
            header="Positions"
            :sortable="false"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Positions</span>
              <Tag
                v-for="attr in slotProps.data.menuItemOptionPositions"
                class="m-1"
                v-bind:key="attr.publicId"
                :value="attr.title"
                :rounded="false"
                severity="success"
              ></Tag>
            </template>
          </Column>

          <Column
            v-if="!menuItemContextId"
            field="menuItemTitle"
            header="Menu item"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Menu item</span>
              {{ slotProps.data.menuItemTitle }}
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
            <router-link v-if="item.route" v-slot="{ href, navigate }" :to="{name: item.route, params: {optionId: selectedMenu.publicId } }" custom>
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
          header="Menu item option details"
          :modal="true"
          class="p-fluid"
        >
          <div class="field">
            <label for="title">Title</label>
            <InputText
              id="title"
              v-model.trim="menuItemOption.title"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !menuItemOption.title }"
            />
            <small class="p-invalid" v-if="submitted && !menuItemOption.title">Title is required.</small>
          </div>

          <div class="field">
            <label for="ingredientsSelect">Select a few positions</label>
            <AutoComplete
              id="ingredientsSelect"
              v-model="selectedAutoValueIngredients"
              :class="{ 'p-invalid': submitted && !menuItemOption.menuItemOptionPositions }"
              optionLabel="name"
              multiple
              :suggestions="autoFilteredValueIngredient"
              @complete="searchIngredients"
            />
            <small class="p-invalid" v-if="submitted && !menuItemOption.menuItemOptionPositions"
            >Few ingredients are required.</small>
          </div>


          <div class="field" v-if="!menuItemContextId">
            <label for="currency">Select Menu Item for this Option</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !menuItemOption.menuItemId }"
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
            <small class="p-invalid" v-if="submitted && !menuItemOption.menuItemId"
              >Selling point is required.</small>
          </div>
          <div class="field-checkbox mb-0">
            <Checkbox
              id="active"
              name="active"
              value="true"
              v-model="menuItemOption.enabled"
              :binary="true"
            />
            <label for="active" class = "mr-3">Is Active</label>

            <Checkbox
              id="required"
              name="required"
              value="true"
              v-model="menuItemOption.multiSelect"
              :binary="true"
            />
            <label for="required" class = "mr-3">Is multiselect</label>

            <Checkbox
              id="multiSelect"
              name="required"
              value="true"
              v-model="menuItemOption.required"
              :binary="true"
            />
            <label for="multiSelect" >Is required</label>
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
            <span v-if="menuItemOption"
              >Are you sure you want to delete <b>{{ qrMapping.name }}</b
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
