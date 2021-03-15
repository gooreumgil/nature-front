<template>
  <section class="main-container">
    <Header v-bind:transparent="false" v-bind:header-tab="'my-page'" />
    <div class="inner-container" v-if="init">
      <div class="my-page-wrapper clearfix">
        <div class="my-page-list nav">
          <MyPageNav v-bind:user="user" v-bind:set-current-tab="setCurrentTab" v-bind:current-tab="currentTab"/>

        </div>
        <div class="my-page-list content">
          <div class="basic-info" v-if="basicInfoView">
            <div class="title">
              <h3>
                <span>{{ user.name }}</span>님 환영합니다!
              </h3>

            </div>
            <div class="detail">
              <div>
                <h4>배송중</h4>
                <p>{{ deliveryOnGoingTotal }}</p>
              </div>
              <div>
                <h4>포인트</h4>
                <p>{{ getOwnPoints() | price }}</p>
              </div>
              <div>
                <h4>구매후기</h4>
                <p>{{ reviewTotal }}</p>
              </div>
            </div>
          </div>

          <div class="order-detail">
            <div class="title-box">
              <h3>주문상세내역</h3>
              <p>해당 주문의 상세 내역입니다.</p>
            </div>

            <div class="status">
              <p class="order-date">주문날짜: {{ convertTimeToStr(order.orderAt, 'monthAndDay') }}</p>
              <p class="delivery-status">· {{ getDeliveryStatus() }}</p>
              <div class="order-status">
                <button class="cancel" @click="cancelOrder()" v-if="isOrderStatus('ORDER') && isDeliveryStatus('READY')" type="button">주문취소</button>
                <button @click="confirmOrder()" v-if="isOrderStatus('ORDER') && isNotDeliveryStatus('READY')" type="button">구매확정</button>
                <button @click="goReviewTab('review')" v-if="isOrderStatus('COMP') && hasItemCanReview()" type="button">리뷰쓰기</button>
              </div>
            </div>

            <div class="item-container">
              <ul class="item-wrapper clearfix">

                <li class="item-list head">
                  <div class="list-inner item-info">
                    <p>상품정보</p>
                  </div>
                  <div class="list-inner item-price">
                    <p>상품가격</p>
                  </div>
                  <div class="list-inner item-discount-price">
                    <p>할인금액</p>
                  </div>
                </li>
                <li class="item-list content" v-for="(item, index) in order.orderItemResponseDtos" v-bind:key="index">
                  <div class="list-inner item-info">
                    <div class="img-box">
                      <img v-bind:src="item.mainImgPath" alt="">
                    </div>
                    <div class="info-box">
                      <p class="name-kor">{{ item.itemNameKor }}</p>
                      <p class="name-eng">{{ item.itemNameEng }}</p>
                      <p class="quantity">수량: {{ item.itemQuantity }}개</p>
                    </div>
                  </div>
                  <div class="list-inner item-price">
                    <p>{{ getItemPrice(item) | price }} <span class="won">원</span></p>
                  </div>
                  <div class="list-inner item-discount-price">
                    <p>
                      <span class="minus">(-)</span> {{ getItemDiscountPrice(item) | price }} <span class="won">원</span>
                    </p>

                  </div>
                </li>
              </ul>
            </div>
          </div>

          <DeliveryInfo v-bind:delivery="order.deliveryResponseDto" />
          <PayInfo v-bind:order="order" />
        </div>
      </div>
    </div>

    <WriteReviewModal v-if="writeModalView"
                      v-bind:review-item="reviewItem"
                      v-bind:write-modal-view-toggle="writeModalViewToggle"
                      v-bind:write-review-complete="writeReviewComplete"/>

    <Bottom />
    <Footer />
    <SourceCodeLinkModal />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import orderApi from "@/api/OrderApi";
import MyPageNav from "@/components/core/MyPageNav";
import userApi from "@/api/UserApi";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import commonUtils from "@/utils/commonUtils";
import WriteReviewModal from "@/components/core/WriteReviewModal";
import DeliveryInfo from "@/components/core/DeliveryInfo";
import PayInfo from "@/components/core/PayInfo";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";
export default {
  name: "OrderDetail",
  components: {
    SourceCodeLinkModal,
    PayInfo,
    DeliveryInfo,
    WriteReviewModal, Footer, Bottom, MyPageNav, Header},
  data() {
    return {
      init: false,
      order: null,
      user: null,
      reviewItem: null,
      currentTab: 'orderAndDelivery',
      deliveryOnGoingTotal: 0,
      reviewTotal: 0,
      basicInfoView: true,
      writeModalView: false,
      s3UrlPrefix: 'https://nature-portfolio.s3.ap-northeast-2.amazonaws.com/',
    }
  },

  async created() {
    this.$store.commit('SET_CURRENT_MY_PAGE_TAB', 'orderAndDelivery');
    const orderId = this.$route.params.id;
    await this.setOrder(orderId);
    await this.setUser();
    await this.setDeliveryTotal();
    await this.setReviewsTotal();


  },
  methods: {
    async setUser() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getUser(token);
        this.user = res.data;
        this.init = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setDeliveryTotal() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getTotalByDeliveryStatus(token, 'ONGOING');
        this.deliveryOnGoingTotal = res.data;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setReviewsTotal() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getReviewsTotal(token);
        this.reviewTotal = res.data;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setOrder(orderId) {

      const token = this.$cookies.get('token');
      try {
        const res = await orderApi.getOrder(token, orderId);
        this.order = res.data;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    cancelOrder() {
      const check = confirm('정말로 결제를 취소하시겠습니까?');
      if (!check) {
        return;
      }

      const token = this.$cookies.get('token');
      const id = this.order.id;

      try {
        orderApi.cancelOrder(token, id);
        alert('결제가 취소되었습니다.');
        this.$router.replace('/my-page');
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async confirmOrder() {
      const check = confirm('구매를 확정하시겠습니까?');
      if (!check) {
        return;
      }

      const token = this.$cookies.get('token');
      const id = this.order.id;

      try {
        await orderApi.confirmOrder(token, id);
        const check = confirm('구매가 확정되었습니다. 리뷰를 작성하시겠습니까?');
        if (check) {
          this.$store.commit('SET_CURRENT_MY_PAGE_TAB', 'review');
          this.$store.commit('SET_CURRENT_REVIEW_NAV', 'canReview');
          await this.$router.push('/my-page');
        }
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }


    },

    getDeliveryStatus() {
      const deliveryStatus = this.order.deliveryResponseDto.status;
      if (deliveryStatus === 'READY') return '배송준비중';
      else if (deliveryStatus === 'ONGOING') return '배송중'
      else if (deliveryStatus === 'COMP') return '배송완료';
    },

    isDeliveryStatus(status) {
      return this.order.deliveryResponseDto.status === status;
    },

    isNotDeliveryStatus(status) {
      return this.order.deliveryResponseDto.status !== status;
    },

    isOrderStatus(status) {
      return this.order.status === status;
    },

    hasItemCanReview() {
      const orderItems = this.order.orderItemResponseDtos;
      const filter = orderItems.filter(orderItem => orderItem.isLeaveReview === false);
      return filter.length > 0;

    },

    setCurrentTab(tab) {
      this.$store.commit('SET_CURRENT_MY_PAGE_TAB', tab);
      this.$router.push('/my-page')

    },

    goReviewTab() {
      this.$store.commit('SET_CURRENT_MY_PAGE_TAB', 'review');
      this.$store.commit('SET_CURRENT_REVIEW_NAV', 'canReview');
      this.$router.push('/my-page');
    },

    writeReviewComplete(review) {
      this.myReviews.forEach(myReview => {
        if (myReview.id === review.id) {
          const indexOfReview = this.myReviews.indexOf(myReview);
          this.myReviews.splice(indexOfReview, 1);
        }
      })
    },

    getItemPrice(item) {
      return item.itemPrice * item.itemQuantity;
    },

    getItemDiscountPrice(item) {
      return item.itemDiscountPrice * item.itemQuantity;
    },

    getOwnPoints() {
      return this.user.ownPoints === null ? 0 : this.user.ownPoints;
    },

    isCurrentTabThis(tab) {
      return this.currentTab === tab;
    },

    convertTimeToStr(time, type) {
      if (type === 'monthAndDay') return commonUtils.localDateTimeToYearMonthDay(time);
      else return commonUtils.localDateTimeToYearMonthDayHourMinutes(time);

    },


    writeModalViewToggle(item) {
      this.reviewItem = item;
      this.writeModalView = !this.writeModalView;
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

  section.main-container .inner-container {
    box-sizing: border-box;
    padding-top: 90px;
  }

  section.main-container .inner-container div.my-page-wrapper {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    margin-top: 40px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list {
    float: left;
    box-sizing: border-box;
    padding: 10px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.nav {
    width: 20%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content {
    width: 80%;
    text-align: left;
    padding-left: 20px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info {
    box-sizing: border-box;
    padding-top: 15px;
    padding-left: 10px;
    padding-right: 10px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title h3 {
    font-size: 16px;
    font-weight: 400;
    color: #888;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title h3 span {
    font-weight: 700;
    color: #333;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.detail {
    margin-top: 20px;
    display: flex;
    align-items: center;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.detail div {
    flex-direction: column;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100px;
    height: 80px;
    /*background-color: #f9f9f9;*/
    border-radius: 5px;
    margin-right: 10px;
    border: 1px solid #eaeaea;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.detail div h4 {
    margin-bottom: 8px;
    font-size: 15px;
    font-weight: 400;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.detail div p {
    color: #7ebb34;
    font-weight: 700;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail {
    box-sizing: border-box;
    padding-top: 30px;
    padding-left: 10px;
    padding-right: 10px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.title-box {
    box-sizing: border-box;
    padding: 10px 0;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.title-box h3 {
    font-size: 17px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status {
    position: relative;
    box-sizing: border-box;
    padding: 15px;
    border: 1px solid #eaeaea;
    border-radius: 5px;
    display: flex;
    align-items: center;
    margin-top: 10px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status p {
    font-size: 14px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status p.order-date {
    color: #555;
    font-weight: 400;
    margin-right: 20px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status p.delivery-status {
    font-weight: 700;
    color: #7ebb34;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status div.order-status {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status div.order-status button {
    background-color: #fff;
    border-radius: 3px;
    padding: 5px 10px;
    border: 1px solid #eaeaea;
    font-size: 12px;
    color: #0fafbe;
    outline-color: #eaeaea;
    cursor: pointer;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.status div.order-status button.cancel {
    color: #ff0974;

  }


  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.item-container {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.item-container ul.item-wrapper {
    position: relative;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.item-container ul.item-wrapper li {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.order-detail div.item-container ul.item-wrapper li.head {
    height: 50px;
    background-color: #f9f9f9;
    margin-top: 20px;
    border-top: 1px solid #eaeaea;
    border-bottom: 1px solid #eaeaea;
    padding: 0;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li div.list-inner {
    float: left;
    box-sizing: border-box;
    padding: 20px 0;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.head div.list-inner p {
    font-size: 14px;
    font-weight: 400;
    color: #333;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li div.list-inner.item-info {
    width: 60%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li div.list-inner.item-price {
    width: 20%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li div.list-inner.item-discount-price {
    width: 20%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content {
    height: 158px;
    box-sizing: border-box;
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner {
    padding: 0;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info {
    justify-content: left;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.img-box {
    width: 100px;
    height: 118px;
    display: flex;
    align-items: center;
    margin-right: 20px;
  }


  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.img-box img {
    width: 100%;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.info-box {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.info-box p.name-kor {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.info-box p.name-eng {
    font-size: 14px;
    color: #888;
    font-weight: 300;
    margin-top: 8px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-info div.info-box p.quantity {
    font-size: 14px;
    color: #888;
    font-weight: 300;
    margin-top: 5px;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-price {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-discount-price {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-discount-price p {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-discount-price p span.minus {
    font-size: 14px;
    display: inline-block;
    color: #888;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content
  div.order-detail div.item-container ul.item-wrapper li.content div.list-inner.item-discount-price p span.won {

  }


</style>