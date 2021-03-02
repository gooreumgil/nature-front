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

  getUserOrders(token) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/users/orders`;
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
  }
}