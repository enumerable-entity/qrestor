import { useAuthStore } from '@/store'

export const fetchWrapper = {
  get: await request('GET'),
  post: await request('POST'),
  put: await request('PUT'),
  delete: await request('DELETE')
}

async function request(method) {
  return async (url, body) => {
    const requestOptions = {
      method,
      headers: authHeader(url)
    }
    if (body) {
      requestOptions.headers['Content-Type'] = 'application/json'
      requestOptions.body = JSON.stringify(body)
    }
    return await fetch(url, requestOptions).then(handleResponse)
  }
}

// helper functions

function authHeader(url) {
  // return auth header with jwt if user is logged in and request is to the api url
  const { user } = useAuthStore()
  const isLoggedIn = !!user?.accessToken
  const isApiUrl = url.startsWith('/api')
  if (isLoggedIn && isApiUrl) {
    return { Authorization: `Bearer ${user.accessToken}` }
  } else {
    return {}
  }
}

async function handleResponse(response) {
  return response.text().then((text) => {
    const data = text && JSON.parse(text)

    if (!response.ok) {
      const { user, logout } = useAuthStore()
      if ([401].includes(response.status) && user) {
        // auto logout if 401 Unauthorized  response returned from api
        logout()
      }
    }
    return data, response.status
  })
}
