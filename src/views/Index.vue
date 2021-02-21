<template>
  <section class="main-container">

    <aside class="banner">
      <div class="banner__inner">
        <Header v-bind:authenticated="authenticated" v-bind:transparent="true"/>
        <h2>
          <span>네이처리퍼블릭</span>은 <br>
          자연의 완벽함을 믿습니다.
        </h2>
      </div>
    </aside>

    <section class="most-popular">
      <div class="title-container">
        <h3 class="main-font">Most Popular</h3>
        <p class="sub-font">네이처리퍼블릭에서 가장 사랑받는 제품들을 소개합니다.</p>
      </div>
      <ul class="clearfix">
        <li v-for="(item, index) in popularItems" v-bind:key="index">
          <div class="inner-box">
            <div class="img-box">
              <img v-bind:src="item.imgSrcPath" alt="">

              <div class="hover-box"></div>
            </div>
            <div class="description">
              <div class="item-name">
                <h4>{{ item.nameKor }}</h4>
              </div>
              <span class="price" v-bind:class="{disable: item.discountPrice}">{{ item.price | price }} <span class="won" v-if="!item.discountPrice">원</span></span>
              <span class="discountPrice" v-if="item.discountPrice">{{ (item.price - item.discountPrice) | price }} <span class="won">원</span></span>
            </div>

          </div>
        </li>
      </ul>
    </section>

    <section class="latest">
      <div class="title-container">
        <h3 class="main-font">Latest Product</h3>
        <p class="sub-font">네이처리퍼블릭에서 새로나온 제품들을 소개합니다.</p>
      </div>

      <ul class="clearfix">
        <li v-for="(item, index) in popularItems" v-bind:key="index">
          <div class="inner-box">
            <div class="img-box">
              <img v-bind:src="item.imgSrcPath" alt="">
              <div class="hover-box"></div>
            </div>
            <div class="description">
              <div class="item-name">
                <h4>{{ item.nameKor }}</h4>
              </div>
              <span class="price" v-bind:class="{disable: item.discountPrice}">{{ item.price | price }} <span class="won" v-if="!item.discountPrice">원</span></span>
              <span class="discountPrice" v-if="item.discountPrice">{{ (item.price - item.discountPrice) | price }} <span class="won">원</span></span>
            </div>
          </div>
        </li>
      </ul>
    </section>

    <Bottom />
    <Footer />
  </section>
</template>

<script>

import Header from "@/components/core/Header";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import itemApi from "@/api/ItemApi";
import authApi from "@/api/AuthApi";

export default {
  name: "Index",
  components: {Header, Bottom, Footer},
  data() {
    return {
      popularItems: [],
      latestItems: [],
      authenticated: false
    }
  },
  async created() {
    const token = this.$cookies.get('token');
    if (token) {
      await this.setAuthenticate(token);
    }
    await this.setPopularItems(0, 4);
    await this.setLatestItems(0, 4);
  },

  methods: {

    async setAuthenticate(token) {
      try {
        await authApi.authenticate(token);
        this.authenticated = true;
      } catch (err) {
        console.log(err);
      }
    },

    // 인기상품 가져오기
    async setPopularItems(page, size) {
      try {
        const res = await itemApi.getItems(page, size, 'likesCount,desc');
        this.popularItems = res.data.content;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    },

    // 최신상품 가져오기
    async setLatestItems(page, size) {
      try {
        const res = await itemApi.getItems(page, size, 'registerAt,desc');
        this.latestItems = res.data.content;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    }

  }
}
</script>

<style scoped>
  section.main-container {
  }

  section aside.banner {
    background-image: url("../assets/image/banner_bg_11.png");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: top center;
    background-attachment: fixed;
    height: 700px;
  }

  section aside.banner div.banner__inner {
    position: relative;
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    display: flex;
    height: 100%;
    align-items: center;
    justify-content: center;
  }

  section aside.banner div.banner__inner h2 {
    color: #fff;
    font-size: 38px;
    font-weight: 300;
    line-height: 1.3;
  }

  section aside.banner div.banner__inner h2 span {
    font-weight: 700;
  }

  section section ul li div.inner-box div.img-box {
    position: relative;
  }

  section section ul li div.inner-box div.img-box:hover > div.hover-box {
    opacity: 1;
    vertical-align: middle;
    pointer-events: none;
  }

  section section ul li div.inner-box div.img-box div.hover-box {
    opacity: 0;
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    text-align: center;
    background-color: rgba(255, 255, 255, .2);
    transition: all .2s ease-in-out;
  }

  section section.most-popular {
    padding-top: 180px;
  }

  section section.most-popular div.title-container {

  }

  section section.most-popular ul {
    max-width: 1260px;
    margin: 0 auto;
    width: 100%;
    margin-top: 60px;
  }

  section section.most-popular ul li {
    float: left;
    padding: 0 10px;
    box-sizing: border-box;
    width: 25%;
    text-align: left;
  }



  section section.most-popular ul li div.inner-box {

  }
  section section.most-popular ul li div.inner-box div.img-box {

  }

  section section.most-popular ul li div.description {
    padding: 0 15px 5px 15px;

  }

  section section.most-popular ul li div.description div.item-name {
    margin-bottom: 15px;
  }
  section section.most-popular ul li div.description div.item-name h4 {
    color: #555;
    font-weight: 300;
    font-size: 18px;
    width: 295px;

    margin-top: 20px;
    margin-left: 0;
    box-sizing: border-box;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  section section.most-popular ul li div.description span.price {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
    margin-right: 15px;
  }

  section section.most-popular ul li div.description span.won {
    font-size: 18px;
    font-weight: 300;
    color: #8f8f8f;

  }

  section section.most-popular ul li div.description span.price.disable {
    font-size: 16px;
    color: #9b9b9b;
    text-decoration: line-through;
    font-weight: 100;
  }

  section section.most-popular ul li div.description span.discountPrice {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
  }


  /* ///////////////////////////////  */

  section section.latest {
    padding-top: 160px;
  }

  section section.latest ul {
    max-width: 1260px;
    margin: 0 auto;
    width: 100%;
    margin-top: 60px;
  }

  section section.latest ul li {
    float: left;
    padding: 0 10px;
    box-sizing: border-box;
    width: 25%;
    text-align: left;
  }

  section section.latest ul li div.inner-box {

  }
  section section.latest ul li div.inner-box div.img-box {

  }

  section section.latest ul li div.description {
    padding: 0 15px 5px 15px;

  }

  section section.latest ul li div.description div.item-name {
    margin-bottom: 15px;
  }
  section section.latest ul li div.description div.item-name h4 {
    color: #555;
    font-weight: 300;
    font-size: 18px;
    width: 295px;

    margin-top: 20px;
    margin-left: 0;
    box-sizing: border-box;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  section section.latest ul li div.description span.price {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
    margin-right: 15px;
  }

  section section.latest ul li div.description span.won {
    font-size: 18px;
    font-weight: 300;
    color: #8f8f8f;

  }

  section section.latest ul li div.description span.price.disable {
    font-size: 16px;
    color: #9b9b9b;
    text-decoration: line-through;
    font-weight: 100;
  }

  section section.latest ul li div.description span.discountPrice {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
  }


</style>