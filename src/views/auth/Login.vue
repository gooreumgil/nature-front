<template>
  <section>
    <form @submit.prevent="login">
      <input type="email" v-model="email" placeholder="이메일">
      <input type="password" v-model="password" placeholder="비밀번호">
      <button type="submit">로그인</button>
    </form>
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

</style>