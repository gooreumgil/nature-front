import commonUtils from "@/utils/commonUtils";
import axios from 'axios';

export default {
  getAll() {
    const url = `${commonUtils.getApiBaseUrl()}/v1/categories`;
    return axios.get(url);
  }
}