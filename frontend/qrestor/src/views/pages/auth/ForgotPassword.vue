<script setup>
import { useLayout } from '@/layout/composables/layout'
import { ref, computed } from 'vue'
import { PassrowdResetService } from '@/service/AuthService'
import router from '@/router/index'

const { layoutConfig } = useLayout()

const email = ref('')
const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})

const passwordResetService = new PassrowdResetService()
function onSubmit() {
  passwordResetService.resetPasswordRequest(email.value)

  router.push('/auth/reset-password-send')
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center">
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
        <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
          <div class="text-center mb-5">
            <!-- <img src="/demo/images/login/avatar.png" alt="Image" height="50" class="mb-3" /> -->
            <div class="text-900 text-3xl font-medium mb-3">Password reset</div>
            <span class="text-600 font-medium">Enter Your email</span>
          </div>

          <div>
            <InputText
              id="email1"
              type="text"
              placeholder="Email address"
              class="w-full md:w-30rem"
              style="padding: 1rem"
              v-model="email"
            />

            <div class="flex align-items-center justify-content-between mb-5 gap-5"></div>
            <Button label="Reset" class="w-full p-3 text-xl" @click="onSubmit"></Button>
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
