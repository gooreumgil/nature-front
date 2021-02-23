<template>
  <section class="main-container">
    <Header v-bind:transparent="false" />
    <div class="title-container">
      <h2>장바구니</h2>

    </div>

  </section>
</template>

<script>
import Header from "@/components/core/Header";
export default {
name: "Cart",
  components: {Header}
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