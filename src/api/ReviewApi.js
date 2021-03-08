import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  writeReview(token, itemId, orderItemId, form) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/reviews`;
    return axios.post(url, form, {
      params: {
        itemId,
        orderItemId
      },
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': 'Bearer ' + token
      }
    })
  }
}