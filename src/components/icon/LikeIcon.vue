<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Heart</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer1"
          d="M47 5c-6.5 0-12.9 4.2-15 10-2.1-5.8-8.5-10-15-10A15 15 0 0 0 2 20c0 13 11 26 30 39 19-13 30-26 30-39A15 15 0 0 0 47 5z"
          v-bind:fill="fill" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round"
          stroke-linecap="round"></path>
  </svg>
</template>

<script>
export default {
  name: "LikeIcon",
  props: {
    stroke: {
      default: '#202020'
    },
    fill: {
      default: '#fff'
    }
  },

}
</script>

<style scoped>

</style>