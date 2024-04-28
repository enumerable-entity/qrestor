<script setup>
import { useLayout } from '@/layout/composables/layout'
import { ref, computed, onMounted } from 'vue'
import { RegistrationService } from '@/service/AuthService'
import { CurrencyService, CountryService } from '@/service/DropBoxService'
import router  from '@/router/index'

const { layoutConfig } = useLayout()

const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})

const countryService = new CountryService()
const currencyService = new CurrencyService()
onMounted(() => {
  countryService.getCountries().then((data) => (autoValueCountry.value = data))
  currencyService.getCurencies().then((data) => (autoValueCurrency.value = data))
})

const autoValueCountry = ref(null)
const selectedAutoValueCountry = ref(null)
const autoFilteredValueCountry = ref([])

const autoValueCurrency = ref(null)
const selectedAutoValueCurrency = ref(null)
const autoFilteredValueCurrency = ref([])

const darkMode = ref(false)
const isTermsAccepted = ref(false)

const searchCountry = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueCountry.value = [...autoValueCountry.value]
    } else {
      autoFilteredValueCountry.value = autoValueCountry.value.filter((country) => {
        return country.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const searchCurrency = (event) => {
  setTimeout(() => {
    if (!event.query.trim().length) {
      autoFilteredValueCurrency.value = [...autoValueCurrency.value]
    } else {
      autoFilteredValueCurrency.value = autoValueCurrency.value.filter((currency) => {
        return currency.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

const repeatedPassword = ref('')

const registrationRequest = ref({
  email: '',
  password: '',
  username: '',
  address: {
    number: '',
    address: '',
    city: '',
    state: '',
    zip: '',
    country: ''
  },
  settings: {
    theme: '',
    language: 'EN',
    currency: ''
  },
  information: {
    firstName: '',
    lastName: '',
    middleName: '',
    phone: '',
    businessName: ''
  }
})

const registrationService = new RegistrationService()
function onSubmit() {
  registrationRequest.value.address.country = selectedAutoValueCountry.value.code
  registrationRequest.value.settings.currency = selectedAutoValueCurrency.value.code
  registrationRequest.value.settings.theme = darkMode.value ? 'DARK' : 'LIGHT'
  const result = registrationService.registerUser(registrationRequest.value)
  if (result) {
    router.push('/auth/email-verification-send')
  }

}
</script>

<template>
  <div
    class="surface-ground align-items-center justify-content-center min-w-screen grid min-h-screen overflow-hidden"
  >
    <div class="flex-column align-items-center flex pl-4 pr-2 sm:w-11 md:w-9">
      <img :src="logoUrl" alt="Qrestor logo" class="w-6rem mb-5 mt-8 shrink-0"  @click="$router.push('/')" />
      <div
        style="
          border-radius: 56px;
          padding: 0.3rem;
          background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%);
        "
      >
        <div class="surface-card w-full px-2 py-5 sm:px-5" style="border-radius: 53px 53px 0 0">
          <div class="mb-5 text-center">
            <div class="text-900 mb-3 text-3xl font-medium">Sign Up</div>
            <span class="text-600 font-medium">Sign up to continue</span>
          </div>
          <div class="p-fluid grid">
            <div class="col-12 md:col-6">
              <div class="card p-fluid">
                <div class="field col-12 md:col-12 pb-0">
                  <label for="email">Email</label>
                  <InputText
                    id="email"
                    class="w-full"
                    type="text"
                    placeholder="Email address"
                    v-model="registrationRequest.email"
                  ></InputText>
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="username">Username</label>
                  <InputText
                    id="username"
                    class="w-full"
                    type="text"
                    placeholder="Username"
                    v-model="registrationRequest.username"
                  ></InputText>
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="pass">Password</label>
                  <Password
                    id="pass"
                    v-model="registrationRequest.password"
                    placeholder="Password"
                    :toggleMask="true"
                    class="w-full pb-0"
                  ></Password>
                </div>
                <div class="field col-12 md:col-12 py-0">
                  <label for="pass1">Repeat password</label>
                  <Password
                    id="pass1"
                    v-model="repeatedPassword"
                    placeholder="Password"
                    :toggleMask="true"
                    class="w-full pb-0"
                  ></Password>
                </div>
              </div>
            </div>
            <div class="col-12 md:col-6">
              <div class="card p-fluid">
                <h5>Preferences</h5>

                <div class="field col-12 md:col-12">
                  <label for="currency">Currency</label>
                  <AutoComplete
                    class="w-full"
                    placeholder="Search"
                    id="currency"
                    :dropdown="true"
                    :multiple="false"
                    v-model="selectedAutoValueCurrency"
                    :suggestions="autoFilteredValueCurrency"
                    @complete="searchCurrency($event)"
                    field="name"
                  />
                </div>
                <div class="col-12 md:col-12">
                  <h6>Dark mode</h6>
                  <InputSwitch id="themeMode" v-model="darkMode" />
                </div>
              </div>
            </div>
            
            <div class="col-12 md:col-6">
              <div class="card p-fluid">
                <h5>Additional information</h5>

                <div class="field col-12 md:col-12 py-0">
                  <label for="fname">First name</label>
                  <InputText
                    id="fname"
                    placeholder="..."
                    type="text"
                    v-model="registrationRequest.information.firstName"
                  />
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="lname">Last name</label>
                  <InputText
                    id="lname"
                    placeholder="..."
                    type="text"
                    v-model="registrationRequest.information.lastName"
                  />
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="lname">Middle name</label>
                  <InputText
                    id="mname"
                    placeholder="..."
                    type="text"
                    v-model="registrationRequest.information.middleName"
                  />
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="phone">Phone</label>
                  <InputText
                    id="phone"
                    placeholder="..."
                    type="text"
                    v-model="registrationRequest.information.phone"
                  />
                </div>

                <div class="field col-12 md:col-12 py-0">
                  <label for="trade">Trademark name</label>
                  <InputText
                    id="trade"
                    placeholder="..."
                    type="text"
                    v-model="registrationRequest.information.businessName"
                  />
                </div>
              </div>
            </div>
            <div class="col-12 md:col-6">
              <div class="card p-fluid">
                <h5>Headquarters address</h5>
                <div class="p-fluid formgrid grid">
                  <div class="field col-12 md:col-6">
                    <label for="dd">Country</label>
                    <AutoComplete
                      class="w-full"
                      placeholder="Search"
                      id="dd"
                      :dropdown="true"
                      :multiple="false"
                      v-model="selectedAutoValueCountry"
                      :suggestions="autoFilteredValueCountry"
                      @complete="searchCountry($event)"
                      field="name"
                    />
                  </div>

                  <div class="field col-12 md:col-6">
                    <label for="city">City</label>
                    <InputText
                      id="city"
                      placeholder="..."
                      type="text"
                      v-model="registrationRequest.address.city"
                    />
                  </div>

                  <div class="field col-12 md:col-6">
                    <label for="State">State</label>
                    <InputText
                      type="text"
                      placeholder="..."
                      v-model="registrationRequest.address.state"
                    ></InputText>
                  </div>
                  <div class="field col-12 md:col-6">
                    <label for="zip">Zip</label>
                    <InputText
                      id="zip"
                      type="text"
                      placeholder="..."
                      v-model="registrationRequest.address.zip"
                    ></InputText>
                  </div>

                  <div class="field col-12 md:col-6">
                    <label for="street">Street</label>
                    <InputText
                      id="street"
                      type="text"
                      placeholder="..."
                      v-model="registrationRequest.address.address"
                    ></InputText>
                  </div>

                  <div class="field col-12 md:col-6">
                    <label for="number">Number</label>
                    <InputText
                      id="number"
                      class="w-full"
                      type="text"
                      placeholder="..."
                      v-model="registrationRequest.address.number"
                    ></InputText>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-12 md:col-6">
              <div class="field-checkbox mb-0">
                <Checkbox id="terms" name="option" value="true" v-model="isTermsAccepted" />
                <label for="terms">I agree to the terms and conditions as set out by the user agreement</label>
              </div>
            </div>
            <div class="col-12 md:col-6 md:col-offset-10">
              <Button label="Sign Up" class="p-3 text-xl md:w-3" :disabled="!isTermsAccepted"  @click="onSubmit"></Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.pi-eye {
  transform: scale(1.6);
  margin-right: 1rem;
}

.pi-eye-slash {
  transform: scale(1.6);
  margin-right: 1rem;
}
</style>
@/service/DropBoxService
