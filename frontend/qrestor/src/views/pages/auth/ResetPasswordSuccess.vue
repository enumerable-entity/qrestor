<script setup>
import { useLayout } from '@/layout/composables/layout'
import { computed, ref } from 'vue'

const { layoutConfig } = useLayout()

const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})

var showLoginButton = ref(true)
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center w-7">
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
        <div class="w-full surface-card py-8 px-1 sm:px-1 md:pb-1" style="border-radius: 56px">
          <div class="text-center mb-5">
            <div class="grid">
              <div class="col-12 md:col-12">
                <div class="text-900 text-3xl font-medium">Password was reseted</div>
              </div>
              <div class="col-12 md:col-12">
                <span class="text-900 text-2xl">Now You can log in with the new password</span>
              </div>
              <div class="col-12 md:col-12">
                <Button
                  v-if="showLoginButton"
                  label="Login"
                  class="mr-2 mb-2 w-3"
                  @click="$router.push('/auth/login')"
                ></Button>
              </div>
            </div>
          </div>
          <div></div>
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
