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
          <input type="checkbox">
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

      <li class="cart-list clearfix" v-for="(item, index) in items" v-bind:key="index">
        <div class="cart-col select">
          <input type="checkbox">
        </div>
        <div class="cart-col info">
          <p>{{ item.nameKor }}</p>
        </div>
        <div class="cart-col total">
          <p>0</p>
        </div>
        <div class="cart-col price">
          <p>{{ item.price }}</p>
        </div>
        <div class="cart-col discount">
          <p>{{ item.discountPrice }}</p>
        </div>
        <div class="cart-col totalPrice">
          <p>최종금액</p>
        </div>
        <div class="cart-col remove">
          삭제
        </div>
      </li>
    </ul>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
export default {
name: "Cart",
  components: {Header},
  data() {
    return {
      items: []
    }
  },
  created() {
    const cartItemIds = JSON.parse(this.$cookies.get('cart-items'));
    itemApi.getItemsByIds(cartItemIds)
        .then((res) => {
          this.items = res.data;
        })
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
  }

  section.main-container div.title-container p {
    font-size: 14px;
    font-weight: 400;
    color: #888;
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
    font-size: 15px;
    color: #555;
    font-weight: 400;
  }

  section.main-container ul.cart-wrapper li.cart-list.head {
    padding: 20px 0;
    background-color: #f9f9f9;

  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col {
    float: left;
    box-sizing: border-box;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.select {
    width: 7%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.info {
    text-align: left;
    width: 30%;
  }

  section.main-container ul.cart-wrapper li.cart-list div.cart-col.total {
    width: 15%;
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



</style>