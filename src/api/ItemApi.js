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

  searchItems(page, size, sort, keyword) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/search?sort=${sort}`;
    return axios.get(url, {
      params: {
        page,
        size,
        keyword
      }
    })
  },

  itemLike(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}/item-likes`;
    return axios.post(url, {}, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },
  itemLikeDelete(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}/item-likes`;
    return axios.delete(url, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  addQna(token, id, qnaContent, qnaSecret) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}/qnas`;
    return axios.post(url, {
      content: qnaContent,
      isSecret: qnaSecret
    }, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
  },

  getQnaList(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}/qnas`;
    if (token) {
      return axios.get(url, {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      })
    } else {
      return axios.get(url);
    }
  },

  getReviews(token, id) {
    const url = `${commonUtils.getApiBaseUrl()}/v1/items/${id}/reviews`;
    if (token) {
      return axios.get(url, {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      });
    } else {
      return axios.get(url);
    }
  },
}