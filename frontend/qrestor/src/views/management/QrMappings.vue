<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api'
import { computed, onBeforeMount, ref } from 'vue'
import QrService from '@/service/QrService.js'
import MenuService from '@/service/MenuService.js'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'
import StyledQRCode from '@/qr/components/StyledQRCode.vue'
import { allPresets } from '@/qr/utils/presets.ts'
import { getNumericCSSValue } from '@/qr/utils/formatting.ts'

const { t } = useI18n()
const toast = useToast()

const qrMappings = ref(null)
const menuComboList = ref(null)
const sellingPointsComboList = ref(null)

const selectedPreset = ref(null)
const qrCodeConfig = ref(null)
const defaultPreset = allPresets[0]
const dataToEncodeInQr = ref('www.qrestor.com/qr/7a640ca4-a0ae-4ea0-9368-e2a53b7996e3')
const imageFullUrl = ref()
const width = ref()
const height = ref()
const margin = ref()
const imageMargin = ref()

const dotsOptionsColor = ref()
const dotsOptionsType = ref()
const cornersSquareOptionsColor = ref()
const cornersSquareOptionsType = ref()
const cornersDotOptionsColor = ref()
const cornersDotOptionsType = ref()
const styleBorderRadius = ref()
const styledBorderRadiusFormatted = computed(() => `${styleBorderRadius.value}px`)
const styleBackground = ref(defaultPreset.style.background)

const dotsOptions = computed(() => ({
  color: dotsOptionsColor.value,
  type: dotsOptionsType.value
}))
const cornersSquareOptions = computed(() => ({
  color: cornersSquareOptionsColor.value,
  type: cornersSquareOptionsType.value
}))
const cornersDotOptions = computed(() => ({
  color: cornersDotOptionsColor.value,
  type: cornersDotOptionsType.value
}))
const style = computed(() => ({
  borderRadius: styledBorderRadiusFormatted.value,
  background: styleBackground.value
}))
const imageOptions = computed(() => ({
  margin: imageMargin.value
}))

const qrCodeProps = computed(() => ({
  data: dataToEncodeInQr.value,
  image: imageFullUrl.value,
  width: width.value,
  height: height.value,
  margin: margin.value,
  dotsOptions: dotsOptions.value,
  cornersSquareOptions: cornersSquareOptions.value,
  cornersDotOptions: cornersDotOptions.value,
  imageOptions: imageOptions.value
}))

function loadQRConfig(jsonString) {
  const qrCodeConfig = JSON.parse(jsonString)
  const qrCodeProps = qrCodeConfig.props
  const qrCodeStyle = qrCodeConfig.style
  const preset = {
    ...qrCodeProps,
    style: qrCodeStyle
  }

  selectedPreset.value = preset
}

function loadQRConfigFromLocalStorage() {
  const qrCodeConfigString = localStorage.getItem('qrCodeConfig')
  if (qrCodeConfigString) {
    console.debug('Loading QR code config from local storage')
    loadQRConfig(qrCodeConfigString)
  }
}

const gerQrURL = (path) => {
  return import.meta.env.VITE_ROOT_DOMAIN + '/qr/' + path
}

const divCount = ref(1)
const printQRCode = () => {
  // Get the content of the element
  let content = document.getElementById('qr-code-container').innerHTML;
  let printWindow = window.open('', '_blank');

  // Write the content to the new window
  let divs = '';
  for (let i = 0; i < divCount.value; i++) {
    divs += `<div style="display: inline-block;">${content}</div>`;
  }

  printWindow.document.write(`
    <html>
      <body onload="window.print()">
        ${divs}
      </body>
    </html>
  `);
  printWindow.document.close();
}

const qrMapping = ref({})
const qrDialog = ref(false)
const qrPreviewDialog = ref(false)
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
        return menuItem.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const autoValueQrMapping = ref(null)
const selectedAutoValueSellingPoints = ref([])
const autoFilteredValueSellingPoints = ref([])

const searchSellingPoints = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueSellingPoints.value = [...autoValueQrMapping.value]
    } else {
      autoFilteredValueSellingPoints.value = autoValueQrMapping.value.filter((qrMapping) => {
        return qrMapping.name.toLowerCase().startsWith(event.query.toLowerCase())
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
    sellingPointId: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },

    isActive: { value: null, matchMode: FilterMatchMode.EQUALS }
  }
}

// SERVICES INSTANCES
const qrService = new QrService()
const menuService = new MenuService()
const sellingPointsService = new SellingPointsService()

onBeforeMount(async () => {
  initFilters1()
  loadQRConfigFromLocalStorage()
  imageFullUrl.value = selectedPreset.value.image
  width.value = selectedPreset.value.width
  height.value = selectedPreset.value.height
  margin.value = selectedPreset.value.margin
  imageMargin.value = selectedPreset.value.imageOptions.margin
  dotsOptionsColor.value = selectedPreset.value.dotsOptions.color
  dotsOptionsType.value = selectedPreset.value.dotsOptions.type
  cornersSquareOptionsColor.value = selectedPreset.value.cornersSquareOptions.color
  cornersSquareOptionsType.value = selectedPreset.value.cornersSquareOptions.type
  cornersDotOptionsColor.value = selectedPreset.value.cornersDotOptions.color
  cornersDotOptionsType.value = selectedPreset.value.cornersDotOptions.type
  styleBorderRadius.value = getNumericCSSValue(selectedPreset.value.style.borderRadius)
  styleBackground.value = selectedPreset.value.style.background

  const { data: menuData } = await qrService.getAllQrCodes()
  const { data: menuCombo } = await menuService.getMenuCombo()
  const { data: spointsCombo } = await sellingPointsService.getSellingPointsDictionary()
  qrMappings.value = menuData
  menuComboList.value = menuCombo
  autoValueQrMapping.value = spointsCombo
  autoValueMenu.value = menuCombo
  loading1.value = false
})

const clearFilter1 = () => {
  initFilters1()
}

const openNew = () => {
  qrMapping.value = {
    sellingPointId: '',
    menuId: '',
    tableId: '',
    isActive: false
  }
  submitted.value = false
  qrDialog.value = true
}

const hideDialog = () => {
  qrDialog.value = false
  submitted.value = false
}

const saveQrMapping = async () => {
  submitted.value = true
  qrMapping.value.sellingPointId = selectedAutoValueSellingPoints.value.id
  qrMapping.value.menuId = selectedAutoValueSMenu.value.id

  if (qrMapping.value.publicId) {
    const { data } = await qrService.updateQrCode(qrMapping.value)
    qrDialog.value = false
    const index = qrMappings.value.findIndex((el) => el.publicId === data.publicId)
    if (index !== -1) {
      qrMappings.value[index] = data
    }
  } else {
    const { data } = await qrService.addQrCode(qrMapping.value)
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'New QR mapping saved',
      life: 3000
    })
    qrDialog.value = false
    qrMappings.value.push(data)
  }
  qrMapping.value = {}
  selectedAutoValueSMenu.value = null
  selectedAutoValueSellingPoints.value = null
  submitted.value = false
}
const editMenuItem = (editMenu) => {
  selectedAutoValueSellingPoints.value = {
    id: editMenu.sellingPointId,
    name: editMenu.restaurantName
  }
  selectedAutoValueSMenu.value = { id: editMenu.menuId, name: editMenu.menuName }

  qrMapping.value = { ...editMenu }
  qrDialog.value = true
}

const confirmDeleteMenuItem = (editMenu) => {
  qrMapping.value = editMenu
  deleteMenuDialog.value = true
}

const openQrPreview = (menuItem) => {
  dataToEncodeInQr.value = gerQrURL(menuItem.publicId)
  qrCodeConfig.value = menuItem
  qrPreviewDialog.value = true
}

const deleteMenuItem = async () => {
  const { status } = await qrService.deleteQrCode(qrMapping.value.publicId)
  if (status === 204) {
    qrMappings.value = qrMappings.value.filter((val) => val.publicId !== qrMapping.value.publicId)
    deleteMenuDialog.value = false
    qrMapping.value = {}
    toast.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'QR mapping deleted',
      life: 3000
    })
  }
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
        </Toolbar>

        <DataTable
          ref="dt"
          :value="qrMappings"
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
          :rows="5"
          :globalFilterFields="['name', 'description', 'sellingPointId']"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} menus"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">QR codes management</h5>
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
          <template #empty> No qr codes mappings found.</template>
          <template #loading> Loading qr codes mappings. Please wait.</template>

          <Column
            field="publicId"
            header="Qr code identifier"
            headerStyle="width:13%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.publicId }}
            </template>
          </Column>

          <Column
            field="restaurantName"
            header="Selling point title"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.restaurantName }}
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
            field="menuName"
            header="Menu title"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.menuName }}
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
            field="tableId"
            header="Table number"
            :sortable="true"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              {{ slotProps.data.tableId }}
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
                  'text-green-500 pi-check-circle': data.isActive,
                  'text-pink-500 pi-times-circle': !data.isActive
                }"
              ></i>
            </template>
          </Column>

          <Column headerStyle="min-width:12rem; width:10%;" header="Actions">
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
                icon="pi pi-qrcode"
                class="p-button-rounded p-button-warning mt-2"
                @click="openQrPreview(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>

        <Dialog
          v-model:visible="qrDialog"
          :style="{ width: '450px' }"
          header="QR code mapping details"
          :modal="true"
          class="p-fluid"
        >
          <div class="field">
            <label for="price">Table number</label>
            <InputNumber v-model="qrMapping.tableId" id="price" inputId="currency-us" showButtons />
          </div>

          <div class="field">
            <label for="ingredientsSelect">Select selling point</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !qrMapping.sellingPointId }"
              required="true"
              autofocus
              placeholder="Search"
              id="ingredientsSelect"
              :dropdown="true"
              :multiple="false"
              v-model="selectedAutoValueSellingPoints"
              :suggestions="autoFilteredValueSellingPoints"
              @complete="searchSellingPoints($event)"
              field="name"
            />
            <small class="p-invalid" v-if="submitted && !qrMapping.sellingPointId"
              >Selling point required</small
            >
          </div>

          <div class="field">
            <label for="currency">Select menu for linking</label>
            <AutoComplete
              class="w-full"
              :class="{ 'p-invalid': submitted && !qrMapping.menuItemId }"
              required="true"
              autofocus
              placeholder="Search"
              id="currency"
              :dropdown="true"
              :multiple="false"
              v-model="selectedAutoValueSMenu"
              :suggestions="autoFilteredValueMenu"
              @complete="searchMenuItem($event)"
              field="name"
            />
            <small class="p-invalid" v-if="submitted && !qrMapping.menuItemId"
              >Selling point is required.</small
            >
          </div>
          <div class="field-checkbox mb-0">
            <Checkbox
              id="active"
              name="active"
              value="true"
              v-model="qrMapping.isActive"
              :binary="true"
            />
            <label for="active" class="mr-3">Is Active</label>
          </div>

          <template #footer>
            <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
            <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveQrMapping" />
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
            <span v-if="qrMapping"
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

        <Dialog
          v-model:visible="qrPreviewDialog"
          :style="{ width: '343px' }"
          header="QR code preview"
          :modal="true"
          class="p-fluid"
        >
          <div class="flex justify-content-start flex-wrap">
            <div>
              <div
                :style="[
                  style,
                  {
                    width: '300px',
                    height: '300px'
                  }
                ]"
              >
                <div id="qr-code-container">
                  <StyledQRCode
                    id="qrContainer"
                    v-if="dataToEncodeInQr"
                    v-bind="{ ...qrCodeProps, width: 300, height: 300 }"
                    role="img"
                    aria-label="QR code"
                  />
                  <p v-else>{{ 'No data to encode!' }}</p>
                </div>
              </div>
            </div>
          </div>

          <template #footer>
            <div class="grid ">
              <div class=" field col-6 mb-0">
                <label for="divCount">Codes count on sheet</label>
                <InputNumber v-model="divCount" id="divCount" inputId="divCount" show-buttons/>
              </div>
              <div class="col-6 flex align-content-end flex-wrap">
                <Button
                  label="Print"
                  icon="pi pi-print"
                  class=" w-full"
                  @click="printQRCode"
                />
              </div>
            </div>
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
