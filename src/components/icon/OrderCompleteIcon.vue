<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Purchase Order</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          d="M16 48V8a6 6 0 0 1 6-6h34" fill="none" v-bind:stroke="stroke" stroke-miterlimit="10"
          v-bind:stroke-width="strokeWidth" stroke-linejoin="round" stroke-linecap="round"></path>
    <path data-name="layer2" d="M50 12h12V8a6 6 0 0 0-6-6 6 6 0 0 0-6 6v44a10 10 0 0 1-10 10 10 10 0 0 1-10-10v-4H2v4a10 10 0 0 0 10 10h28"
          fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" v-bind:stroke-width="strokeWidth" stroke-linejoin="round"
          stroke-linecap="round"></path>
    <path data-name="layer1" fill="none" v-bind:stroke="stroke" stroke-miterlimit="10"
          v-bind:stroke-width="strokeWidth" d="M24 12h16M24 28h16m-16-8h12M24 36h12" stroke-linejoin="round"
          stroke-linecap="round"></path>
  </svg>
</template>

<script>
export default {
  name: "OrderCompleteIcon",
  props: {
    stroke: {
      default: '#7ebb34'
    },
    strokeWidth: {
      default: 2
    }
  }
}
</script>

<style scoped>

</style>