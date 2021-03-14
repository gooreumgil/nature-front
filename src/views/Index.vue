<template>
  <section class="main-container">
    <Header v-bind:transparent="true"/>

    <aside class="banner">
      <div class="banner__inner">
        <h2>
          <span>네이처리퍼블릭</span>은 <br>
          자연의 완벽함을 믿습니다.
          <router-link to="/brand">Brand Story</router-link>
        </h2>

      </div>
    </aside>

    <section class="most-popular">
      <div class="title-container">
        <h3 class="main-font">Most Popular</h3>
        <p class="sub-font">네이처리퍼블릭에서 가장 사랑받는 제품들을 소개합니다.</p>
      </div>
      <ItemList v-bind:items="popularItems" />
    </section>

    <section class="latest">
      <div class="title-container">
        <h3 class="main-font">Latest Product</h3>
        <p class="sub-font">네이처리퍼블릭에서 새로나온 제품들을 소개합니다.</p>
      </div>
      <ItemList v-bind:items="latestItems" />
    </section>

    <Bottom />
    <Footer />
  </section>
</template>

<script>

import Header from "@/components/core/Header";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import ItemList from "@/components/core/ItemList";
import itemApi from "@/api/ItemApi";
import authApi from "@/api/AuthApi";

export default {
  name: "Index",
  components: {Header, Bottom, Footer, ItemList},
  data() {
    return {
      popularItems: [],
      latestItems: [],
      // authenticated: false
    }
  },
  async created() {
    // const token = this.$cookies.get('token');
    // if (token) {
    //   await this.setAuthenticate(token);
    // }
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
        this.$cookies.remove('token');
      }
    },

    // 인기상품 가져오기
    async setPopularItems(page, size) {
      try {
        const res = await itemApi.getItems(page, size, 'sellTotal,desc&sort=createdDate,desc');
        this.popularItems = res.data.content;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    },

    // 최신상품 가져오기
    async setLatestItems(page, size) {
      try {
        const res = await itemApi.getItems(page, size, 'createdDate,desc');
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

  section aside.banner div.banner__inner a {
    display: block;
    width: 168px;
    color: white;
    font-size: 17px;
    font-weight: 300;
    margin: auto;
    margin-top: 30px;
    padding: 8px 0px;
    border: 1px solid white;
    text-align: center;
    border-radius: 25px;
    transition: all .3s ease-in-out;
    letter-spacing: -0.005em;
  }

  section aside.banner div.banner__inner a:hover {
    background-color: #fff;
    color: #555;
    border: 1px solid #fff;
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


  /* ///////////////////////////////  */

  section section.latest {
    padding-top: 160px;
  }



</style>