<template>
  <section class="main-container">
    <Header />

    <section class="inner-container">
      <div class="title-box">
        <h1>상품주문</h1>
        <p>주문 정보를 자세히 확인해주세요.</p>
      </div>

      <div class="order-info-box" v-if="itemInit">
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
              <img v-bind:src="item.mainImgPath" alt="">
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

      <div class="purchase-info clearfix" v-if="userInit">
        <div class="purchase-list pay-info">
          <div class="title">
            <h3>결제정보</h3>
          </div>
          <div class="inner clearfix">
            <div class="inner-row order-by">
              <div class="label-box">
                <span class="label">주문자</span>

              </div>
              <div class="content-box">
                <p>{{ user.name }}</p>
                <span class="divider"></span>
                <p>{{ user.phoneNum1 }} - {{ user.phoneNum2 }} - {{ user.phoneNum3 }}</p>
                <span class="divider"></span>
                <p>{{ user.email }}</p>
              </div>
            </div>

            <div class="inner-row user-address" v-if="user.addressResponseDtos.length > 0">
              <div class="label-box">
                <span class="label">배송지선택</span>

              </div>
              <div class="content-box">
                <button type="button">기본배송지</button>
                <button type="button">신규배송지</button>
                <button type="button">배송지목록</button>
              </div>
            </div>

            <div class="inner-row receiver-name">
              <div class="label-box">
                <span class="label">이름</span>

              </div>
              <div class="content-box">
                <input type="text" v-model="receiver">
              </div>
            </div>

            <div class="inner-row phone-num">
              <div class="label-box">
                <span class="label">연락처</span>

              </div>
              <div class="content-box">
                <input type="text" v-bind:value="user.phoneNum1">
                <input type="text" v-bind:value="user.phoneNum2">
                <input type="text" v-bind:value="user.phoneNum3">
              </div>
            </div>

            <div class="inner-row receive-address">
              <div class="label-box">
                <span class="label">주소</span>

              </div>
              <div class="content-box">
                <div class="zip-code">
                  <input type="text" placeholder="우편번호를 입력해주세요" v-model="zipCode">

                </div>
                <div class="address clearfix">
                  <div class="address-list main">
                    <input type="text" placeholder="주소를 입력해주세요." v-model="mainAddress">
                  </div>
                  <div class="address-list detail">
                    <input type="text" placeholder="상세주소" v-model="detailAddress">
                  </div>

                </div>

                <div class="set-main-address">
                  <input type="checkbox" v-model="setMainAddress"> <p>기본 주소로 등록</p>
                </div>
              </div>
            </div>

            <div class="inner-row user-points">
              <div class="label-box">
                <span class="label">포인트사용</span>

              </div>
              <div class="content-box">
                <input type="text" v-model="usedPointsTmp" v-bind:disabled="isPointCannotUse()">
                <p>P</p>
                <button @click="usePoints" type="button" v-bind:disabled="isPointCannotUse()">사용하기</button>
                <input @change="pointAllUseToggle" type="checkbox" v-bind:disabled="isPointCannotUse()" v-bind:checked="isPointAllUse">
                <p class="all-points-use">모두사용 (보유포인트: <span>{{ getUserOwnPoints() | price }}P</span>)</p>
              </div>
            </div>

            <div class="inner-row payment-method">
              <div class="label-box">
                <span class="label">결제수단</span>

              </div>
              <div class="content-box">
                <div class="btn-list clearfix">

                  <div class="btn-list-inner">
                    <button @click="setPaymentMethod('신용카드')" v-bind:class="{active: isPaymentMethodThis('신용카드')}" type="button">신용카드</button>
                  </div>

                  <div class="btn-list-inner">
                    <button @click="setPaymentMethod('무통장입금')" v-bind:class="{active: isPaymentMethodThis('무통장입금')}" type="button">무통장입금</button>
                  </div>

                  <div class="btn-list-inner">
                    <button @click="setPaymentMethod('휴대폰결제')" v-bind:class="{active: isPaymentMethodThis('휴대폰결제')}" type="button">휴대폰결제</button>
                  </div>

                </div>
              </div>
            </div>

          </div>
        </div>

        <div class="purchase-list price-info" v-if="itemInit">
          <div class="title">
            <h3>결제금액</h3>
          </div>
          <div class="inner">
            <div class="inner-row fixed-price">
              <span class="label">상품금액</span>
              <p>{{ getItemsTotalPrice() | price }} <span class="won">원</span></p>
            </div>
            <div class="inner-row delivery-price">
              <span class="label">배송비</span>
              <p>{{ getDeliveryPrice() | price }} <span class="won">원</span></p>
            </div>
            <div class="inner-row discount-price">
              <span class="label">할인금액</span>
              <p><span class="minus">(-)</span> {{ getFinalDiscountPrice() | price }} <span class="won">원</span></p>
            </div>
            <div class="inner-row used-points">
              <span class="label">포인트 사용</span>
              <p><span class="minus">(-)</span> {{ usedPoints | price }} <span class="won">P</span></p>
            </div>

            <div class="inner-row final-price">
              <span class="label">최종결제금액</span>
              <p id="id_color">{{ getFinalPrice() | price }} <span class="won">원</span></p>
            </div>

            <div class="inner-row pay-btn">
              <button @click="payment" type="button">결제하기</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <Bottom />
    <Footer />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
import userApi from "@/api/UserApi";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import orderApi from "@/api/OrderApi";

export default {
  name: "Index",
  components: {Footer, Bottom, Header},
  data() {
    return {
      items: [],
      user: null,
      itemInit: false,
      userInit: false,
      usedPointsTmp: 0,
      usedPoints: 0,
      paymentMethod: '신용카드',
      setMainAddress: false,
      receiver: null,
      phoneNum1: null,
      phoneNum2: null,
      phoneNum3: null,
      mainAddress: null,
      detailAddress: null,
      zipCode: null,
      isPointAllUse: false
    }
  },

  async created() {
    let orderItems = JSON.parse(this.$cookies.get('order-items'));
    let ids = [];
    orderItems.forEach(orderItem => {
      ids.push(orderItem.id);
    })

    await this.setItems(ids);
    await this.setUser();
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

        this.itemInit = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setUser() {

      const token = this.$cookies.get('token');

      try {
        const res = await userApi.getUser(token);
        this.user = res.data;
        if (this.user.addressResponseDtos.length === 0) {
          this.setMainAddress = true;
        }
        this.receiver = this.user.name;
        this.phoneNum1 = this.user.phoneNum1;
        this.phoneNum2 = this.user.phoneNum2;
        this.phoneNum3 = this.user.phoneNum3;

        if (this.user.addressResponseDtos.length > 0) {
          const defaultAddress = this.user.addressResponseDtos.filter(dto => {
            return dto.isDefault
          });

          if (defaultAddress.length > 0) {
            this.mainAddress = defaultAddress[0].main;
            this.detailAddress = defaultAddress[0].detail;
            this.zipCode = defaultAddress[0].zipCode;
          }
        }


        this.userInit = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    payment() {
      const token = this.$cookies.get('token');


      const receiver = this.receiver;
      const phoneNum1 = this.phoneNum1
      const phoneNum2 = this.phoneNum2
      const phoneNum3 = this.phoneNum3;
      const zipCode = this.zipCode;
      const mainAddress = this.mainAddress;
      const detailAddress = this.detailAddress;
      const usedPoints = this.usedPoints;
      const finalDiscountPrice = this.getFinalDiscountPrice();
      const finalPrice = this.getFinalPrice();
      const deliveryPrice = this.getDeliveryPrice();
      let paymentMethod = this.paymentMethod;
      if (this.paymentMethod === '신용카드') {
        paymentMethod = 'CREDIT_CARD';
      } else if (this.paymentMethod === '무통장입금') {
        paymentMethod = 'DEPOSIT'
      } else if (this.paymentMethod === '휴대폰결제') {
        paymentMethod = 'PHONE';
      }

      const orderItemSaveRequestDtos = [];
      this.items.forEach(item => {
        const orderItem = {};
        orderItem.id = item.id;
        orderItem.itemPrice = item.price;
        orderItem.itemDiscountPrice = item.discountPrice;
        orderItem.itemQuantity = item.quantity;

        orderItemSaveRequestDtos.push(orderItem);
      })

      orderApi.productOrder(token, receiver, phoneNum1, phoneNum2, phoneNum3, zipCode, mainAddress, detailAddress, usedPoints, finalDiscountPrice, finalPrice, deliveryPrice, paymentMethod, orderItemSaveRequestDtos)
      .then((res) => {
        this.$router.replace(res.data + '/complete');
      }).catch((err) => {
        console.log(err);
      })


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
    },

    getItemsTotalPrice() {
      let sum = 0;
      this.items.forEach(item => sum += item.price * item.quantity);
      return sum;
    },

    getFinalDiscountPrice() {
      let sum = 0;
      this.items.forEach(item => sum += item.discountPrice * item.quantity);
      return sum;
    },

    getFinalPrice() {
      return this.getItemsTotalPrice() - this.getFinalDiscountPrice() - this.usedPoints;
    },

    getUserOwnPoints() {
      return this.user.ownPoints === null ? 0 : this.user.ownPoints;
    },

    isPointCannotUse() {
      return this.user.ownPoints === 0;
    },

    isPaymentMethodThis(method) {
      return this.paymentMethod === method;
    },

    setPaymentMethod(method) {
      this.paymentMethod = method;
    },

    getDeliveryPrice() {
      return this.getItemsTotalPrice() > 50000 ? 0 : 2500;
    },

    usePoints() {
      if (this.usedPointsTmp > this.user.ownPoints) {
        alert('보유하신 포인트 이상으로 사용하실 수 없습니다');
        return;
      }

      this.usedPoints = this.usedPointsTmp;
      if (this.usedPointsTmp !== this.user.ownPoints) {
        this.isPointAllUse = false;
      }

    },

    pointAllUseToggle() {
      if (!this.isPointAllUse) {
        this.isPointAllUse = true;
        this.usedPointsTmp = this.user.ownPoints;
        this.usedPoints = this.user.ownPoints;
      }  else {
        this.isPointAllUse = false;
        this.usedPointsTmp = 0;
        this.usedPoints = 0;
      }

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
    height: 180px;
    border-bottom: 1px solid #eaeaea;
  }

  section.main-container section.inner-container div.order-info-box ul li.head {
    background-color: #7ebb34;
    height: 60px;
    border-bottom: none;
  }

  section.main-container section.inner-container div.order-info-box ul li.head p {
    font-size: 15px;
    font-weight: 700;
    color: #fff;
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

  section.main-container section.inner-container div.purchase-info {
    max-width: 1300px;
    width: 100%;
    margin: 0 auto;
    margin-top: 40px;
    text-align: left;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list {
    float: left;
    box-sizing: border-box;
    padding: 20px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info {
    width: 65%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info {
    width: 35%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list div.title {
    text-align: left;
    box-sizing: border-box;
    padding-bottom: 20px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list div.title h3 {
    font-size: 18px;
    font-weight: 400;
    color: #555;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list div.inner div.inner-row:first-child {
    /*border-top: 2px solid #7ebb34;*/
  }
  section.main-container section.inner-container div.purchase-info div.purchase-list div.inner {
    border-top: 2px solid #7ebb34;

  }

  /* pay info */

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row {
    display: flex;
    align-items: center;
    /*height: 60px;*/
    border-bottom: 1px solid #eaeaea;
    font-size: 14px;
    position: relative;
    padding: 20px 0;
  }



  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.label-box  {
    display: flex;
    align-items: center;
    box-sizing: border-box;
    padding-left: 25px;
    width: 140px;
    background-color: #f9f9f9;
    height: 100%;
    position: absolute;
    left: 0;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.label-box span {
    font-weight: 700;
    color: #555;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.content-box {
    box-sizing: border-box;
    padding-left: 165px;
    display: flex;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.content-box input {
    box-sizing: border-box;
    border: 1px solid #ddd;
    padding: 8px 12px;
    border-radius: 3px;
    outline-color: #eaeaea;
    font-size: 14px;
    font-weight: 400;
    color: #555;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.content-box input::placeholder {
    color: #a0a0a0;
    font-weight: 400;
    font-size: 14px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row div.content-box span.divider {
    height: 10px;
    width: 1px;
    display: inline-block;
    background-color: #bbb;
    margin: 0 10px;
    transform: translateY(3px);
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receiver-name {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receiver-name div.content-box {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receiver-name div.content-box input {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.phone-num {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.phone-num .content-box {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.phone-num .content-box input {

    width: 80px;
    margin-right: 10px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.phone-num .content-box input:last-child {
    margin-right: 0;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address {
    /*height: 110px;*/
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address .content-box {
    flex-direction: column;
    width: 100%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address .content-box div.zip-code {
    box-sizing: border-box;
    padding-bottom: 12px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address .content-box div.address {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address .content-box div.address div.address-list {
    float: left;
    box-sizing: border-box;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.address div.address-list.main {
    width: 60%;
    padding-right: 8px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.address div.address-list.detail {
    width: 40%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address .content-box input {

    width: 100%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.zip-code input {
    width: 40%;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box input::placeholder {
    font-size: 13px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.set-main-address {
    display: flex;
    align-items: center;
    height: 20px;
    margin-top: 10px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.set-main-address input[type=checkbox] {
    width: 13px;
    height: 13px;
    margin: 0;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.receive-address
  .content-box div.set-main-address p {
    font-size: 13px;
    color: #555;
    font-weight: 400;
    margin-left: 5px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points {
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box {
    display: flex;
    align-items: center;

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box input {
    text-align: right;
    width: 160px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box input[type=checkbox] {
    margin-left: 10px;
    width: auto;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box p {
    margin: 0 10px;
    font-size: 14px;
    font-weight: 700;
    color: #333;

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box p.all-points-use {
    font-weight: 400;
    color: #555;

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box p.all-points-use span {
    font-weight: 700;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box button {
    cursor: pointer;
    height: 34px;
    border-radius: 3px;
    box-sizing: border-box;
    padding: 5px 10px;
    margin-left: 10px;
    border: 1px solid #ddd;
    background-color: #fff;
    outline-color: #eaeaea;
    color: #555;
    font-size: 14px;
    font-weight: 400;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.user-points .content-box button:disabled {
    cursor: auto;
    color: #bbb;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box {
    flex-direction: column;
    width: 100%;
    padding-left: 155px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box div.btn-list {
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box .btn-list-inner {
    float: left;
    width: 33.33%;
    box-sizing: border-box;
    padding-left: 10px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box .btn-list-inner:first-child {
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box .btn-list-inner button {
    cursor: pointer;
    outline: none;
    width: 100%;
    height: 45px;
    border-radius: 3px;
    background-color: #fff;
    border: 1px solid #ddd;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.pay-info div.inner div.inner-row.payment-method .content-box .btn-list-inner button.active {
    background-color: #555;
    color: #fff;
    font-weight: 700;
    border: 0;
  }

  /* price-info */
  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info {

  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner {
    background-color: #f2f3f6;
    padding: 20px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 20px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row span.label {
    font-size: 15px;
    color: #555;
    font-weight: 400;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row p {
    font-size: 18px;
    font-weight: 700;
    color: #333;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row p span.won {
    font-size: 14px;
    font-weight: 400;
    color: #555;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row p span.minus {
    font-size: 13px;
    font-weight: 400;
    color: #888;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row.final-price p {
    font-size: 24px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row.pay-btn {
    margin-top: 20px;
  }

  section.main-container section.inner-container div.purchase-info div.purchase-list.price-info div.inner div.inner-row.pay-btn button {
    cursor: pointer;
    outline: none;
    background-color: #7ebb34;
    width: 100%;
    height: 60px;
    border-radius: 3px;
    color: #fff;
    font-size: 18px;
    font-weight: 700;
  }



</style>