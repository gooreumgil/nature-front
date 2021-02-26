<template>
  <section class="main-container">
    <Header />

    <section class="inner-container">
      <div class="title-box">
        <h1>상품주문</h1>
        <p>주문 정보를 자세히 확인해주세요.</p>
      </div>

      <div class="order-info-box">
        <ul class="order-item-wrapper">
          <li class="order-item-list head clearfix">
            <div class="order-item-col name-and-img">
              <p>상품정보</p>
            </div>
            <div class="order-item-col quantity">
              <p>수량</p>
            </div>
            <div class="order-item-col price">
              <p>상품금액</p>
            </div>
            <div class="order-item-col discountPrice">
              <p>할인금액</p>
            </div>
            <div class="order-item-col resultPrice">
              <p>할인적용금액</p>
            </div>
          </li>

          <li class="order-item-list clearfix" v-for="(item, index) in items" v-bind:key="index">
            <div class="order-item-col name-and-img">
              <img v-bind:src="item.mainSrcPath" alt="">
              <div class="name-box">
                <p>{{ item.nameKor }}</p>
                <p>{{ item.nameEng }}</p>
              </div>

            </div>
            <div class="order-item-col quantity">
              <p>{{ item.quantity }}</p>
            </div>
            <div class="order-item-col price">
              <p>{{ sumItemPrice(item) | price }} <span class="won">원</span></p>
            </div>
            <div class="order-item-col discountPrice">
              <p>{{ sumItemDiscountPrice(item) | price }} <span class="won">원</span></p>
            </div>
            <div class="order-item-col resultPrice">
              <p id="id_color">{{ getItemTotalPrice(item) | price}} <span class="won">원</span></p>
            </div>
          </li>
        </ul>
      </div>

      <div class="delivery-info-box">
        <div class="title-box">
          <h3>배송정보</h3>
        </div>
      </div>
    </section>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";

export default {
  name: "Index",
  components: {Header},
  data() {
    return {
      items: []
    }
  },

  async created() {
    let orderItems = JSON.parse(this.$cookies.get('order-items'));
    let ids = [];
    orderItems.forEach(orderItem => {
      ids.push(orderItem.id);
    })

    await this.setItems(ids);

  },

  methods: {
    async setItems(ids) {
      try {
        const res = await itemApi.getItemsByIds(ids);

        for (const item of res.data) {
          const quantity = this.getQuantity(item.id);
          this.items.push({
            ...item,
            quantity
          })
        }
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    getQuantity(id) {
      const orderItems = JSON.parse(this.$cookies.get('order-items'));
      let quantity = 1;
      orderItems.forEach(orderItem => {
        if (orderItem.id === id) quantity = orderItem.quantity;
      })

      return quantity;
    },

    sumItemPrice(item) {
      return item.price * item.quantity;
    },

    sumItemDiscountPrice(item) {
      return item.discountPrice * item.quantity;
    },

    getItemTotalPrice(item) {
      return (item.price - item.discountPrice) * item.quantity;
    }
  }
}
</script>

<style scoped>

  section.main-container {
    position: relative;
    box-sizing: border-box;
    padding-top: 90px;
  }

  section.main-container header {
    box-shadow: 0 0 3px 0 #dcdcdc;
  }

  section.main-container section.inner-container {
    box-sizing: border-box;
    padding-top: 60px;
  }

  section.main-container section.inner-container div.title-box {
    max-width: 1260px;
    margin: 0 auto;
    text-align: left;
  }

  section.main-container section.inner-container div.title-box h1 {
    font-size: 20px;
    font-weight: 400;
    color: #555;
  }

  section.main-container section.inner-container div.title-box p {
    font-size: 15px;
    font-weight: 400;
    color: #a0a0a0;
    margin-top: 10px;
  }

  section.main-container section.inner-container div.order-info-box {

  }

  section.main-container section.inner-container div.order-info-box ul {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 40px;
  }

  section.main-container section.inner-container div.order-info-box ul li {
    border-radius: 3px;
    height: 180px;
    border-bottom: 1px solid #eaeaea;
  }

  section.main-container section.inner-container div.order-info-box ul li.head {
    background-color: #f9f9f9;
    height: 60px;
    border-bottom: none;
  }

  section.main-container section.inner-container div.order-info-box ul li.head p {
    font-size: 15px;
    color: #555;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col {
    float: left;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;

  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col span.won {
    font-size: 14px;
    font-weight: 400;
    color: #888;
    display: inline-block;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.quantity {
    width: 20%;

  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.price {
    width: 15%;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.discountPrice {
    width: 15%;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.resultPrice {
    width: 15%;
  }



  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.name-and-img {
    width: 35%;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.name-and-img img {
    max-width: 100px;
    margin-right: 30px;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.name-and-img div.name-box {
    text-align: left;

  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.name-and-img div.name-box p:first-child {
    font-size: 16px;
    font-weight: 400;
    color: #333;
    margin-top: 3px;
  }

  section.main-container section.inner-container div.order-info-box ul li div.order-item-col.name-and-img div.name-box p:last-child {
    margin-top: 5px;
    font-size: 14px;
    font-weight: 400;
    color: #888;
  }

  section.main-container section.inner-container div.delivery-info-box {

  }

  section.main-container section.inner-container div.delivery-info-box div.title-box {
    box-sizing: border-box;
    padding-top: 40px;
  }

  section.main-container section.inner-container div.delivery-info-box div.title-box h3 {
    font-size: 18px;
    font-weight: 400;
    color: #555;
  }



</style>