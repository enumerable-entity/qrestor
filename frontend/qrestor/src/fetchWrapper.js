import { useAuthStore } from '@/store'

const apiUrl = import.meta.env.VITE_ROOT_API

export const fetchWrapper = {
  get: request('GET'),
  post: request('POST'),
  put: request('PUT'),
  delete: request('DELETE')
}

function request(method) {
  return async (url, body) => {
    const requestUrl = apiUrl + url
    const requestOptions = {
      method,
      mode: 'cors',
      headers: authHeader(requestUrl)
    }
    if (body) {
      requestOptions.headers['Content-Type'] = 'application/json'
      requestOptions.body = JSON.stringify(body)
    }
    const response = await fetch(requestUrl, requestOptions)
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

  function authHeader(url) {
    const { tokens: user } = useAuthStore()
    const isLoggedIn = !!user?.accessToken
    const isApiUrl = url.startsWith(apiUrl)
    if (isLoggedIn && isApiUrl) {
      return { Authorization: `Bearer ${user.accessToken}` }
    } else {
      return {}
    }
  }
}
