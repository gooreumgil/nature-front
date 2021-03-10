import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  productOrder(token, receiver, phoneNum1, phoneNum2, phoneNum3, zipCode, mainAddress, detailAddress, usedPoints, finalDiscountPrice, finalPrice, deliveryPrice, paymentMethod, orderItemSaveRequestDtos, registerDefaultAddress, registerNewAddress) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders`;
    return axios.post(url, {
      receiver,
      phoneNum1,
      phoneNum2,
      phoneNum3,
      zipCode,
      mainAddress,
      detailAddress,
      usedPoints,
      finalDiscountPrice,
      finalPrice,
      deliveryPrice,
      paymentMethod,
      orderItemSaveRequestDtos,
      registerDefaultAddress,
      registerNewAddress
    },{
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getOrder(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders/${id}`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  confirmOrder(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders/${id}`;
    return axios.patch(url, {}, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  cancelOrder(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders/${id}`;
    return axios.delete(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  }

}