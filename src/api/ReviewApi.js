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
  },

  saveLike(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/reviews/${id}/review-likes`;
    return axios.post(url, {}, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  cancelLike(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/reviews/${id}/review-likes`;
    return axios.delete(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  }
}