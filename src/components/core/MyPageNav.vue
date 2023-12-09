<template>
  <nav>
    <div class="nav-inner">
      <div class="profile">
        <h3>마이페이지</h3>
        <p class="name">{{ user.name }}</p>
        <p class="email">{{ user.email }}</p>
      </div>

      <div class="tab">
        <div class="tab-list">
          <button @click="setCurrentTab('orderAndDelivery')" v-bind:class="{active: isCurrentTabThis('orderAndDelivery')}" type="button">주문배송조회</button>
        </div>

        <div class="tab-list">
          <button @click="setCurrentTab('likes')" v-bind:class="{active: isCurrentTabThis('likes')}" type="button">찜리스트</button>
        </div>

        <div class="tab-list">
          <button @click="setCurrentTab('qna')" v-bind:class="{active: isCurrentTabThis('qna')}" type="button">상품 Q&A</button>
        </div>

        <div class="tab-list">
          <button @click="setCurrentTab('review')" v-bind:class="{active: isCurrentTabThis('review')}" type="button">구매후기</button>
        </div>

        <div class="tab-list">
          <button @click="noneDevelop" v-bind:class="{active: isCurrentTabThis('profileUpdate')}" type="button">회원정보수정</button>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "MyPageNav",
  props: {
    user: {
      value: null
    },
    currentTab: {
      default: 'orderAndDelivery'
    },
    setCurrentTab: {
      type: Function
    }
  },

  methods: {
    isCurrentTabThis(tab) {
      return this.currentTab === tab;
    },

    noneDevelop() {
      alert('개발중입니다.');
    }
  }
}
</script>

<style scoped>
  nav {

  }

  nav div.nav-inner {

  }



  nav div.nav-inner .profile {
    background-color: #7ebb34;
    box-sizing: border-box;
    padding: 40px;
    text-align: left;
  }

  nav div.nav-inner .profile h3 {
    font-size: 20px;
    font-weight: 700;
    color: #fff;
    margin-bottom: 20px;
  }

  nav div.nav-inner .profile p {
    font-size: 16px;
    font-weight: 400;
    color: #fff;
    margin-top: 10px;
  }

  nav div.nav-inner .tab {
    padding: 15px 20px;
    border: 1px solid #eaeaea;
    text-align: left;
  }

  nav div.nav-inner .tab .tab-list {
    box-sizing: border-box;
    padding: 15px;
  }

  nav div.nav-inner .tab button {
    outline: none;
    width: 100%;
    height: 100%;
    display: inline-block;
    cursor: pointer;
    background-color: transparent;
    color: #888;
    font-weight: 400;
    font-size: 15px;
    text-align: left;
  }

  nav div.nav-inner .tab button.active {
    color: #333;
    font-weight: 700;
  }

</style>