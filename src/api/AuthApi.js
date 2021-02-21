import commonUtils from "@/utils/CommonUtils";
import axios from 'axios';

export default {
  login(email, password) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/session`;
    return axios.post(url, {email, password});
  },

  signUp(nickname, email, password, passwordConfirm, phoneNumber, birthDay) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users`;
    return axios.post(url, {name: nickname, email, password, passwordConfirm, phoneNumber, birthDay});
  },

  authenticate(token) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/session`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });
  },
}
