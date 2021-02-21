<template>
  <section class="main-container">
    <div class="inner-container">
      <div class="title-box">
        <img src="@/assets/image/NATURE REPUBLIC_white_back.png" alt="">
        <h1>회원가입 페이지입니다.</h1>
      </div>
      <div class="form-container">
        <form @submit.prevent="signUp">
          <input type="text" v-model="nickname" placeholder="닉네임">
          <input type="email" v-model="email" placeholder="이메일">
          <input type="password" v-model="password" placeholder="패스워드">
          <input type="password" v-model="passwordConfirm" placeholder="패스워드 재입력">
          <input type="text" v-model="phoneNumber" placeholder="휴대폰번호 - 없이 입력">
          <input type="text" v-model="birthDay" placeholder="생년월일입력 - 8자리 숫자로 입력 (ex.19920101)">
          <button type="submit">회원가입</button>
        </form>
      </div>
    </div>

  </section>
</template>

<script>
import authApi from "@/api/AuthApi";

export default {
  name: "SignUp",
  data() {
    return {
      nickname: '덕배',
      email: 'test@email.com',
      password: 'aormfl123',
      passwordConfirm: 'aormfl123',
      phoneNumber: '0101231234',
      birthDay: '19890407'
    }
  },
  methods: {
    signUp() {
      const nickname = this.nickname;
      const email = this.email;
      const password = this.password;
      const passwordConfirm = this.passwordConfirm;
      const phoneNumber = this.phoneNumber;
      const birthDay = this.birthDay;
      authApi.signUp(nickname, email, password, passwordConfirm, phoneNumber, birthDay)
          .then((response) => {
            console.log(response);
          })
    }
  }
}
</script>

<style scoped>
  section.main-container {
    padding-top: 80px;
  }

  section.main-container div.inner-container {
    max-width: 400px;
    width: 100%;
    margin: 0 auto;
  }

  section.main-container div.inner-container div.title-box {
    text-align: left;
  }

  section.main-container div.inner-container div.title-box h1 {
    font-size: 15px;
    margin-top: 10px;
    color: #888;
    font-weight: 400;
  }

  section.main-container div.inner-container div.form-container {

  }
  section.main-container div.inner-container div.form-container form {
    margin-top: 30px;
  }

  section.main-container div.inner-container div.form-container form input {
    width: 100%;
    box-sizing: border-box;
    padding: 15px;
    margin-bottom: 10px;
    border: 1px solid #eaeaea;
    border-radius: 3px;
    background-color: #f9f9f9;
    outline-color: #ddd;
    outline-width: thin;
  }

  section.main-container div.inner-container div.form-container form button {
    width: 100%;
    box-sizing: border-box;
    background-color: #7ebb34;
    padding: 15px;
    border-radius: 5px;
    color: #fff;
    font-size: 14px;
    margin-top: 10px;
  }
</style>