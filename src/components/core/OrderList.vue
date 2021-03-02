<template>
  <ul class="order-list-wrapper">
    <div class="title-box">
      <h3>주문/배송조회</h3>
    </div>
    <li class="order-list clearfix head">
      <div class="inner-col no">
        <p>주문일</p>
      </div>
      <div class="inner-col img">
        <p>상품정보</p>
      </div>
      <div class="inner-col item-price">
        <p>결제금액</p>
      </div>
      <div class="inner-col delivery-price">
        <p>배송비</p>
      </div>
      <div class="inner-col delivery-status">
        <p>상태</p>
      </div>
    </li>
    <li class="order-list clearfix" v-for="(order, index) in orders" v-bind:key="index">
      <div class="inner-col no">
        <p>{{ getOrderAt(order.orderAt) }}</p>
      </div>
      <div class="inner-col img">
        <div class="img-box">
          <img v-bind:src="order.orderItemResponseDtos[0].mainSrcPath" alt="">
        </div>
        <p>{{ order.orderItemResponseDtos[0].itemNameKor }}</p>
      </div>
      <div class="inner-col item-price">
        <p>{{ order.finalPrice  | price}}</p>
      </div>
      <div class="inner-col delivery-price">
        <p>{{ order.deliveryResponseDto.deliveryPrice  | price}}</p>
      </div>
      <div class="inner-col delivery-status">
        <p>{{ getDeliveryStatus(order.deliveryResponseDto.status) }}</p>
        <button @click="cancelOrder(order.id)" type="button" v-if="isDeliveryStatusReady">주문취소</button>
      </div>
    </li>
  </ul>
</template>

<script>
import commonUtils from "@/utils/commonUtils";
export default {
  name: "OrderList",
  props: {
    orders: {
      value: []
    },
    cancelOrder: {
      type: Function
    }
  },

  methods: {
    getOrderAt(time) {
      console.log(time);
      return commonUtils.localDateTimeToYearMonthDay(time);
    },

    getDeliveryStatus(status) {
      if (status === 'READY') return '결제완료';
      else if (status === 'ONGOING') return '배송중';
      else return '배송완료'
    },

    isDeliveryStatusReady() {
      return this.orders.deliveryResponseDto.status === 'READY';
    },


  },


}
</script>

<style scoped>
  ul {
    box-sizing: border-box;
    padding-top: 50px;
  }

  ul div.title-box {

  }

  ul div.title-box h3 {
    font-size: 20px;
  }

  ul li {
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
    height: 120px;
  }

  ul li.head {
    height: 50px;
    background-color: #f9f9f9;
    margin-top: 20px;
    border-top: 1px solid #eaeaea;
    border-bottom: 1px solid #eaeaea;
    padding: 0;
  }

  ul li.head div.inner-col p {
    font-size: 14px;
    font-weight: 400;
    color: #333;
  }



  ul li div.inner-col {
    float: left;
    box-sizing: border-box;
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  ul li div.inner-col.no {
    width: 15%;
  }

  ul li div.inner-col.img {
    width: 40%;
    justify-content: left;
  }

  ul li div.inner-col.item-price {
    width: 15%;
  }

  ul li div.inner-col.delivery-price {
    width: 15%;
  }

  ul li div.inner-col.delivery-status {
    flex-direction: column;
    width: 15%;
  }

  ul li div.inner-col.no p {
    font-size: 14px;
  }

  ul li.head div.inner-col.img {
    justify-content: center;
  }

  ul li div.inner-col.img div.img-box {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #f9f9f9;
    width: 80px;
    margin-right: 20px;
  }

  ul li div.inner-col.img div.img-box img {
    width: 100%;
  }

  ul li div.inner-col.item-price {
    font-size: 14px;
  }

  ul li div.inner-col.delivery-price {
    font-size: 14px;
  }

  ul li div.inner-col.delivery-status p {
    font-size: 14px;
  }

  ul li div.inner-col.delivery-status button {
    cursor: pointer;
    outline: none;
    background-color: #fff;
    border-radius: 3px;
    border: 1px solid #ddd;
    padding: 3px 10px;
    margin-top: 8px;
    font-size: 12px;
    font-weight: 400;
    color: #ff0974;
  }

</style>