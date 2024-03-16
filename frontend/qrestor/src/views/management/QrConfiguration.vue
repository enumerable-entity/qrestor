<script setup>
import { onBeforeMount, ref } from 'vue'
import QrService from '@/service/QrService.js'
import MenuService from '@/service/MenuService.js'
import SellingPointsService from '@/service/SellingPointsService.js'
import { useToast } from 'primevue/usetoast'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
const toast = useToast()

const qrMappings = ref(null)
const menuComboList = ref(null)

const qrMapping = ref({})
const qrDialog = ref(false)
const deleteMenuDialog = ref(false)

const submitted = ref(false)

// SERVICES INSTANCES
const qrService = new QrService()
const menuService = new MenuService()
const sellingPointsService = new SellingPointsService()

onBeforeMount(async () => {
  const { data: menuData } = await qrService.getAllQrCodes()
  const { data: menuCombo } = await menuService.getMenuCombo()
  const { data: spointsCombo } = await sellingPointsService.getSellingPointsDictionary()
  qrMappings.value = menuData
  menuComboList.value = menuCombo
})

const hideDialog = () => {
  qrDialog.value = false
  submitted.value = false
}

const saveQrMapping = async () => {
  submitted.value = true

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

  submitted.value = false
}

// ROW SELECT
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
