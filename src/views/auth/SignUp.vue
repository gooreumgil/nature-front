<template>
  <section class="main-container">
    <div class="inner-container">
      <div class="title-box">
        <img src="@/assets/image/NATURE REPUBLIC_white_back.png" alt="">
        <h1>회원가입 페이지입니다.</h1>
      </div>
      <div class="form-container">
        <form @submit.prevent="signUp">
          <input type="text" v-model.trim="nickname" placeholder="닉네임">
          <div class="email-box">
            <input type="email" v-model.trim="email" placeholder="이메일">
            <button @click="verifyNumSend()" type="button">인증번호받기</button>
          </div>
          <div class="email-verify" v-if="verifyNumView">
            <input type="text" v-model.trim="verifyNum" placeholder="인증번호를 입력하세요.">
            <button @click="verifyNumConfirm" type="button">확인</button>
          </div>
          <input type="password" v-model.trim="password" placeholder="패스워드">
          <input type="password" v-model.trim="passwordConfirm" placeholder="패스워드 재입력">
          <input type="text" v-model.trim="phoneNumber" placeholder="휴대폰번호 - 없이 입력">
          <input type="text" v-model.trim="birthDay" placeholder="생년월일입력 - 8자리 숫자로 입력 (ex.19920101)">
          <button class="signUp" type="submit">회원가입</button>
        </form>
      </div>
    </div>

  </section>
</template>

<script>
import authApi from "@/api/AuthApi";
import emailVerifyApi from "@/api/EmailVerifyApi";

export default {
  name: "SignUp",
  data() {
    return {
      nickname: null,
      email: null,
      password: null,
      passwordConfirm: null,
      phoneNumber: null,
      birthDay: null,
      verifyNumView: false,
      verifyNum: null,
      emailVerify: false
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
      const emailVerify = this.emailVerify;

      // if (!nickname) {
      //   alert('닉네임을 입력해주세요.');
      //   return;
      // }
      //
      // if (!email) {
      //   alert('이메일을 입력해주세요.');
      //   return;
      // }
      //
      // if (!password) {
      //   alert('비밀번호를 입력해주세요.');
      //   return;
      // }
      //
      // if (!phoneNumber) {
      //   alert('연락처를 입력해주세요.');
      //   return;
      // }
      //
      // if (!birthDay) {
      //   alert('생년월일을 입력해주세요.');
      //   return;
      // }
      //
      // if (password !== passwordConfirm) {
      //   alert('비밀번호가 서로 일치하지 않습니다.');
      //   return;
      // }
      //
      // if (!emailVerify) {
      //   alert('이메일 인증이 완료되지 않았습니다.');
      //   return;
      // }

      authApi.signUp(nickname, email, password, passwordConfirm, phoneNumber, birthDay)
          .then((response) => {
            alert('회원가입이 완료되었습니다. 로그인 화면으로 이동합니다.');
            this.$router.replace('/login');
          })
          .catch((err) => {
            alert(err.response.data.message);
          })
    },

    async verifyNumSend() {
      const email = this.email;

      try {
        await emailVerifyApi.verifyNumSend(email)
        alert('인증번호가 이메일로 발송되었습니다. 인증번호는 10분 동안만 유효합니다');
        this.verifyNumView = true;
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }
    },

    async verifyNumConfirm() {
      const email = this.email;
      const verifyNum = this.verifyNum;

      try {
        await emailVerifyApi.verifyNumConfirm(email, verifyNum);
        this.verifyNumView = false;
        this.emailVerify = true;
        this.verifyNum = null;
        alert('이메일 인증이 완료 되었습니다.');
      } catch (err) {
        alert('문제가 발생하였습니다.');
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

  section.main-container div.inner-container div.form-container form div.email-box {
    position: relative;
  }

  section.main-container div.inner-container div.form-container form div.email-box button {
    position: absolute;
    cursor: pointer;
    background-color: #555;
    color: #fff;
    font-weight: 700;
    top: 0;
    right: 0;
    height: 51px;
    font-size: 13px;
    width: 90px;
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px;
  }

  section.main-container div.inner-container div.form-container form div.email-verify {
    position: relative;
  }

  section.main-container div.inner-container div.form-container form div.email-verify button {
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
    height: 51px;
    width: 60px;
    color: #fff;
    font-weight: 700;
    background-color: #0fafbe;
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px;
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

  section.main-container div.inner-container div.form-container form button.signUp {
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