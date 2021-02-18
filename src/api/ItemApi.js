import commonUtils from "@/utils/CommonUtils";
import axios from "axios";

export default {
  getItems(page, size, sort) {
    const url = `${commonUtils.getApiBaseUrl()}/items?page=${page}&size=${size}&sort=${sort}`;
    return axios.get(url);
  },
}