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

  getUserOrders(token, page) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/orders?page=${page}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getTotalByDeliveryStatus(token, deliveryStatus) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/count/orders/${deliveryStatus}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getReviewsTotal(token) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/count/reviews`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  checkItemLike(token, itemId) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/check/item-likes`;
    return axios.get(url, {
      params: {
        itemId
      },
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getLikeItems(token, page) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/item-likes?page=${page}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getQnaList(token, page) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/qnas?page=${page}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getCanReviewItems(token, page) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/order-items?page=${page}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getReviews(token, page) {

    const url = `${commonUtils.getApiBaseUrl()}/v1/users/reviews?page=${page}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })

  },

  sendPasswordChangeLink(email) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/${email}/password/change-link-send`;
    return axios.post(url);
  },

  passwordChangeByEmail(email, password, passwordConfirm) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/${email}/password/change-by-email`;
    return axios.patch(url, {password, passwordConfirm});
  }
}