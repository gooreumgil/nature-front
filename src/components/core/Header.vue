<template>
  <header v-bind:class="{fontBlack: !transparent}" v-if="init">
    <div class="header-inner">
      <nav>
        <router-link to="/">
          <img v-if="transparent" src="@/assets/image/NATURE REPUBLIC_fullback.png" alt="">
          <img v-else src="@/assets/image/NATURE REPUBLIC_white_back.png" alt="">
        </router-link>
        <router-link v-bind:class="{textBoldWhite: isHeaderTab('brand')}" to="/brand">브랜드</router-link>
        <router-link v-bind:class="{textBold: isHeaderTab('item')}" to="/items">상품</router-link>
        <router-link v-bind:class="{textBold: isHeaderTab('my-page')}" to="/my-page" v-if="authenticated">마이페이지</router-link>
      </nav>

      <ul>
        <li class="search-item">
          <input type="text">
          <img v-if="transparent" src="@/assets/image/search_fullback.png" alt="">
          <img v-else src="@/assets/image/top_icon3.png" alt="">
        </li>
        <li class="cart">
          <div @click="goCart" class="img-box">
            <img v-if="transparent" src="@/assets/image/cart_fullback.png" alt="">
            <img v-else src="@/assets/image/top_icon2.png" alt="">
            <span v-bind:class="{green: !transparent}" class="count">{{ cartTotal }}</span>
          </div>
        </li>
        <li class="logout" v-if="authenticated">
        <span @click="logout">
          <img v-if="transparent" src="@/assets/image/login_fullback.png" alt="">
          <img v-else src="@/assets/image/top_icon1.png" alt="">
        </span>

        </li>

        <li class="login" v-if="!authenticated">
          <router-link to="/login" v-bind:class="{white: transparent}">로그인</router-link>
        </li>
      </ul>
    </div>

  </header>
</template>

<script>
import authApi from "@/api/AuthApi";

export default {
  computed: {
    cartTotal() {
      return this.$store.state.cartTotal;
    }
  },
  data() {
    return {
      init: false,
      authenticated: false
    }
  },
  name: "Header",
  props: {
    transparent: {
      value: true
    },
    headerTab: {
      default: 'home'
    }
  },

  async created() {

    await this.setCartTotal()

    const token = this.$cookies.get('token');
    if (token) {
      await this.setAuthenticate(token);
    }
    this.init = true;
  },

  methods: {
    logout() {
      this.$cookies.remove('token');
      window.location.reload();
    },

    async setAuthenticate(token) {
      try {
        await authApi.authenticate(token);
        this.authenticated = true;
      } catch (err) {
        console.log(err);
        this.$cookies.remove('token');
      }
    },

    async setCartTotal() {
      const cartItems = JSON.parse(this.$cookies.get('cart-items'));
      if (cartItems) {
        await this.$store.commit('SET_CART_TOTAL', cartItems.length);
      }

    },

    goCart() {
      this.$router.push('/cart');
    },

    isHeaderTab(tab) {
      return this.headerTab === tab;
    }
  }
}
</script>

<style scoped>
  header {
    width: 100%;
    text-align: left;
    position: absolute;
    left: 50%;
    top: 0;
    transform: translateX(-50%);
    z-index: 1;
  }

  header .header-inner {
    max-width: 1260px;
    width: 100%;
    margin: 0 auto;
    position: relative;
  }

  header nav {
    height: 90px;
    display: flex;
    align-items: center;
    padding-left: 10px;
  }

  header.fontBlack {
    background-color: #fff;
  }

  header.fontBlack a {
    color: #a0a0a0;
  }

  header.fontBlack nav a:hover {
    color: #333;
  }

  header nav a {
    cursor: pointer;
    margin-right: 50px;
    color: #d0d0d0;
    font-weight: 400;
    transition: all .2s ease-in-out;
  }

  header nav a.textBold {
    font-weight: 400;
    color: #333;
  }

  header nav a.textBoldWhite {
    color: #fff;
  }

  header nav a:hover {
    color: #fff;
  }


  header nav a:last-child {
    margin-right: 0;
  }

  header ul {
    height: 90px;
    display: flex;
    align-items: center;
    position: absolute;
    right: 0;
    top: 0;
  }

  header ul li {
    float: left;
    display: flex;
    height: 90px;
    align-items: center;
    box-sizing: border-box;
    padding: 0 15px;
  }

  header ul li.search-item {

  }

  header ul li.search-item img {
    cursor: pointer;
  }

  header ul li.search-item input {
    height: 38px;
    background-color: #f6f6f6;
    border-radius: 100px;
    border: 1px solid #ddd;
    width: 160px;
    outline: none;
    box-sizing: border-box;
    padding: 10px 15px;
    margin-right: 30px;
  }

  header ul li.cart {
  }
  header ul li.cart div.img-box {
    position: relative;
    cursor: pointer;
  }
  header ul li.cart div.img-box span.count {
    color: #7ebb34;
    font-size: 12px;
    width: 16px;
    height: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    border-radius: 50%;
    position: absolute;
    right: -7px;
    top: -5px;
  }

  header ul li.cart div.img-box span.count.green {
    background-color: #7ebb34;
    color: #fff;
  }

  header ul li.login a {
    cursor: pointer;
    padding: 7px 17px;
    display: inline-block;
    height: auto;
    line-height: 1.5;
    border-radius: 100px;
    font-size: 14px;
    color: #fff;
    background-color: #71a532;
  }

  header ul li.login a.white {
    background-color: #fff;
    color: #555;
    background-color: rgba(255, 255, 255, .9);
  }

  header ul li.logout span {
    cursor: pointer;
  }


</style>