<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>LOL Emoji</title>
    <desc>A solid styled icon from Orion Icon Library.</desc>
    <path data-name="layer1"
          d="M32 2a30 30 0 1 0 30 30A30 30 0 0 0 32 2zm10 19.9a6 6 0 0 1 6 6 2 2 0 1 1-4 0 2 2 0 1 0-4 0 2 2 0 1 1-4 0 6 6 0 0 1 6-6zm-20 0a6 6 0 0 1 6 6 2 2 0 1 1-4 0 2 2 0 1 0-4 0 2 2 0 1 1-4 0 6 6 0 0 1 6-6zM32 54c-8.6 0-15.8-6.1-18-14h36c-2.2 7.9-9.4 14-18 14z"
          v-bind:fill="fill"></path>
  </svg>
</template>

<script>
export default {
  name: "UserIcon",
  props: {
    fill: {
      default: '#202020'
    }
  }
}
</script>

<style scoped>

</style>