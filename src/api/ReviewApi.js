import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  writeReview(token, itemId, rating, content, files) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/reviews`;
    return axios.post(url, {itemId, rating, content, files}, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  }
}