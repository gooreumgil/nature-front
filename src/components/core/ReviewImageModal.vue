<template>
  <div class="modal-container">
    <div class="modal-inner">
      <img v-bind:src="imgSrc" alt="">
      <span @click="reviewImgModalClose()" class="close-ic-helper">
        <CloseIcon v-bind:stroke="'#fff'" />
      </span>
    </div>
  </div>
</template>

<script>
import CloseIcon from "@/components/icon/CloseIcon";
export default {
  name: "ReviewImageModal",
  components: {CloseIcon},
  props: {
    imgSrc: {
      value: null
    },
    reviewImgModalClose: {
      type: Function
    }
  },


}
</script>

<style scoped>
  div.modal-container {
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .35);
    top: 0;
    left: 0;
    z-index: 2;
  }

  div.modal-container div.modal-inner {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 500px;
  }

  div.modal-container div.modal-inner img {
    width: 100%;
    border-radius: 10px;
  }

  div.modal-container div.modal-inner span.close-ic-helper {
    cursor: pointer;
    position: absolute;
    top: -17px;
    right: -17px;
    width: 34px;
    height: 34px;
    background-color: rgba(0, 0, 0, .3);
    border-radius: 50%;
  }

  div.modal-container div.modal-inner span.close-ic-helper svg {

  }


</style>