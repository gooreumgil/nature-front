<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
    <div class="inner-container" v-if="init">
      <div class="my-page-wrapper clearfix">
        <div class="my-page-list nav">
          <MyPageNav v-bind:user="user" />

        </div>
        <div class="my-page-list content">
          <div class="basic-info">
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

          <OrderList v-bind:orders="orders" />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import orderApi from "@/api/OrderApi";
import MyPageNav from "@/components/core/MyPageNav";
import userApi from "@/api/UserApi";
import OrderList from "@/components/core/OrderList";
export default {
  name: "Index",
  components: {OrderList, MyPageNav, Header},
  data() {
    return {
      init: false,
      orders: [],
      user: null,
      currentTab: 'orderAndDelivery',
      deliveryOnGoingTotal: 0,
      reviewTotal: 0
    }
  },

  async created() {
    await this.setOrders();
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

    getOwnPoints() {
      return this.user.ownPoints === null ? 0 : this.user.ownPoints;
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
    margin-top: 60px;
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
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title {

  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title h3 {
    font-size: 16px;
    font-weight: 400;
    color: #555;
  }

  section.main-container .inner-container div.my-page-wrapper div.my-page-list.content div.basic-info div.title h3 span {
    font-size: 20px;
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
    background-color: #f9f9f9;
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