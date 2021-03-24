<template>
  <div class="modal-container">
    <div class="modal-inner">

      <span @click="writeModalClose" class="close-ic-helper">
        <CloseIcon v-bind:stroke="'#fff'" />
      </span>

      <div class="info-box">
        <img v-bind:src="reviewItem.mainImgPath" alt="">
        <h3>{{ reviewItem.itemNameKor }}</h3>
        <div class="rating-img">
          <span @click="checkRating(star.idx)"
                v-on:mouseover="hoverEvent(star.idx, 'in')"
                v-on:mouseout="hoverEvent(star.idx, 'out')"
                v-for="(star, index) in stars" v-bind:key="index">

            <StarIcon v-bind:idx="star.idx" v-bind:stroke="star.stroke" v-bind:fill="star.fill" v-bind:hover-event="hoverEvent" v-bind:check-rating="checkRating" />
          </span>


        </div>
      </div>

      <div class="content-box">

        <nav class="review-modal-nav">
          <button @click="setReviewModalNav('text')" v-bind:class="{active: isReviewModalNavThis('text')}" type="button">
            <PencilIcon />
          </button>
          <button @click="setReviewModalNav('img')" v-bind:class="{active: isReviewModalNavThis('img')}" type="button">
            <CameraIcon />
          </button>
        </nav>

        <div class="review-text" v-if="isReviewModalNavThis('text')">
          <textarea v-model.trim="reviewContent" cols="30" rows="10" placeholder="내용을 입력해주세요.(최대 500자)"></textarea>
        </div>
        <div class="review-img" v-if="isReviewModalNavThis('img')">

          <p>리뷰로 등록할 사진을 추가해주세요(최대 3장)</p>

          <input id="file-selector" ref="file" type="file" multiple="multiple" @change="handleFileUpload" accept="image/*">

          <ul class="image-wrapper clearfix">
            <li class="image-list" v-for="(uploadImage, index) in uploadImageFile" v-bind:key="index">
              <div class="imageItem" :style="'backgroundImage: url('+ uploadImage.result + ')'">
                <button type="button" v-if="uploadImage.result" @click="imageDelete(index)" class="img-delete">
                </button>
              </div>
            </li>
            <li class="img-add" v-if="uploadImageFile.length < 3">
              <label for="file-selector" class="addFile">
                <PlusIcon v-bind:fill="'#555'" />
              </label>
            </li>
          </ul>
        </div>

      </div>

      <div class="register-btn-box">
        <p class="bonus-point">보너스 포인트: {{ calcBonusPoint(false) | price }}P (사진 첨부시 {{ calcBonusPoint(true) }}P)</p>
        <button @click="writeReview" class="register-review" type="button">등록</button>
      </div>

    </div>
  </div>
</template>

<script>
import StarIcon from "@/components/icon/StarIcon";
import PencilIcon from "@/components/icon/PencilIcon";
import CameraIcon from "@/components/icon/CameraIcon";
import reviewApi from "@/api/ReviewApi";
import CloseIcon from "@/components/icon/CloseIcon";
import PlusIcon from "@/components/icon/PlusIcon";
export default {
  name: "WriteReviewModal",
  components: {PlusIcon, CloseIcon, CameraIcon, PencilIcon, StarIcon},
  props: {
    reviewItem: {
      value: null
    },
    writeReviewComplete: {
      type: Function
    },
    writeModalViewToggle: {
      type: Function
    },
    writeModalClose: {
      type: Function
    }
  },

  data() {
    return {
      stars: [
        {'idx': 0, 'checked': false, rating: 1, stroke: '#202020', fill: 'none'},
        {'idx': 1, 'checked': false, rating: 2, stroke: '#202020', fill: 'none'},
        {'idx': 2, 'checked': false, rating: 3, stroke: '#202020', fill: 'none'},
        {'idx': 3, 'checked': false, rating: 4, stroke: '#202020', fill: 'none'},
        {'idx': 4, 'checked': false, rating: 5, stroke: '#202020', fill: 'none'}
      ],
      reviewContent: null,
      files: [],
      uploadImageFile: [],
      reviewModalNav: 'text'
    }
  },

  created() {
  },

  methods: {
    async writeReview() {

      const checkedStar = this.stars.filter(star => star.checked === true);

      if (checkedStar.length === 0) {
        alert('평점을 입력해주세요');
        return;
      }

      if (!this.reviewContent) {
        alert('리뷰 내용을 입력해주세요');
        return;
      }

      let fileSizeOver = false;

      this.files.forEach(file => {
        if (file.size > 10485760) fileSizeOver = true;
      });

      if (fileSizeOver) {
        alert('리뷰 이미지는 10MB를 넘길 수 업습니다.');
        return;
      }

      const form = new FormData();

      const token = this.$cookies.get('token');
      const orderItemId = this.reviewItem.id;
      const itemId = this.reviewItem.itemId;

      const rating = checkedStar[0].rating;
      const reviewContent = this.reviewContent;
      const files = this.files;

      form.append('rating', rating);
      if (reviewContent) {
        form.append('content', reviewContent);
      }
      if (files) {
        files.forEach(file => {
          form.append('files', file)
        });
      }

      try {
        await reviewApi.writeReview(token, itemId, orderItemId, form);
        alert('완료');
        this.writeModalViewToggle(this.reviewItem);
        this.writeReviewComplete(this.reviewItem);

      } catch (err) {
        alert(err);
        if (err) {
          alert(err.response.data.message);
        }
      }


    },

    handleFileUpload(event) {
      const files = event.target.files;
      let maxFileSizeCheck = false;

      if ((this.files.length + files.length) > 3) {
        alert("사진은 3장 까지만 가능합니다.");
        return;
      }

      files.forEach(value => {
        if (value.size > 10485760) {
          maxFileSizeCheck = true;
        }
      });

      if (maxFileSizeCheck === true) {
        alert("파일 용량은 10MB를 넘을 수 없습니다");
        return;
      }

      const length = this.files.length + files.length;

      if (length >= 4) {
        alert("사진은 3장까지만 가능합니다");
        return;
      }

      for (let i = 0; i < files.length; i++) {
        this.files.push(files[i]);
      }

      if (window.File && window.FileList && window.FileReader) {
        // const output = document.getElementById('result');
        for (var i = 0; i < files.length; i++) {
          // var file = files[i];
          var file = files.item(i);

          //Only pics
          if (!file.type.match('image')) continue;

          var picReader = new FileReader();

          picReader.onload = function (e) {
            const result = e.target.result;

            const obj = {
              result: result,
              idx: this.imgIdx
            };

            this.uploadImageFile.push(obj);
            this.imgIdx = this.imgIdx + 1;

          }.bind(this);
          // this.imgIdx = this.imgIdx + 1;
          picReader.readAsDataURL(file);
        }
      } else {
        console.log("Your browser does not support File API");
      }
    },

    imageDelete(idx) {
      this.$delete(this.files, idx);
      this.$delete(this.uploadImageFile, idx);
    },

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
    },

    setReviewModalNav(nav) {
      this.reviewModalNav = nav;
    },

    isReviewModalNavThis(nav) {
      return this.reviewModalNav === nav;
    },

    sumItemTotalPrice(reviewItem) {
      return reviewItem.itemPrice * reviewItem.itemQuantity;
    },

    sumItemTotalDiscountPrice(reviewItem) {
      return reviewItem.itemDiscountPrice * reviewItem.itemQuantity;
    },

    calcBonusPoint(containImages) {
      const reviewItem = this.reviewItem;
      if (containImages) {
        return Math.round((this.sumItemTotalPrice(reviewItem) - this.sumItemTotalDiscountPrice(reviewItem)) * 0.012);
      } else {
        return Math.round((this.sumItemTotalPrice(reviewItem) - this.sumItemTotalDiscountPrice(reviewItem)) * 0.01);
      }
    }
  }
}
</script>

<style scoped>
  div.modal-container {
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .3);
    top: 0;
    left: 0;
    z-index: 2;
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

  div.modal-container div.modal-inner span.close-ic-helper {
    cursor: pointer;
    position: absolute;
    right: -17px;
    top: -17px;
    background-color: rgba(0, 0, 0, .2);
    border-radius: 50%;
    width: 34px;
    height: 34px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  div.modal-container div.modal-inner span.close-ic-helper svg {
    max-width: 35px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.register-btn-box {
    position: absolute;
    bottom: 20px;
    width: 100%;
    left: 0;
    text-align: center;
    box-sizing: border-box;
    padding: 0 30px;
  }

  div.modal-container div.modal-inner div.register-btn-box p {
    text-align: left;
    font-size: 13px;
    margin-bottom: 10px;
  }

  div.modal-container div.modal-inner div.register-btn-box button.register-review {
    cursor: pointer;
    outline: none;
    width: 100%;
    background-color: #7ebb34;
    border: none;
    color: #fff;
    font-weight: 700;
    font-size: 16px;
    height: 45px;
    border-radius: 3px;
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

  div.modal-container div.modal-inner div.content-box {
    /*height: 395px;*/
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
    padding: 20px 30px;
  }

  div.modal-container div.modal-inner div.content-box div.review-text {
    /*height: 100%;*/
  }

  div.modal-container div.modal-inner div.content-box div.review-text textarea {
    width: 100%;
    box-sizing: border-box;
    font-size: 14px;
    outline-color: #eaeaea;
    padding: 15px;
    border: 1px solid #eaeaea;
    border-radius: 5px;
  }

  div.modal-container div.modal-inner div.content-box div.review-img {
    /*box-sizing: border-box;*/
    /*padding: 20px;*/
  }

  div.modal-container div.modal-inner div.content-box div.review-img p {
    text-align: left;
    font-size: 14px;
    color: #777;
    margin-top: 3px;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav {
    text-align: left;
    padding-bottom: 15px;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav button {
    cursor: pointer;
    width: 32px;
    height: 32px;
    background-color: #fff;
    border-radius: 50%;
    border: 1px solid #ddd;
    outline: none;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav button:first-child {
    margin-right: 5px;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav button.active {
    background-color: #f1f1f1;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav button svg {
    max-width: 14px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.content-box nav.review-modal-nav button:last-child svg {
    max-width: 16px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.content-box div.review-img label {
    cursor: pointer;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    /*margin-top: 10px;*/
    font-size: 13px;
    font-weight: 500;
    color: #333;
    box-sizing: border-box;
    padding: 10px;
    border-radius: 3px;
    border: 1px solid #eaeaea;
    background-color: #fff;
  }

  div.modal-container div.modal-inner div.content-box div.review-img label svg {
    max-width: 20px;
    width: 100%;
    /*margin-left: 5px;*/
  }

  div.modal-container div.modal-inner div.content-box div.review-img input[type=file] {
    display: none;
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper {
    padding-top: 10px;
    width: calc(100% + 10px);
    margin: 0 auto;
    transform: translateX(-5px);
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper li {
    float: left;
    width: 33.33%;
    box-sizing: border-box;
    height: 115px;
    padding: 5px;
    position: relative;

    /*height: 105px;*/
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper li div.imageItem {
    background-position: center center;
    background-size: cover;
    background-repeat: no-repeat;
    border-radius: 3px;
    height: 100%;
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper li div.imageItem button {
    outline: none;
    cursor: pointer;
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper li div.imageItem button.img-delete {
    -webkit-appearance: none;
    cursor: pointer;
    background-color: rgba(0, 0, 0, .3);
    line-height: 0;
    border-radius: 50%;
    width: 22px;
    height: 22px;
    position: absolute;
    right: 10px;
    top: 10px;
    text-align: center;
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 64 64' aria-labelledby='title' aria-describedby='desc' role='img' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Ctitle%3EClose%3C/title%3E%3Cdesc%3EA line styled icon from Orion Icon Library.%3C/desc%3E%3Cpath data-name='layer1' fill='none' stroke='%23ffffff' stroke-miterlimit='10' stroke-width='3' d='M41.999 20.002l-22 22m22 0L20 20' stroke-linejoin='round' stroke-linecap='round'%3E%3C/path%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-size: 18px;
    background-position: center center;
  }

  div.modal-container div.modal-inner div.content-box div.review-img ul.image-wrapper label svg {
    max-width: 20px;
    width: 100%;
  }






</style>