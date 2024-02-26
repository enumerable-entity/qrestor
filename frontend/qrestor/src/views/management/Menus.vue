<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { onBeforeMount, ref } from 'vue'
import MenuService from '@/service/MenuService.js'
import { useToast } from 'primevue/usetoast'

const toast = useToast()

const points = ref(null)
const pointDialog = ref(false)
const deletePointDialog = ref(false)
const deletePointsDialog = ref(false)
const point = ref({})
const selectedPoints = ref(null)
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

const menuService = new MenuService()

onBeforeMount(async () => {
  initFilters1();
  const { data } = await menuService.getMenus()
  points.value = data
  loading1.value = false;
})

const clearFilter1 = () => {
  initFilters1();
};


const emptyPointModel = {
  publicId: '',
  name: '',
  title: '',
  description: '',
  address: {
    address: '',
    city: '',
    zip: ''
  },
  phone: '',
  settings: {
    backgroundImageUrl: '',
    primaryColor: '',
    secondaryColor: '',
    logoUrl: '',
    topHeaderUrl: ''
  }
}

const openNew = () => {
  point.value = emptyPointModel
  submitted.value = false
  pointDialog.value = true
}

const hideDialog = () => {
  pointDialog.value = false
  submitted.value = false
}

const savePoint = async () => {
  submitted.value = true

  if (point.value.publicId) {
    const { data } = await menuService.updateMenu(point.value)
    pointDialog.value = false
    const index = points.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      points.value[index] = data
    }
  } else {
    const { data } = await menuService.addMenu(point.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Selling point Created',
      life: 3000
    })
    pointDialog.value = false
    points.value.push(data)
  }
  point.value = emptyPointModel
}
const editPoint = (editPoint) => {
  point.value = { ...editPoint }
  pointDialog.value = true
}

const confirmDeletePoint = (editPoint) => {
  point.value = editPoint
  deletePointDialog.value = true
}

const deletePoint = async () => {
  const { status } = await menuService.deleteMenu(point.value.publicId)
  if (status === 204) {
    points.value = points.value.filter((val) => val.publicId !== point.value.publicId)
    deletePointDialog.value = false
    point.value = emptyPointModel
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Selling point deleted',
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

const confirmDeleteSelected = () => {
  deletePointsDialog.value = true
}
const deleteSelectedPoints = () => {
  points.value = points.value.filter((val) => !selectedPoints.value.includes(val))
  deletePointsDialog.value = false
  selectedPoints.value = null
  toast.add({
    severity: 'success',
    summary: 'Successful',
    detail: 'Selling points deleted',
    life: 3000
  })
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
                label="New"
                icon="pi pi-plus"
                class="p-button-success mr-2"
                @click="openNew"
              />
              <Button
                label="Delete"
                icon="pi pi-trash"
                class="p-button-danger"
                @click="confirmDeleteSelected"
                :disabled="!selectedPoints || !selectedPoints.length"
              />
            </div>
          </template>

          <template v-slot:end>
            <FileUpload
              mode="basic"
              accept="image/*"
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
          :value="points"
          v-model:selection="selectedPoints"
          v-model:filters="filters1"
          dataKey="id"
          :paginator="true"
          :loading="loading1"
          :filters="filters1"
          filterDisplay="menu"
          :rowHover="true"
          :rows="10"
          :globalFilterFields="['name', 'description', 'restaurantId']"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} selling points"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">Manage Selling points</h5>
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
          <template #empty> No customers found. </template>
          <template #loading> Loading customers data. Please wait. </template>

          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
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
            header="Name"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Name</span>
              {{ slotProps.data.name }}
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
            header="Restaurant ID"
            headerStyle="width:23%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Restaurant ID</span>
              {{ slotProps.data.restaurantId }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                type="text"
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by restaurant"
              />
            </template>
          </Column>
          <Column
            field="isActive"
            :sortable="true"
            header="Is Active"
            dataType="boolean"
            bodyClass=""
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
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>
          <Column headerStyle="min-width:10rem;" header="Actions">
            <template #body="slotProps">
              <Button
                icon="pi pi-pencil"
                class="p-button-rounded p-button-success mr-2"
                @click="editPoint(slotProps.data)"
              />
              <Button
                icon="pi pi-trash"
                class="p-button-rounded p-button-warning mt-2"
                @click="confirmDeletePoint(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>

        <Dialog
          v-model:visible="pointDialog"
          :style="{ width: '450px' }"
          header="Selling point details"
          :modal="true"
          class="p-fluid"
        >
          <img
            :src="'/demo/images/product/' + point.image"
            :alt="point.image"
            v-if="point.image"
            width="150"
            class="mt-0 mx-auto mb-5 block shadow-2"
          />
          <div class="field">
            <label for="name">Name</label>
            <InputText
              id="name"
              v-model.trim="point.name"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !point.name }"
            />
            <small class="p-invalid" v-if="submitted && !point.name">Name is required.</small>
          </div>
          <div class="field">
            <label for="name">Title</label>
            <InputText
              id="name"
              v-model.trim="point.title"
              required="true"
              autofocus
              :class="{ 'p-invalid': submitted && !point.name }"
            />
            <small class="p-invalid" v-if="submitted && !point.name">Title is required.</small>
          </div>
          <div class="field">
            <label for="description">Description</label>
            <Textarea
              id="description"
              v-model.trim="point.description"
              required="false"
              rows="2"
              cols="20"
            />
          </div>
          <div class="formgrid grid">
            <div class="field col">
              <label for="zip">Zip</label>
              <InputText
                id="zip"
                v-model.trim="point.address.zip"
                required="true"
                autofocus
                :class="{ 'p-invalid': submitted && !point.address.zip }"
              />
            </div>
            <div class="field col">
              <label for="city">City</label>
              <InputText
                id="city"
                v-model.trim="point.address.city"
                required="true"
                autofocus
                :class="{ 'p-invalid': submitted && !point.address.city }"
              />
            </div>
            <div class="field col-12 md:col-12">
              <label for="address">Address</label>
              <InputText
                id="address"
                v-model.trim="point.address.address"
                required="true"
                autofocus
                :class="{ 'p-invalid': submitted && !point.address.address }"
              />
              <small class="p-invalid" v-if="submitted && !point.address.address"
                >Address is required.</small
              >
            </div>
            <div class="field col-12 md:col-12">
              <label for="phone">Phone</label>
              <InputText
                id="phone"
                v-model.trim="point.phone"
                required="true"
                autofocus
                :class="{ 'p-invalid': submitted && !point.phone }"
              />
            </div>
            <!--                      <div class="field col-12 md:col-12">-->
            <!--                        <label for="file">Background image</label>-->
            <!--                        <FileUpload id="file" mode="basic" name="demo[]" accept="image/*" url="localhost:8080/fileupload" :maxFileSize="1000000" @uploader="onUpload" customUpload />-->
            <!--                      </div>-->
          </div>
          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="savePoint" />
          </template>
        </Dialog>

        <Dialog
          v-model:visible="deletePointDialog"
          :style="{ width: '450px' }"
          header="Confirm"
          :modal="true"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span v-if="point"
              >Are you sure you want to delete <b>{{ point.name }}</b
              >?</span
            >
          </div>
          <template #footer>
            <Button
              label="No"
              icon="pi pi-times"
              class="p-button-text"
              @click="deletePointDialog = false"
            />
            <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deletePoint" />
          </template>
        </Dialog>

        <Dialog
          v-model:visible="deletePointsDialog"
          :style="{ width: '450px' }"
          header="Confirm"
          :modal="true"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span v-if="point">Are you sure you want to delete the selected points?</span>
          </div>
          <template #footer>
            <Button
              label="No"
              icon="pi pi-times"
              class="p-button-text"
              @click="deletePointsDialog = false"
            />
            <Button
              label="Yes"
              icon="pi pi-check"
              class="p-button-text"
              @click="deleteSelectedPoints"
            />
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
