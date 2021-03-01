<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
    <div class="inner-container" v-if="init">
      <div class="my-page-wrapper clearfix">
        <div class="my-page-list nav">
          <MyPageNav v-bind:user="user" />

        </div>
        <div class="my-page-list content">

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
export default {
  name: "Index",
  components: {MyPageNav, Header},
  data() {
    return {
      init: false,
      orders: [],
      user: null
    }
  },

  async created() {
    await this.setOrders();
    await this.setUser();

  },
  methods: {
    async setOrders() {
      const token = this.$cookies.get('token');
      try {
        const res = await orderApi.getOrders(token);
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
  }


</style>