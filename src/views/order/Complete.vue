<template>
  <section class="main-container">
    <Header />
    <div class="inner-container">
      <div class="title-box">
        <OrderCompleteIcon />
        <h1>주문완료</h1>
        <p>상품 주문이 완료되었습니다.</p>
      </div>

      <div class="btn-box">
        <button @click="goOrderInfo" type="button">주문정보확인</button>
        <button @click="shoppingKeepGoing" type="button">계속쇼핑하기</button>
      </div>
    </div>

    <Bottom />
    <Footer />
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import OrderCompleteIcon from "@/components/icon/OrderCompleteIcon";

export default {
  name: "Complete",
  components: {OrderCompleteIcon, Footer, Bottom, Header},
  methods: {
    goOrderInfo() {
      const id = this.$route.params.id;
      this.$router.push('/orders/' + id);
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
    font-size: 22px;
    font-weight: 400;
    color: #555;
    margin-top: 30px;
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