<template>
  <div class="modal-container">
    <div class="modal-inner">
      <div class="title-box">
        <h3>주소선택</h3>
        <p>주소를 선택해주세요.</p>
      </div>
      <ul class="modal-wrapper">
        <li v-for="(address, index) in addressList" v-bind:key="index">
          {{ address }}
          <button @click="selectExistingAddress(address)" type="button">선택</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddressListModal",
  props: {
    addressList: {
      value: []
    },
    selectExistingAddress: {
      type: Function
    }
  }
}
</script>

<style scoped>
  div.modal-container {
    top: 0;
    left: 0;
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .15);
    display: flex;
    align-items: center;
    justify-content: center;
  }

  div.modal-container div.modal-inner {
    width: 400px;
    height: 500px;
    box-sizing: border-box;
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    text-align: left;
  }
</style>