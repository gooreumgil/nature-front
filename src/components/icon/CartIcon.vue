<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Cart</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          fill="transparent" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" d="M2 6h10l10 40h32l8-24H16"
          stroke-linejoin="round" stroke-linecap="round"></path>
    <circle data-name="layer1" cx="23" cy="54" r="4" fill="transparent" v-bind:stroke="stroke"
            stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></circle>
    <circle data-name="layer1" cx="49" cy="54" r="4" fill="transparent"
            v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round"
            stroke-linecap="round"></circle>
  </svg>
</template>

<script>
export default {
  name: "CartIcon",
  props: {
    stroke: {
      default: '#7ebb34'
    },
  }
}
</script>

<style scoped>

</style>