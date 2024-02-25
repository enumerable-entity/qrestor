import { fetchWrapper } from '@/fetchWrapper'

export class RegistrationService {
  constructor() {
    this.url = '/auth/registration'
  }

  async registerUser(userInfo) {
    const userRegistrationResponse = await fetchWrapper.post(this.url, userInfo)
    return userRegistrationResponse
  }
}

export class EmailVerificationService {
  constructor() {
    this.url = '/auth/registration/verifyEmail/'
  }

  async verifyToken(token) {
    return await fetchWrapper.get(this.url + token)
  }
}

export class PassrowdResetService {
  constructor() {
    this.url = '/auth/authentication/forgot-password/'
  }

  async resetPasswordRequest(emaill) {
    return await fetchWrapper.post(this.url, { email: emaill })
  }

  async resetPasswordToken(token, password, emaill) {
    return await fetchWrapper.post(this.url, { password: password, token: token, email: emaill})
  }
}
