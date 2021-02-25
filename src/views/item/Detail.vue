<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />

    <section class="info-container" v-if="init">
      <ul class="info-wrapper clearfix">
        <li class="info-list img">
          <div class="inner-box">
            <img v-bind:src="item.mainSrcPath" alt="">
          </div>

        </li>
        <li class="info-list detail">
          <div class="category">
            <p>Product > {{ item.category }}</p>
          </div>
          <div class="name-and-description">
            <h2>{{ item.nameKor }}</h2>
            <h3>{{ item.nameEng }}</h3>
            <p>{{ item.description }}</p>
          </div>

          <div class="price">
            <p class="fixed-price" v-bind:class="{disable: item.discountPrice > 0}">
              <span class="label">판매가</span> {{ item.price | price}} <span class="won">원</span>
            </p>
            <p class="discount-price">
              <span class="label">할인가</span> {{ sumDiscountPrice(item) | price }} <span class="won">원</span>
            </p>
          </div>

          <div class="capacity-and-savePoints">
            <p class="capacity">
              <span class="label">용량</span> {{ item.capacity }} ml
            </p>
            <p class="savePoints">
              <span class="label">적립금</span> {{ item.savePoints | price }} 원
            </p>
          </div>

          <div class="totalPrice">
            <p class="quantity">
              <span class="label">수량</span>

              <button @click="quantitySum(item, 'PLUS')" type="button" class="plus">
                <PlusIcon />
              </button>

              {{ item.quantity }}

              <button @click="quantitySum(item, 'MINUS')" type="button" class="minus">
                <MinusIcon />
              </button>

            </p>
            <p class="result-price" id="id_color">
              <span class="label">최종금액</span> {{ getTotalPrice(item) | price }} <span class="won">원</span>
            </p>
          </div>
        </li>
      </ul>
    </section>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
import PlusIcon from "@/components/icon/PlusIcon";
import MinusIcon from "@/components/icon/MinusIcon";
export default {
  name: "Detail",
  components: {MinusIcon, PlusIcon, Header},

  data() {
    return {
      init: false,
      item: null
    }
  },

  async created() {
    const id = this.$route.params.id;
    await this.setItem(id);
  },

  methods: {
    async setItem(id) {
      try {
        const res = await itemApi.getItem(id);
        const resItem = res.data;

        resItem.quantity = 1;

        this.item = resItem;
        this.init = true;
      } catch (err) {
        alert('문제가 발생하였습니다.')
        console.log(err);
      }
    },

    quantitySum(item, type) {
      if (type === 'PLUS') {
        item.quantity++;
      } else {
        if (item.quantity <= 1) {
          alert('주문수량은 1개 이상만 가능합니다.');
          return;
        }
        item.quantity--;
      }
    },

    sumDiscountPrice(item) {
      return item.price - item.discountPrice;
    },

    getTotalPrice(item) {
      return this.sumDiscountPrice(item) * item.quantity;
    }
  }
}
</script>

<style scoped>
  section.main-container {
    position: relative;
  }

  section.main-container header {
    box-shadow: 0 0 3px 0 #dcdcdc;
  }

  section.main-container section.info-container {
    box-sizing: border-box;
    padding: 90px;
  }

  section.main-container section.info-container ul {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 80px;
  }

  section.main-container section.info-container ul li {
    float: left;
    box-sizing: border-box;
    width: 50%;
  }

  section.main-container section.info-container ul li:first-child {
    width: 60%;
  }

  section.main-container section.info-container ul li:last-child {
    width: 40%;
  }

  section.main-container section.info-container ul li.img {

  }

  section.main-container section.info-container ul li.img div.inner-box {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 470px;
    width: 400px;
    background-color: #f6f6f6;
    margin: 0 auto;
  }

  section.main-container section.info-container ul li.img div.inner-box img {
    width: 100%;
  }

  section.main-container section.info-container ul li.detail {
    text-align: left;
  }

  section.main-container section.info-container ul li.detail div.category {
    padding-top: 0;
    padding-bottom: 0;
    border-bottom: 0;
  }

  section.main-container section.info-container ul li.detail div.category p {
    display: inline-block;
    font-size: 16px;
    color: #6a6969;
    font-weight: 200;
  }

  section.main-container section.info-container ul li.detail div.name-and-description {
    border-bottom: 1px solid #eaeaea;
    box-sizing: border-box;
    padding-top: 10px;
    padding-bottom: 20px;
  }

  section.main-container section.info-container ul li.detail div.name-and-description h2 {
    font-size: 30px;
    color: #333;
    font-weight: 400;
    margin-top: 5px;
  }

  section.main-container section.info-container ul li.detail div.name-and-description h3 {
    font-size: 20px;
    color: #555;
    font-weight: 100;
    margin-top: 15px;
  }

  section.main-container section.info-container ul li.detail div.name-and-description p {
    font-size: 16px;
    color: #a0a0a0;
    font-weight: 100;
    margin-top: 20px;
    line-height: 1.5;
  }

  section.main-container section.info-container ul li.detail div {
    box-sizing: border-box;
    padding: 25px 0;
    border-bottom: 1px solid #eaeaea;
  }

  section.main-container section.info-container ul li.detail div p {
    font-weight: 400;
    color: #555;
  }

  section.main-container section.info-container ul li.detail div p:first-child {
    margin-bottom: 15px;
  }

  section.main-container section.info-container ul li.detail div p span {
    display: inline-block;
    font-size: 16px;
    color: #676767;
    font-weight: 300;
  }

  section.main-container section.info-container ul li.detail div p span.label {
    width: 90px;
    color: #555;
    font-weight: 300;
    font-size: 15px;
  }

  section.main-container section.info-container ul li.detail div.price {

  }

  section.main-container section.info-container ul li.detail div.price p {

  }

  section.main-container section.info-container ul li.detail div.price p.fixed-price.disable {
    color: #888;
    text-decoration: line-through;
  }

  section.main-container section.info-container ul li.detail div.price p.discount-price {
    font-weight: 400;
    color: #333;
  }

  section.main-container section.info-container ul li.detail div.price p span.won {
    font-weight: 300;
    color: #888;
  }

  section.main-container section.info-container ul li.detail div.totalPrice {

  }

  section.main-container section.info-container ul li.detail div.totalPrice p.quantity {
    display: flex;
    align-items: center;
    margin-bottom: 25px;
  }

  section.main-container section.info-container ul li.detail div.totalPrice p button {
    width: 20px;
    height: 20px;
    background-color: #f1f1f1;
    border-radius: 50%;
    display: flex;
    align-items: center;
    outline: none;
  }

  section.main-container section.info-container ul li.detail div.totalPrice p button.plus {
    margin-right: 8px;
  }

  section.main-container section.info-container ul li.detail div.totalPrice p button.minus {
    margin-left: 8px;
  }

  section.main-container section.info-container ul li.detail div.totalPrice p button svg {
    max-width: 15px;
    width: 100%;
  }

  section.main-container section.info-container ul li.detail div.totalPrice p.result-price {
    font-size: 24px;
    font-weight: 700;
  }



</style>