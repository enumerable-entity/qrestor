import { fetchWrapper } from '@/fetchWrapper'

export default class RegistrationService {
  constructor() {
    this.url = '/api/auth/registration'
  }

  async registerUser(userInfo) {
    const userRegistrationResponse = await fetchWrapper.post(this.url, userInfo)
    return userRegistrationResponse
  }
}
