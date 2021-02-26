import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  getUser(token) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },
}