<template>
  <div class="review-container">

    <ul class="review-wrapper">
      <li class="review-list" v-for="(review, index) in reviews" v-bind:key="index">
        <div class="inner-box">

          <div class="item-info">
            <div class="img-box">
              <img v-bind:src="review.itemResponseDto.mainImgPath" alt="">
            </div>
            <div class="info">
              <h4>{{ review.itemResponseDto.nameKor }}</h4>
              <div class="rating">
                <p>평점:</p>
                <span v-for="(star, index) in stars" v-bind:key="index">
                  <StarIcon v-bind:stroke="getStroke(review, star)" v-bind:fill="getFill(review, star)"/>
                </span>
              </div>
            </div>
          </div>

          <div class="review-info" v-if="review.showContent">
            <div class="img-box">
              <div v-bind:style="{backgroundImage: getReviewImageUrl(image)}" v-for="(image, index) in review.reviewImageResponseDtos" v-bind:key="index"></div>
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
export default {
  name: "ItemReviews",
  components: {StarIcon},
  props: {
    reviews: {
      value: []
    },
    convertTimeToStr: {
      type: Function
    },
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
    getStroke(review, star) {
      return review.rating >= star.rating ? '#7ebb34' : '#202020';

    },

    getFill(review, star) {
      return review.rating >= star.rating ? '#7ebb34' : 'none';
    },

    getReviewImageUrl(image) {
      return 'url(' + this.s3UrlPrefix + image.s3Key + ')'
    }
  }
}
</script>

<style scoped>

</style>