<template>
  <div v-if="getModalView" class="modal-container">
    <div class="modal-inner">
      <span @click="sourceCodeLinkModalClose" class="img-helper">
        <CloseIcon v-bind:stroke="'#fff'"/>
      </span>
      <div class="title-box">
        <h2>Bitbucket Link</h2>
      </div>
      <div class="content-box">
        <p>백엔드 소스코드</p>
        <a href="https://bitbucket.org/gooreumgil47/nature/src/master/" target="_blank">https://bitbucket.org/gooreumgil47/nature/src/master/</a><br>

        <p>프론트엔드 소스코드</p>
        <a href="https://bitbucket.org/gooreumgil47/nature-front/src/master/" target="_blank">https://bitbucket.org/gooreumgil47/nature-front/src/master/</a>
      </div>
    </div>
  </div>
</template>

<script>
import CloseIcon from "@/components/icon/CloseIcon";
export default {
  name: "SourceCodeLinkModal",
  components: {CloseIcon},

  computed: {
    getModalView() {
      return this.$store.state.sourceCodeLinkModalView;
    }
  },

  methods: {
    sourceCodeLinkModalClose() {
      this.$store.commit('SET_SOURCE_CODE_LINK_MODAL_VIEW', false);
    }
  }

}
</script>

<style scoped>
  div.modal-container {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(0, 0, 0, .3);
    z-index: 2;
  }

  div.modal-container div.modal-inner {
    position: relative;
    text-align: left;
    background-color: #fff;
    border-radius: 10px;
    box-sizing: border-box;
    padding: 40px;
    box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, .15);

  }

  div.modal-container div.modal-inner span.img-helper {
    cursor: pointer;
    position: absolute;
    width: 34px;
    height: 34px;
    border-radius: 50%;
    background-color: rgba(0, 0, 0, .3);
    right: -17px;
    top: -17px;
    display: flex;
    align-items: center;
    justify-content: center;

  }

  div.modal-container div.modal-inner span.img-helper svg {
    max-width: 30px;
    width: 100%;
  }

  div.modal-container div.modal-inner div.title-box {

  }
  div.modal-container div.modal-inner div.title-box h2 {
    font-size: 22px;
    margin-bottom: 20px;
  }

  div.modal-container div.modal-inner div.content-box {

  }

  div.modal-container div.modal-inner div.content-box p {
  }

  div.modal-container div.modal-inner div.content-box a {
    margin-top: 5px;
    text-decoration: underline;
    color: #0f81be;
    display: inline-block;
    margin-bottom: 20px;
  }

</style>