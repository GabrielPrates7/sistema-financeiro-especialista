import { createRouter, createWebHistory } from 'vue-router'
import Sistema from '../views/Sistema.vue'
import HomePage from '../views/HomePage.vue'
import Respostas from "../views/Respostas.vue"

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Sistema
    },
    {
        path: '/sistema',
        name: 'Sistema',
        component: Sistema
    },
    {
        path: '/respostas-sistema/:id',
        name: 'Respostas',
        component: Respostas
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router