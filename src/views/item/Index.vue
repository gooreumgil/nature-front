<template>
  <section class="main-container">
    <Header v-bind:transparent="false"/>
    <aside class="top">
      <div class="top_name">
        <h1>Products</h1>
        <p>네이처리퍼블릭은 자연의 완벽함을 믿습니다.<br>우리는 전세계의 청정 자연에서 자연이 주는 선물을 찾아서 가장 자연<br>그대로에 가까운 상태로 고객들에게 제공하는 것을 목표로 합니다.</p>
      </div>
    </aside>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
export default {
  name: "Index",
  components: {Header}
}
</script>

<style scoped>
  .top {
    width: 100%;
    padding-top: 90px;
    height: 454px;
    font-family: 'Noto Sans KR', sans-serif;
    text-align: center;

    background: linear-gradient(
        rgba(124, 124, 124, .4),
        rgba(124, 124, 124, .4)
    ), url("../../assets/image/list_img/list_banner_bg.png");


    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
  }

  .top .top_name {
    max-width: 1260px;
    margin: 0 auto;
  }

  .top h1 {
    padding: 130px 10px 5px 10px;
    font-size: 60px;
    font-weight: 400;
    color: #fff;
    line-height: 1.3;
  }

  .top_name p {
    font-size: 16px;
    padding: 0 10px;
    color: rgba(255, 255, 255, .8);
    font-weight: 300;
    line-height: 1.5;
    font-weight: 100;
  }


  .top li {
    float: left;
    margin-left: 10px;
  }

  .top li a {
    color: #333;
  }

  .top li img {
    margin-left: 7px;
  }

  .top ul {
    position: absolute;
    top: -35px;
    color: #333;
    font-size: 14px;
    font-weight: 100;

  }

  .top ul::after {
    content: "";
    display: block;
    clear: both;
  }

</style>