import { useAuthStore } from '@/store'

export const fetchWrapper = {
  get: request('GET'),
  post: request('POST'),
  put: request('PUT'),
  delete: request('DELETE')
}

function request(method) {
  return async (url, body) => {
    const requestOptions = {
      method,
      headers: authHeader(url)
    }
    if (body) {
      requestOptions.headers['Content-Type'] = 'application/json'
      requestOptions.body = JSON.stringify(body)
    }
    const response = await fetch(url, requestOptions)
    const text = await response.text()
    const data = text && JSON.parse(text)

    if (!response.ok) {
      const { tokens: user, logout } = useAuthStore()
      if ([401].includes(response.status) && user) {
        logout()
      }
    }
    const status = response.status
    return { data: data, status: status }
  }

  // helper functions

  function authHeader(url) {
    // return auth header with jwt if user is logged in and request is to the api url
    const { tokens: user } = useAuthStore()
    const isLoggedIn = !!user?.accessToken
    const isApiUrl = url.startsWith('/api')
    if (isLoggedIn && isApiUrl) {
      return { Authorization: `Bearer ${user.accessToken}` }
    } else {
      return {}
    }
  }
}
