
export default {

  addCart(id) {
    const cartItems = this.$cookies.get('cart-items');
    if (!cartItems) {
      let ids = [];
      ids.push(id);
      this.$cookies.set('cart-items', JSON.stringify(ids));
    } else {

      let duplicate = false;
      const cartItemIds = JSON.parse(cartItems);
      cartItemIds.forEach(itemId => {
        if (itemId === id) {
          duplicate = true;
        }
      })

      if (duplicate) {
        throw new Error("이미 장바구니에 담은 상품입니다.");
      }

      if (!duplicate) {
        cartItemIds.push(id);
        this.$cookies.set('cart-items', JSON.stringify(cartItemIds));
        this.$store.commit('SET_CART_TOTAL', cartItemIds.length)
      }
    }
  },


}