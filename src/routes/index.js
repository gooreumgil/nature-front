import Vue from 'vue';
import VueRouter from "vue-router";
import Index from "@/views/Index";
import Login from "@/views/auth/Login";
import SignUp from "@/views/auth/SignUp";

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Index
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/sign-up',
      component: SignUp
    }
  ]
})