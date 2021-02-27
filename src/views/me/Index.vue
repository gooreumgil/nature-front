<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
    <div class="title-container">
      <h2>마이페이지</h2>
    </div>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import orderApi from "@/api/OrderApi";
export default {
  name: "Index",
  components: {Header},
  data() {
    return {
      orders: []
    }
  },

  async created() {
    await this.setOrders();
  },
  methods: {
    async setOrders() {
      const token = this.$cookies.get('token');
      try {
        const res = await orderApi.getOrders(token);
        this.orders = res.data.content;
        console.log(this.orders);
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
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

  section.main-container div.title-container {
    box-sizing: border-box;
    padding-top: 120px;
  }
</style>