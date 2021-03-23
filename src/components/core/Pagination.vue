<template>
  <div class="page-container">
    <ul class="page-wrapper">
      <div  class="previous">
        <span @click="$emit('previousPage', page.number - 1)" v-if="!isExistPrevious()">
          <ArrowLeft />
        </span>
        <span v-else>
          <ArrowLeft v-bind:stroke="'#bbb'" />
        </span>
      </div>

      <li v-bind:class="{active: page.number + 1 === getStartIndex() + index}" @click="$emit('goPage', getStartIndex() + index)" class="page-list" v-for="(content, index) in getIndexSize()" v-bind:key="index">
        {{ getStartIndex() + index }}
      </li>

      <div  class="next">
        <span v-if="isNotLast()" @click="$emit('nextPage', page.number + 1)">
          <ArrowRight />
        </span>
        <span v-else>
          <ArrowRight v-bind:stroke="'#bbb'" />
        </span>
      </div>
    </ul>
  </div>
</template>

<script>
import ArrowRight from "@/components/icon/ArrowRight";
import ArrowLeft from "@/components/icon/ArrowLeft";
export default {
  name: "Pagination",
  components: {ArrowLeft, ArrowRight},
  data() {
    return {
      pageColumn: 5,
      startIndex: Number
    }
  },
  props: {
    page: {
      value: null,
    }
  },

  methods: {
    isExistPrevious() {
      return this.page.first;
    },

    isNotLast() {
      const totalPages = this.page.totalPages;
      return this.page.number + 1 < totalPages;
    },


    getIndexSize() {

      const {size, totalPages} = this.page;
      let number;

      if (totalPages < this.pageColumn) {
        number = totalPages;
      } else {
        const last = Math.floor(totalPages % this.pageColumn);
        if (last === 0) {
          number = size;
        } else {
          number = last;
        }
      }

      return this.getStartIndex() + 2 < totalPages ? this.pageColumn : number;

    },

    getStartIndex() {
      const number = Math.floor(this.page.number / this.pageColumn) * this.pageColumn + 1;
      // this.startIndex = number;
      return number;
    }
  },



}
</script>

<style scoped>
  div.page-container {

  }

  div.page-container ul {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  div.page-container ul div.previous,
  div.page-container ul div.next {
    cursor: pointer;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 1px solid #ddd;
    margin: 0 5px;
  }

  div.page-container ul li {
    cursor: pointer;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 1px solid #ddd;
    text-align: center;
    font-size: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 5px;
  }

  div.page-container ul li.active {
    color: #fff;
    border: none;
    font-weight: 500;
    background-color: #7ebb34;
  }

</style>