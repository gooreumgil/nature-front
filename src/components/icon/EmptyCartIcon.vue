<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>E-commerce Cart</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer1"
          fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" d="M62 22H16M2 6h10l10 40h32"
          stroke-linejoin="round" stroke-linecap="round"></path>
    <circle data-name="layer2" cx="24" cy="54" r="4" fill="none" v-bind:stroke="stroke"
            stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></circle>
    <circle data-name="layer2" cx="50" cy="54" r="4" fill="none"
            v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round"
            stroke-linecap="round"></circle>
    <path data-name="layer1" fill="none" v-bind:stroke="stroke" stroke-miterlimit="10"
          stroke-width="2" d="M59 30H26m30 8H28" stroke-linejoin="round" stroke-linecap="round"></path>
  </svg>
</template>

<script>
export default {
  name: "EmptyCartIcon",
  props: {
    stroke: {
      default: '#202020'
    }
  }
}
</script>

<style scoped>

</style>