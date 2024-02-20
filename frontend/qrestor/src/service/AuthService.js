export default class ProductService {
  loginAttempt(email, password) {
    return fetch('/api/auth/authentication/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    }).then((res) => res.json())
  }
}
