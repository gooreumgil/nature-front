<template>
  <ul class="clearfix">
    <li v-bind:class="{categoryProduct: categoryItem}" v-for="(item, index) in items" v-bind:key="index">
      <div class="inner-box">
        <div @click="goItemDetail(item.id)" class="img-box">
          <img v-bind:src="item.mainImgPath" alt="">
          <div class="hover-box"></div>
        </div>
        <div class="description" v-bind:class="{borderBottom: cart}">
          <div class="item-name">
            <h4>{{ item.nameKor }}</h4>
            <div class="item-description" v-if="categoryItem">
              <p >{{ item.description }}</p>
            </div>
          </div>
          <span class="price" v-bind:class="{disable: item.discountPrice}">{{ item.price | price }} <span class="won" v-if="!item.discountPrice">원</span></span>
          <span class="discountPrice" v-if="item.discountPrice">{{ (item.price - item.discountPrice) | price }} <span class="won">원</span></span>

          <div @click="addCart(item.id)" class="cart-box" v-if="cart">
            <img src="@/assets/image/list_img/list/cart.png" alt="">
          </div>
        </div>

      </div>
    </li>
  </ul>
</template>

<script>
import commonService from "@/service/commonService";

export default {
  name: "ItemList",
  props: {
    items: {
      value: []
    },
    categoryItem: {
      value: false
    },
    cart: {
      value: false
    }
  },

  methods: {
    addCart: function (id) {

      try {
        commonService.addCart.bind(this)(id);
        this.cartAddComp();
      } catch (err) {
        alert(err.message);
      }
    },

    setCartTotal(num) {
      commonService.setCartTotal(num);
    },

    cartAddComp() {
      this.$emit('cartAddComplete');
    },

    goItemDetail(id) {
      this.$router.push('/items/' + id);
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

  ul li.categoryProduct {
    padding-bottom: 60px;
  }

  ul li.categoryProduct:hover .description.borderBottom {
    border-bottom: 2px solid #8bc545;
  }

  ul li div.inner-box {

  }
  ul li div.inner-box div.img-box {
    cursor: pointer;
    position: relative;
    //background-color: #f6f6f6;
    height: 351px;
    display: flex;
    animation: ce;
    justify-content: center;
    flex-direction: column;
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

  ul li div.inner-box div.img-box:hover > div.hover-box {
    opacity: 1;
    vertical-align: middle;
    pointer-events: none;
  }

  section section ul li div.inner-box div.img-box div.hover-box {
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
    width: 295px;

    margin-top: 20px;
    margin-left: 0;
    box-sizing: border-box;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  ul li div.description div.item-name div.item-description {

  }
  ul li div.description div.item-name div.item-description p {
    font-size: 14px;
    color: #a0a0a0;
    font-weight: 200;
    line-height: 1.5;
    padding: 5px 0px;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    height: calc(12px * 1.5 * 2);
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

  ul li div.cart-box {
    position: absolute;
    right: 10px;
    bottom: 13px;
    cursor: pointer;
  }

</style>