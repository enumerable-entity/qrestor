<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import MenuItemsService from '@/service/MenuItemsService.js'
import MenuService from '@/service/MenuService.js'
import IngredientsService from '@/service/IngredientsService.js'
import { useToast } from 'primevue/usetoast'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useUserStore } from '@/store.js'

const { t } = useI18n()
const toast = useToast()
const route = useRoute()

const menuItems = ref(null)
const menuItem = ref({})
const categories = ref(null)
const menuDialog = ref(false)
const deleteMenuDialog = ref(false)
const selectedMenu = ref(null)
const dt = ref(null)
const submitted = ref(false)
const selectedAdditionalItemProps = ref([])
const selectedCategoryId = ref(null)

const autoValueMenu = ref(null)
const selectedAutoValueSMenu = ref(null)
const autoFilteredValueMenu = ref([])

const searchMenu = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueMenu.value = [...autoValueMenu.value]
    } else {
      autoFilteredValueMenu.value = autoValueMenu.value.filter((restaurant) => {
        return restaurant.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const autoValueIngredient = ref(null)
const selectedAutoValueIngredients = ref(null)
const autoFilteredValueIngredient = ref([])

const searchIngredients = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueIngredient.value = [...autoValueIngredient.value]
    } else {
      autoFilteredValueIngredient.value = autoValueIngredient.value.filter((restaurant) => {
        return restaurant.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const menuContextId = ref(route.params.menuId)
const menuContextName = ref(null)

const filters1 = ref(null)
const loading1 = ref(null)

const initFilters1 = () => {
  filters1.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    title: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    description: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    itemCategory: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }]
    },

    isActive: { value: null, matchMode: FilterMatchMode.EQUALS }
  }
}

// SERVICES INSTANCES
const menuService = new MenuService()
const ingredientsService = new IngredientsService()
const menuItemsService = new MenuItemsService()

onBeforeMount(async () => {
  initFilters1()
  const { data } = await menuItemsService.getAllCategories()
  // replace nlskey with the actual translation
  data.forEach((el) => {
    el.nlsKey = t(el.nlsKey)
  })
  categories.value = data

  if (menuContextId.value) {
    const { data } = await menuItemsService.getMenuItemsForMenuId(menuContextId.value)
    menuItems.value = data
    menuContextName.value = data.length > 0 ? data[0].menu.name : ''
  } else {
    const { data } = await menuItemsService.getAllMenuItems()
    const { data: menuData } = await menuService.getMenus()
    menuItems.value = data
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
  menuItem.value = {
    title: '',
    menu: { publicId: menuContextId.value },
    description: '',
    itemCategory: { publicId: '' },
    price: 0,
    imageUrl: '',
    enabled: false
  }
  submitted.value = false
  menuDialog.value = true
}

const hideDialog = () => {
  menuDialog.value = false
  submitted.value = false
}

const fillAdditionalProps = () => {
  const selected = selectedAdditionalItemProps.value.map((el) => el.value)

  menuItem.value.isVegetarian = selected.includes('isVegetarian')
  menuItem.value.isVegan = selected.includes('isVegan')
  menuItem.value.isGlutenFree = selected.includes('isGlutenFree')
  menuItem.value.isSpicy = selected.includes('isSpicy')
  menuItem.value.isHalal = selected.includes('isHalal')
  menuItem.value.isKosher = selected.includes('isKosher')
  menuItem.value.isPeanuts = selected.includes('isPeanuts')
  menuItem.value.isTreeNuts = selected.includes('isTreeNuts')
  menuItem.value.isDairy = selected.includes('isDairy')
  menuItem.value.isEggs = selected.includes('isEggs')
  menuItem.value.isShellfish = selected.includes('isShellfish')
  menuItem.value.isSoy = selected.includes('isSoy')
}

const routemenu = ref(null)

const items = [
  { label: 'Manage item options', route: 'menu-item-options', icon: 'pi pi-fw pi-pencil' }
]

const onRowContextMenu = (event) => {
  routemenu.value.show(event.originalEvent)
}

const saveMenuItem = async () => {
  submitted.value = true
  menuItem.value.itemCategory.publicId = selectedCategoryId.value
  menuItem.value.ingredients = selectedAutoValueIngredients.value
  menuItem.value.menu = selectedAutoValueSMenu.value || menuContextId.value
  const price = menuItem.value.price
  menuItem.value.price = formatCurrencyForAPI(price)
  fillAdditionalProps()

  if (menuItem.value.publicId) {
    const { data } = await menuItemsService.updateMenuItem(menuItem.value)
    menuDialog.value = false
    const index = menuItems.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      menuItems.value[index] = data
    }
  } else {
    const { data } = await menuItemsService.addMenuItem(menuItem.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'New Menu Item saved',
      life: 3000
    })
    menuDialog.value = false
    menuItems.value.push(data)
  }
  menuItem.value = {}
  selectedAutoValueSMenu.value = null
  selectedAutoValueIngredients.value = null
  submitted.value = false
}
const editMenuItem = (editMenu) => {
  selectedCategoryId.value = editMenu.itemCategory.publicId
  selectedAutoValueIngredients.value = editMenu.ingredients
  selectedAutoValueSMenu.value = editMenu.menu
  menuItem.value = { ...editMenu }
  menuDialog.value = true
}

const confirmDeleteMenuItem = (editMenu) => {
  menuItem.value = editMenu
  deleteMenuDialog.value = true
}

const openDetailsDialog = (menuItem) => {
  menuItem.value = menuItem
  menuDialog.value = true
}

const deleteMenuItem = async () => {
  const { status } = await menuItemsService.deleteMenuItem(menuItem.value.publicId)
  if (status === 204) {
    menuItems.value = menuItems.value.filter((val) => val.publicId !== menuItem.value.publicId)
    deleteMenuDialog.value = false
    menuItem.value = {}
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

const formatCurrency = (value) => {
  const price = value / 10
  return price.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
}

const formatCurrencyForAPI = (value) => {
  return value * 10
}
const getImageURL = (path) => {
  return import.meta.env.VITE_ROOT_API + '/files/' + path
}

const getUploadURL = () => {
  return import.meta.env.VITE_ROOT_API + '/menu/menu/upload'
}

const onUpload = (event) => {
  menuItem.value.imageUrl = 'menu-items-pics/' + event.xhr.response
}

const options = [
  { name: 'isVegetarian', value: 'isVegetarian' },
  { name: 'isVegan', value: 'isVegan' },
  { name: 'isGlutenFree', value: 'isGlutenFree' },
  { name: 'isSpicy', value: 'isSpicy' },
  { name: 'isHalal', value: 'isHalal' },
  { name: 'isKosher', value: 'isKosher' },
  { name: 'isPeanuts', value: 'isPeanuts' },
  { name: 'isTreeNuts', value: 'isTreeNuts' },
  { name: 'isDairy', value: 'isDairy' },
  { name: 'isEggs', value: 'isEggs' },
  { name: 'isShellfish', value: 'isShellfish' },
  { name: 'isSoy', value: 'isSoy' }
]
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <Toolbar>
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
          :value="menuItems"
          v-model:selection="selectedMenu"
          v-model:contextMenuSelection="selectedMenu"
          selectionMode="single"
          v-model:filters="filters1"
          dataKey="publicId"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          @row-contextmenu="onRowContextMenu"
          contextMenu
          :rows="5"
          :globalFilterFields="['title', 'description', 'itemCategory']"
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
                Manage menu items <span v-if="menuContextId">for menu: </span>
                <Tag
                  v-if="menuContextId"
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
          <template #empty> No menu items found.</template>
          <template #loading> Loading menu items data. Please wait.</template>

          <Column field="publicId" header="Id" headerStyle="width:10%; min-width:10rem;">
            <template #body="slotProps">
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
            field="description"
            header="Description"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
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
            field="itemCategory.nlsKey"
            header="Category"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <Tag
                class="mr-2"
                :value="t(slotProps.data.itemCategory.nlsKey)"
                :rounded="false"
                severity="warning"
              ></Tag>
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by category"
              />
            </template>
          </Column>
          <Column field="price" header="Price" headerStyle="width:7%; min-width:10rem;">
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
          <Column header="Image" headerStyle="width:6%; min-width:8rem;">
            <template #body="slotProps">
              <span class="p-column-title">Image</span>
              <Image
                preview
                :src="getImageURL(slotProps.data.imageUrl)"
                :alt="slotProps.data.imageUrl"
                class="shadow-2"
                height="75"
              />
            </template>
          </Column>
          <Column
            field="enabled"
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
          </Column>
          <Column
            field="ingredients"
            header="Ingredients"
            dataType="array"
            :sortable="false"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <Tag
                v-for="attr in slotProps.data.ingredients"
                class="m-1"
                v-bind:key="attr.publicId"
                :value="attr.name"
                :rounded="false"
                severity="success"
              ></Tag>
            </template>
          </Column>
          <Column
            v-if="!menuContextId"
            field="title"
            header="Menu"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Menu</span>
              {{ slotProps.data.menu.name }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by menu name"
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
            <router-link
              v-if="item.route"
              v-slot="{ href, navigate }"
              :to="{ name: item.route, params: { menuItemId: selectedMenu.publicId } }"
              custom
            >
              <a v-ripple :href="href" v-bind="props.action" @click="navigate">
                <span :class="item.icon" />
                <span class="ml-2">{{ item.label }}</span>
              </a>
            </router-link>
          </template>
        </ContextMenu>

        <Dialog
          v-model:visible="menuDialog"
          :style="{ width: '450px' }"
          header="Menu item details"
          :modal="true"
          class="p-fluid"
        >
          <div class="field">
            <label for="title">Title</label>
            <InputText
              id="title"
              v-model.trim="menuItem.title"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !menuItem.title }"
            />
            <small class="p-invalid" v-if="submitted && !menuItem.name">Title is required.</small>
          </div>

          <div class="field">
            <label for="desc">Description</label>
            <InputText id="desc" v-model.trim="menuItem.description" required="false" autofocus />
          </div>

          <div class="field w-full">
            <label for="imageUrl">Image</label>
            <FileUpload
              class="w-full"
              mode="basic"
              id="imageUrl"
              ref="fileUpload"
              name="file"
              :url="getUploadURL()"
              accept="image/*"
              @upload="onUpload"
              :auto="false"
              :maxFileSize="1000000"
              choose-label="Select Image"
            />
          </div>
          <div class="field">
            <label for="price">Price</label>
            <InputNumber
              v-model="menuItem.price"
              id="price"
              inputId="currency-us"
              mode="currency"
              showButtons
              :currency="useUserStore().getUserCurrency()"
              :locale="useUserStore().getUserLocale()"
            />
          </div>

          <div class="field">
            <label for="categorySelect">Select category</label>
            <Dropdown
              id="categorySelect"
              v-model="selectedCategoryId"
              :options="categories"
              optionLabel="nlsKey"
              optionValue="publicId"
              :class="{ 'p-invalid': submitted && !menuItem.itemCategory.publicId }"
              required="true"
              autofocus
              placeholder="..."
            />
            <small class="p-invalid" v-if="submitted && !menuItem.itemCategory.publicId"
              >Category is required.</small
            >
          </div>

          <div class="field">
            <label for="ingredientsSelect">Select a few ingredients</label>
            <AutoComplete
              id="ingredientsSelect"
              v-model="selectedAutoValueIngredients"
              :class="{ 'p-invalid': submitted && !menuItem.ingredients }"
              optionLabel="name"
              multiple
              :suggestions="autoFilteredValueIngredient"
              @complete="searchIngredients"
            />
            <small class="p-invalid" v-if="submitted && !menuItem.ingredients"
              >Few ingredients are required.</small
            >
          </div>

          <div class="field" v-if="!menuContextId">
            <label for="currency">Select Menu for this Menu Item</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !menuItem.restaurantId }"
              required="true"
              autofocus
              placeholder="Search"
              id="currency"
              :dropdown="true"
              :multiple="false"
              v-model="selectedAutoValueSMenu"
              :suggestions="autoFilteredValueMenu"
              @complete="searchMenu($event)"
              field="name"
            />
            <small class="p-invalid" v-if="submitted && !menuItem.restaurantId"
              >Selling point is required.</small
            >
          </div>
          <div class="field">
            <label for="additionalItemProps">Additional properties</label>
            <SelectButton
              id="additionalItemProps"
              v-model="selectedAdditionalItemProps"
              :options="options.slice(0, 4)"
              optionLabel="name"
              multiple
              aria-labelledby="multiple"
            />
          </div>
          <div class="field">
            <SelectButton
              id="additionalItemProps"
              v-model="selectedAdditionalItemProps"
              :options="options.slice(5, 9)"
              optionLabel="name"
              multiple
              aria-labelledby="multiple"
            />
          </div>
          <div class="field-checkbox mb-0">
            <Checkbox
              id="terms"
              name="option"
              value="true"
              v-model="menuItem.enabled"
              :binary="true"
            />
            <label for="terms">Is Active</label>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveMenuItem" />
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
            <span v-if="menuItem"
              >Are you sure you want to delete <b>{{ menuItem.name }}</b
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
