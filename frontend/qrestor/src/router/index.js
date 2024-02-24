import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '@/layout/AppLayout.vue'
import { useAuthStore } from '@/store'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/management',
      name: 'management',
      component: AppLayout,
      children: [
        {
          path: '/pages/crud',
          name: 'crud',
          component: () => import('@/views/pages/Crud.vue')
        }
      ]
    },
    {
      path: '/qr/:code',
      name: 'qr',
      props: true,
      component: () => import('@/views/pages/Empty.vue')
    },
    {
      path: '/',
      name: 'landing',
      component: () => import('@/views/pages/Landing.vue')
    },
    {
      path: '/pages/notfound',
      name: 'notfound',
      component: () => import('@/views/pages/NotFound.vue')
    },

    {
      path: '/auth/login',
      name: 'login',
      component: () => import('@/views/pages/auth/Login.vue')
    },
    {
      path: '/auth/register',
      name: 'register',
      component: () => import('@/views/pages/auth/Registration.vue')
    },
    {
      path: '/auth/email-verification-send',
      name: 'email-verification-send',
      component: () => import('@/views/pages/auth/EmailVerificationSend.vue')
    },
    {
      path: '/registration/verifyEmail/:token',
      name: 'email-verification',
      component: () => import('@/views/pages/auth/EmailVerification.vue')
    },
    {
      path: '/auth/access',
      name: 'accessDenied',
      component: () => import('@/views/pages/auth/Access.vue')
    },
    {
      path: '/auth/error',
      name: 'error',
      component: () => import('@/views/pages/auth/Error.vue')
    }
  ]
})

router.beforeEach((to, from) => {
  const publicPages = [
    'login',
    'register',
    'email-verification-send',
    'email-verification',
    'accessDenied',
    'error',
    'landing',
    'qr'
  ]
  const authRequired = !publicPages.includes(to.name)
  const auth = useAuthStore()
  if (authRequired && !auth.user) {
    auth.returnUrl = to.fullPath
    return '/auth/login'
  } else if (to.name === 'login' && auth.user && from.name === 'landing') {
    return 'management'
  }
})

export default router
