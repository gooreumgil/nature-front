import Vue from 'vue'
import App from './App.vue'
import {router} from "@/routes";
import {store} from "@/store";
import VueRouter from "vue-router";
import VueCookie from 'vue-cookies';

Vue.config.productionTip = false

Vue.use(VueRouter);
Vue.use(VueCookie);

Vue.filter("price", function(value) {
  return String(value).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
});

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
