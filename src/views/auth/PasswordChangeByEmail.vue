<template>
  <section class="main-container">
    <div class="inner-container">
      <div class="title-box">
        <h1>비밀번호변경</h1>
      </div>
      <div class="form-container">
        <form @submit.prevent="passwordChange">
          <input type="password" v-model.trim="password" placeholder="새로운 비밀번호">
          <input type="password" v-model.trim="passwordConfirm" placeholder="비밀번호 재입력">
          <button type="submit">변경하기</button>
        </form>
      </div>
    </div>

  </section>
</template>

<script>
import userApi from "@/api/UserApi";

export default {
  name: "PasswordChangeByEmail",
  data() {
    return {
      password: null,
      passwordConfirm: null
    }
  },
  created() {

  },

  methods: {

    async passwordChange() {

      const email = this.$route.params.email;
      const password = this.password;
      const passwordConfirm = this.passwordConfirm;
      try {
        await userApi.passwordChangeByEmail(email, password, passwordConfirm);
        alert('비밀번호 변경이 완료 되었습니다.');
        window.close();
      }
      catch (err) {
        const data = err.response.data;
        const errorList = data.errorList;
        if (data && errorList.length > 0) {
          errorList.forEach(e => {
            alert(e.message);
          })
        } else if (data && errorList.length === 0) {
          alert(data.message);
        } else {
          alert('문제가 발생하였습니다.');
        }
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
    cursor: pointer;
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