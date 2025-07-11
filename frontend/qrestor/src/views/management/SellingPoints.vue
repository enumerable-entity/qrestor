<script setup>
import { FilterMatchMode } from 'primevue/api'
import { onBeforeMount, onMounted, ref } from 'vue'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'

const toast = useToast()

const points = ref(null)
const pointDialog = ref(false)
const deletePointDialog = ref(false)
const point = ref({})
const selectedPoints = ref(null)
const dt = ref(null)
const filters = ref({})
const submitted = ref(false)

const sellingPointService = new SellingPointsService()

onBeforeMount(() => {
  initFilters()
})
onMounted(async () => {
  const { data } = await sellingPointService.getSellingPoints()
  points.value = data
})

const formatAddress = (address) => {
  return `${address.zip}, \u00A0 ${address.city}, \u00A0 ${address.address}`
}

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
    const { data } = await sellingPointService.updateSellingPoint(point.value)
    pointDialog.value = false
    const index = points.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      points.value[index] = data
    }
  } else {
    const { data } = await sellingPointService.addSellingPoint(point.value)
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
  const { status } = await sellingPointService.deleteSellingPoint(point.value.publicId)
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

const getUploadURL = () => {
  return import.meta.env.VITE_ROOT_API + '/restaurant/upload'
}

const onUpload = (event) => {
  emptyPointModel.value.settings.backgroundImageUrl = 'backgrounds/' + event.xhr.response
}

const getImageURL = (path) => {
  return import.meta.env.VITE_ROOT_API + '/files/' + path
}

const makeIdShorter = (id) => {
  return id.substring(0, 8) + ' ...'
}

const initFilters = () => {
  filters.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
  }
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
        </Toolbar>

        <DataTable
          ref="dt"
          :value="points"
          v-model:selection="selectedPoints"
          dataKey="id"
          :paginator="true"
          :rowHover="true"
          :rows="5"
          :filters="filters"
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
              <span class="block mt-2 md:mt-0 p-input-icon-left">
                <i class="pi pi-search" />
                <InputText v-model="filters['global'].value" placeholder="Search..." />
              </span>
            </div>
          </template>
          <template #empty> No selling points found. </template>
          <template #loading> Loading selling points data. Please wait. </template>
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
              {{ slotProps.data.name }}
            </template>
          </Column>
          <Column
            field="title"
            header="Title"
            :sortable="true"
            headerStyle="width:14%; min-width:8rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.title }}
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
          </Column>
          <Column
            field="address"
            header="Address"
            headerStyle="width:19%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ formatAddress(slotProps.data.address) }}
            </template>
          </Column>
          <Column
            field="phone"
            header="Phone"
            :sortable="true"
            headerStyle="width:10%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Phone</span>
              {{ slotProps.data.phone }}
            </template>
          </Column>
          <Column header="Menu background" headerStyle="width:10%; min-width:8rem;">
            <template #body="slotProps">
              <span class="p-column-title">Menu background</span>
              <Image
                :src="getImageURL(slotProps.data.settings.backgroundImageUrl)"
                :alt="slotProps.data.settings.backgroundImageUrl"
                class="shadow-2"
                height="75"
              />
            </template>
          </Column>
          <Column header="Actions" headerStyle="min-width:10rem;">
            <template #body="slotProps">
              <Button
                text
                icon="pi pi-pencil"
                class="p-button-rounded p-button-success mr-2"
                @click="editPoint(slotProps.data)"
              />
              <Button
                text
                icon="pi pi-trash"
                class="p-button-rounded p-button-danger mt-2"
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
            <div class="field col-12 md:col-12">
              <label for="file">Background image</label>
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
                :maxFileSize="10000000"
                choose-label="Select Image"
              />
            </div>
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
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
