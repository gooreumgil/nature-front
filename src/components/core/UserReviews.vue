<template>
  <ul class="reviews-wrapper">
    <div class="title-box">
      <nav @click="setReviewNav('myReviews')" v-bind:class="{active: isReviewNavThis('myReviews')}">내가 작성한 리뷰</nav> <span>/</span>
      <nav @click="setReviewNav('canReview')" v-bind:class="{active: isReviewNavThis('canReview')}">작성 가능한 리뷰</nav>
    </div>

    <div v-if="isReviewNavThis('canReview')" class="can-review-container">
      <div class="can-review-items" v-for="(item, index) in canReviewItems" v-bind:key="index">
        <div class="inner-box">
          <div class="img-box">
            <img v-bind:src="item.mainSrcPath" alt="">
          </div>
          <div class="info-box">
            <h4>{{ item.itemNameKor }}</h4>
            <p>주문날짜: {{ convertTimeToStr(item.orderedAt) }}</p>
          </div>
          <div class="btn-box">
            <button type="button">리뷰쓰기</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="isReviewNavThis('myReviews')" class="my-review-container">
      <div v-if="isReviewsEmpty" class="reviewEmpty">
        <p>작성한 상품 리뷰가 없습니다.</p>
      </div>
    </div>
  </ul>
</template>

<script>
export default {
  name: "UserReviews",
  props: {
    canReviewItems: {
      value: []
    },
    reviews: {
      value: []
    },
    reviewNav: {
      default: 'myReviews'
    },
    setReviewNav: {
      type: Function
    },
    convertTimeToStr: {
      type: Function
    }
  },

  methods: {
    isReviewNavThis(nav) {
      return this.reviewNav === nav;
    },

    isReviewsEmpty() {
      return this.reviews.length === 0;
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
    display: flex;
    align-items: center;
  }

  ul div.title-box nav {
    cursor: pointer;
    font-size: 17px;
    font-weight: 400;
    color: #888;
    margin-right: 10px;
  }

  ul div.title-box nav:last-child {
    margin-left: 10px;
  }

  ul div.title-box nav.active {
    font-weight: 700;
    color: #333;
  }

  ul div.title-box span {
    font-size: 13px;
  }

  ul div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
  }

  ul div.can-review-container {
    border-top: 1px solid #eaeaea;
    box-sizing: border-box;
    padding-top: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.can-review-items {

  }

  ul div.can-review-items div.sub-title {
    margin-bottom: 20px;
  }

  ul div.can-review-items div.sub-title h4 {
    font-size: 14px;
    font-weight: 400;
    color: #555;
  }

  ul div.can-review-items div.inner-box {
    display: flex;
    align-items: center;
    position: relative;
  }

  ul div.can-review-items div.inner-box div.img-box {
    width: 100px;
  }

  ul div.can-review-items div.inner-box div.img-box img {
    max-width: 100px;
    width: 100%;
  }

  ul div.can-review-items div.inner-box div.info-box {
    box-sizing: border-box;
    padding-left: 20px;
  }

  ul div.can-review-items div.inner-box div.info-box h4 {
    font-size: 16px;
    font-weight: 400;
    color: #333;
  }

  ul div.can-review-items div.inner-box div.info-box p {
    font-size: 14px;
    font-weight: 400;
    color: #888;
    margin-top: 8px;
  }

  ul div.can-review-items div.inner-box div.btn-box {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 10px;
  }

  ul div.can-review-items div.inner-box div.btn-box button {
    outline: none;
    font-size: 13px;
    cursor: pointer;
    color: #7ebb34;
    box-sizing: border-box;
    padding: 8px 13px;
    border-radius: 3px;
    border: 1px solid #7ebb34;
    background-color: #fff;
  }

  ul div.my-review-container {
    border-top: 1px solid #eaeaea;
    box-sizing: border-box;
    padding-top: 25px;
  }

  ul div.my-review-container div.reviewEmpty {

  }

  ul div.my-review-container div.reviewEmpty p {
    font-size: 15px;
    font-weight: 400;
    color: #555;
  }

</style>