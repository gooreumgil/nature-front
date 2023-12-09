<template>
  <footer th:fragment="footer">
    <p>(주)네이처리퍼블릭대표이사 : 호종환 주소 : 서울시 강남구 테헤란로 534 글라스타워 22, 23, 24층(대치동)<br> 사업자등록번호 : 220-87-77410 통신판매업 신고번호 : 강남
      00622호<br> 개인정보관리책임자 : 고원일 cpo@naturerepublic.co.kr<br> COPYRIGHTS(C) 2016 NATURE REPUBLIC ALL RIGHTS RESERVED.
    </p>
    <img class="a" src="@/assets/image/footer/footer1.png">
    <!--    <img class="b" src="/image/footer/footer2.png">-->
  </footer>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
  footer {
    font-family: 'Noto Sans KR', sans-serif;
    height: 309px;
    max-width: 1260px;
    box-sizing: border-box;
    padding-left: 10px;
    padding-right: 10px;
    margin: auto;
    text-align: center;
  }

  footer p {
    text-align: left;
    padding-top: 40px;
    font-size: 14px;
    font-weight: 300;
    color: #555;
    line-height: 1.8;
  }

  footer img {
    display: block;
  }

  footer .a {
    margin-top: 42px;
    margin-bottom: 25px;
  }
</style>