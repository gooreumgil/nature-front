import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  verifyNumSend(email) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/email-verify`;
    return axios.post(url, {}, {
      params: {
        email
      }
    })
  },

  verifyNumConfirm(email, verifyNum) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/email-verify`;
    return axios.patch(url, {email, verifyNum})
  }
}