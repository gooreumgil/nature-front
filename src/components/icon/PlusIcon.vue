<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Add</title>
    <desc>A solid styled icon from Orion Icon Library.</desc>
    <path data-name="layer1"
          d="M53 29H35V11a3 3 0 0 0-6 0v18H11a3 3 0 0 0 0 6h18v18a3 3 0 0 0 6 0V35h18a3 3 0 0 0 0-6z"
          v-bind:fill="fill"></path>
  </svg>
</template>

<script>
export default {
  name: "PlusIcon",
  props: {
    fill: {
      default: '#202020'
    }
}
}
</script>

<style scoped>

</style>