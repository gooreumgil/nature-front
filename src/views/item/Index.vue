<template>
  <section class="main-container">
    <Header v-bind:transparent="false"/>
    <aside class="top">
      <div class="top_name">
        <h1>Products</h1>
        <p>네이처리퍼블릭은 자연의 완벽함을 믿습니다.<br>우리는 전세계의 청정 자연에서 자연이 주는 선물을 찾아서 가장 자연<br>그대로에 가까운 상태로 고객들에게 제공하는 것을 목표로 합니다.</p>
      </div>
    </aside>

    <section class="all-best">
      <div class="title-container">
        <h3 class="main-font">Category Best</h3>
        <p class="sub-font">전체 베스트 상품입니다.</p>
        <ItemList v-bind:items="popularItems" />
      </div>
    </section>

    <nav class="category-nav">
      <ul class="category-wrapper clearfix">
        <li @click="setItems(0, 12, null, category.name)" class="category-list" v-for="(category, index) in categories" v-bind:key="index">
          <div v-bind:class="{active: currentCategory === category.name}" class="list-inner">
            <p>{{ category.name }}</p>

          </div>
        </li>
      </ul>
    </nav>

    <section class="product-container">
      <div class="product-top">
        <p>
          전체 <span id="id_color">{{ totalElements }}</span>개의 제품이 있습니다 [<span id="id_color">{{ items.length }}</span>]
        </p>
      </div>

      <nav class="sort-nav">

        <button @click="setItems(0, 12, sort.val)" type="button" v-bind:class="{active: currentSort === sort.val}" v-for="(sort, index) in sorts" v-bind:key="index">
          <span>
            <img v-if="currentSort === sort.val" src="@/assets/image/check.svg">
            <span v-else class="dot">· </span>
          </span>
          {{ sort.name }}
        </button>
      </nav>

      <ItemList v-bind:items="items" v-bind:category-item="true" v-bind:cart="true" v-on:cartAddComplete="cartAddComplete"/>
      <div class="more-view" v-if="!last">
        <button @click="setItems(pageNum + 1, 12, null, null, true)" type="button">
          <img src="@/assets/image/list_img/list/arrow.png" alt="">
        </button>
      </div>
    </section>

    <Bottom />
    <Footer />

<!--    <div v-if="cartAddView" class="cart-add-modal">-->
<!--      <div class="modal-inner">-->
<!--        <CartIcon />-->
<!--        <h3>장바구니에 담았습니다.</h3>-->

<!--        <div class="btn-box">-->
<!--          <button type="button" @click="shoppingKeepGoing">계속쇼핑</button>-->
<!--          <button type="button" @click="goCart">장바구니</button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

    <CartModal v-bind:cart-add-view="cartAddView" :shopping-keep-going="shoppingKeepGoing"/>
  </section>
</template>

<script>
import Header from "@/components/core/Header";
import itemApi from "@/api/ItemApi";
import categoryApi from "@/api/CategoryApi";
import ItemList from "@/components/core/ItemList";
import Bottom from "@/components/core/Bottom";
import Footer from "@/components/core/Footer";
import CartModal from "@/components/core/CartModal";

export default {
  name: "Index",
  components: {CartModal, Footer, Bottom, ItemList, Header},
  data() {
    return {
      popularItems: [],
      categories: [],
      currentCategory: 'ALL',
      currentSort: 'likesCount,desc&sort=createdDate,desc',
      sorts: [
          {'name': '인기상품순', 'val': 'likesCount,desc&sort=createdDate,desc'},
          {'name': '등록일순', 'val': 'createdDate,desc'},
          {'name': '높은가격순', 'val': 'price,desc'},
          {'name': '낮은가격순', 'val': 'price,asc'},
      ],
      items: [],
      totalElements: null,
      pageNum: 0,
      last: false,
      cartAddView: false
    }
  },

  async created() {
    await this.setPopularItems(0, 4);
    await this.setCategories();
    await this.setItems(0, 12);
  },
  methods: {

    // 전체 카테고리 인기상품 가져오기
    async setPopularItems(page, size) {
      try {
        const res = await itemApi.getItems(page, size, 'likesCount,desc&sort=createdDate,desc');
        this.popularItems = res.data.content;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    },

    async setCategories() {
      try {
        const res = await categoryApi.getAll();
        this.categories = res.data;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    },

    async setItems(page, size, sort, category, addItems) {
      if (!sort) {
        sort = this.currentSort;
      }

      if (!category) {
        category = this.currentCategory;
      }

      try {
        const res = await itemApi.getItems(page, size, sort, category);
        this.currentSort = sort;
        this.currentCategory = category;
        if (addItems) {
          const content = res.data.content;
          content.forEach(val => {
            this.items.push(val);
          })
        } else {
          this.items = res.data.content;
        }
        this.totalElements = res.data.totalElements;
        this.pageNum = res.data.pageable.pageNumber;
        this.last = res.data.last;
      } catch (err) {
        alert("문제가 발생하였습니다.");
        console.log(err);
      }
    },

    cartAddComplete() {
      this.cartAddView = true;
    },

    shoppingKeepGoing() {
      this.cartAddView = false;
    },

    goCart() {
      this.cartAddView = false;
      this.$router.push('/cart');
    }
  }
}
</script>

<style scoped>
  section.main-container {
    position: relative;
  }

  /*section.main-container div.cart-add-modal {*/
  /*  width: 300px;*/
  /*  height: 300px;*/
  /*  background-color: #fff;*/
  /*  border-radius: 30px;*/
  /*  position: fixed;*/
  /*  left: 50%;*/
  /*  top: 50%;*/
  /*  transform: translate(-50%, -50%);*/
  /*  box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, .15);*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner {*/
  /*  width: 100%;*/
  /*  height: 100%;*/
  /*  display: flex;*/
  /*  align-items: center;*/
  /*  justify-content: center;*/
  /*  flex-direction: column;*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner svg {*/
  /*  max-width: 100px;*/
  /*  width: 100px;*/
  /*  transform: translateX(-5px);*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner h3 {*/
  /*  margin-top: 20px;*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner .btn-box {*/
  /*  box-sizing: border-box;*/
  /*  padding-top: 15px;*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner .btn-box button {*/
  /*  border-radius: 3px;*/
  /*  box-sizing: border-box;*/
  /*  padding: 5px 10px;*/
  /*  margin: 0 5px;*/
  /*  font-size: 13px;*/
  /*  outline: none;*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner .btn-box button:first-child {*/
  /*  background-color: #fff;*/
  /*  border: 1px solid #eaeaea;*/
  /*}*/

  /*section.main-container div.cart-add-modal div.modal-inner .btn-box button:last-child {*/
  /*  background-color: #555;*/
  /*  color: #fff;*/
  /*  font-weight: 400;*/
  /*}*/

  .top {
    width: 100%;
    padding-top: 90px;
    height: 454px;
    font-family: 'Noto Sans KR', sans-serif;
    text-align: center;

    background: linear-gradient(
        rgba(124, 124, 124, .4),
        rgba(124, 124, 124, .4)
    ), url("../../assets/image/list_img/list_banner_bg.png");


    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
  }

  .top .top_name {
    max-width: 1260px;
    margin: 0 auto;
  }

  .top h1 {
    padding: 130px 10px 5px 10px;
    font-size: 60px;
    font-weight: 400;
    color: #fff;
    line-height: 1.3;
  }

  .top_name p {
    font-size: 16px;
    padding: 0 10px;
    color: rgba(255, 255, 255, .8);
    font-weight: 300;
    line-height: 1.5;
    font-weight: 100;
  }


  .top li {
    float: left;
    margin-left: 10px;
  }

  .top li a {
    color: #333;
  }

  .top li img {
    margin-left: 7px;
  }

  .top ul {
    position: absolute;
    top: -35px;
    color: #333;
    font-size: 14px;
    font-weight: 100;

  }

  .top ul::after {
    content: "";
    display: block;
    clear: both;
  }

  /* category best */
  section.all-best {
    padding-top: 120px;
  }

  /* category nav */
  nav.category-nav {
    padding-top: 80px;
  }

  nav.category-nav ul {
    max-width: 1250px;
    width: 100%;
    margin: 0 auto;
  }
  nav.category-nav ul li {
    cursor: pointer;
    float: left;
    width: 10%;
    height: 100px;
    box-sizing: border-box;
    padding: 5px;
    color: #333;
    font-size: 15px;
  }

  nav.category-nav ul li div.list-inner {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 10px;
    border: 1px solid #e0e0e0;
    box-sizing: border-box;
    padding: 15px;
    height: 100%;
    width: 100%;
  }

  nav.category-nav ul li div.list-inner.active {
    background-color: #71a532;
    color: #fff;
    font-weight: 700;
    border: 0;
  }

  nav.category-nav ul li div.list-inner p {
    font-weight: 400;
    word-break: keep-all;
    line-height: 1.3;
  }

  section.product-container {
    position: relative;
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    padding-top: 80px;
  }

  section.product-container .product-top {
    position: absolute;
    left: 10px;
    top: 80px;
  }

  section.product-container .product-top p {

  }
  section.product-container .product-top p span {
  }

  section.product-container nav.sort-nav {
    position: absolute;
    right: 10px;
    top: 78px;
  }

  section.product-container nav.sort-nav button {
    cursor: pointer;
    background: none;
    outline: none;
    font-size: 14px;
    color: #a0a0a0;
    font-weight: 400;
  }

  section.product-container nav.sort-nav button.active {
    color: #555;
    font-weight: 700;
  }

  section.product-container nav.sort-nav button span {
    width: 15px;
    display: inline-block;
  }
  section.product-container nav.sort-nav button span img {
    width: 15px;
    max-width: 100%;
    vertical-align: middle;
  }

  section.product-container nav.sort-nav button span span.dot {

  }

  section.product-container div.more-view {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    text-align: center;
  }

  section.product-container div.more-view button {
    cursor: pointer;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    box-sizing: border-box;
    padding: 0;
    border: 1px solid #ddd;
    background-color: transparent;
    outline: none;
  }

</style>