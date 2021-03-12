<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Exclamation Mark</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2" d="M32 12v26"
          stroke-linejoin="round" stroke-linecap="round"></path>
    <circle data-name="layer1" cx="32" cy="49" r="3" fill="none" v-bind:stroke="stroke"
            stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></circle>
  </svg>
</template>

<script>
export default {
  name: "ExclamationIcon",
  props: {
    stroke: {
      default: "#202020"
    }
  }
}
</script>

<style scoped>

</style>