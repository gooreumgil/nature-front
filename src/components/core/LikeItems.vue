<template>
  <ul class="like-item-wrapper clearfix">
    <div class="title-box">
      <h3>찜리스트</h3>
      <p>고객님의 위시리스트 상품입니다.</p>
      <div class="btn-box" v-if="!isLikeItemsEmpty()">
        <button @click="$emit('allSelectLikeItemsToggle')" type="button">전체선택</button>
        <button @click="selectedLikeItemDelete()" type="button">선택삭제</button>
        <button @click="allLikeItemDelete()" type="button">모두삭제</button>
      </div>
    </div>

    <div class="divider">
      <div class="divider-inner"></div>
    </div>

    <div class="like-item-empty" v-if="isLikeItemsEmpty()">
      <div class="like-item-empty-inner">
        <span class="img-helper">
          <ExclamationIcon v-bind:stroke="'#a0a0a0'" />
        </span>
        <p>찜한 상품이 없습니다.</p>
      </div>
    </div>
    <li class="like-item-list" v-for="(likeItem, index) in likeItems" v-bind:key="index">
      <div class="check-box">
        <input type="checkbox" v-model="likeItem.selected">
      </div>
      <div class="inner-box">
        <div @click="goItemDetail(likeItem.itemResponseDto.id)" class="img-box">
          <img v-bind:src="getItemMainImgPath(likeItem)" alt="">
          <div class="hover-box"></div>
        </div>
        <div class="description borderBottom">
          <div class="item-name">
            <h4>{{ getItemName(likeItem) }}</h4>
          </div>

          <span class="price" v-bind:class="{disable: !isDiscountPriceNull(likeItem)}">
            {{ getItemPrice(likeItem) | price }} <span class="won" v-if="isDiscountPriceNull(likeItem)">원</span>
          </span>
          <span class="discountPrice" v-if="!isDiscountPriceNull(likeItem)">
            {{ (getResultPrice(likeItem)) | price }} <span class="won">원</span>
          </span>
        </div>
      </div>
    </li>
  </ul>
</template>

<script>
import ExclamationIcon from "@/components/icon/ExclamationIcon";
import itemLikeApi from "@/api/ItemLikeApi";
export default {
  name: "LikeItems",
  components: {ExclamationIcon},
  props: {
    likeItems: {
      type: Array,
      default: () => {
        return [];
      }
    }
  },

  methods: {
    goItemDetail(id) {
      this.$router.push('/items/' + id);
    },

    isLikeItemsEmpty() {
      return this.likeItems.length === 0;
    },

    async allLikeItemDelete() {

      const check = confirm('모든 찜한 상품 목록이 삭제됩니다. 계속 하시겠습니까?');
      if (!check) {
        return;
      }

      const token = this.$cookies.get('token');
      const ids = [];
      this.likeItems.forEach(likeItem => {
        ids.push(likeItem.id);
      })

      try {
        await itemLikeApi.delete(token, ids);
        this.$emit('setLikeItems');
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    async selectedLikeItemDelete() {

      const token = this.$cookies.get('token');

      const ids = []
      const selectedLikeItems = this.likeItems.filter(likeItem => likeItem.selected);
      if (selectedLikeItems.length === 0) {
        alert('선택된 상품이 없습니다.');
        return;
      }


      selectedLikeItems.forEach(selectedLikeItem => ids.push(selectedLikeItem.id));
      try {
        await itemLikeApi.delete(token, ids);
        this.$emit('setLikeItems');
      } catch (err) {
        alert('문제가 발생하였습니다.');
        console.log(err);
      }

    },

    getItemName(likeItem) {
      return likeItem.itemResponseDto.nameKor;
    },

    getItemPrice(likeItem) {
      return likeItem.itemResponseDto.price;
    },

    isDiscountPriceNull(likeItem) {
      return likeItem.itemResponseDto.discountPrice === null || likeItem.itemResponseDto.discountPrice === 0;
    },

    getResultPrice(likeItem) {
      const itemResponseDto = likeItem.itemResponseDto;
      return itemResponseDto.price - itemResponseDto.discountPrice;
    },

    getItemMainImgPath(likeItem) {
      return likeItem.itemResponseDto.mainImgPath;
    }
  }
}
</script>

<style scoped>
  ul {
    box-sizing: border-box;
    padding-top: 10px;
    /*padding-left: 10px;*/
    /*padding-right: 10px;*/
  }

  ul div.title-box {
    position: relative;
    box-sizing: border-box;
    padding: 10px;
    margin-bottom: 10px;
  }

  ul div.title-box h3 {
    font-size: 17px;
  }
  ul div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
  }

  ul div.title-box div.btn-box {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
  }

  ul div.title-box div.btn-box button {
    cursor: pointer;
    outline: none;
    margin-right: 10px;
    box-sizing: border-box;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 3px;
    padding: 5px 10px;
    font-size: 12px;
    color: #0fafbe;

  }


  ul div.divider {
    box-sizing: border-box;
    padding-left: 10px;
    margin-bottom: 10px;
  }

  ul div.divider div.divider-inner {
    width: 100%;
    height: 1px;
    background-color: #eaeaea;
  }

  ul div.like-item-empty {
    width: 100%;

  }

  ul div.like-item-empty div.like-item-empty-inner {
    text-align: center;
    box-sizing: border-box;
    padding: 150px 0;
    border-bottom: 1px solid #eaeaea;
  }

  ul div.like-item-empty div.like-item-empty-inner span {
    margin: 0 auto;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  ul div.like-item-empty div.like-item-empty-inner span svg {
    max-width: 40px;
    width: 100%;
  }

  ul div.like-item-empty div.like-item-empty-inner p {

  margin-top: 30px;
  font-size: 14px;
  color: #555;
  }


  ul li {
    position: relative;
    float: left;
    width: 25%;
    box-sizing: border-box;
    padding: 10px;
    padding-bottom: 30px;
  }

  ul li div.check-box {
    position: absolute;
    right: 20px;
    top: 20px;
    z-index: 1;
  }

  ul li div.inner-box {

  }

  ul li div.inner-box div.img-box {
    cursor: pointer;
    position: relative;
    background-color: #f6f6f6;
    height: 280px;
    display: flex;
    animation: ce;
    justify-content: center;
    flex-direction: column;
  }

  ul li div.inner-box div.img-box img {
    width: 100%;
  }

  ul li div.description {
    padding: 0 15px 5px 5px;
    position: relative;
  }

  ul li div.description.borderBottom {
    padding-bottom: 15px;
    border-bottom: 2px solid #efefef;
    transition: all .1s ease-in-out;
  }

  ul li:hover .description.borderBottom {
    border-bottom: 2px solid #8bc545;
  }

  ul li div.inner-box div.img-box:hover > div.hover-box {
    opacity: 1;
    vertical-align: middle;
    pointer-events: none;
  }

  ul li div.inner-box div.img-box div.hover-box {
    opacity: 0;
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    text-align: center;
    background-color: rgba(255, 255, 255, .2);
    transition: all .2s ease-in-out;
  }

  ul li div.description div.item-name {
    margin-bottom: 15px;
  }
  ul li div.description div.item-name h4 {
    color: #555;
    font-weight: 300;
    font-size: 18px;
    width: 100%;

    margin-top: 20px;
    margin-left: 0;
    box-sizing: border-box;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  ul li div.description span.price {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
    margin-right: 15px;
  }

  ul li div.description span.won {
    font-size: 18px;
    font-weight: 300;
    color: #8f8f8f;

  }

  ul li div.description span.price.disable {
    font-size: 16px;
    color: #9b9b9b;
    text-decoration: line-through;
    font-weight: 100;
  }

  ul li div.description span.discountPrice {
    font-size: 24px;
    color: #7ebb34;
    font-weight: 400;
  }

</style>