import commonUtils from "@/utils/CommonUtils";
import axios from 'axios';

export default {
  login(email, password) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/session`;
    return axios.post(url, {email, password});
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
