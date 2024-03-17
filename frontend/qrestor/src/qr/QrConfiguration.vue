<script setup lang="ts">
import StyledQRCode from '@/qr/components/StyledQRCode.vue'
import {
  copyImageToClipboard,
  downloadPngElement,
  downloadSvgElement,
  IS_COPY_IMAGE_TO_CLIPBOARD_SUPPORTED
} from '@/qr/utils/convertToImage'
import { computed, onMounted, ref, watch } from 'vue'
import 'vue-i18n'
import { getNumericCSSValue } from './utils/formatting'
import { allPresets, type Preset } from './utils/presets'

//#region /** styling states and computed properties */
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

const allPresetOptions = computed(() => {
  const options = lastCustomLoadedPreset.value
    ? [lastCustomLoadedPreset.value, ...allPresets]
    : allPresets
  return options.map((preset) => ({ value: preset.name, label: preset.name }))
})
const selectedPreset = ref<Preset & { key?: string }>(defaultPreset)
watch(selectedPreset, () => {
  dataToEncodeInQr.value = selectedPreset.value.data
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
  styleBorderRadius.value = getNumericCSSValue(selectedPreset.value.style.borderRadius as string)
  styleBackground.value = selectedPreset.value.style.background
})

const LAST_LOADED_LOCALLY_PRESET_KEY = 'Last saved locally'
const LOADED_FROM_FILE_PRESET_KEY = 'Loaded from file'
const CUSTOM_LOADED_PRESET_KEYS = [LAST_LOADED_LOCALLY_PRESET_KEY, LOADED_FROM_FILE_PRESET_KEY]
const selectedPresetKey = ref<string>(LAST_LOADED_LOCALLY_PRESET_KEY)
const lastCustomLoadedPreset = ref<Preset>()
watch(
  selectedPresetKey,
  (newKey, prevKey) => {
    if (newKey === prevKey || !newKey) return

    if (CUSTOM_LOADED_PRESET_KEYS.includes(newKey) && lastCustomLoadedPreset.value) {
      selectedPreset.value = lastCustomLoadedPreset.value
      return
    }

    const updatedPreset = allPresets.find((preset) => preset.name === newKey.value)
    if (updatedPreset) {
      selectedPreset.value = updatedPreset
    }
  },
  { immediate: true }
)

//#endregion

//#region /* export image utils */
const options = computed(() => ({
  width: width.value,
  height: height.value
}))

async function copyQRToClipboard() {
  console.debug('Copying image to clipboard')
  const qrCode = document.querySelector('#qr-code-container')
  if (qrCode) {
    await copyImageToClipboard(qrCode as HTMLElement, options.value)
  }
}

function downloadQRImageAsPng() {
  console.debug('Copying image to clipboard')
  const qrCode = document.querySelector('#qr-code-container')
  if (qrCode) {
    downloadPngElement(qrCode as HTMLElement, 'qr-code.png', options.value)
  }
}

function downloadQRImageAsSvg() {
  console.debug('Copying image to clipboard')
  const qrCode = document.querySelector('#qr-code-container')
  if (qrCode) {
    downloadSvgElement(qrCode as HTMLElement, 'qr-code.svg', options.value)
  }
}

function uploadImage() {
  console.debug('Uploading image')
  const imageInput = document.createElement('input')
  imageInput.type = 'file'
  imageInput.accept = 'image/*'
  imageInput.onchange = (event: Event) => {
    const target = event.target as HTMLInputElement
    if (target.files) {
      const file = target.files[0]
      const reader = new FileReader()
      reader.onload = (event: ProgressEvent<FileReader>) => {
        const target = event.target as FileReader
        const result = target.result as string
        imageFullUrl.value = result
      }
      reader.readAsDataURL(file)
    }
  }
  imageInput.click()
}

//#endregion

//#region /* QR Config Utils */
function createQrConfig() {
  return {
    props: qrCodeProps.value,
    style: style.value
  }
}

function downloadQRConfig() {
  console.debug('Downloading QR code config')
  const qrCodeConfig = createQrConfig()
  const qrCodeConfigString = JSON.stringify(qrCodeConfig)
  const qrCodeConfigBlob = new Blob([qrCodeConfigString], { type: 'application/json' })
  const qrCodeConfigUrl = URL.createObjectURL(qrCodeConfigBlob)
  const qrCodeConfigLink = document.createElement('a')
  qrCodeConfigLink.href = qrCodeConfigUrl
  qrCodeConfigLink.download = 'qr-code-config.json'
  qrCodeConfigLink.click()
}

function saveQRConfigToLocalStorage() {
  const qrCodeConfig = createQrConfig()
  const qrCodeConfigString = JSON.stringify(qrCodeConfig)
  localStorage.setItem('qrCodeConfig', qrCodeConfigString)
}

function loadQRConfig(jsonString: string, key?: string) {
  const qrCodeConfig = JSON.parse(jsonString)
  const qrCodeProps = qrCodeConfig.props
  const qrCodeStyle = qrCodeConfig.style
  const preset = {
    ...qrCodeProps,
    style: qrCodeStyle
  }

  if (key) {
    preset.name = key
    lastCustomLoadedPreset.value = preset
  }

  selectedPreset.value = preset
}

function loadQrConfigFromFile() {
  console.debug('Loading QR code config')
  const qrCodeConfigInput = document.createElement('input')
  qrCodeConfigInput.type = 'file'
  qrCodeConfigInput.accept = 'application/json'
  qrCodeConfigInput.onchange = (event: Event) => {
    const target = event.target as HTMLInputElement
    if (target.files) {
      const file = target.files[0]
      const reader = new FileReader()
      reader.onload = (event: ProgressEvent<FileReader>) => {
        const target = event.target as FileReader
        const result = target.result as string
        loadQRConfig(result, LOADED_FROM_FILE_PRESET_KEY)
      }
      reader.readAsText(file)
    }
  }
  qrCodeConfigInput.click()
}

function loadQRConfigFromLocalStorage() {
  const qrCodeConfigString = localStorage.getItem('qrCodeConfig')
  if (qrCodeConfigString) {
    console.debug('Loading QR code config from local storage')
    loadQRConfig(qrCodeConfigString, LAST_LOADED_LOCALLY_PRESET_KEY)
  } else {
    selectedPreset.value = { ...defaultPreset }
  }
}

watch(qrCodeProps, () => {
  saveQRConfigToLocalStorage()
})

onMounted(() => {
  loadQRConfigFromLocalStorage()
})
//#endregion
</script>

<template>
  <div class="grid">
    <div class="col">
      <div class="card ">
        <h1>QR Code Configuration</h1>
        <Toast />
        <div class="grid">
        <div class=" col-12 md:col-4">
          <div class = "grid">
            <div class=" col-12 md:col-12 pb-0">
              <p>QR code preview</p>
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
                  <p v-else>{{ 'No dataToEncodeInQr!' }}</p>
                </div>
              </div>
            </div>
            <div class=" col-12 md:col-12 pb-0">
              <Button
                v-if="IS_COPY_IMAGE_TO_CLIPBOARD_SUPPORTED"
                id="copy-qr-image-button"
                class="w-22rem p-text-secondary"
                @click="copyQRToClipboard"
                label="Copy QR Code to clipboard"
              >
              </Button>
            </div>
            <div class=" col-12 md:col-12 pb-0">
              <Button
                id="download-qr-image-button-png"
                class="w-22rem p-text-secondary"
                @click="downloadQRImageAsPng"
                label="Download QR Code as PNG"
              ></Button>
            </div>
            <div class=" col-12 md:col-12 pb-0">
              <Button
                id="download-qr-image-button-svg"
                class="w-22rem p-text-secondary"
                @click="downloadQRImageAsSvg"
                label="Download QR Code as SVG"
              ></Button>
            </div>
            <div class=" col-12 md:col-12 pb-0">
              <Button
                id="save-qr-code-config-button"
                class="w-22rem p-text-secondary"
                @click="downloadQRConfig"
                label="Save configuration"
              >
              </Button>
            </div>
            <div class=" col-12 md:col-12 pb-0">
              <Dropdown
                id="load-qr-code-config-dropdown"
                class="w-22rem p-text-secondary"
                :options="allPresetOptions"
                optionLabel="value"
                v-model="selectedPresetKey"
                ariaLabel="Load preset"
              >Select
              </Dropdown>
            </div>
            <div class=" col-12 md:col-12 pb-0">

              <Button class="w-22rem p-text-secondary" @click="uploadImage" label="Upload logo"> </Button>
            </div>
          </div>
        </div>
        <div class=" field col-12 md:col-8">
          <div class="grid">

          <div class="field col-12 md:col-6 pb-0">
            <label for="background-color">Background color </label>
            <input id="background-color" type="color" class="w-full p-text-secondary" v-model="styleBackground" />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="dots-color">{{ 'Dots color' }}</label>
            <input id="dots-color" type="color" class="w-full p-text-secondary" v-model="dotsOptionsColor" />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="corners-square-color">{{ 'Corners Square color' }}</label>
            <input
              id="corners-square-color"
              type="color"
              class="w-full p-text-secondary"
              v-model="cornersSquareOptionsColor"
            />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="corners-dot-color">{{ 'Corners Dot color' }}</label>
            <input
              id="corners-dot-color"
              type="color"
              class="w-full p-text-secondary"
              v-model="cornersDotOptionsColor"
            />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="width">
              {{ 'Width (px)' }}
            </label>
            <InputNumber
              class="w-full p-text-secondary"
              id="width"
              placeholder="width in pixels"
              v-model="width"
            />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="height">
              {{ 'Height (px)' }}
            </label>
            <InputNumber
              class="w-full p-text-secondary"
              id="height"
              placeholder="height in pixels"
              v-model="height"
            />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="margin">
              {{ 'Margin (px)' }}
            </label>
            <InputNumber class="w-full p-text-secondary" id="margin" placeholder="0" v-model="margin" />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="image-margin">
              {{ 'Image margin (px)' }}
            </label>
            <InputNumber class="w-full p-text-secondary" id="image-margin" placeholder="0" v-model="imageMargin" />
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <label for="border-radius">
              {{ 'Border radius (px)' }}
            </label>
            <InputNumber
              class="w-full p-text-secondary"
              id="border-radius"
              placeholder="24"
              v-model="styleBorderRadius"
            />
          </div>
            <div class="field col-12 md:col-6 pb-0">
              <Fieldset legend = 'Corners Dot type'>
                <div v-for="type in ['dot', 'square']" :key="type">
                  <RadioButton
                    :id="'cornersDotOptionsType-' + type"
                    v-model="cornersDotOptionsType"
                    :value="type"
                  />
                  <label :for="'cornersDotOptionsType-' + type">{{ type }}</label>
                </div>
              </Fieldset>
            </div>
          <div class="field col-12 md:col-6 pb-0">
            <Fieldset legend = 'Dots type' class="w-full p-text-secondary">
              <div
                class="radiogroup"
                v-for="type in [
                'dots',
                'rounded',
                'classy',
                'classy-rounded',
                'square',
                'extra-rounded'
              ]"
                :key="type"
              >
                <RadioButton
                  :id="'dotsOptionsType-' + type"

                  v-model="dotsOptionsType"
                  :value="type"
                />
                <label :for="'dotsOptionsType-' + type">{{ type }}</label>
              </div>
            </Fieldset>
          </div>
          <div class="field col-12 md:col-6 pb-0">
            <Fieldset legend="Corners Square type" class="w-full p-text-secondary">
              <div class="radiogroup" v-for="type in ['dot', 'square', 'extra-rounded']" :key="type">
                <RadioButton

                  :inputId="'cornersSquareOptionsType-' + type"
                  v-model="cornersSquareOptionsType"
                  :value="type"
                />
                <label :for="'cornersSquareOptionsType-' + type">{{ type }}</label>
              </div>
            </Fieldset>
          </div>


          </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>
