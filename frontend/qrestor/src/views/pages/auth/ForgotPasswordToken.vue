<script setup>
import { useLayout } from '@/layout/composables/layout'
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import { PassrowdResetService } from '@/service/AuthService'
import router from '@/router/index'

const { layoutConfig } = useLayout()
const route = useRoute()
const token = route.params.token
const count = ref(0)
const message = ref([])

const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})
const passwordResetService = new PassrowdResetService()

const email = ref('')
const password = ref('')
const repeatPassword = ref('')

async function onSubmit() {
  const status = await passwordResetService.resetPasswordToken(token, password.value, email.value)
  if (status == 200) {
    router.push('/auth/reset-password-success')
  } else {
    message.value = [
      {
        severity: 'error',
        detail: 'Error Message',
        content: 'Data You provided is incorrect',
        id: count.value++
      }
    ]
  }
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center md:w-8">
      <img
        :src="logoUrl"
        alt="Qrestor logo"
        class="mb-5 w-6rem flex-shrink-0"
        @click="$router.push('/')"
      />
      <div
        style="
          border-radius: 56px;
          padding: 0.3rem;
          background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%);
        "
      >
        <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 56px">
          <div class="text-center mb-5">
           
            <div class="text-900 text-3xl font-medium mb-3">
              You are about to reset your password
            </div>
            <span class="text-600 font-medium">Enter Your email and new password</span>
          </div>

          <div>
            <label for="email1" class="flex">Email</label>
            <InputText
              id="email1"
              type="text"
              placeholder="Email address"
              class="w-full md:w-30rem mb-2"
              style="padding: 1rem"
              v-model="email"
            />

            <label for="password" class="flex">Password</label>
            <Password
              id="password"
              v-model="password"
              placeholder="Password"
              :toggleMask="true"
              class="w-full mb-2"
              inputClass="w-full"
              :inputStyle="{ padding: '1rem' }"
            ></Password>

            <label for="password1" class="flex">Repeat password</label>
            <Password
              id="password1"
              placeholder="Password"
              :toggleMask="true"
              class="w-full mb-3"
              inputClass="w-full"
              v-model="repeatPassword"
              :inputStyle="{ padding: '1rem' }"
            ></Password>

            <Button label="Set new password" class="w-full p-3 text-xl" @click="onSubmit"></Button>
            <transition-group name="p-message" tag="div">
              <Message v-for="msg of message" :severity="msg.severity" :key="msg.content">{{
                msg.content
              }}</Message>
            </transition-group>
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
