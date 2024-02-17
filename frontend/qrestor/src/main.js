import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import '@/assets/styles.scss';
import PrimeVue from 'primevue/config';
import Button from 'primevue/button'
import Card from 'primevue/card'

const app = createApp(App)
app.use(PrimeVue)
app.use(createPinia())
app.use(router)

app.mount('#app')

app.component('Button', Button)
app.component('Card', Card)
