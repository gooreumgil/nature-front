<template>
  <ul class="qna-wrapper">
    <div class="title-box">
      <h3>상품 Q&A</h3>
      <p>고객님의 상품문의 내역입니다.</p>
    </div>
    <div class="divider"></div>

    <div class="qna-empty-box" v-if="isQnaListEmpty()">
      <div class="qna-empty-inner">
        <span class="img-helper">
          <ExclamationIcon v-bind:stroke="'#a0a0a0'" />
        </span>
        <p>문의 내역이 없습니다.</p>
      </div>
    </div>

    <li v-bind:class="{showContent: qna.showContent}" class="qna-list" v-for="(qna, index) in qnaList" v-bind:key="index">
      <div class="list-inner" @click="showContentToggle(qna)">
        <div class="img-box">
          <img v-bind:src="qna.itemResponseDto.mainImgPath" alt="">
        </div>
        <div class="info-box">
          <h4>{{ qnaContentSlice(qna.content) }}</h4>
          <h5>상품명: {{ qna.itemResponseDto.nameKor }}</h5>
          <p>날짜: {{ convertTime(qna.wroteAt) }}</p>
        </div>

        <div class="qna-status">
          <p v-bind:class="{active: qna.status === 'COMP'}">{{ getQnaStatus(qna.status) }}</p>

        </div>
      </div>
      <div class="qna-content" v-if="qna.showContent">
        <p>{{ qna.content }}</p>

      </div>


    </li>
  </ul>
</template>

<script>
import commonUtils from "@/utils/commonUtils";
import ExclamationIcon from "@/components/icon/ExclamationIcon";

export default {
  name: "UserQnaList",
  components: {ExclamationIcon},
  props: {
    qnaList: {
      value: []
    },

  },

  methods: {
    convertTime(time) {
      return commonUtils.localDateTimeToYearMonthDayHourMinutes(time);
    },

    showContentToggle(qna) {
      qna.showContent = !qna.showContent;
    },

    getQnaStatus(qnaStatus) {
      return qnaStatus === 'WAIT' ? '답변대기' : '답변완료';
    },

    qnaContentSlice(qnaContent) {
      return qnaContent.substring(0, 80);
    },

    isQnaListEmpty() {
      return this.qnaList.length === 0;
    }
  }
}
</script>

<style scoped>
  ul {
    box-sizing: border-box;
    padding-top: 10px;
    padding-left: 10px;
    padding-right: 10px;
  }

  ul div.title-box {
    box-sizing: border-box;
    padding: 10px 0;
    margin-bottom: 10px;
  }

  ul div.title-box h3 {
    font-size: 17px;
  }

  ul div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
  }

  ul div.divider {
    width: 100%;
    height: 1px;
    background-color: #eaeaea;
  }

  ul div.qna-empty-box {

  }

  ul div.qna-empty-box div.qna-empty-inner {
    text-align: center;
    box-sizing: border-box;
    padding: 150px 0;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.qna-empty-box div.qna-empty-inner span {
    margin: 0 auto;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  ul div.qna-empty-box div.qna-empty-inner span svg {
    max-width: 40px;
    width: 100%;
  }

  ul div.qna-empty-box div.qna-empty-inner p {
    margin-top: 30px;
    font-size: 14px;
    color: #555;
  }


  ul li {
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
  }

  ul li.showContent {
    padding-bottom: 0;
  }



  ul li div.list-inner {
    cursor: pointer;
    position: relative;
    display: flex;
    align-items: center;
  }

  ul li div.list-inner div.img-box {
    min-width: 80px;
    margin-right: 15px;
  }

  ul li div.list-inner div.img-box img {
    max-width: 80px;
    width: 100%;
  }

  ul li div.list-inner div.info-box {
    width: 100%;
    font-size: 14px;
    color: #333;
  }

  ul li div.list-inner div.info-box h4 {
    width: 75%;
    font-size: 15px;
    font-weight: 400;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  ul li div.list-inner div.info-box h5 {
    font-weight: 400;
    margin-top: 8px;
    color: #888;
  }

  ul li div.list-inner div.info-box p {
    font-size: 13px;
    color: #a0a0a0;
    font-weight: 400;
    margin-top: 5px;
  }

  ul li div.list-inner div.qna-status {
    position: absolute;
    right: 30px;
    top: 50%;
    transform: translateY(-50%);
  }

  ul li div.list-inner div.qna-status p {
    font-size: 14px;
    font-weight: 400;
    color: #a0a0a0;
  }

  ul li div.list-inner div.qna-status p.active {
    font-weight: 700;
    color: #7ebb34;
  }


  ul li div.qna-content {
    min-height: 120px;
    margin-top: 20px;
    box-sizing: border-box;
    padding: 30px;
    background-color: #f9f9f9;
  }

  ul li div.qna-content p {
    font-size: 14px;
    line-height: 1.4;
  }




</style>