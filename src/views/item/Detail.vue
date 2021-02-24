<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />

    <section class="info-container">
      <ul class="info-wrapper clearfix">
        <li class="info-list img">
          <div class="inner-box">
            <img v-bind:src="item.mainSrcPath" alt="">
          </div>

        </li>
        <li class="info-list detail">

        </li>
      </ul>
    </section>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
export default {
  name: "Detail",
  components: {Header},

  data() {
    return {
      init: false,
      item: null
    }
  },

  async created() {
    const id = this.$route.params.id;
    await this.setItem(id);
  },

  methods: {
    async setItem(id) {
      try {
        const res = await itemApi.getItem(id);
        console.log(res.data);
        this.item = res.data;
      } catch (err) {
        alert('문제가 발생하였습니다.')
        console.log(err);
      }
    }
  }
}
</script>

<style scoped>
  section.main-container {
    position: relative;
  }

  section.main-container header {
    box-shadow: 0 0 3px 0 #dcdcdc;
  }

  section.main-container section.info-container {
    box-sizing: border-box;
    padding: 90px;
  }

  section.main-container section.info-container ul {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 80px;
  }

  section.main-container section.info-container ul li {
    float: left;
    box-sizing: border-box;
    width: 50%;
  }

  section.main-container section.info-container ul li.img {

  }

  section.main-container section.info-container ul li.img div.inner-box {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 351px;
    width: 295px;
    background-color: #f6f6f6;
    margin: 0 auto;
  }

  section.main-container section.info-container ul li.img div.inner-box img {
    width: 100%;
  }

</style>