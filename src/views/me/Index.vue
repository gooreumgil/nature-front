<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
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
                <p>{{ getOwnPoints() }}</p>
              </div>
              <div>
                <h4>구매후기</h4>
                <p>{{ reviewTotal }}</p>
              </div>
            </div>
          </div>

          <OrderList v-bind:orders="orders" v-bind:cancel-order="cancelOrder" v-if="isCurrentTabThis('orderAndDelivery')"/>
          <LikeItems v-if="isCurrentTabThis('likes')" v-bind:like-items="likeItems"/>
          <UserQnaList v-if="isCurrentTabThis('qna')" v-bind:qna-list="qnaList"/>
          <UserReviews v-if="isCurrentTabThis('review')"
                       v-bind:can-review-items="canReviewItems"
                       v-bind:my-reviews="myReviews"
                       v-bind:is-reviews-empty="isReviewsEmpty"
                       v-bind:set-review-nav="setReviewNav"
                       v-bind:review-nav="reviewNav" v-bind:convert-time-to-str="convertTimeToStr" v-bind:write-modal-view-toggle="writeModalViewToggle"/>
        </div>
      </div>
    </div>

    <WriteReviewModal v-if="writeModalView" v-bind:review-item="reviewItem" v-bind:write-modal-view-toggle="writeModalViewToggle"/>

    <Bottom />
    <Footer />
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
export default {
  name: "Index",
  components: {WriteReviewModal, UserReviews, UserQnaList, Footer, Bottom, LikeItems, OrderList, MyPageNav, Header},
  data() {
    return {
      init: false,
      orders: [],
      user: null,
      currentTab: 'orderAndDelivery',
      deliveryOnGoingTotal: 0,
      reviewTotal: 0,
      likeItems: [],
      qnaList: [],
      basicInfoView: true,
      canReviewItems: [],
      reviewNav: 'myReviews',
      writeModalView: false,
      reviewItem: null,
      myReviews: []
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
    }

    await this.setUser();
    await this.setDeliveryTotal();
    await this.setReviewsTotal();

  },
  methods: {
    async setOrders() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getUserOrders(token);
        this.orders = res.data.content;
        const tab = 'orderAndDelivery';
        this.currentTab = tab;
        this.$store.commit('SET_CURRENT_MY_PAGE_TAB', tab);
        this.basicInfoView = true;
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

    async setLikeItems() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getLikeItems(token);
        this.likeItems = res.data.content;
        const tab = 'likes';

        this.currentTab = tab;
        this.$store.commit('SET_CURRENT_MY_PAGE_TAB', tab);
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setQnaList() {
      const token = this.$cookies.get('token');
      try {

        const res = await userApi.getQnaList(token);
        const qnaList = res.data.content;
        qnaList.forEach(qna => qna.showContent = false);
        this.qnaList = qnaList;

        this.currentTab = 'qna';
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    async setCanReviewItems() {
      const token = this.$cookies.get('token');
      try {
        const res = await userApi.getCanReviewItems(token);
        this.canReviewItems = res.data.content;

        this.currentTab = 'review';
        this.reviewNav = 'canReview';
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async setMyReviews() {

      const token = this.$cookies.get('token');

      try {
        const res = await userApi.getReviews(token);
        this.currentTab = 'review';
        this.reviewNav = 'myReviews';
        this.myReviews = res.data.content;
        console.log(this.myReviews);
        this.basicInfoView = false;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    isReviewsEmpty() {
      return this.myReviews.length === 0;
    },

    setReviewNav(nav) {
      if (nav === 'myReviews') this.setMyReviews();
      else this.setCanReviewItems();
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

    convertTimeToStr(time) {
      return commonUtils.localDateTimeToYearMonthDay(time);
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



</style>