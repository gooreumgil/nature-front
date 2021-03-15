<template>
  <section class="main-container">
    <Header />
    <div class="inner-container" v-if="init">
      <div class="title-box">
        <OrderCompleteIcon />
        <h1>주문완료</h1>
        <h3>{{ order.orderItemResponseDtos[0].itemNameKor }}의</h3>
        <p>주문이 완료되었습니다.</p>
      </div>

      <div class="btn-box">
        <button @click="goOrderInfo" type="button">주문정보확인</button>
        <button @click="shoppingKeepGoing" type="button">계속쇼핑하기</button>
      </div>
    </div>

    <Bottom />
    <Footer />
    <SourceCodeLinkModal />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import OrderCompleteIcon from "@/components/icon/OrderCompleteIcon";
import orderApi from "@/api/OrderApi";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";

export default {
  name: "Complete",
  components: {SourceCodeLinkModal, OrderCompleteIcon, Footer, Bottom, Header},
  data() {
    return {
      init: false,
      order: null
    }
  },

  async created() {
    await this.setOrder();
  },

  methods: {
    async setOrder() {
      const id = this.$route.params.id;
      const token = this.$cookies.get('token');
      try {
        const res = await orderApi.getOrder(token, id);
        this.order = res.data;
        this.init = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    goOrderInfo() {
      const id = this.$route.params.id;
      this.$router.replace('/my-page/orders/' + id);
    },

    shoppingKeepGoing() {
      this.$router.replace('/items');
    }
  }

}
</script>

<style scoped>
  section.main-container {
    position: relative;
    padding-top: 90px;

  }

  section.main-container header {
    box-shadow: 0 0 3px 0 #dcdcdc;
  }

  section.main-container div.inner-container {
    box-sizing: border-box;
    padding-top: 120px;
    padding-bottom: 120px;
  }

  section.main-container div.inner-container div.title-box {
    max-width: 1260px;
    margin: 0 auto;
    text-align: center;
  }

  section.main-container div.inner-container div.title-box svg {
    max-width: 80px;
    width: 100%;
  }

  section.main-container div.inner-container div.title-box h1 {
    font-size: 18px;
    font-weight: 400;
    color: #888;
    margin-top: 30px;
  }

  section.main-container div.inner-container div.title-box h3 {
    margin-top: 10px;
    font-size: 18px;
    color: #333;
    font-weight: 700;
  }

  section.main-container div.inner-container div.title-box p {
    font-size: 16px;
    font-weight: 400;
    color: #a0a0a0;
    margin-top: 10px;
  }

  section.main-container div.inner-container div.btn-box {
    margin-top: 20px;
  }

  section.main-container div.inner-container div.btn-box button {
    cursor: pointer;
    box-sizing: border-box;
    padding: 10px 15px;
    font-size: 14px;
    border-radius: 3px;
  }

  section.main-container div.inner-container div.btn-box button:first-child {
    background-color: #fff;
    border: 1px solid #bbb;
    margin-right: 10px;

  }

  section.main-container div.inner-container div.btn-box button:last-child {
    background-color: #555;
    color: #fff;
    font-weight: 700;
    border: none;
  }


</style>