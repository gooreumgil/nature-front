<template>
  <header>
    <nav>
      <router-link to="/">
        <img src="@/assets/image/NATURE REPUBLIC_fullback.png" alt="">
      </router-link>
      <router-link to="/brand">브랜드</router-link>
      <router-link to="/items">상품</router-link>
      <router-link to="/my-page">마이페이지</router-link>
    </nav>

    <ul>
      <li class="search-item">
        <input type="text">
        <img src="@/assets/image/search_fullback.png" alt="">
      </li>
      <li class="cart">
        <div class="img-box">
          <img src="@/assets/image/cart_fullback.png" alt="">
          <span class="count">0</span>
        </div>
      </li>
      <li class="logout">
        <img src="@/assets/image/login_fullback.png" alt="">
      </li>
    </ul>
  </header>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
  header {
    max-width: 1260px;
    width: 100%;
    text-align: left;
    margin: 0 auto;
    position: absolute;
    top: 0;
  }

  header nav {
    height: 90px;
    display: flex;
    align-items: center;
  }

  header nav a {
    margin-right: 50px;
    color: #fff;
    font-weight: 400;
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
  }
  header ul li.cart div.img-box span.count {
    color: #7ebb34;
    font-size: 12px;
    width: 15px;
    height: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    border-radius: 50%;
    position: absolute;
    right: -7px;
    top: -5px;
  }


</style>