<template>
  <section class="main-container">
    <div class="title-box">
      <h2>비밀번호변경</h2>
    </div>
    <div class="form-container">
      <form @submit.prevent="passwordChange">
        <input type="password" v-model.trim="password" placeholder="새로운 비밀번호">
        <input type="password" v-model.trim="passwordConfirm" placeholder="비밀번호 재입력">
        <button type="submit">변경하기</button>
      </form>
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
      } catch (err) {
        alert(err.response.data.message);
        console.log(err);
      }
    }


  }

}
</script>

<style scoped>

</style>