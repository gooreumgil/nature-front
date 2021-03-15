import Vue from 'vue';
import Vuex from 'vuex';
import mutations from "@/store/mutations";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    cartTotal: 0,
    currentMyPageTab: null,
    currentReviewNav: null,
    sourceCodeLinkModalView: false
  },
  mutations
})
