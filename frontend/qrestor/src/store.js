import { defineStore } from 'pinia'
import { fetchWrapper } from '@/fetchWrapper'
import router from '@/router/index'

export const useUserStore = defineStore({
  id: 'authUserInfo',
  state: () => ({
    userInfo: JSON.parse(localStorage.getItem('authUserInfo'))
  }),
  actions: {
    async getLoggedUserInfo() {
      const { data } = await fetchWrapper.get('/auth/authentication/me')
      this.userInfo = await data
      localStorage.setItem('authUserInfo', JSON.stringify(data))
    },
    getUserLocale() {
      return this.userInfo?.settings.language
    },
    getUserCurrency() {
      return this.userInfo?.settings.currency
    }
  }
})

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
    tokens: JSON.parse(localStorage.getItem('tokens')),
    returnUrl: null
  }),
  actions: {
    async login(email, password) {
      const { data } = await fetchWrapper.post(`/auth/authentication/login`, {
        email,
        password
      })

      // update pinia state
      this.tokens = data

      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('tokens', JSON.stringify(data))
      var useUserStor = useUserStore()
      useUserStor.getLoggedUserInfo()

      // redirect to previous url or default to home page
      router.push(this.returnUrl || '/management')
    },
    logout() {
      this.tokens = null
      localStorage.removeItem('tokens')
      localStorage.removeItem('authUserInfo')
      router.push('/auth/login')
    },
    isAuthenticated() {
      var isAuth = !!this.tokens?.accessToken
      return isAuth
    }
  }
})

export const useWaiterCallsStore = defineStore({
  id: 'waiterCalls',
  state: () => ({
    waiterCalls: new Set()
  }),
  actions: {
    async addWaiterCall(call) {
      this.waiterCalls.add(call)
    },
    getCalls() {
      return this.waiterCalls
    },
    remove(tableNr){
      this.waiterCalls.delete(tableNr)
      return this.waiterCalls
    }
  }
})