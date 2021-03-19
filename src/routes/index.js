import Vue from 'vue';
import VueRouter from "vue-router";
import Index from "@/views/Index";
import Login from "@/views/auth/Login";
import SignUp from "@/views/auth/SignUp";
import Brand from "@/views/Brand";
import ItemIndex from "@/views/item/Index"
import Cart from "@/views/item/Cart";
import MeIndex from "@/views/me/Index"
import Detail from "@/views/item/Detail";
import OrderIndex from "@/views/order/Index"
import OrderComplete from "@/views/order/Complete";
import OrderDetail from "@/views/me/OrderDetail";
import Search from "@/views/item/Search";
import PasswordFind from "@/views/auth/PasswordFind";
import PasswordChangeByEmail from "@/views/auth/PasswordChangeByEmail";


Vue.use(VueRouter);

export const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Index
    },
    {
      path: '/brand',
      component: Brand
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/password-find',
      component: PasswordFind
    },
    {
      path: '/password-change-by-email/:email',
      component: PasswordChangeByEmail
    },
    {
      path: '/sign-up',
      component: SignUp
    },
    {
      path: '/items',
      component: ItemIndex
    },
    {
      path: '/items/search',
      component: Search
    },
    {
      path: '/items/:id',
      component: Detail
    },
    {
      path: '/cart',
      component: Cart
    },
    {
      path: '/my-page',
      component: MeIndex
    },
    {
      path: '/my-page/orders/:id',
      component: OrderDetail
    },
    {
      path: '/orders',
      component: OrderIndex
    },
    {
      path: '/orders/:id/complete',
      component: OrderComplete
    }
  ]
})