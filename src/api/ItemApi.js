import commonUtils from "@/utils/commonUtils";
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


  getItem(id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}`;
    return axios.get(url);
  },


  getItemsByIds(ids) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items`;
    return axios.get(url, {
      params: {
        ids: ids.join(",")
      }
    })
  },
}