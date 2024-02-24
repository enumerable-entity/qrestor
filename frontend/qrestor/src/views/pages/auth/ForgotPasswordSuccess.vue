<script setup>
import { useLayout } from '@/layout/composables/layout'
import { ref, computed } from 'vue'
import { RegistrationService } from '@/service/AuthService'
import router from '@/router/index'

const { layoutConfig } = useLayout()

const email = ref('')
const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})

const registrationService = new RegistrationService()
function onSubmit() {
  const status = registrationService.resetPasswordRequest(email.value)
  if (status == 200) {
    router.push('/auth/email-verification-send')
  }
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
          <div class="text-center ">
            <div class="text-900 text-3xl font-medium">Email with reset link was send</div>
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
