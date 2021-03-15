export default {
  SET_CART_TOTAL(state, total) {
    state.cartTotal = total;
  },
  SET_CURRENT_MY_PAGE_TAB(state, tab) {
    state.currentMyPageTab = tab;
  },
  SET_CURRENT_REVIEW_NAV(state, nav) {
    state.currentReviewNav = nav;
  },

  SET_SOURCE_CODE_LINK_MODAL_VIEW(state, modalView) {
    state.sourceCodeLinkModalView = modalView;
  }
}