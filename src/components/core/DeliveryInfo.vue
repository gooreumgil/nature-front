<template>
  <div class="delivery-container">
    <div class="title-box">
      <h3>배송정보</h3>
    </div>

    <div class="delivery-wrapper">
      <div class="delivery-inner">
        <div class="inner-list label-box">
          <div class="receiver">
            <p>받는사람</p>
          </div>
          <div class="phone-num">
            <p>연락처</p>
          </div>
          <div class="address">
            <p>주소</p>
          </div>
          <div class="memo">
            <p>메모</p>
          </div>
        </div>
        <div class="inner-list content-box">
          <div class="receiver">
            <p>{{ delivery.receiver }}</p>
          </div>
          <div class="phone-num">
            <p>{{ getPhoneNum() }}</p>
          </div>
          <div class="address">
            <p>{{ getAddress() }}</p>
          </div>
          <div class="memo">
            <p>{{ getDeliveryMemo() }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DeliveryInfo",
  props: {
    delivery: {
      value: null
    }
  },

  methods: {
    getPhoneNum() {
      return this.delivery.phoneNum1 + ' - ' + this.delivery.phoneNum2 + ' - ' + this.delivery.phoneNum3;
    },

    getAddress() {
      return '[' + this.delivery.zipCode + '] ' + this.delivery.mainAddress + ' ' + this.delivery.detailAddress;
    },

    getDeliveryMemo() {
      return this.delivery.memo === null ? '없음' : this.delivery.memo;
    }
  }
}
</script>

<style scoped>
  div.delivery-container {
    margin-top: 40px;
    box-sizing: border-box;
    padding: 0 10px;
  }

  div.delivery-container div.title-box {

  }

  div.delivery-container div.title-box h3 {
    font-size: 17px;
    font-weight: 700;
    color: #333;
  }

  div.delivery-container div.delivery-wrapper {
    margin-top: 20px;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner {
    display: flex;
    align-items: center;
    border-top: 1px solid #eaeaea;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list {

  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list div {
    display: flex;
    align-items: center;
    justify-content: left;
    height: 60px;
    border-bottom: 1px solid #eaeaea;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list div.receiver {
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list div.phone-num {

  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list div.address {

  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list div.memo {

  }


  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.label-box {
    width: 10%;
    background-color: #f9f9f9;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.label-box div {
    box-sizing: border-box;
    padding: 20px;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.label-box p {
    font-size: 14px;
    color: #333;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.content-box {
    width: 90%;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.content-box p {
    font-size: 14px;
    color: #555;
  }

  div.delivery-container div.delivery-wrapper div.delivery-inner div.inner-list.content-box div {
    justify-content: left;
    box-sizing: border-box;
    padding: 20px;
  }


</style>