<template>
  <ul class="reviews-wrapper">
    <div class="title-box">
      <nav @click="setReviewNav('myReviews')" v-bind:class="{active: isReviewNavThis('myReviews')}">내가 작성한 리뷰</nav> <span>/</span>
      <nav @click="setReviewNav('canReview')" v-bind:class="{active: isReviewNavThis('canReview')}">작성 가능한 리뷰</nav>
    </div>

    <div v-bind:class="{borderBottom: canReviewItems.content.length > 0}" v-if="isReviewNavThis('canReview')" class="can-review-container">
      <div v-if="isCanReviewsEmpty()" class="reviewEmpty">
        <span class="img-helper">
          <ExclamationIcon v-bind:stroke="'#a0a0a0'" />
        </span>
        <p>리뷰 작성 가능한 상품이 없습니다.</p>
      </div>

      <div class="can-review-items" v-for="(item, index) in canReviewItems.content" v-bind:key="index">
        <div class="inner-box">
          <div class="img-box">
            <img v-bind:src="item.mainImgPath" alt="">
          </div>
          <div class="info-box">
            <h4>{{ item.itemNameKor }}</h4>
            <p>주문날짜: {{ convertTimeToStr(item.orderedAt, 'monthAndDay') }}</p>
          </div>
          <div class="btn-box">
            <button @click="writeModalViewToggle(item)" type="button">리뷰쓰기</button>
          </div>
        </div>
      </div>

      <Pagination v-if="canReviewItems.totalPages > 1" v-bind:page="canReviewItems" @nextPage="canReviewNextPage" @previousPage="canReviewPreviousPage" @goPage="goCanReviewPage"/>

    </div>

    <div v-if="isReviewNavThis('myReviews')" class="my-review-container">
      <div v-if="isReviewsEmpty()" class="reviewEmpty">
        <span class="img-helper">
          <ExclamationIcon v-bind:stroke="'#a0a0a0'" />
        </span>
        <p>작성한 상품 리뷰가 없습니다.</p>
      </div>
      <ul v-else class="my-review-wrapper">
        <li class="my-review-list" v-for="(myReview, index) in myReviews.content" v-bind:key="index">
          <div class="inner-box">
            <div @click="myReviewShowContentToggle(myReview)" class="item-info">
              <div class="img-box">
                <img v-bind:src="myReview.itemResponseDto.mainImgPath" alt="">
              </div>
              <div class="info">
                <h4>
                  {{ myReview.itemResponseDto.nameKor }}
                  <span class="exist-review-image" v-if="isExistReviewImage(myReview)">
                    <CameraIcon />
                  </span>
                </h4>
                <div class="rating">
                  <p>평점:</p>
                  <span v-for="(star, index) in stars" v-bind:key="index">
                    <StarIcon v-bind:stroke="getStroke(myReview, star)" v-bind:fill="getFill(myReview, star)"/>
                  </span>
                </div>
              </div>

              <div class="likes-box">
                <div class="likes-inner">
                  <LikeIcon v-bind:stroke="'#ff1a5a'" />
                  <p>{{ myReview.likesCount }}</p>
                </div>
              </div>
            </div>

            <div class="review-info" v-if="myReview.showContent">
              <div v-if="isExistReviewImage(myReview)" class="img-box">
                <div @click="reviewImgModalShow(image.s3Key)" v-bind:style="{backgroundImage: getReviewImageUrl(image)}" v-for="(image, index) in myReview.reviewImageResponseDtos" v-bind:key="index"></div>
              </div>
              <div class="text-box">
                <p>{{ myReview.content }}</p>
                <span>작성일: {{ convertTimeToStr(myReview.wroteAt, 'hoursAndMinutes') }}</span>
              </div>
            </div>

          </div>
        </li>
      </ul>
      <Pagination v-if="myReviews.totalPages > 1" v-bind:page="myReviews" @nextPage="myReviewNextPage" @previousPage="myReviewPreviousPage" @goPage="goMyReviewPage"/>
    </div>
  </ul>
</template>

<script>
import StarIcon from "@/components/icon/StarIcon";
import ExclamationIcon from "@/components/icon/ExclamationIcon";
import LikeIcon from "@/components/icon/LikeIcon";
import CameraIcon from "@/components/icon/CameraIcon";
import Pagination from "@/components/core/Pagination";
export default {
  name: "UserReviews",
  components: {Pagination, CameraIcon, LikeIcon, ExclamationIcon, StarIcon},
  props: {
    canReviewItems: {
      value: []
    },
    myReviews: {
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
    },
    writeModalViewToggle: {
      type: Function
    },
    s3UrlPrefix: {
      type: String
    },
    reviewImgModalShow: {
      type: Function
    },
    myReviewNextPage: {
      type: Function
    },
    myReviewPreviousPage: {
      type: Function
    },
    goMyReviewPage: {
      type: Function
    },
    canReviewNextPage: {
      type: Function
    },
    canReviewPreviousPage: {
      type: Function
    },
    goCanReviewPage: {
      type: Function
    },

  },

  computed: {
    getCurrentReviewNav() {
      return this.$store.state.currentReviewNav;
    }
  },

  created() {
    const currentReviewNav = this.getCurrentReviewNav;
    if (currentReviewNav) {
      this.setReviewNav(currentReviewNav);
    }
  },

  data() {
    return {
      stars: [
        {'rating': 1, stroke: '#202020', fill: 'none'},
        {'rating': 2, stroke: '#202020', fill: 'none'},
        {'rating': 3, stroke: '#202020', fill: 'none'},
        {'rating': 4, stroke: '#202020', fill: 'none'},
        {'rating': 5, stroke: '#202020', fill: 'none'}
      ],
    }
  },

  methods: {
    isReviewNavThis(nav) {
      return this.reviewNav === nav;
    },

    getStroke(myReview, star) {
      return myReview.rating >= star.rating ? '#7ebb34' : '#202020';

    },

    getFill(myReview, star) {
      return myReview.rating >= star.rating ? '#7ebb34' : 'none';
    },

    isReviewsEmpty() {
      return this.myReviews.length === 0;
    },

    isCanReviewsEmpty() {
      return this.canReviewItems.content.length === 0;
    },

    myReviewShowContentToggle(myReview) {
      myReview.showContent = !myReview.showContent;
    },

    getReviewImageUrl(image) {
      return 'url(' + this.s3UrlPrefix + image.s3Key + ')'
    },

    isExistReviewImage(myReview) {
      return myReview.reviewImageResponseDtos.length > 0;
    },
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
    /*border-bottom: 1px solid #eaeaea;*/
  }

  ul div.can-review-container.borderBottom {
    /*border-bottom: 1px solid #eaeaea;*/

  }

  ul div.can-review-container div.page-container {
    margin-top: 40px;
  }

  ul div.can-review-items {
    box-sizing: border-box;
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
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
    height: 118px;
    background-color: #f6f6f6;
    display: flex;
    align-items: center;
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
  }

  ul div.reviewEmpty {
    text-align: center;
    box-sizing: border-box;
    padding: 150px 0;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.reviewEmpty span {
    margin: 0 auto;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  ul div.reviewEmpty span svg {
    max-width: 40px;
    width: 100%;
  }

  ul div.reviewEmpty p {
    margin-top: 30px;
    font-size: 14px;
    color: #555;
  }

  ul div.my-review-container ul.my-review-wrapper {
    padding: 0;
    margin-bottom: 40px;

  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list {

  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box {

  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info {
    position: relative;
    cursor: pointer;
    display: flex;
    align-items: center;
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
  }
  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.img-box {
    width: 100px;
    height: 118px;
    //background-color: #f6f6f6;
    margin-right: 20px;
    display: flex;
    align-items: center;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.img-box img {
    width: 100%;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info {

  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info h4 {
    font-size: 16px;
    font-weight: 400;
    color: #333;
    position: relative;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info h4 span {
    font-weight: 700;
    color: #333;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info h4 span.exist-review-image {
    font-weight: 700;
    color: #333;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: -30px;

  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info h4 span.exist-review-image svg {
    max-width: 18px;
    width: 100%;
  }


  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info div.rating {
    display: flex;
    align-items: center;
    margin-top: 10px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info div.rating p {
    font-size: 14px;
    font-weight: 400;
    color: #888;
    margin-right: 5px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.info div.rating svg {
    max-width: 15px;
    width: 100%;
    padding: 0 1px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.likes-box {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 25px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.likes-box div.likes-inner {
    text-align: center;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.likes-box div.likes-inner svg {
    max-width: 20px;
    width: 100%;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.item-info div.likes-box div.likes-inner p {
    margin-top: 3px;
    font-size: 13px;
    font-weight: 400;
    color: #777;
  }


  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info {
    box-sizing: border-box;
    padding: 30px 20px;
    background-color: #fafafa;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info div.img-box {
    display: flex;
    align-items: center;
    margin-bottom: 30px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info div.img-box div {
    cursor: pointer;
    width: 80px;
    height: 80px;
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    border-radius: 5px;
    margin-right: 10px;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info div.text-box {
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info div.text-box p {
    font-size: 15px;
    font-weight: 400;
    color: #333;
    line-height: 1.4;
  }

  ul div.my-review-container ul.my-review-wrapper li.my-review-list div.inner-box div.review-info div.text-box span {
    display: inline-block;
    font-size: 13px;
    color: #888;
    margin-top: 20px;
  }


</style>