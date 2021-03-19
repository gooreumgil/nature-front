<template>
  <section class="main-container">
    <div class="inner-container">
      <div class="title-box">
        <h1>비밀번호찾기</h1>
      </div>
      <div class="form-container">
        <form @submit.prevent="passwordChangeLinkEmailSend">
          <input type="email" v-model="email" placeholder="가입시 사용했던 이메일을 입력하세요.">
          <button type="submit">입력</button>
        </form>
      </div>

    </div>

  </section>
</template>

<script>
import userApi from "@/api/UserApi";

export default {
  name: "PasswordFind",
  data() {
    return {
      email: null
    }
  },
  methods: {
    async passwordChangeLinkEmailSend() {
      const email = this.email;
      try {
        await userApi.sendPasswordChangeLink(email);
        alert('해당 이메일로 패스워드 변경 링크가 전송되었습니다.');
      } catch (err) {
        alert(err.response.data.message);
        console.log(err);
      }
    }
  }
}
</script>

<style scoped>
  section.main-container {
    padding-top: 80px;

  }

  section.main-container div.inner-container {
    max-width: 340px;
    width: 100%;
    margin: 0 auto;
    text-align: left;
  }

  section.main-container div.inner-container div.title-box {

  }

  section.main-container div.inner-container div.title-box h1 {
    font-size: 24px;
  }

  section.main-container div.inner-container div.form-container {
    margin-top: 30px;
  }

  section.main-container div.inner-container div.form-container form {

  }

  section.main-container div.inner-container div.form-container form input {
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