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
          path: '/pages/spoints',
          name: 'selling-points',
          component: () => import('@/views/pages/SellingPoints.vue')
        },
        {
          path: '/management/menus',
          name: 'menus',
          component: () => import('@/views/management/Menus.vue')
        },
        {
          path: '/management/menus/:menuId/menu-items',
          name: 'menu-items',
          component: () => import('@/views/management/MenuItems.vue')
        },
        {
          path: '/management/menus/menu-items',
          name: 'menu-items-all',
          component: () => import('@/views/management/MenuItems.vue')
        },
        {
          path: '/management/menus/menu-items/:menuItemId/menu-item-options',
          name: 'menu-item-options',
          component: () => import('@/views/management/MenuItemOptions.vue')
        },
        {
          path: '/management/menus/menu-items/menu-item-options',
          name: 'menu-item-options-all',
          component: () => import('@/views/management/MenuItemOptions.vue')
        },
        {
          path: '/management/menus/menu-item-options/options-positions',
          name: 'menu-item-options-positions-all',
          component: () => import('@/views/management/MenuItemOptionsPositions.vue')
        },
        {
          path: '/management/menus/menu-item-options/:optionId/options-positions',
          name: 'menu-item-options-positions',
          component: () => import('@/views/management/MenuItemOptionsPositions.vue')
        },
        {
          path: '/management/menus/ingredients',
          name: 'ingredients-all',
          component: () => import('@/views/management/Ingredients.vue')
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
      path: '/auth/forgot-password',
      name: 'forgot-password',
      component: () => import('@/views/pages/auth/ForgotPassword.vue')
    },
    {
      path: '/auth/reset-password-send',
      name: 'forgot-password-success',
      component: () => import('@/views/pages/auth/ForgotPasswordSuccess.vue')
    },
    {
      path: '/authentication/forgot-password/:token',
      name: 'forgot-password-token',
      component: () => import('@/views/pages/auth/ForgotPasswordToken.vue')
    },
    {
      path: '/auth/reset-password-success',
      name: 'reset-password-success',
      component: () => import('@/views/pages/auth/ResetPasswordSuccess.vue')
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
    'forgot-password',
    'email-verification-send',
    'forgot-password-success',
    'forgot-password-token',
    'reset-password-success',
    'email-verification',
    'accessDenied',
    'error',
    'landing',
    'qr'
  ]
  const authRequired = !publicPages.includes(to.name)
  const auth = useAuthStore()
  if (authRequired && !auth.tokens) {
    auth.returnUrl = to.fullPath
    return '/auth/login'
  } else if (to.name === 'login' && auth.tokens && from.name === 'landing') {
    return 'management'
  }
})

export default router
