import { defineStore } from 'pinia'
import { fetchWrapper } from '@/fetchWrapper'
import router  from '@/router/index'

export const useUserStore = defineStore({
  id: 'authUserInfo',
  state: () => ({
    userInfo: JSON.parse(localStorage.getItem('authUserInfo'))
  }),
  actions: {
    async getLoggedUserInfo() {
      const userInfo = await fetchWrapper.get('/api/auth/authentication/me')

      // update pinia state
      this.userInfo = userInfo

      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('authUserInfo', JSON.stringify(userInfo))
    }
  }
})

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('user')),
    returnUrl: null
  }),
  actions: {
    async login(email, password) {
      const user = await fetchWrapper.post(`/api/auth/authentication/login`, { email, password })

      // update pinia state
      this.user = user

      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('user', JSON.stringify(user))
      var useUserStor = useUserStore()
      useUserStor.getLoggedUserInfo()

      // redirect to previous url or default to home page
      router.push(this.returnUrl || '/management')
    },
    logout() {
      this.user = null
      localStorage.removeItem('user')
      localStorage.removeItem('authUserInfo')
      router.push('/auth/login')
    }
  }
})