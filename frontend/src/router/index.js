import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import StartView from '@/views/StartView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/main',
      name: 'main',
      component: MainView,
    },
    {
      path: '/start',
      name: 'start',
      component: StartView
    },
  ],
})

export default router