<template>
  <div class="bottom">
    <ul>
      <li><a href="#">이용약관</a></li>
      <p>│</p>
      <li><a href="#">개인정보 취급방침</a></li>
      <p>│</p>
      <li><a href="#">가맹점 문의</a></li>
      <p>│</p>
      <li><a href="#">본사 정품 판매처</a></li>
      <p>│</p>
      <li><a href="#">채용안내</a></li>
    </ul>
  </div>
</template>

<script>
export default {
name: "Bottom"
}
</script>

<style scoped>
  .bottom {
    width: 100%;
    margin: 0 auto;
    margin-top: 160px;
    padding: 15px 0;
    text-align: left;
    border-top: 1px solid #efefef;
    border-bottom: 1px solid #efefef;
    font-family: 'Noto Sans KR', sans-serif;
  }

  .bottom ul {
    max-width: 1240px;
    margin: 0 auto;
    display: block;
  }

  .bottom li {
    display: inline-block;
  }

  .bottom a {
    font-size: 14px;
    color: #555;
    font-weight: 300;
  }

  .bottom p {
    display: inline-block;
    color: #555;
    font-size: 14px;
    font-weight: 300;
  }

</style>