<template>
  <ul class="order-list-wrapper">
    <div class="title-box">
      <h3>주문/배송조회</h3>
      <p>고객님의 주문 내역입니다.</p>
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
        <button v-if="isNotOrderCancel(order)" class="goOrderDetail" @click="goOrderDetail(order.id)" type="button">주문상세</button>
      </div>
      <div class="inner-col img">
        <div class="img-box">
          <img v-bind:src="order.orderItemResponseDtos[0].mainImgPath" alt="">
        </div>
        <p class="item-name">
          {{ order.orderItemResponseDtos[0].itemNameKor }} <span v-if="isOrderItemsMoreThanOne(order)">외 {{ countOrderItems(order) }}건</span>
        </p>
      </div>
      <div class="inner-col item-price">
        <p>{{ order.finalPrice  | price}}</p>
      </div>
      <div class="inner-col delivery-price">
        <p>{{ order.deliveryResponseDto.deliveryPrice  | price}}</p>
      </div>
      <div class="inner-col delivery-status">
        <p v-bind:class="{active: isOrderStatusComp(order)}">{{ getDeliveryStatus(order) }}</p>
        <button @click="cancelOrder(order.id)" type="button" v-if="isDeliveryStatusReady(order)">주문취소</button>
        <button class="confirm-order" @click="confirmOrder(order)" type="button" v-if="canOrderConfirm(order)">구매확정</button>
      </div>
    </li>
    <div class="order-list-empty" v-if="isOrdersEmpty()">
      <div class="empty-inner">
        <span class="img-helper">
            <ExclamationIcon v-bind:stroke="'#a0a0a0'" />

        </span>
        <p>주문 내역이 존재하지 않습니다.</p>
      </div>
    </div>
  </ul>
</template>

<script>
import commonUtils from "@/utils/commonUtils";
import orderApi from "@/api/OrderApi";
import ExclamationIcon from "@/components/core/ExclamationIcon";
export default {
  name: "OrderList",
  components: {ExclamationIcon},
  props: {
    orders: {
      value: []
    },
    cancelOrder: {
      type: Function
    },
    goOrderDetail: {
      type: Function
    }
  },

  methods: {
    getOrderAt(time) {
      return commonUtils.localDateTimeToYearMonthDay(time);
    },

    getDeliveryStatus(order) {
      if (order.status === 'CANCEL') return '결제취소';
      const deliveryStatus = order.deliveryResponseDto.status;

      if (order.status === 'COMP') return '구매완료';
      else if (deliveryStatus === 'READY') return '결제완료';
      else if (deliveryStatus === 'ONGOING') return '배송중';
      else return '배송완료';
    },

    isDeliveryStatusReady(order) {
      return order.deliveryResponseDto.status === 'READY' && order.status !== 'CANCEL';
    },

    canOrderConfirm(order) {

      if (order.status === 'COMP') {
        return false;
      }


      return order.deliveryResponseDto.status === 'ONGOING' || order.deliveryResponseDto.status === 'COMP';
    },

    confirmOrder(order) {

      const token = this.$cookies.get('token');

      try {
        orderApi.confirmOrder(token, order.id);
        alert('구매확정 되었습니다!');
        order.status = 'COMP';
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    isOrderStatusComp(order) {
      return order.status === 'COMP';
    },

    isNotOrderCancel(order) {
      return order.status !== 'CANCEL';
    },

    isOrdersEmpty() {
      return this.orders.length === 0;
    },

    isOrderItemsMoreThanOne(order) {
      return order.orderItemResponseDtos.length > 1;
    },

    countOrderItems(order) {
      return order.orderItemResponseDtos.length - 1;
    }

  },


}
</script>

<style scoped>
  ul {
    box-sizing: border-box;
    padding-top: 30px;
    padding-left: 10px;
    padding-right: 10px;
  }

  ul div.title-box {
    box-sizing: border-box;
    padding: 10px 0;
  }

  ul div.title-box h3 {
    font-size: 17px;
  }

  ul div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
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
    flex-direction: column;
  }

  ul li div.inner-col.no button.goOrderDetail {
    cursor: pointer;
    outline: none;
    background-color: transparent;
    border-radius: 3px;
    border: 1px solid #ddd;
    color: #555;
    padding: 3px 8px;
    margin-top: 5px;
    font-size: 12px;
  }

  ul li div.inner-col.img {
    width: 40%;
    justify-content: left;
  }

  ul li div.inner-col.item-price {
    width: 15%;
  }

  ul li div.inner-col.item-price {
    width: 15%;
  }

  ul li div.inner-col.delivery-status {
    flex-direction: column;
    width: 15%;
  }

  ul li div.inner-col.delivery-status p.active {
    font-weight: 700;
    color: #7ebb34;
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
    width: 15%;
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

  ul li div.inner-col.delivery-status button.confirm-order {
    color: #0fafbe;
  }



  ul li div.inner-col.img p.item-name {
  }

  ul div.order-list-empty {
    display: flex;
    width: 100%;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding: 150px 0;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.order-list-empty div.empty-inner {
  }

  ul div.order-list-empty div.empty-inner span.img-helper {
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #ddd;

  }

  ul div.order-list-empty div.empty-inner span.img-helper svg {
    max-width: 50px;
    width: 100%;
  }

  ul div.order-list-empty div.empty-inner p {
    margin-top: 30px;
    font-size: 14px;
  }


</style>