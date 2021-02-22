<template>
  <ul class="clearfix">
    <li v-for="(item, index) in items" v-bind:key="index">
      <div class="inner-box">
        <div class="img-box">
          <img v-bind:src="item.imgSrcPath" alt="">
          <div class="hover-box"></div>
        </div>
        <div class="description">
          <div class="item-name">
            <h4>{{ item.nameKor }}</h4>
          </div>
          <span class="price" v-bind:class="{disable: item.discountPrice}">{{ item.price | price }} <span class="won" v-if="!item.discountPrice">원</span></span>
          <span class="discountPrice" v-if="item.discountPrice">{{ (item.price - item.discountPrice) | price }} <span class="won">원</span></span>
        </div>
      </div>
    </li>
  </ul>
</template>

<script>
export default {
  name: "ItemList",
  props: {
    items: {
      value: []
    }
  }
}
</script>

<style scoped>
  ul {
    max-width: 1260px;
    margin: 0 auto;
    width: 100%;
    margin-top: 60px;
  }

  ul li {
    float: left;
    padding: 0 10px;
    box-sizing: border-box;
    width: 25%;
    text-align: left;
  }



  ul li div.inner-box {

  }
  ul li div.inner-box div.img-box {
    background-color: #f6f6f6;
    height: 351px;
    display: flex;
    animation: ce;
    justify-content: center;
    flex-direction: column;
  }

  ul li div.description {
    padding: 0 15px 5px 15px;

  }

  ul li div.description div.item-name {
    margin-bottom: 15px;
  }
  ul li div.description div.item-name h4 {
    color: #555;
    font-weight: 300;
    font-size: 18px;
    width: 295px;

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