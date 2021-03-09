<template>
  <div class="delivery-container">
    <div class="title-box">
      <h3>배송정보</h3>
    </div>

    <div class="delivery-wrapper">
      <div class="delivery-inner">
        <div class="label-box">
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
        <div class="content-box">
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
            <p>{{ delivery.memo }}</p>
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
    }
  }
}
</script>

<style scoped>
  div.delivery-container {
    margin-top: 30px;
    box-sizing: border-box;
    padding: 0 10px;
  }

  div.delivery-container div.title-box {

  }

  div.delivery-container div.title-box h3 {
    font-size: 16px;
    font-weight: 700;
    color: #333;
  }

</style>