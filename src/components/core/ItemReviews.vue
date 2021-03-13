<template>
  <div class="review-container">
    <div class="title-box">
      <p>총 <span>{{ reviews.totalElements }}</span>건의 후기가 있습니다.</p>
    </div>
    <div class="review-empty" v-if="reviewsIsEmpty()">
      <div class="review-empty-inner">
        <CommentIcon v-bind:stroke="'#a0a0a0'" />
        <h4>작성된 구매후기가 없습니다.</h4>
        <p>상품을 구매하셨다면, 구매후기를 남겨주세요.</p>
      </div>
    </div>
    <ul class="review-wrapper" v-if="!reviewsIsEmpty()">
      <li class="review-list" v-for="(review, index) in reviews.content" v-bind:key="index">
        <div class="inner-box">

          <div class="item-info">
            <div class="like-box">
              <span @click="reviewLike(review)" class="like-helper">
                <LikeIcon v-bind:fill="getLikeIconFill(review)" v-bind:stroke="'#ff1a5a'" />
              </span>
              <p>{{ review.likesCount }}</p>
            </div>

            <div class="user-icon">
              <UserIcon v-bind:fill="'#eaeaea'" />
            </div>
            <div class="writer">
              <p>{{ review.writer }}</p>
            </div>
            <div class="review-title-box">
              <h4>{{ getReviewTitle(review) }}</h4>
            </div>
            <div class="info">
              <div class="rating">
                <p>평점:</p>
                <span v-for="(star, index) in stars" v-bind:key="index">
                  <StarIcon v-bind:stroke="getStroke(review, star)" v-bind:fill="getFill(review, star)"/>
                </span>
              </div>
            </div>
          </div>

          <div class="review-info">
            <div v-if="isReviewImagesNotEmpty(review)" class="img-box">
              <div @click="reviewImgModalShow(image.s3Key)" v-bind:style="{backgroundImage: getReviewImageUrl(image)}" v-for="(image, index) in review.reviewImageResponseDtos" v-bind:key="index"></div>
            </div>
            <div class="text-box">
              <p>{{ review.content }}</p>
              <span>작성일: {{ convertTimeToStr(review.wroteAt, 'hoursAndMinutes') }}</span>
            </div>
          </div>
        </div>

      </li>
    </ul>

  </div>

</template>

<script>
import StarIcon from "@/components/icon/StarIcon";
import CommentIcon from "@/components/icon/CommentIcon";
import UserIcon from "@/components/icon/UserIcon";
import LikeIcon from "@/components/icon/LikeIcon";
import reviewApi from "@/api/ReviewApi";
export default {
  name: "ItemReviews",
  components: {LikeIcon, UserIcon, CommentIcon, StarIcon},
  props: {
    reviews: {
      value: []
    },
    convertTimeToStr: {
      type: Function
    },
    s3UrlPrefix: {
      type: String
    },
    reviewImgModalShow: {
      type: Function
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
    async reviewLike(review) {
      const token = this.$cookies.get('token');
      if (!token) {
        alert('로그인을 해주세요!');
        return;
      }

      const id = review.id;
      const userLike = review.userLike;

      if (userLike === true) {
        await reviewApi.cancelLike(token, id);
        review.likesCount--;
        review.userLike = false;
      } else {
        try {
          await reviewApi.saveLike(token, id);
          review.likesCount++;
          review.userLike = true;
        } catch (err) {
          alert('문제가 발생하였습니다.');
          console.log(err);
        }
      }


    },

    getStroke(review, star) {
      return review.rating >= star.rating ? '#7ebb34' : '#202020';

    },

    getFill(review, star) {
      return review.rating >= star.rating ? '#7ebb34' : 'none';
    },

    getReviewImageUrl(image) {
      return 'url(' + this.s3UrlPrefix + image.s3Key + ')'
    },

    showContentToggle(review) {
      review.showContent = !review.showContent;
    },

    reviewsIsEmpty() {
      return this.reviews.content.length === 0;
    },

    getReviewTitle(review) {
      const substring = review.content.substring(0, 80);
      return review.content.length > 80 ? substring + '...' : substring;
    },

    getLikeIconFill(review) {
      return review.userLike === true ? '#ff1a5a' : 'none';
    },

    isReviewImagesNotEmpty(review) {
      return review.reviewImageResponseDtos.length > 0;
    }

  }
}
</script>

<style scoped>
  div.review-container {
    max-width: 1110px;
    box-sizing: border-box;
    margin: 0 auto;
    padding: 15px;
    min-height: 450px;
  }

  div.review-container div.title-box {
    text-align: left;
    padding-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid #eaeaea;
  }

  div.review-container div.title-box p {
    font-size: 18px;
    color: #555;
    font-weight: 400;
  }

  div.review-container div.title-box p span {
    font-weight: 700;
    color: #333;
  }

  div.review-container div.review-empty {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 350px;
    border-bottom: 1px solid #eaeaea;
  }

  div.review-container div.review-empty div.review-empty-inner {

  }

  div.review-container div.review-empty div.review-empty-inner svg {
    max-width: 55px;
    width: 100%;
  }

  div.review-container div.review-empty div.review-empty-inner h4 {
    margin-top: 10px;
    font-size: 16px;
  }

  div.review-container div.review-empty div.review-empty-inner p {
    margin-top: 8px;
    font-size: 14px;
    font-weight: 400;
    color: #888;
  }

  div.review-container ul.review-wrapper {
    padding: 0;
    box-sizing: border-box;
  }

  div.review-container ul.review-wrapper li.review-list {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info {
    position: relative;
    text-align: left;
    display: flex;
    padding: 25px 10px;
    padding-left: 80px;
    border-bottom: 1px solid #eaeaea;
    flex-direction: column;
    justify-content: left;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.like-box {
    cursor: pointer;
    position: absolute;
    z-index: 9;
    right: 25px;
    top: 50%;
    transform: translateY(-50%);
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.like-box span {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.like-box span svg {
    max-width: 20px;
    width: 100%;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.like-box p {
    margin-top: 3px;
    font-size: 13px;
    font-weight: 400;
    color: #777;
  }


  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.user-icon {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.user-icon svg {
    max-width: 50px;
    width: 100%;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.writer {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.writer p {
    font-size: 15px;
    font-weight: 700;
    color: #333;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.review-title-box {
    margin-top: 10px;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.review-title-box h4 {
    width: 75%;
    font-size: 14px;
    font-weight: 400;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }


  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.img-box {
    width: 100px;
    height: 118px;
    background-color: #f6f6f6;
    margin-right: 20px;
    display: flex;
    align-items: center;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.img-box img {
    width: 100%;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info h4 {
    font-size: 16px;
    font-weight: 400;
    color: #333;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info div.rating {
    display: flex;
    align-items: center;
    margin-top: 5px;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info div.rating p {
    font-size: 14px;
    font-weight: 400;
    color: #888;
    margin-right: 5px;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info div.rating span {

  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.item-info div.info div.rating span svg {
    max-width: 15px;
    width: 100%;
    padding: 0 1px;
  }



  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info {
    padding: 30px 20px;
    box-sizing: border-box;
    text-align: left;
    background-color: #fafafa;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info div.img-box {
    display: flex;
    align-items: center;
    margin-bottom: 30px;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info div.img-box div {
    cursor: pointer;
    width: 80px;
    height: 80px;
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    border-radius: 5px;
    margin-right: 10px;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info div.text-box {
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info div.text-box p {
    font-size: 15px;
    font-weight: 400;
    color: #333;
    line-height: 1.4;
  }

  div.review-container ul.review-wrapper li.review-list div.inner-box div.review-info div.text-box span {
    display: inline-block;
    font-size: 13px;
    color: #888;
    margin-top: 20px;
  }

</style>