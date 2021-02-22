import commonUtils from "@/utils/CommonUtils";
import axios from "axios";

export default {
  getItems(page, size, sort, category) {
    console.log(null);
    const url = `${commonUtils.getApiBaseUrl()}/v1/items?${sort}`;
    return axios.get(url, {
      params: {
        page,
        size,
        category
      }
    });
  },
}