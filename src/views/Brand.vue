<template>
  <section class="main-container">
    <Header v-bind:transparent="true" v-bind:header-tab="'brand'" />

    <aside class="banner">
      <div class="banner__inner">
        <h1>BRAND</h1>
        <h3>네이처리퍼블릭은 자연의 완벽함을 믿습니다.</h3>
        <p>
          우리는 전세계의 청정 자연에서 자연이 주는 선물을 찾아서 가장 자연<br>
          그대로에 가까운 상태로 고객들에게 제공하는 것을 목표로 합니다.
        </p>
      </div>
    </aside>

    <section class="container">

      <ul class="clearfix">
        <li class="col-6 img">
          <div class="img-box">
            <img src="@/assets/image/brand/about_13.gif" alt="">
          </div>
        </li>
        <li class="col-6 text right">
          <div class="text-box">
            <h4>Pure Nature</h4>
            <h3>오직 자연 그대로</h3>
            <p>
              네이처리퍼블릭은 자연의 완벽함을 믿습니다.<br>
              우리는 전세계의 청정 자연에서 자연이 주는 선물을 찾아서 가장 자연<br>
              그대로에 가까운 상태로 고객들에게 제공하는 것을 목표로 합니다.
            </p>
          </div>
        </li>

        <li class="col-6 text">
          <div class="text-box">
            <h4>For Everyone</h4>
            <h3>누구나 누릴 수 있도록</h3>
            <p>
              네이처리퍼블릭은 늘 고객의 가까이에서 품질과 가격의 균형을 맞춘<br>
              합리적인 제품을 제안합니다.<br>
              전세계인 누구나 네이처리퍼블릭의 제품과 브랜드를 통해<br>
              자연의 선물을 누리게 할 수 있드록 하기 위함입니다.
            </p>
          </div>
        </li>
        <li class="col-6 img">
          <div class="img-box">
            <img src="@/assets/image/brand/about_17.gif" alt="">
          </div>
        </li>

        <li class="col-6 img">
          <div class="img-box">
            <img src="@/assets/image/brand/about_29.gif" alt="">
          </div>
        </li>
        <li class="col-6 text right">
          <div class="text-box">
            <h4>Originality</h4>
            <h3>앞선 감각과 확신으로</h3>
            <p>
              우리는 세계의 청정자연을 탐구하고 전 세계인을 고객으로 대하는<br>
              일은 우리에게 늘 새로운 영감을 줍니다.<br>
              우리는 자연주의 화장품의 선구자라는 자부심으로 다른 누군가를<br>
              모방하지 않고 오직 자연을 닮은 제품을 연구합니다.
            </p>
          </div>
        </li>

        <li class="col-6 text">
          <div class="img-box">
            <h4>Coummunity</h4>
            <h3>함께 소통하며</h3>
            <p>
              네이처리퍼블릭은 우리의 고객 그리고 파트너들과 늘 대화 하기를<br>
              즐기고 이들과 즐거움과 이로움을 함께 나누기 위해 제품과<br>
              매장에서의 경험을 끊임없이 개선합니다.
            </p>
          </div>
        </li>
        <li class="col-6 img">
          <div class="img-box">
            <img src="@/assets/image/brand/about_33.gif" alt="">
          </div>
        </li>
      </ul>

    </section>

    <Bottom />
    <Footer />
    <SourceCodeLinkModal />
  </section>



</template>

<script>
import Header from "@/components/core/Header";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";
export default {
  name: "brand",
  components: {SourceCodeLinkModal, Header, Bottom, Footer}
}
</script>

<style scoped>
  .banner {
    width: 100%;
    padding-top: 90px;
    height: 454px;
    text-align: center;
    background: linear-gradient(
        rgba(124, 124, 124, .4),
        rgba(124, 124, 124, .4)
    ), url("../assets/image/brand/about_02.png");

    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
  }

  .banner .banner__inner {
    max-width: 1260px;
    margin: 0 auto;
  }

  .banner h1 {
    padding: 100px 10px 5px 10px;
    font-size: 50px;
    font-weight: 400;
    color: #fff;
    line-height: 1.3;
  }

  .banner h3 {
    font-size: 22px;
    font-weight: 600;
    color: #fff;
    margin-top: 20px;
    margin-bottom: 15px;
  }

  .banner p {
    font-size: 14px;
    padding: 0 10px;
    color: rgba(255, 255, 255, .8);
    font-weight: 300;
    line-height: 1.5;
    font-weight: 100;
  }

  section.container {
    max-width: 1260px;
    padding-top: 120px;
    margin: 0 auto;
    text-align: center;
  }

  section.container ul {
    text-align: left;
    margin-top: 50px;
    padding-bottom: 100px;
  }

  section.container ul li {
    height: 361px;
    padding-top: 20px;
    padding-bottom: 20px;
  }

  section.container ul li.right .text-box {
    margin-left: 45px;
  }

  section.container ul li div.text-box {
    padding-top: 50px;
  }

  section.container ul li h4 {
    font-size: 24px;
    font-weight: 300;
    color: #818181;
  }

  section.container ul li h3 {
    margin-top: 5px;
    font-size: 32px;
    font-weight: 500;
    color: #333;
  }

  section.container ul li p {
    margin-top: 15px;
    font-size: 16px;
    color: #818181;
    font-weight: 300;
    line-height: 1.7;
  }
</style>