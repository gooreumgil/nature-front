<template>
  <div class="modal-container">
    <div class="modal-inner">
      <div class="info-box">
        <img v-bind:src="reviewItem.mainSrcPath" alt="">
        <h3>{{ reviewItem.itemNameKor }}</h3>
        <div class="rating-img">
          <span v-for="(star, index) in stars" v-bind:key="index">
            <StarIcon v-bind:idx="star.idx" v-bind:stroke="star.stroke" v-bind:fill="star.fill" v-bind:hover-event="hoverEvent" v-bind:check-rating="checkRating" />
          </span>


        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StarIcon from "@/components/icon/StarIcon";
export default {
  name: "WriteReviewModal",
  components: {StarIcon},
  props: {
    reviewItem: {
      value: null
    }
  },

  data() {
    return {
      stars: [
        {'idx': 0, 'checked': false, stroke: '#202020', fill: 'none'},
        {'idx': 1, 'checked': false, stroke: '#202020', fill: 'none'},
        {'idx': 2, 'checked': false, stroke: '#202020', fill: 'none'},
        {'idx': 3, 'checked': false, stroke: '#202020', fill: 'none'},
        {'idx': 4, 'checked': false, stroke: '#202020', fill: 'none'}
      ],
    }
  },

  created() {
    console.log(this.stars);
  },

  methods: {
    hoverEvent(idx, type) {

      if (type === 'in') {
        this.starStyle(this.stars[idx], 'color');

        const filterStars = this.stars.filter(star => star.idx < idx);
        if (filterStars.length > 0){
          filterStars.forEach(star => {
            this.starStyle(star, 'color');
          })
        }

      } else {

        const checkedStar = this.geCheckedStar();

        if (checkedStar.length > 0) {
          const checkedStarIdx = checkedStar[0].idx;
          if (checkedStarIdx < idx) {
            const filter = this.stars.filter(star => star.idx > checkedStarIdx);
            if (filter.length > 0) {
              filter.forEach(star => {
                this.starStyle(star, 'line');
              })
            }
          }
          return;
        }

        const filterStars = this.stars.filter(star => star.idx >= idx);
        if (filterStars) {
          filterStars.forEach(star => {
            this.starStyle(star, 'line');
          })
        }
      }
    },

    checkRating(idx) {
      const filterStars = this.stars.filter(star => star.checked === true);
      if (filterStars.length > 0) {
        filterStars.forEach(star => star.checked = false);
      }
      this.stars[idx].checked = true;
      const filter = this.stars.filter(star => star.idx > idx);
      filter.forEach(star => {
        this.starStyle(star, 'line');
      })
    },

    starStyle(star, style) {
      if (style === 'line') {
        star.stroke = '#202020';
        star.fill = 'none';
      } else {
        star.stroke = '#7ebb34';
        star.fill = '#7ebb34';
      }
    },

    geCheckedStar() {
      return this.stars.filter(star => star.checked === true);
    }
  }
}
</script>

<style scoped>
  div.modal-container {
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .15);
    top: 0;
    left: 0;
  }

  div.modal-container div.modal-inner {
    background-color: #fff;
    border-radius: 10px;
    width: 400px;
    height: 700px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  div.modal-container div.modal-inner div.info-box {
    text-align: center;
    background-color: #f6f6f6;
    box-sizing: border-box;
    padding: 30px 30px 35px 30px;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }

  div.modal-container div.modal-inner div.info-box img {
    max-width: 160px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.info-box h3 {
    font-size: 16px;
    color: #555;
    font-weight: 400;
  }

  div.modal-container div.modal-inner div.info-box div.rating-img {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 10px;
  }

  div.modal-container div.modal-inner div.info-box div.rating-img span {
    display: inline-block;
  }


</style>