<template>
  <div class="order-detail">
    <div class="title-box">
      <h3>주문상세내역</h3>
      <p>해당 주문의 상세 내역입니다.</p>
    </div>

    <div class="item-container">
      <ul class="item-wrapper clearfix">
        <li class="item-list head">
          <div class="list-inner item-info">
            <p>상품정보</p>
          </div>
          <div class="list-inner delivery-price">
            <p>배송비</p>
          </div>
          <div class="list-inner status">
            <p>주문/배송상태</p>
          </div>
        </li>
        <li class="item-list content" v-for="(item, index) in order.orderItemResponseDtos" v-bind:key="index">
          <div class="list-inner item-info">
            <div class="img-box">
              <img v-bind:src="item.mainImgPath" alt="">
            </div>
            <div class="info-box">
              <p>{{ item.itemNameKor }}</p>
              <p>{{ item.itemPrice | price }}</p>
              <p>{{ item.itemQuantity }} 개</p>
            </div>
          </div>
          <div class="list-inner delivery-price">
          </div>
          <div class="list-inner status">
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderDetail",
  props: {
    order: {
      value: null
    }
  }
}
</script>

<style scoped>
  div.order-detail {
    box-sizing: border-box;
    padding-top: 30px;
    padding-left: 10px;
    padding-right: 10px;
  }

  div.order-detail div.title-box {
    box-sizing: border-box;
    padding: 10px 0;
  }

  div.order-detail div.title-box h3 {
    font-size: 17px;
  }

  div.order-detail div.title-box p {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
  }

  div.order-detail div.item-container {

  }

  div.order-detail div.item-container ul.item-wrapper {

  }

  div.order-detail div.item-container ul.item-wrapper li.item-list {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.head {
    height: 50px;
    background-color: #f9f9f9;
    border-top: 1px solid #eaeaea;
    border-bottom: 1px solid #eaeaea;
    margin-top: 20px;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.head div.list-inner {

  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.head div.list-inner p {
    font-size: 14px;
    font-weight: 400;
    color: #333;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list div.list-inner.item-info {
    width: 60%;
    text-align: center;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list div.list-inner.delivery-price {
    width: 20%;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list div.list-inner.status {
    width: 20%;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content {
    padding: 20px;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content div.list-inner {

  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content div.list-inner.item-info {
    display: flex;
    align-items: center;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content div.list-inner.item-info div.img-box {
    width: 100px;
    height: 118px;
    background-color: #f6f6f6;
    display: flex;
    align-items: center;
    margin-right: 20px;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content div.list-inner.item-info div.img-box img {
    width: 100%;
  }

  div.order-detail div.item-container ul.item-wrapper li.item-list.content div.list-inner.item-info div.info-box {
    text-align: left;
  }


</style>