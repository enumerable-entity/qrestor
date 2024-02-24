<script setup>
import { useLayout } from '@/layout/composables/layout'
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { EmailVerificationService } from '@/service/AuthService'

const { layoutConfig } = useLayout()
const route = useRoute()
const token = route.params.token

const logoUrl = computed(() => {
  return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})
const tokenVerificationService = new EmailVerificationService()

const positveAnswer = {
  header1: 'Thanks for joining Us!',
  header2: 'Your email was verified. Now You can log in'
}

const negativeAnswer = {
  header1: 'Email verificaton token is invalid or expired',
  header2: 'Your email was not verified. Please try again'
}

var renderAnswer = ref({ header1: '', header2: '' })
var showLoginButton = ref(false)

onMounted(async () => {
  const status = await tokenVerificationService.verifyToken(token)
  if (status == 200) {
    renderAnswer.value.header1 = positveAnswer.header1
    renderAnswer.value.header2 = positveAnswer.header2
    showLoginButton.value = true
  } else {
    renderAnswer.value.header1 = negativeAnswer.header1
    renderAnswer.value.header2 = negativeAnswer.header2
    showLoginButton.value = false
  }
})
</script>

<template>
  <main>
    <div
      class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
    >
      <div class="flex flex-column align-items-center justify-content-center">
        <img
          :src="logoUrl"
          alt="Qrestor logo"
          @click="$router.push('/')"
          class="mb-5 w-6rem flex-shrink-0"
        />
        <div
          style="
            border-radius: 22px;
            padding: 0.3rem;
            background: linear-gradient(
              180deg,
              var(--primary-color) 10%,
              rgba(33, 150, 243, 0) 30%
            );
          "
        >
          <div class="w-full surface-card py-8 px-1 sm:px-1 md:pb-1" style="border-radius: 22px">
            <div class="text-center mb-5">
              <div class="grid">
                <div class="col-12 md:col-12">
                  <div class="text-900 text-3xl font-medium">{{ renderAnswer.header1 }}</div>
                </div>
                <div class="col-12 md:col-12">
                  <span class="text-900 text-2xl">{{ renderAnswer.header2 }}</span>
                </div>
                <div class="col-12 md:col-12">
                  <Button
                    v-if="showLoginButton"
                    label="Login"
                    class="mr-2 mb-2"
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
  </main>
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
