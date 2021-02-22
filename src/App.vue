<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  components: {
  }
}
</script>

<style>

  @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;700&display=swap');
  *{
    font-family: 'Noto Sans KR', Helvetica, Arial, sans-serif;
  }
  #app {
    font-family: 'Noto Sans KR', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%;
    width: 100%;
  }

  .main-font {
    font-family: 'Noto Sans KR', sans-serif;
    font-weight: 300;
    color: #555;
    font-size: 32px;
  }

  .sub-font {
    font-family: 'Noto Sans KR', sans-serif;
    color: #a0a0a0;
    font-size: 16px;
    font-weight: 300;
    margin-top: 15px;
  }

  #id_color {
    color: #7ebb34;
  }

  html, body {
    margin: 0 auto;
    position: relative;
    height: 100%;
    width: 100%;
  }

  input::-ms-clear,
  input::-ms-reveal{
    display:none;width:0;height:0;
  }

  input::-webkit-search-decoration,
  input::-webkit-search-cancel-button,
  input::-webkit-search-results-button,
  input::-webkit-search-results-decoration{
    display:none;
  }

  /*
  =====================================
  CSS RESET
  =====================================
  */
  html, body, div, span, object, iframe,
  h1, h2, h3, h4, h5, h6, p, blockquote, pre,
  abbr, address, cite, code,
  del, dfn, em, img, ins, kbd, q, samp,
  small, strong, sub, sup, var,
  b, i,
  dl, dt, dd, ol, ul, li,
  fieldset, form, label, legend,
  table, caption, tbody, tfoot, thead, tr, th, td,
  article, aside, canvas, details, figcaption, figure,
  footer, header, hgroup, menu, nav, section, summary,
  time, mark, audio, video {
    margin: 0;
    padding: 0;
    border: 0;
    outline: 0;
    font-size: 100%;
    vertical-align: baseline;
  }

  body {
    line-height: 1;
  }

  article, aside, details, figcaption, figure,
  footer, header, hgroup, menu, nav, section {
    display: block;
  }

  ul li {
    list-style: none;
  }

  a {
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
    background: transparent;
    text-decoration: none;
    color: inherit;
    display: inline-block;
  }

  img {
    vertical-align: bottom;
  }

  button {
    border: 0;
  }
  /*
  =====================================
  COMMON SYSTEM
  =====================================
  */
  .wrapper {
    max-width: 1200px;
    margin: 0 auto;
  }

  .clearfix:after {
    content: "";
    display: block;
    clear: both;
  }

  .table_cell {
    display: table-cell;
    vertical-align: middle;
  }

  /*
  =====================================
  GRID SYSTEM
  =====================================
  */
  [class*="col-"] {
    width: 100%;
    float: left;
    padding: 10px;
    box-sizing: border-box;
  }

  .col-right {
    float: right;
  }

  .col-fit {
    padding: 0;
  }

  .col-1 {
    width: 8.33%;
  }

  .col-2 {
    width: 16.66%;
  }

  .col-3 {
    width: 25%;
  }

  .col-4 {
    width: 33.33%;
  }

  .col-5 {
    width: 41.66%;
  }

  .col-6 {
    width: 50%;
  }

  .col-7 {
    width: 58.33%;
  }

  .col-8 {
    width: 66.66%;
  }

  .col-9 {
    width: 75%;
  }

  .col-10 {
    width: 83.33%;
  }

  .col-11 {
    width: 91.66%;
  }

  .col-12 {
    width: 100%;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }
</style>
