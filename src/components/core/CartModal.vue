<template>
  <div v-if="cartAddView" class="cart-add-modal">
    <div class="modal-inner">
      <CartIcon />
      <h3>장바구니에 담았습니다.</h3>

      <div class="btn-box">
        <button type="button" @click="shoppingKeepGoing">계속쇼핑</button>
        <button type="button" @click="goCart">장바구니</button>
      </div>
    </div>
  </div>
</template>

<script>
import CartIcon from "@/components/icon/CartIcon";
export default {
  name: "CartModal",
  components: {CartIcon},
  props: {
    cartAddView: {
      default: false
    },
    shoppingKeepGoing: {
      type: Function
    }
  },

  methods: {
    goCart() {
      this.$router.push('/cart');
    },
  }
}
</script>

<style scoped>
  div.cart-add-modal {
    width: 300px;
    height: 300px;
    background-color: #fff;
    border-radius: 30px;
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, .15);
  }

  div.cart-add-modal div.modal-inner {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }

  div.cart-add-modal div.modal-inner svg {
    max-width: 100px;
    width: 100px;
    transform: translateX(-5px);
  }

  div.cart-add-modal div.modal-inner h3 {
    margin-top: 20px;
  }

  div.cart-add-modal div.modal-inner .btn-box {
    box-sizing: border-box;
    padding-top: 15px;
  }

  div.cart-add-modal div.modal-inner .btn-box button {
    border-radius: 3px;
    box-sizing: border-box;
    padding: 5px 10px;
    margin: 0 5px;
    font-size: 13px;
    outline: none;
  }

  div.cart-add-modal div.modal-inner .btn-box button:first-child {
    background-color: #fff;
    border: 1px solid #eaeaea;
  }

  div.cart-add-modal div.modal-inner .btn-box button:last-child {
    background-color: #555;
    color: #fff;
    font-weight: 400;
  }
</style>