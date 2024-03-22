<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import IngredientsService from '@/service/IngredientsService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
const toast = useToast()

const menuItemOptions = ref(null)
const optionPosition = ref({})
const menuDialog = ref(false)
const deleteMenuDialog = ref(false)
const selectedMenu = ref(null)
const dt = ref(null)
const submitted = ref(false)

const filters1 = ref(null)
const loading1 = ref(null)

const initFilters1 = () => {
  filters1.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    name: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    isActive: { value: null, matchMode: FilterMatchMode.EQUALS }
  }
}

// SERVICES INSTANCES
const ingredientsService = new IngredientsService()

onBeforeMount(async () => {
  initFilters1()

  const { data } = await ingredientsService.getIngredients()
  menuItemOptions.value = data
  loading1.value = false
})

const clearFilter1 = () => {
  initFilters1()
}

const openNew = () => {
  optionPosition.value = {
    name: '',
    isEnabled: false
  }

  submitted.value = false
  menuDialog.value = true
}

const hideDialog = () => {
  menuDialog.value = false
  submitted.value = false
}

const saveMenuItemOption = async () => {
  submitted.value = true

  if (optionPosition.value.publicId) {
    const { data } = await ingredientsService.updateIngredients(optionPosition.value)
    menuDialog.value = false
    const index = menuItemOptions.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      menuItemOptions.value[index] = data
    }
  } else {
    const { data } = await ingredientsService.addIngredients(optionPosition.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'New ingredient saved',
      life: 3000
    })
    menuDialog.value = false
    menuItemOptions.value.push(data)
  }
  optionPosition.value = {}
  submitted.value = false
}
const editMenuItem = (editPosition) => {
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
  const { status } = await ingredientsService.deleteIngredients(optionPosition.value.publicId)
  if (status === 204) {
    menuItemOptions.value = menuItemOptions.value.filter(
      (val) => val.publicId !== optionPosition.value.publicId
    )
    deleteMenuDialog.value = false
    optionPosition.value = {}
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Ingredient deleted',
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
          :value="menuItemOptions"
          v-model:selection="selectedMenu"
          v-model:contextMenuSelection="selectedMenu"
          v-model:filters="filters1"
          dataKey="publicId"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          :rows="5"
          :globalFilterFields="['name']"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} ingredients"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">Manage Ingredients</h5>
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
          <template #empty> No ingredients found.</template>
          <template #loading> Loading ingrediets data. Please wait.</template>

          <Column field="publicId" header="Id" headerStyle="width:15%; min-width:10rem;">
            <template #body="slotProps">
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
                  'text-green-500 pi-check-circle': data.isEnabled,
                  'text-pink-500 pi-times-circle': !data.isEnabled
                }"
              ></i>
            </template>
          </Column>

          <Column headerStyle="min-width:12rem; width:25%;" header="Actions">
            <template #body="slotProps">
              <Button
                text
                icon="pi pi-pencil"
                class="p-button-rounded p-button-success m-0 p-0"
                @click="editMenuItem(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-trash"
                class="p-button-rounded p-button-warning m-0 p-0"
                @click="confirmDeleteMenuItem(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-info-circle"
                class="p-button-rounded p-button-warning m-0 p-0"
                @click="openDetailsDialog(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>
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
              v-model.trim="optionPosition.name"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !optionPosition.name }"
            />
            <small class="p-invalid" v-if="submitted && !optionPosition.name"
              >Title is required.</small
            >
          </div>

          <div class="field-checkbox mb-0">
            <Checkbox
              id="active"
              name="active"
              value="true"
              v-model="optionPosition.isEnabled"
              :binary="true"
            />
            <label for="active" class="mr-3">Is Active</label>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button
              label="Save"
              icon="pi pi-check"
              class="p-button-text"
              @click="saveMenuItemOption"
            />
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
