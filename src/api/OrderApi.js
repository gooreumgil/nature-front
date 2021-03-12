import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  productOrder(token, orderSaveRequestDto) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/orders`;
    return axios.post(url, {
      receiver: orderSaveRequestDto.receiver,
      phoneNum1: orderSaveRequestDto.phoneNum1,
      phoneNum2: orderSaveRequestDto.phoneNum2,
      phoneNum3: orderSaveRequestDto.phoneNum3,
      usedPoints: orderSaveRequestDto.usedPoints,
      finalDiscountPrice: orderSaveRequestDto.finalDiscountPrice,
      finalPrice: orderSaveRequestDto.finalPrice,
      deliveryPrice: orderSaveRequestDto.deliveryPrice,
      paymentMethod: orderSaveRequestDto.paymentMethod,
      addressRequestDto: orderSaveRequestDto.addressRequestDto,
      orderItemSaveRequestDtos: orderSaveRequestDto.orderItemSaveRequestDtos
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