<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
    <div class="title-container">
      <h2>Shopping Cart</h2>
      <p>장바구니 내역입니다.</p>
    </div>

    <ul class="cart-wrapper">
      <li class="cart-list clearfix head">
        <div class="cart-col select">
          <input type="checkbox" @click="allSelect" v-model="allSelected">
        </div>
        <div class="cart-col info">
          <p>상품정보</p>
        </div>
        <div class="cart-col total">
          <p>수량</p>
        </div>
        <div class="cart-col price">
          <p>상품금액</p>
        </div>
        <div class="cart-col discount">
          <p>할인금액</p>
        </div>
        <div class="cart-col totalPrice">
          <p>최종금액</p>
        </div>
        <div class="cart-col remove">
          삭제
        </div>
      </li>

      <div class="cart-empty" v-if="isCartEmpty()">
        <div class="cart-empty-inner">
          <EmptyCartIcon v-bind:stroke="'#a0a0a0'" />
          <p>장바구니가 비어있습니다.</p>
          <button @click="goItems()" type="button">쇼핑하러 가기</button>
        </div>
      </div>

      <li class="cart-list clearfix" v-for="(item, index) in items" v-bind:key="index">
        <div class="cart-col select">
          <input type="checkbox" v-model="item.selected">
        </div>
        <div class="cart-col info">
          <div class="info-inner">
            <img v-bind:src="item.mainImgPath" alt="">
            <p>{{ item.nameKor }}</p>

          </div>
        </div>
        <div class="cart-col total">
          <div class="total-inner">
            <button @click="quantitySum(item, 'PLUS')" type="button">
              <PlusIcon />
            </button>
            <p>{{ item.quantity }}</p>
            <button @click="quantitySum(item, 'MINUS')" type="button">
              <MinusIcon />
            </button>
          </div>
        </div>
        <div class="cart-col price">
          <p>{{ priceSum(item) | price }} 원</p>
        </div>
        <div class="cart-col discount">
          <p><span class="minus">(-)</span> {{ discountPriceSum(item) | price }} <span class="won">원</span></p>
        </div>
        <div class="cart-col totalPrice">
          <p> {{ totalPriceSum(item) | price }} <span class="won">원</span></p>
        </div>
        <div class="cart-col remove">
          <span @click="removeInCart(item.id)">
            <RemoveIcon />
          </span>

        </div>
      </li>
    </ul>

    <div v-if="items.length > 0" class="btn-box">
      <button @click="selectedItemOrder()" type="button">선택상품주문</button>
      <button @click="allItemOrder()" type="button">전체상품주문</button>
    </div>

    <Bottom />
    <Footer />
    <SourceCodeLinkModal />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import PlusIcon from "@/components/icon/PlusIcon";
import MinusIcon from "@/components/icon/MinusIcon";
import RemoveIcon from "@/components/icon/RemoveIcon";
import EmptyCartIcon from "@/components/icon/EmptyCartIcon";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";

export default {
name: "Cart",
  components: {SourceCodeLinkModal, EmptyCartIcon, RemoveIcon, MinusIcon, PlusIcon, Footer, Bottom, Header},
  data() {
    return {
      items: [],
      allSelected: false
    }
  },
  created() {
    const cartItemIds = JSON.parse(this.$cookies.get('cart-items'));

    if (cartItemIds && cartItemIds.length > 0) {
      this.setItems(cartItemIds);

    }

  },

  methods: {
    async setItems(cartItemIds) {
      try {
        const res = await itemApi.getItemsByIds(cartItemIds);

        for (const item of res.data) {
          this.items.push({
            ...item,
            selected: false,
            quantity: 1
          })
        }

      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    allSelect() {

      if (!this.allSelected) {
        this.items.forEach(item => {
          item.selected = true;
        });
      } else if (this.allSelected) {
        this.items.forEach(item => {
          item.selected = false;
        });
      }
    },

    quantitySum(item, type) {

      if (type === 'PLUS') {
        item.quantity = item.quantity + 1;
      }

      else if (type === 'MINUS') {
        if (item.quantity === 1) {
          alert('주문수량은 1개 이상만 가능합니다.')
        }
        if (item.quantity > 1) {
          item.quantity = item.quantity - 1;
        }
      }
    },

    priceSum(item) {
      return item.price * item.quantity;
    },

    discountPriceSum(item) {
      return item.discountPrice * item.quantity;
    },

    totalPriceSum(item) {
      const priceTotal = this.priceSum(item);
      const discountPriceTotal = this.discountPriceSum(item);
      return priceTotal - discountPriceTotal;

    },

    removeInCart(id) {
      const cartItems = JSON.parse(this.$cookies.get('cart-items'));
      cartItems.forEach(cartItem => {
        if (cartItem === id) {
          const indexOfCartItem = cartItems.indexOf(cartItem);
          cartItems.splice(indexOfCartItem, 1);
        }
      })


      this.$cookies.set('cart-items', JSON.stringify(cartItems));

      this.items.forEach(item => {
        if (item.id === id) {
          const indexOfItem = this.items.indexOf(item);
          this.items.splice(indexOfItem, 1);
        }
      })

      this.$store.commit('SET_CART_TOTAL', cartItems.length)
    },

    isCartEmpty() {
      const cartItems = JSON.parse(this.$cookies.get('cart-items'));
      return !(cartItems && cartItems.length > 0);

    },

    async goItems() {
      await this.$router.push('/items');
    },

    selectedItemOrder() {
      const selectedItems = this.items.filter(item => item.selected);
      if (selectedItems.length === 0) {
        alert('선택된 상품이 없습니다.');
        return;
      }

      const token = this.$cookies.get('token');
      if (!token) {
        alert('로그인해주세요.');
        return;
      }

      let orderItems = [];
      selectedItems.forEach(selectedItem => orderItems.push(selectedItem));

      this.$cookies.set('order-items', JSON.stringify(orderItems));
      this.$router.push('/orders');

    },

    allItemOrder() {
      const token = this.$cookies.get('token');
      if (!token) {
        alert('로그인해주세요.');
        return;
      }

      const items = this.items;
      if (items.length === 0) {
        alert('상품이 존재하지 않습니다.');
        return;
      }

      let orderItems = [];
      items.forEach(selectedItem => orderItems.push(selectedItem));

      this.$cookies.set('order-items', JSON.stringify(orderItems));
      this.$router.push('/orders');
    }
  }
}
</script>

<style scoped>
  header {
    box-shadow: 0 0 3px 0 #dcdcdc;
  }

  section.main-container {
    position: relative;
  }

  section.main-container div.title-container {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 160px;
    text-align: left;

  }

  section.main-container div.title-container h2 {
    font-size: 20px;
    color: #333;
    font-weight: 400;
  }

  section.main-container div.title-container p {
    font-size: 14px;
    font-weight: 400;
    color: #a0a0a0;
    margin-top: 10px;
  }

  section.main-container ul.cart-wrapper {
    max-width: 1260px;
    width: 100%;
    box-sizing: border-box;
    margin: 0 auto;
    margin-top: 40px;
  }

  section.main-container ul.cart-wrapper li.cart-list {
    padding: 40px 0;
    font-size: 14px;
    font-weight: 400;
    box-sizing: border-box;
    height: 160px;
    border-bottom: 1px solid #eee;
    font-weight: 400;
    color: #555;
  }

  section.main-container ul.cart-wrapper div.cart-empty {
    width: 100%;
    box-sizing: border-box;
    padding: 120px 0;
    border-bottom: 1px solid #eaeaea;
    padding-bottom: 160px;
  }

  section.main-container ul.cart-wrapper div.cart-empty div.cart-empty-inner {

  }

  section.main-container ul.cart-wrapper div.cart-empty div.cart-empty-inner svg {
    max-width: 70px;
    width: 100%;
    transform: translateX(-3px);
  }

  section.main-container ul.cart-wrapper div.cart-empty div.cart-empty-inner p {
    font-size: 16px;
    font-weight: 700;
    color: #555;
    margin-top: 20px;
  }

  section.main-container ul.cart-wrapper div.cart-empty div.cart-empty-inner button {
    outline: none;
    cursor: pointer;
    background-color: #7ebb34;
    border: none;
    border-radius: 3px;
    padding: 8px 15px;
    color: #fff;
    font-weight: 700;
    margin-top: 15px;
  }


  section.main-container ul.cart-wrapper li.cart-list.head {
    padding: 20px 0;
    background-color: #f9f9f9;
    box-sizing: border-box;
    height: 60px;
    border: 0;
    font-size: 13px;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col {
    float: left;
    box-sizing: border-box;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.select {
    width: 7%;
    justify-content: center;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.info {
    text-align: left;
    width: 30%;
    justify-content: left;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.info .info-inner {
    display: flex;
    align-items: center;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.info .info-inner p {
    font-weight: 400;
    color: #555;
    font-size: 15px;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.info .info-inner img {
    max-width: 100px;
    margin-right: 25px;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total {
    width: 15%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total div.total-inner {
    position: relative;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total div.total-inner button {
    cursor: pointer;
    outline: none;
    top: 50%;
    transform: translateY(-50%);
    position: absolute;
    background-color: #eaeaea;
    border-radius: 50%;
    width: 22px;
    height: 22px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total div.total-inner button svg {
    width: 100%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total div.total-inner button:first-child {
    left: -40px;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total div.total-inner button:last-child {
    right: -40px;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.price {
    width: 15%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.discount {
    width: 15%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.totalPrice {
    width: 10%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.remove {
    width: 8%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.remove svg {
    max-width: 20px;
    width: 100%;
  }

  section.main-container div.btn-box {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    margin-top: 20px;
    box-sizing: border-box;
    text-align: left;
  }

  section.main-container div.btn-box button {
    cursor: pointer;
    outline: none;
    margin-right: 10px;
    box-sizing: border-box;
    background-color: #fff;
    border: 1px solid #0fafbe;
    border-radius: 3px;
    padding: 5px 10px;
    font-size: 12px;
    color: #555;
  }

</style>