import { fetchWrapper } from '@/fetchWrapper'

export class RegistrationService {
  constructor() {
    this.url = '/api/auth/registration'
  }

  async registerUser(userInfo) {
    const userRegistrationResponse = await fetchWrapper.post(this.url, userInfo)
    return userRegistrationResponse
  }
}

export class EmailVerificationService {
  constructor() {
    this.url = '/api/auth/registration/verifyEmail/'
  }

  async verifyToken(token) {
    return await fetchWrapper.get(this.url + token)
  }
}
