import Vue from 'vue';
import VueRouter from "vue-router";
import VueCookies from 'vue-cookies';


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
import AuthApi from "@/api/AuthApi";


Vue.use(VueRouter);
Vue.use(VueCookies);

/**
 * 로그인 페이지로
 */
function goLoginPage() {
  router.replace("/login");
}

/**
 * 로그인이 필요한 페이지에 접근시에 세션검증
 */
function redirectLoginPageIfTokenNotValid(to, from, next) {
  const token = Vue.$cookies.get("token");
  const TAG = "[redirectLoginIfTokenNotValid]";

  const thenFunc = function(response) {
    const {accessToken} = response.data;
    if (accessToken) {
      Vue.$cookies.set("token", accessToken, '10d');
    }
    next();
  }

  const catchFunc = function(e) {
    console.log(e);
    Vue.$cookies.remove("token");
    goLoginPage();
  }

  if (token) {
    AuthApi.authenticate(token)
      .then(thenFunc.bind(this))
      .catch(catchFunc.bind(this));
  } else {
    goLoginPage();
  }

}

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
      component: MeIndex,
      beforeEnter: redirectLoginPageIfTokenNotValid
    },
    {
      path: '/my-page/orders/:id',
      component: OrderDetail
    },
    {
      path: '/orders',
      component: OrderIndex,
      beforeEnter: redirectLoginPageIfTokenNotValid
    },
    {
      path: '/orders/:id/complete',
      component: OrderComplete
    }
  ]
})