<template>
  <section class="main-container">
    <div class="title-box">
      <h2>비밀번호찾기</h2>
    </div>
    <form @submit.prevent="passwordChangeLinkEmailSend">
      <input type="email" v-model="email" placeholder="가입시 사용했던 이메일을 입력하세요.">
      <button type="submit">입력</button>
    </form>
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

</style>