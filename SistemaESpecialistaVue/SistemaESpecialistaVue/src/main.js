import { createApp } from 'vue'
import App from './components/App.vue'
import router from './router'

import './style.css'
import './styleSistema.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap'

const app = createApp(App)

app.use(router)

app.mount('#app')