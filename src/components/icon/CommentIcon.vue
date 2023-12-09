<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Write Comment</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path data-name="layer2"
          fill="none" v-bind:stroke="stroke" stroke-linecap="round" stroke-linejoin="round"
          stroke-width="2" d="M56 29V7H2v36h14v9.9L26.8 43h13.1"></path>
    <path data-name="layer1" fill="none" v-bind:stroke="stroke" stroke-linecap="round"
          stroke-linejoin="round" stroke-width="2" d="M55 28L34.2 48.7 29.8 60l11.4-4.3L62 35l-7-7zM34.2 48.7l7 7"></path>
  </svg>
</template>

<script>
export default {
  name: "CommentIcon",
  props: {
    stroke: {
      default: '#202020'
    }
  }
}
</script>

<style scoped>

</style>