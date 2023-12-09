import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {

  delete(token, ids) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/item-like`;
    return axios.delete(url, {
      params: {
        ids: ids.join(",")
      },
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  }

}