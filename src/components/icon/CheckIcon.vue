<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Checkbox Confirm Circle</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" d="M15.74 30l12 14 34-42"
          stroke-linejoin="round" stroke-linecap="round"></path>
    <path data-name="layer1" d="M53.443 32A26.002 26.002 0 1 1 27.75 10a25.914 25.914 0 0 1 10 1.993"
          fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round"
          stroke-linecap="round"></path>
  </svg>
</template>

<script>
export default {
  name: "CheckIcon",
  props: {
    stroke: {
      default: '#202020'
    }
  }
}
</script>

<style scoped>

</style>