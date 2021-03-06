<template>
  <svg @click="checkRating(idx)" v-on:mouseover="hoverEvent(idx, 'in')" v-on:mouseout="hoverEvent(idx, 'out')" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" aria-labelledby="title"
       aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">
    <title>Star</title>
    <desc>A line styled icon from Orion Icon Library.</desc>
    <path d="M32 48L15 58l4-19L5 26l19-2 8-18 8 18 19 2-14 13 4 19-17-10z"
          stroke-width="2" stroke-miterlimit="10" stroke-linecap="round" v-bind:stroke="stroke"
          v-bind:fill="fill" data-name="layer1" stroke-linejoin="round"></path>
  </svg>
</template>

<script>
export default {
  name: "StarIcon",
  props: {
    idx: {
      default: null
    },
    stroke: {
      default: '#202020'
    },
    fill: {
      default: 'none'
    },
    hoverEvent: {
      type: Function
    },
    checkRating: {
      type: Function
    }
  },

  methods: {

  }
}
</script>

<style scoped>
  svg {
    cursor: pointer;
    max-width: 25px;
    width: 100%;
    padding: 0 3px;
  }

  /*svg:hover > path {*/
  /*  fill: #7ebb34;*/
  /*  stroke: #7ebb34;*/
  /*}*/
</style>