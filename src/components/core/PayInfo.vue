<template>
  <div class="pay-container">
    <div class="title-box">
      <h3>결제정보</h3>
    </div>

    <div class="pay-wrapper">
      <div class="pay-inner">
        <div class="inner-list order-price">
          <div class="top">
            <p>주문 금액</p>
            <span>{{ getOrderPrice() | price}} 원</span>
          </div>
        </div>
        <div class="inner-list discount-price">
          <div class="top">
            <p>할인 금액</p>
            <span>{{ getOrderDiscountPrice() | price }} 원</span>
          </div>
        </div>
        <div class="inner-list use-points">
          <div class="top">
            <p>포인트 사용</p>
            <span>{{ order.usedPoints | price }} P</span>
          </div>
        </div>
        <div class="inner-list final-price">
          <div class="top">
            <p>결제 금액</p>
            <span>{{ order.finalPrice | price }} 원</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PayInfo",
  props: {
    order: {
      value: null
    }
  },

  methods: {
    getOrderPrice() {
      let sum = 0;
      const orderitems = this.order.orderItemResponseDtos;

      orderitems.forEach(orderItem => sum += orderItem.itemPrice);
      sum += this.order.deliveryResponseDto.deliveryPrice;
      return sum;
    },

    getOrderDiscountPrice() {
      let sum = 0;
      const orderitems = this.order.orderItemResponseDtos;

      orderitems.forEach(orderItem => sum += orderItem.itemDiscountPrice);
      return sum;
    }
  }
}
</script>

<style scoped>
  div.pay-container {
    margin-top: 40px;
    box-sizing: border-box;
    padding: 0 10px;
  }

  div.pay-container div.title-box {

  }

  div.pay-container div.title-box h3 {
    font-size: 17px;
    font-weight: 700;
    color: #333;
  }

  div.pay-container div.pay-wrapper {
    margin-top: 20px;
  }

  div.pay-container div.pay-wrapper div.pay-inner {
    display: flex;
    align-items: center;
    justify-content: left;
    border-top: 1px solid #eaeaea;
    border-bottom: 1px solid #eaeaea;
    border-left: 1px solid #eaeaea;
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list {
    box-sizing: border-box;
    padding: 20px;
    width: 25%;
    border-right: 1px solid #eaeaea;
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list .top {

  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list .top p {
    font-size: 15px;
    font-weight: 400;
    color: #888;
    margin-bottom: 10px;
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list .top span {
    font-size: 18px;
    font-weight: 700;
    color: #555;
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list.final-price {
    background-color: #f9f9f9;
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list.final-price p {
  }

  div.pay-container div.pay-wrapper div.pay-inner div.inner-list.final-price span {
    color: #7ebb34;
  }

</style>