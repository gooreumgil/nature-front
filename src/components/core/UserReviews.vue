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
    font-size: 18px;
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
    font-size: 15px;
    color: #777;
    margin-top: 6px;
  }

  ul div.can-review-container {
    border-top: 1px solid #eaeaea;
    box-sizing: border-box;
    padding-top: 25px;
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
  }

  ul div.can-review-items div.inner-box div.img-box {
    width: 80px;
  }

  ul div.can-review-items div.inner-box div.img-box img {
    max-width: 80px;
    width: 100%;
  }

  ul div.my-review-container {
    border-top: 1px solid #eaeaea;
    box-sizing: border-box;
    padding-top: 25px;
  }

</style>