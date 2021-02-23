export default {
  SET_CART_TOTAL(state, total) {
    console.log(total);
    state.cartTotal = total;
  }
}