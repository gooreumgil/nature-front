<template>
  <section class="main-container">

    <aside class="banner">
      <div class="banner__inner">
        <Header />
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
      <ul>
        <li>

        </li>
      </ul>
    </section>

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
  section {

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

  section section.most-popular {
    padding-top: 180px;
  }
</style>