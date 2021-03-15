<template>
  <section class="main-container">
    <Header v-bind:transparent="false" v-bind:header-tab="'item'" />

    <section class="info-container" v-if="init">
      <ul class="info-wrapper clearfix">
        <li class="info-list img">
          <div class="inner-box">
            <img v-bind:src="item.mainImgPath" alt="">
          </div>

        </li>
        <li class="info-list detail">
          <div class="out-box category">
            <p>Product > {{ item.category }}</p>
          </div>
          <div class="out-box name-and-description">
            <h2>{{ item.nameKor }}</h2>
            <h3>{{ item.nameEng }}</h3>
            <p>{{ item.description }}</p>
          </div>

          <div class="out-box price">
            <p class="fixed-price" v-bind:class="{disable: item.discountPrice > 0}">
              <span class="label">판매가</span> {{ item.price | price}} <span class="won">원</span>
            </p>
            <p class="discount-price">
              <span class="label">할인가</span> {{ sumDiscountPrice(item) | price }} <span class="won">원</span>
            </p>
          </div>

          <div class="out-box capacity-and-savePoints">
            <p class="capacity">
              <span class="label">용량</span> {{ item.capacity }} ml
            </p>
<!--            <p class="savePoints">-->
<!--              <span class="label">적립금</span> {{ item.savePoints | price }} 원-->
<!--            </p>-->
          </div>

          <div class="out-box totalPrice">
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
              <span class="label">최종금액</span>{{ getTotalPrice(item) | price }} <span class="won">원</span>
            </p>
          </div>

          <div class="out-box buy clearfix">
            <div class="buy-col like">
              <div class="buy-inner-box">
                <button @click="itemLike" type="button">
                  <LikeIcon v-bind:stroke="getStroke()" v-bind:fill="getFill()" />
                </button>

              </div>

            </div>
            <div class="buy-col cart">
              <div class="buy-inner-box">
                <button @click="addCart(item.id)" type="button">
                  <CartIcon v-bind:stroke="'#888'" />장바구니
                </button>
              </div>
            </div>
            <div class="buy-col purchase">
              <div class="buy-inner-box">
                <button @click="productOrder(item)" type="button">구매하기</button>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </section>

    <section class="detail-container" v-if="init">
      <nav class="detail-tab clearfix">
        <div class="nav-inner" v-for="(tab, index) in tabs" v-bind:key="index">
          <button type="button" @click="setCurrentTab(tab.val)" v-bind:class="{active: isCurrentTab(tab.val)}">{{ tab.name }}</button>
        </div>
      </nav>

      <div class="detail-info-box" v-if="currentTab === 'detail'">
        <img v-bind:src="item.detailImgPath" alt="">
      </div>

      <div class="item-qna-box" v-if="qnaInit && currentTab === 'qna'">
        <div class="item-qna-inner">
          <div class="title-box">
            <h3>상품문의</h3>
            <p></p>
          </div>
          <form @submit.prevent="saveQna">
            <textarea cols="30" rows="10" placeholder="문의할 내용을 입력해주세요." v-model="qnaContent"></textarea>
            <button type="submit">쓰기</button>
            <button type="reset">취소</button>
            <div class="secret-check">
              <span>비밀글</span>
              <input type="checkbox" v-model="isQnaSecret">
            </div>
          </form>
        </div>

        <div class="qna-empty" v-if="qnaListIsEmpty()">
          <div class="qna-empty-inner">
            <CommentIcon v-bind:stroke="'#a0a0a0'" />
            <h4>작성된 문의가 없습니다.</h4>
            <p>궁금하신점이 있다면, 문의를 남겨주세요.</p>
          </div>
        </div>

        <ul class="qna-wrapper" v-if="!qnaListIsEmpty()">
          <li class="qna-list clearfix" v-bind:class="{contentShow: qna.showContent}" v-for="(qna, index) in qnaList.content" v-bind:key="index">
            <div class="list-inner" @click="qnaShowContentToggle(qna)">
              <div class="status">
                <p>{{ getQnaStatus(qna.status) }}</p>
              </div>
              <div class="content">
                <p>{{ qnaContentSlice(qna.content) }}</p>
              </div>
              <div class="writer">
                <p>{{ qna.writer }}</p>
              </div>
              <div class="wroteAt">
                <p>{{ convertTimeToStr(qna.wroteAt, 'monthAndDay') }}</p>
              </div>
            </div>

            <div class="qna-content" v-if="qna.showContent">
              <p>{{ qna.content }}</p>
            </div>
          </li>
        </ul>


      </div>

      <ItemReviews v-if="reviewInit && isCurrentTab('review')"
                   v-bind:reviews="reviews"
                   v-bind:convert-time-to-str="convertTimeToStr"
                   v-bind:s3-url-prefix="s3UrlPrefix" v-bind:review-img-modal-show="reviewImgModalShow"/>


    </section>

    <Bottom />
    <Footer />
    <CartModal v-bind:cart-add-view="cartAddView" :shopping-keep-going="shoppingKeepGoing"/>
    <ReviewImageModal v-if="reviewImgModalView" v-bind:img-src="reviewImgSrc" v-bind:review-img-modal-close="reviewImgModalClose" />

    <SourceCodeLinkModal />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
import PlusIcon from "@/components/icon/PlusIcon";
import MinusIcon from "@/components/icon/MinusIcon";
import LikeIcon from "@/components/icon/LikeIcon";
import CartIcon from "@/components/icon/CartIcon";
import CartModal from "@/components/core/CartModal";
import commonService from "@/service/commonService";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import userApi from "@/api/UserApi";
import commonUtils from "@/utils/commonUtils";
import ItemReviews from "@/components/core/ItemReviews";
import CommentIcon from "@/components/icon/CommentIcon";
import ReviewImageModal from "@/components/core/ReviewImageModal";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";
export default {
  name: "Detail",
  components: {
    SourceCodeLinkModal,
    ReviewImageModal,
    CommentIcon, ItemReviews, Footer, Bottom, CartModal, CartIcon, LikeIcon, MinusIcon, PlusIcon, Header},

  data() {
    return {
      init: false,
      qnaInit: false,
      reviewInit: false,
      item: null,
      user: null,
      cartAddView: false,
      currentTab: 'detail',
      tabs: [
        {'name': '상품정보', 'val': 'detail'},
        {'name': '리뷰', 'val': 'review'},
        {'name': 'Q&A', 'val': 'qna'}
      ],
      userLike: false,
      qnaContent: null,
      isQnaSecret: true,
      qnaList: [],
      reviews: [],
      s3UrlPrefix: 'https://nature-portfolio.s3.ap-northeast-2.amazonaws.com/',
      reviewImgModalView: false,
      reviewImgSrc: null
    }
  },

  async created() {
    const id = this.$route.params.id;
    await this.setItem(id);
    await this.checkItemLike();
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

    async checkItemLike() {
      const token = this.$cookies.get('token');
      if (!token) return;
      const itemId = this.item.id;

      try {
        const res = await userApi.checkItemLike(token, itemId);
        if (res.data === true) this.userLike = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    async setQnaList() {
      const id = this.item.id;
      try {
        const res = await itemApi.getQnaList(id);
        const qnaList = res.data;
        qnaList.content.forEach(qna => qna.showContent = false);

        this.qnaList = qnaList;
        this.qnaInit = true;
      } catch (err) {
        alert('문제가 발생했습니다.');
        console.log(err);
      }
    },

    async setReviews() {
      const id = this.item.id;
      const token = this.$cookies.get('token');
      try {
        const res = await itemApi.getReviews(token, id);
        const reviews = res.data;
        reviews.content.forEach(review => review.showContent = false);
        this.reviews = reviews;
        this.reviewInit = true;
      } catch (err) {
        alert('문제가 발생했습니다.');
        console.log(err);
      }

    },

    itemLike() {
      const id = this.item.id;
      const token = this.$cookies.get('token');
      if (!token) {
        alert('상품을 찜하시려면 로그인을 해주세요.');
        return;
      }

      if (!this.userLike) {
        try {
          itemApi.itemLike(token, id);
          this.userLike = true;
        } catch (err) {
          alert('문제가 발생하였습니다.');
          console.log(err);
        }
      } else {
        try {
          itemApi.itemLikeDelete(token, id);
          this.userLike = false;
        } catch (err) {
          alert('문제가 발생하였습니다.');
          console.log(err);
        }
      }


    },

    saveQna() {
      const token = this.$cookies.get('token');
      if (!token) {
        alert('로그인해주세요.');
        return;
      }

      const qnaContent = this.qnaContent;
      const qnaSecret = this.isQnaSecret;
      const id = this.item.id;

      try {
        const res = itemApi.addQna(token, id, qnaContent, qnaSecret);
        console.log(res);

      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    qnaListIsEmpty() {
      return this.qnaList.content.length === 0;
    },

    getStroke() {
      return this.userLike === true ? 'none' : '#a0a0a0';
    },

    getFill() {
      return this.userLike === true ? '#ff1a5a' : '#fff';

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
    },

    addCart(id) {
      try {
        commonService.addCart.bind(this)(id);
        this.cartAddView = true;
      } catch (err) {
        alert(err.message);
      }
    },

    shoppingKeepGoing() {
      this.cartAddView = false;
    },

    setCurrentTab(tab) {
      this.currentTab = tab;
      if (tab === 'qna') {
        this.setQnaList();
      } else if (tab === 'review') {
        this.setReviews();
      }
    },

    isCurrentTab(tab) {
      return this.currentTab === tab;
    },

    productOrder(item) {

      let orderItems = [];
      let orderItem = {
        'id': item.id,
        'quantity': item.quantity
      }

      orderItems.push(orderItem);
      this.$cookies.set('order-items', JSON.stringify(orderItems));
      this.$router.push('/orders');


    },

    qnaShowContentToggle(qna) {
      qna.showContent = !qna.showContent;
    },

    qnaContentSlice(qnaContent) {
      return qnaContent.substring(0, 80);
    },

    getQnaStatus(qnaStatus) {
      return qnaStatus === 'WAIT' ? '답변대기' : '답변완료'
    },

    convertTimeToStr(time, type) {
      if (type === 'monthAndDay') return commonUtils.localDateTimeToYearMonthDay(time);
      else return commonUtils.localDateTimeToYearMonthDayHourMinutes(time);

    },

    reviewImgModalShow(s3Key) {
      this.reviewImgModalView = true;
      this.reviewImgSrc = this.s3UrlPrefix + s3Key;
    },

    reviewImgModalClose() {
      this.reviewImgModalView = false;
      this.reviewImgSrc = null;
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
    padding-bottom: 40px;
  }

  section.main-container section.info-container ul {
    max-width: 1100px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 80px;
  }

  section.main-container section.info-container ul li {
    float: left;
    box-sizing: border-box;
    width: 50%;
    padding: 20px;
  }

  section.main-container section.info-container ul li:first-child {
    width: 45%;
  }

  section.main-container section.info-container ul li:last-child {
    /*padding-left: 40px;*/
    padding-right: 80px;
    width: 55%;
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
    max-width: 100%;
    width: fit-content;
  }

  section.main-container section.info-container ul li.detail {
    text-align: left;
  }

  section.main-container section.info-container ul li.detail div.category {
    padding: 0 !important;
    border-bottom: none !important;
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
    padding-top: 10px !important;
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

  section.main-container section.info-container ul li.detail div.out-box {
    box-sizing: border-box;
    padding: 25px 0;
    border-bottom: 1px solid #eaeaea;
  }

  section.main-container section.info-container ul li.detail div p {
    font-weight: 400;
    color: #555;
  }

  section.main-container section.info-container ul li.detail div.out-box p.capacity {
    margin-bottom: 0;
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
    font-weight: 300;
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
    cursor: pointer;
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

  section.main-container section.info-container ul li.detail div.buy {
    border-bottom: none;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col {
    float: left;
    box-sizing: border-box;
    padding: 10px;
  }



  section.main-container section.info-container ul li.detail div.buy div.buy-col.like {
    padding-left: 0;
    width: 15%;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col.cart {
    width: 40%;
  }


  section.main-container section.info-container ul li.detail div.buy div.buy-col.purchase {
    padding-right: 0;
    width: 45%;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col div.buy-inner-box {
    background-color: #f6f6f6;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col div.buy-inner-box button {
    cursor: pointer;
    background-color: transparent;
    box-sizing: border-box;
    padding: 0;
    outline: none;
    width: 100%;
    height: 100%;
    font-size: 15px;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col.purchase div.buy-inner-box button {
    background-color: #7ebb34;
    color: #fff;
    font-weight: 700;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col.like div.buy-inner-box button svg {
    max-width: 23px;
    width: 100%;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col.cart div.buy-inner-box button {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  section.main-container section.info-container ul li.detail div.buy div.buy-col.cart div.buy-inner-box button svg {
    max-width: 26px;
    width: 100%;
    margin-right: 10px;
    transform: translateY(-2px);
  }

  section.main-container section.detail-container {

  }

  section.main-container section.detail-container nav {
    max-width: 1100px;
    width: 100%;
    margin: 0 auto;
  }

  section.main-container section.detail-container nav div.nav-inner {
    float: left;
    box-sizing: border-box;
    padding: 10px;
    margin: 0 auto;
    width: 33.33%;
    height: 80px;
  }

  section.main-container section.detail-container nav div.nav-inner button {
    cursor: pointer;
    width: 100%;
    height: 100%;
    background-color: #f1f1f1;
    border: 3px;
    font-size: 16px;
    color: #555;
    outline: none;
    transition: all .1s ease-in-out;
  }


  section.main-container section.detail-container nav div.nav-inner button.active {
    background-color: #555;
    color: #fff;
    font-weight: 700;
    transition: all .1s ease-in-out;
  }

  section.main-container section.detail-container div.detail-info-box {
    box-sizing: border-box;
    padding: 100px 0;
  }

  section.main-container section.detail-container div.item-qna-box {
    text-align: left;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner {
    max-width: 1100px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding: 60px 25px;
    padding-top: 20px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner div.title-box {
    margin-top: 20px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner div.title-box h3 {
    font-size: 18px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form {
    margin-top: 20px;
    max-width: 50%;
    position: relative;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form textarea {
    outline-color: #ddd;
    width: 100%;
    box-sizing: border-box;
    border: 1px solid #ddd;
    padding: 15px;
    border-radius: 3px;
    font-size: 14px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form button {
    outline: none;
    box-sizing: border-box;
    padding: 5px 13px;
    font-size: 13px;
    border-radius: 3px;
    margin-top: 20px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form button[type=submit] {
    background-color: #7ebb34;
    color: #fff;
    font-weight: 700;
    margin-right: 10px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form button[type=reset] {
    background: #fff;
    color: #555;
    font-weight: 400;
    border: 1px solid #ddd;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form div.secret-check {
    display: flex;
    align-items: center;
    height: 30px;
    position: absolute;
    right: 0;
    bottom: 10px;
  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form div.secret-check input {

  }

  section.main-container section.detail-container div.item-qna-box .item-qna-inner form div.secret-check span {
    display: inline-block;
    margin-right: 5px;
    color: #333;
    font-weight: 400;
    font-size: 14px;
  }

  section.main-container section.detail-container div.item-qna-box div.qna-empty {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 450px;
    max-width: 1110px;
    margin: 0 auto;
    box-sizing: border-box;
    padding: 15px;
  }

  section.main-container section.detail-container div.item-qna-box div.qna-empty div.qna-empty-inner {
    width: 100%;
    height: 100%;
    border-top: 1px solid #eaeaea;
    border-bottom: 1px solid #eaeaea;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }

  section.main-container section.detail-container div.item-qna-box div.qna-empty div.qna-empty-inner svg {
    max-width: 55px;
    width: 100%;
  }

  section.main-container section.detail-container div.item-qna-box div.qna-empty div.qna-empty-inner h4 {
    margin-top: 10px;
    font-size: 16px;
  }

  section.main-container section.detail-container div.item-qna-box div.qna-empty div.qna-empty-inner p {
    margin-top: 8px;
    font-size: 14px;
    font-weight: 400;
    color: #888;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper {
    max-width: 1100px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding: 0 10px;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list {
    box-sizing: border-box;
    border-bottom: 1px solid #eaeaea;
    font-size: 14px;
    cursor: pointer;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list.contentShow {
    padding-bottom: 0;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list:first-child {
    border-top: 1px solid #eaeaea;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.list-inner {
    cursor: pointer;
    display: flex;
    align-items: center;
    padding: 30px 20px;

  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.status {
    width: 10%;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.content {
    width: 70%;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.content p {
    width: 80%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.writer {
    width: 10%;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.wroteAt {
    width: 10%;
    text-align: right;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.writer p {
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.qna-content {
    cursor: auto;
    background-color: #f9f9f9;
    padding: 30px;
  }

  section.main-container section.detail-container div.item-qna-box ul.qna-wrapper li.qna-list div.qna-content p {
    line-height: 1.4;
  }

</style>