<template>
  <ul class="order-list-wrapper">
    <div class="title-box">
      <h3>주문/배송조회</h3>
    </div>
    <li class="order-list" v-for="(order, index) in orders" v-bind:key="index">
      {{ order }}
    </li>
  </ul>
</template>

<script>
export default {
  name: "OrderList",
  props: {
    orders: {
      value: []
    }
  }
}
</script>

<style scoped>

</style>