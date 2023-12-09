<template>
  <div class="modal-container">
    <div class="modal-inner">
      <div class="title-box">
        <h3>주소선택</h3>
      </div>
      <ul class="modal-wrapper">
        <li v-bind:class="{active: isSelected(address)}" @click="selectAddress(address)" v-for="(address, index) in addressList" v-bind:key="index">
          <p class="zip-code">[{{ address.zipCode }}] <span class="default-address" v-if="address.isDefault">[기본주소]</span></p>
          <p class="address">{{ convertAddressToStr(address) }}</p>

          <span class="check-helper">
            <CheckIcon v-bind:stroke="getStroke(address)" />
          </span>

        </li>
      </ul>
      <div class="btn-box">
        <button @click="selectExistingAddress(selectedAddress)">선택하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import CheckIcon from "@/components/icon/CheckIcon";
export default {
  name: "AddressListModal",
  components: {CheckIcon},
  props: {
    addressList: {
      value: []
    },
    selectExistingAddress: {
      type: Function
    }
  },

  data() {
    return {
      selectedAddress: null
    }
  },

  methods: {
    convertAddressToStr(address) {
      return address.main + ' ' + address.detail;
    },

    selectAddress(address) {
      this.selectedAddress = address;
    },

    isSelected(address) {
      if (this.selectedAddress) {
        return this.selectedAddress.id === address.id;
      } else {
        return false;
      }
    },

    getStroke(address) {
      return this.isSelected(address) ? '#0fafbe' : '#a0a0a0';
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
    position: relative;
    width: 400px;
    height: 500px;
    box-sizing: border-box;
    padding: 30px;
    background-color: #fff;
    border-radius: 10px;
    text-align: left;
  }

  div.modal-container div.modal-inner div.title-box {

  }

  div.modal-container div.modal-inner div.title-box h3 {

  }

  div.modal-container div.modal-inner div.title-box p {

  }

  div.modal-container div.modal-inner ul.modal-wrapper {
    margin-top: 20px;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li {
    cursor: pointer;
    position: relative;
    box-sizing: border-box;
    padding: 15px;
    border: 1px solid #eaeaea;
    margin-bottom: 10px;
    border-radius: 5px;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li.active {
    border: 1px solid #0fafbe;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li p {
    font-size: 14px;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li p.zip-code {
    font-size: 13px;
    color: #888;
    font-weight: 400;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li p.zip-code span.default-address {
    color: #0fafbe;
    font-weight: 700;

  }

  div.modal-container div.modal-inner ul.modal-wrapper li p.address {
    margin-top: 8px;
  }

  div.modal-container div.modal-inner ul.modal-wrapper li button {

  }

  div.modal-container div.modal-inner ul.modal-wrapper li span.check-helper {
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
  }

  div.modal-container div.modal-inner ul.modal-wrapper li span.check-helper svg {
    max-width: 25px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.btn-box {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
    box-sizing: border-box;
    padding: 30px;
  }

  div.modal-container div.modal-inner div.btn-box button {
    width: 100%;
    background-color: #7ebb34;
    color: #fff;
    font-weight: 700;
    height: 50px;
    border-radius: 3px;
    font-size: 15px;
  }


</style>