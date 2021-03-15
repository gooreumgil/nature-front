<template>
  <section class="main-container">
    <Header v-bind:transparent="false" v-bind:header-tab="'item'" v-bind:set-items="setItems"/>
    <aside class="top">
      <div class="top_name">
        <h1>Search</h1>
        <p>[ 검색어: <span>{{ searchKeyword }}</span> ]</p>
      </div>
    </aside>

    <section class="product-container">
      <div class="product-top">
        <p>
          검색결과 <span id="id_color">{{ totalElements }}</span>개의 제품이 있습니다 [<span id="id_color">{{ items.length }}</span>]
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

    <CartModal v-bind:cart-add-view="cartAddView" :shopping-keep-going="shoppingKeepGoing"/>
    <SourceCodeLinkModal />
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
import SourceCodeLinkModal from "@/components/core/SourceCodeLinkModal";

export default {
  name: "Search",
  components: {SourceCodeLinkModal, CartModal, Footer, Bottom, ItemList, Header},
  data() {
    return {
      currentSort: 'sellTotal,desc&sort=createdDate,desc',
      sorts: [
        {'name': '주문많은순', 'val': 'sellTotal,desc&sort=createdDate,desc'},
        {'name': '찜많은순', 'val': 'likesCount,desc&sort=createdDate,desc'},
        {'name': '등록일순', 'val': 'createdDate,desc'},
        {'name': '높은가격순', 'val': 'price,desc'},
        {'name': '낮은가격순', 'val': 'price,asc'},
      ],
      items: [],
      totalElements: null,
      pageNum: 0,
      last: false,
      cartAddView: false,
      searchKeyword: null
    }
  },

  async created() {
    const searchKeyword = this.$route.query.searchKeyword;
    this.searchKeyword = searchKeyword;
    await this.setItems(0, 12);
  },
  methods: {


    async setItems(page, size, sort, keyword) {
      if (!sort) {
        sort = this.currentSort;
      }

      if (keyword) {
        this.searchKeyword = keyword;
      }

      const searchKeyword = this.searchKeyword;


      try {
        const res = await itemApi.searchItems(page, size, sort, searchKeyword);
        this.currentSort = sort;
        this.totalElements = res.data.totalElements;
        this.pageNum = res.data.pageable.pageNumber;
        this.items = res.data.content;
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
  padding: 150px 10px 5px 10px;
  font-size: 60px;
  font-weight: 400;
  color: #fff;
  line-height: 1.3;
}

.top_name p {
  font-size: 22px;
  padding: 0 10px;
  color: rgba(255, 255, 255, .8);
  font-weight: 300;
  line-height: 1.5;
  font-weight: 100;
}

.top_name p span {
  font-weight: 700;
  color: #fff;
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