import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate  from 'pinia-plugin-persistedstate'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/none.css'
import App from './App.vue'
import router from './router'
import naive from 'naive-ui'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia=createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(naive)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
