<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Lock</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          d="M19 30V15A13 13 0 0 1 32 2a13 13 0 0 1 13 13v15" fill="none" v-bind:stroke="stroke"
          stroke-miterlimit="10" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
    <path data-name="layer1" fill="none" v-bind:stroke="stroke" stroke-miterlimit="10"
          stroke-width="2" d="M32 41v10" stroke-linejoin="round" stroke-linecap="round"></path>
    <rect data-name="layer1" x="10" y="30" width="44" height="32" rx="6"
          ry="6" fill="none" v-bind:stroke="stroke" stroke-miterlimit="10" stroke-width="2"
          stroke-linejoin="round" stroke-linecap="round"></rect>
  </svg>
</template>

<script>
export default {
  name: "LockIcon",
  props: {
    stroke: {
      default: '#202020'
    }
  }
}
</script>

<style scoped>

</style>