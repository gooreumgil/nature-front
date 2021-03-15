<template>
  <section>

    <div class="top-box">
<!--      <img src="@/assets/image/NATURE REPUBLIC_white_back.png" alt="">-->
      <h1>로그인해 주세요.</h1>
    </div>

    <div class="form-container">
      <form @submit.prevent="login">
        <input type="email" v-model="email" placeholder="이메일">
        <input type="password" v-model="password" placeholder="비밀번호">
        <button type="submit">로그인</button>
        <div class="other">
          <router-link to="sign-up">회원가입</router-link> <span></span> <router-link to="/password-find">비밀번호찾기</router-link>
        </div>
      </form>
    </div>

  </section>
</template>

<script>
import authApi from "@/api/AuthApi";

export default {
  name: "Login",
  data() {
    return {
      email: null,
      password: null
    }
  },

  methods: {
    login() {
      const email = this.email;
      const password = this.password;
      authApi.login(email, password)
          .then((response) => {
            this.$cookies.set('token', response.data, '10d');
            this.$router.replace('/');
          })
    }
  }

}

</script>

<style scoped>
   section {
     padding-top: 80px;
   }

   section div.top-box {
     text-align: left;
     max-width: 340px;
     width: 100%;
     margin: 0 auto;
   }

   section div.top-box h1 {
    font-size: 24px;
   }

   section div.form-container {
     margin-top: 30px;
   }

   section div.form-container form {
     max-width: 340px;
     width: 100%;
     margin: 0 auto;
   }

   section div.form-container form input {
     width: 100%;
     box-sizing: border-box;
     padding: 13px;
     margin-bottom: 10px;
     background-color: #f9f9f9;
     border-radius: 5px;
     border: 1px solid #eaeaea;
     outline-color: #ddd;
     outline-width: thin;
   }

   section div.form-container form button {
     width: 100%;
     box-sizing: border-box;
     background-color: #7ebb34;
     padding: 15px;
     border-radius: 5px;
     color: #fff;
     font-size: 14px;
     margin-top: 10px;

   }

   section div.form-container form div.other {
     text-align: left;
     margin-top: 10px;
   }

   section div.form-container form div.other a {
     color: #888;
     font-size: 13px;
     font-weight: 400;
   }

   section div.form-container form div.other span {
     display: inline-block;
     height: 10px;
     width: 1px;
     background-color: #a0a0a0;
     margin-left: 3px;
     margin-right: 3px;
   }

</style>