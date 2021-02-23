import commonUtils from "@/utils/CommonUtils";
import axios from "axios";

export default {
  getItems(page, size, sort, category) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items?sort=${sort}`;
    return axios.get(url, {
      params: {
        page,
        size,
        category
      }
    });
  },

  getItem(id, type) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}?type=${type}`;
    return axios.get(url);
  }
}