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

          <OrderList v-if="isCurrentTabThis('orderAndDelivery')"
                     v-bind:orders="orders"
                     v-bind:cancel-order="cancelOrder"
                     v-bind:go-order-detail="goOrderDetail"
                     v-bind:next-order-page="nextOrderPage"
                     v-bind:previous-order-page="previousOrderPage"
                     v-bind:go-order-page="goOrderPage" />


          <LikeItems v-if="isCurrentTabThis('likes')"
                     v-bind:like-items="likeItems"
                     @allSelectLikeItemsToggle="allSelectLikeItemsToggle"
                     @setLikeItems="setLikeItems"/>

          <Pagination v-if="isCurrentTabThis('likes') && this.likeItems.content.length > 1"
                      v-bind:page="likeItems"
                      @nextPage="nextLikeItemPage"
                      @previousPage="previousLikeItemPage"
                      @goPage="goLikeItemPage" />



          <UserQnaList v-if="isCurrentTabThis('qna')"
                       v-bind:qna-list="qnaList"
                       v-bind:next-qna-page="nextQnaPage"
                       v-bind:previous-qna-page="previousQnaPage"
                       v-bind:go-qna-page="goQnaPage"/>

          <UserReviews v-if="isCurrentTabThis('review')"
                       v-bind:can-review-items="canReviewItems"
                       v-bind:my-reviews="myReviews"
                       v-bind:s3-url-prefix="s3UrlPrefix"
                       v-bind:is-reviews-empty="isReviewsEmpty"
                       v-bind:set-review-nav="setReviewNav"
                       v-bind:review-nav="reviewNav"
                       v-bind:convert-time-to-str="convertTimeToStr"
                       v-bind:write-modal-view-toggle="writeModalViewToggle"
                       v-bind:review-img-modal-show="reviewImgModalShow"
                       v-bind:my-review-next-page="myReviewNextPage"
                       v-bind:my-review-previous-page="myReviewPreviousPage"
                       v-bind:go-my-review-page="goMyReviewPage"
                       v-bind:can-review-next-page="canReviewNextPage"
                       v-bind:can-review-previous-page="canReviewPreviousPage"
                       v-bind:go-can-review-page="goCanReviewPage"/>
        </div>
      </div>
    </div>

    <WriteReviewModal v-if="writeModalView"
                      v-bind:review-item="reviewItem"
                      v-bind:write-modal-view-toggle="writeModalViewToggle"
                      v-bind:write-modal-close="writeModalClose"
                      v-bind:write-review-complete="writeReviewComplete"/>

    <ReviewImageModal v-if="reviewImgModalView" v-bind:img-src="reviewImgSrc" v-bind:review-img-modal-close="reviewImgModalClose" />

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
import OrderList from "@/components/core/OrderList";
import LikeItems from "@/components/core/LikeItems";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import UserQnaList from "@/components/core/UserQnaList";
import UserReviews from "@/components/core/UserReviews";
import commonUtils from "@/utils/commonUtils";
import WriteReviewModal from "@/components/core/WriteReviewModal";
import ReviewImageModal from "@/components/core/ReviewImageModal";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";
import Pagination from "@/components/core/Pagination";

export default {
  name: "Index",
  components: {
    SourceCodeLinkModal,
    ReviewImageModal,
    WriteReviewModal, UserReviews, UserQnaList, Footer, Bottom, LikeItems, OrderList, MyPageNav, Header, Pagination},
  data() {
    return {
      init: false,
      orders: [],
      orderDetail: null,
      user: null,
      currentTab: 'orderAndDelivery',
      deliveryOnGoingTotal: 0,
      reviewTotal: 0,
      likeItems: [],
      likeItemAllSelected: false,
      qnaList: [],
      basicInfoView: true,
      canReviewItems: [],
      reviewNav: 'myReviews',
      writeModalView: false,
      reviewItem: null,
      myReviews: [],
      s3UrlPrefix: 'https://nature-portfolio.s3.ap-northeast-2.amazonaws.com/',
      reviewImgModalView: false,
      reviewImgSrc: null
    }
  },

  computed: {
    currentMyPageStoreTab() {
      return this.$store.state.currentMyPageTab;
    }
  },

  async created() {


    const currentMyPageStoreTab = this.currentMyPageStoreTab;
    if (!currentMyPageStoreTab || currentMyPageStoreTab === 'orderAndDelivery') {
      await this.setOrders();
    } else if (currentMyPageStoreTab === 'likes') {
      await this.setLikeItems();
    } else if (currentMyPageStoreTab === 'qna') {
      await this.setQnaList();
    } else if (currentMyPageStoreTab === 'review') {
      await this.setMyReviews();
    }

    await this.setUser();
    await this.setDeliveryTotal();
    await this.setReviewsTotal();

  },
  methods: {
    async setOrders(page) {
      if (!page) {
        page = 0;
      }
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getUserOrders(token, page);
        this.orders = res.data;
        const tab = 'orderAndDelivery';
        this.currentTab = tab;
        this.$store.commit('SET_CURRENT_MY_PAGE_TAB', tab);
        this.basicInfoView = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async nextOrderPage(page) {
      await this.setOrders(page)
    },

    async previousOrderPage(page) {
      await this.setOrders(page);
    },

    async goOrderPage(page) {
      await this.setOrders(page - 1);
    },


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

    async setLikeItems(page) {
      if (!page) {
        page = 0;
      }

      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getLikeItems(token, page);

        const likeItems = res.data;
        likeItems.content.forEach(likeItem => {
          likeItem.selected = false;
        })

        this.likeItems = likeItems;

        const tab = 'likes';

        this.currentTab = tab;
        this.$store.commit('SET_CURRENT_MY_PAGE_TAB', tab);
        this.basicInfoView = false;
        this.likeItemAllSelected = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async nextLikeItemPage(page) {
      await this.setLikeItems(page)
    },

    async previousLikeItemPage(page) {
      await this.setLikeItems(page);
    },

    async goLikeItemPage(page) {
      await this.setLikeItems(page - 1);
    },

    allSelectLikeItemsToggle() {
      const allSelected = this.likeItemAllSelected;
      if (allSelected) {
        this.likeItems.content.forEach(likeItem => {
          likeItem.selected = false;
        })
        this.likeItemAllSelected = false;
      } else {
        this.likeItems.content.forEach(likeItem => {
          likeItem.selected = true;
        })
        this.likeItemAllSelected = true;
      }
    },


    async setQnaList(page) {
      if (!page) {
        page = 0;
      }
      const token = this.$cookies.get('token');
      try {

        const res = await userApi.getQnaList(token, page);
        const qnaList = res.data;
        qnaList.content.forEach(qna => qna.showContent = false);
        this.qnaList = qnaList;

        this.currentTab = 'qna';
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    async nextQnaPage(page) {
      await this.setQnaList(page)
    },

    async previousQnaPage(page) {
      await this.setQnaList(page);
    },

    async goQnaPage(page) {
      await this.setQnaList(page - 1);
    },

    async setCanReviewItems(page) {
      if (!page) {
        page = 0;
      }
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getCanReviewItems(token, page);
        this.canReviewItems = res.data;
        this.currentTab = 'review';
        this.reviewNav = 'canReview';
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async canReviewNextPage(page) {
      await this.setCanReviewItems(page)
    },

    async canReviewPreviousPage(page) {
      await this.setCanReviewItems(page);
    },

    async goCanReviewPage(page) {
      await this.setCanReviewItems(page - 1);
    },

    async setMyReviews(page) {

      if (!page) {
        page = 0;
      }

      const token = this.$cookies.get('token');

      try {
        const res = await userApi.getReviews(token, page);
        this.currentTab = 'review';
        this.reviewNav = 'myReviews';

        const myReviews = res.data;
        myReviews.content.forEach(myReview => myReview.showContent = false);
        this.myReviews = myReviews;
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    async myReviewNextPage(page) {
      await this.setMyReviews(page)
    },

    async myReviewPreviousPage(page) {
      await this.setMyReviews(page);
    },

    async goMyReviewPage(page) {
      await this.setMyReviews(page - 1);
    },

    isReviewsEmpty() {
      return this.myReviews.length === 0;
    },

    setReviewNav(nav) {
      if (nav === 'myReviews') this.setMyReviews();
      else this.setCanReviewItems();
    },

    writeReviewComplete() {
      this.setCanReviewItems();
      // this.canReviewItems.content.forEach(canReviewItem => {
      //   if (canReviewItem.id === review.id) {
      //     const indexOfReview = this.canReviewItems.content.indexOf(canReviewItem);
      //     this.canReviewItems.content.splice(indexOfReview, 1);
      //   }
      // })
    },

    getOwnPoints() {
      return this.user.ownPoints === null ? 0 : this.user.ownPoints;
    },

    cancelOrder(orderId) {
      if (!confirm('정말 주문을 취소하시겠습니까?')) return;

      const token = this.$cookies.get('token');
      try {
        orderApi.cancelOrder(token, orderId)
        alert('주문 취소가 완료되었습니다.');
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    isCurrentTabThis(tab) {
      return this.currentTab === tab;
    },

    setCurrentTab(tab) {
      if (tab === 'orderAndDelivery') {
        this.setOrders();
      }
      else if (tab === 'likes') {
        this.setLikeItems();
      }
      else if (tab === 'qna') {
        this.setQnaList();
      }
      else if (tab === 'review') {
        if (this.reviewNav === 'canReview') this.setCanReviewItems();
        else this.setMyReviews();
      }
    },

    goOrderDetail(orderId) {
      this.$router.push('/my-page/orders/' + orderId);
    },

    convertTimeToStr(time, type) {
      if (type === 'monthAndDay') return commonUtils.localDateTimeToYearMonthDay(time);
      else return commonUtils.localDateTimeToYearMonthDayHourMinutes(time);

    },


    writeModalViewToggle(item) {
      this.reviewItem = item;
      this.writeModalView = !this.writeModalView;
    },

    writeModalClose() {
      this.writeModalView = false;
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

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.page-container {
    margin-top: 40px;
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


</style>