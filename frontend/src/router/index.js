import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import StartView from '@/views/StartView.vue'
import Error from "@/components/Error.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/main',
      name: 'main',
      component: MainView,
      beforeEnter: (to, from, next)=>{
        // Function to get a cookie by name
        const getCookie = (name) => {
          const value = `; ${document.cookie}`;
          const parts = value.split(`; ${name}=`);
          if (parts.length === 2) return parts.pop().split(';').shift();
        };

        // Check if the "jwt" cookie exists
        if (getCookie("jwt_auth")) {
          next();
        } else {
          next({
            name: 'error-page-app',
          });
        }
      }
    },
    {
      path: '/start',
      name: 'start',
      component: StartView
    },
    {
      path: '/*',
      name: 'error-page',
      component: Error,
      props: {
          default: true,
          errorCode: "404",
          errorMessage: "Страница не найдена"
      }
    },
    {
      path: '/error',
      name: 'error-page-app',
      component: Error,
      props: {
          default: true,
          errorCode: "401",
          errorMessage: "Нет доступа!"
      }
    }
  ],
})

export default router;