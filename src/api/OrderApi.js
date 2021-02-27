import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  productOrder(token, receiver, phoneNum1, phoneNum2, phoneNum3, zipCode, mainAddress, detailAddress, usedPoints, finalDiscountPrice, finalPrice, deliveryPrice, paymentMethod, orderItemSaveRequestDtos) {
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
      orderItemSaveRequestDtos
    },{
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getOrders(token) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders`;
    return axios.get(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  }
}