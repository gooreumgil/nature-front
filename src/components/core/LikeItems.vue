<template>
  <ul class="like-item-wrapper clearfix">
    <div class="title-box">
      <h3>찜리스트</h3>
      <p>고객님의 위시리스트 상품입니다.</p>
    </div>
    <div class="divider">
      <div class="divider-inner"></div>
    </div>
    <li class="like-item-list" v-for="(likeItem, index) in likeItems" v-bind:key="index">
      <div class="inner-box">
        <div @click="goItemDetail(likeItem.id)" class="img-box">
          <img v-bind:src="likeItem.mainImgPath" alt="">
          <div class="hover-box"></div>
        </div>
        <div class="description borderBottom">
          <div class="item-name">
            <h4>{{ likeItem.nameKor }}</h4>
          </div>

          <span class="price" v-bind:class="{disable: likeItem.discountPrice}">
            {{ likeItem.price | price }} <span class="won" v-if="!likeItem.discountPrice">원</span>
          </span>
          <span class="discountPrice" v-if="likeItem.discountPrice">
            {{ (likeItem.price - likeItem.discountPrice) | price }} <span class="won">원</span>
          </span>
        </div>
      </div>
    </li>
  </ul>
</template>

<script>
export default {
  name: "LikeItems",
  props: {
    likeItems: {
      value: []
    }
  },

  methods: {
    goItemDetail(id) {
      this.$router.push('/items/' + id);
    }
  }
}
</script>

<style scoped>
  ul {
    box-sizing: border-box;
    padding-top: 10px;
    /*padding-left: 10px;*/
    /*padding-right: 10px;*/
  }

  ul div.title-box {
    box-sizing: border-box;
    padding: 10px;
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
    box-sizing: border-box;
    padding-left: 10px;
    margin-bottom: 10px;
  }

  ul div.divider div.divider-inner {
    width: 100%;
    height: 1px;
    background-color: #eaeaea;
  }


  ul li {
    float: left;
    width: 25%;
    box-sizing: border-box;
    padding: 10px;
    padding-bottom: 30px;
  }

  ul li div.inner-box {

  }

  ul li div.inner-box div.img-box {
    cursor: pointer;
    position: relative;
    background-color: #f6f6f6;
    height: 280px;
    display: flex;
    animation: ce;
    justify-content: center;
    flex-direction: column;
  }

  ul li div.inner-box div.img-box img {
    width: 100%;
  }

  ul li div.description {
    padding: 0 15px 5px 5px;
    position: relative;
  }

  ul li div.description.borderBottom {
    padding-bottom: 15px;
    border-bottom: 2px solid #efefef;
    transition: all .1s ease-in-out;
  }

  ul li:hover .description.borderBottom {
    border-bottom: 2px solid #8bc545;
  }

  ul li div.inner-box div.img-box:hover > div.hover-box {
    opacity: 1;
    vertical-align: middle;
    pointer-events: none;
  }

  ul li div.inner-box div.img-box div.hover-box {
    opacity: 0;
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    text-align: center;
    background-color: rgba(255, 255, 255, .2);
    transition: all .2s ease-in-out;
  }

  ul li div.description div.item-name {
    margin-bottom: 15px;
  }
  ul li div.description div.item-name h4 {
    color: #555;
    font-weight: 300;
    font-size: 18px;
    width: 100%;

    margin-top: 20px;
    margin-left: 0;
    box-sizing: border-box;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  ul li div.description span.price {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
    margin-right: 15px;
  }

  ul li div.description span.won {
    font-size: 18px;
    font-weight: 300;
    color: #8f8f8f;

  }

  ul li div.description span.price.disable {
    font-size: 16px;
    color: #9b9b9b;
    text-decoration: line-through;
    font-weight: 100;
  }

  ul li div.description span.discountPrice {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
  }

</style>