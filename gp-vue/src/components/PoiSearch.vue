<template>
  <div class="poi-search" :style="{ width: `calc(${width} + ${height} + 10px)` }">
    <div class="ps-query" :style="{ height: height }">
      <div class="ps-query-input ps-component" :style="{ width: width }">
        <div class="ps-query-logo" :style="{ width: height }">
          <div class="ps-icon-div"><i class="el-icon-s-promotion"></i></div>
        </div>
        <div class="ps-query-search" :style="{ width: height }" @click="search">
          <div class="ps-icon-div">
            <i style="cursor: pointer" :class="[loading ? 'el-icon-loading' : 'el-icon-map-location']"></i>
          </div>
        </div>
        <div class="ps-query-text" :style="{ 'margin-left': height, 'margin-right': height }">
          <input type="text" v-model="key" :placeholder="placeholder" />
        </div>
      </div>
      <div class="ps-query-locate ps-component" :style="{ width: height }">
        <div class="ps-icon-div">
          <i style="cursor: pointer" class="el-icon-aim" @click="onLocate"></i>
        </div>
      </div>
    </div>
    <div class="ps-result">
      <transition name="search-result">
        <div v-if="searchResult !== null && searchResult.length > 0"
          class="ps-result-list ps-component" :style="{ width: width }">
          <div :style="{ 'max-height': `calc(${maxHeight} - ${height} - 10px)` }">
            <PoiComponent
              v-for="(poi, index) in searchResult"
              :key="index"
              :poi="poi"
              @choose="onSelect(poi)"
              :style="{ 'border-top': index === 0 ? 'none' : '1px #aaa solid' }"
            ></PoiComponent>
            <div class="load-more" v-if="showButtonLoadMore">
              <span v-if="!loading" @click="searchMore">加载更多</span>
              <i v-else class="el-icon-loading"></i>
            </div>
          </div>
        </div>
      </transition>
      <transition name="search-result">
        <div v-if="searchResult !== null && searchResult.length > 0"
          class="ps-result-close ps-component" :style="{ width: height, height: height }">
          <div class="ps-icon-div">
            <i style="cursor: pointer" class="el-icon-close" @click="onClose"></i>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script lang="ts" setup>
import API from "../http/api";
import notice from "../util/notice";
import PoiComponent from "./Poi.vue";
import { shallowRef } from "vue";
import { Poi } from "../entity/Poi";
import { SimpleResponse } from "../entity/SimpleResponse";

const props = withDefaults(
  defineProps<{
    placeholder: string;
    width: string;
    height: string;
    maxHeight: string;
  }>(),
  {
    placeholder: "输入查询",
    width: "330px",
    height: "45px",
    maxHeight: "500px",
  }
);
const emit = defineEmits(["locate", "select", "search", "close"]);

const key = shallowRef("");
const searchResult = shallowRef<Poi[]>([]);

const loading = shallowRef(false);
const showButtonLoadMore = shallowRef(true);
let keyword: string = "";

const COUNT_PRE_PAGE = 10;

const search = () => {
  if (!loading.value) {
    if (
      key.value !== null &&
      key.value !== undefined &&
      key.value.trim().length !== 0
    ) {
      loading.value = true;
      API.searchPoi(key.value, COUNT_PRE_PAGE)
        .then((resp) => {
          loading.value = false;
          let data = resp.data as SimpleResponse<Poi[]>;
          if (data.status === 0) {
            if (data.data.length < COUNT_PRE_PAGE) {
              showButtonLoadMore.value = false;
            } else {
              showButtonLoadMore.value = true;
            }
            searchResult.value = data.data;
            keyword = resp.headers["x-keyword"];
            emit("search", searchResult.value);
          } else {
            notice.error(data.message);
          }
        })
        .catch((e) => {
          loading.value = false;
        });
    }
  }
};
const searchMore = () => {
  API.searchMorePoi(
    keyword,
    searchResult.value[searchResult.value.length - 1].pid,
    COUNT_PRE_PAGE
  )
    .then(resp => {
      loading.value = false;
      let data = resp.data as SimpleResponse<Poi[]>;
      if (data.status === 0) {
        if (data.data.length < COUNT_PRE_PAGE) {
          showButtonLoadMore.value = false;
        } else {
          showButtonLoadMore.value = true;
        }
      }
      searchResult.value = searchResult.value.concat(data.data);
      emit("search", searchResult.value);
    })
    .catch((e) => {
      loading.value = false;
    });
};
const onLocate = () => {
  if ("geolocation" in navigator) {
    /* 地理位置服务可用 */
    navigator.geolocation.getCurrentPosition(
      function (position) {
        emit("locate", {
          pid: "0",
          name: "pos",
          address: "your postion",
          type: "000000",
          lat: position.coords.latitude,
          lon: position.coords.longitude,
        });
      },
      function (error) {
        notice.error(
          `未能成功获取到您的地理位置 ERROR_MESSAGE : ${error.message}`
        );
      }
    );
  } else {
    notice.warn("您的浏览器不支持地理位置服务");
  }
};
const onClose = () => {
  searchResult.value = [];
  emit("close");
};
const onSelect = (poi: Poi) => {
  emit("select", poi);
};
</script>

<style scoped>
.load-more {
  width: 100%;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.load-more > span {
  cursor: pointer;
}

.load-more > span:active {
  color: dodgerblue;
}

.ps-icon-div {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  position: relative;
  font-size: large;
}

.ps-icon-div > i:active {
  color: dodgerblue;
}

.ps-component {
  position: relative;
  display: inline-block;
  background-color: white;
  vertical-align: top;
  border-radius: 4px;
  transition: transform 300ms;
}

.ps-query {
  width: 100%;
}

.ps-query-input {
  height: 100%;
}

.ps-query-logo {
  height: 100%;
  float: left;
}

.ps-query-search {
  height: 100%;
  float: right;
}

.ps-query-text {
  height: 100%;
  display: flex;
  align-items: center;
}

.ps-query-text > input {
  width: 100%;
  height: 100%;
  outline: none;
  border: none;
  font-size: large;
  color: #2c3e50;
}

.ps-query-locate {
  height: 100%;
  margin-left: 10px;
}

.ps-result {
  width: 100%;
  margin-top: 10px;
  overflow: hidden;
}

.ps-result-list {
  overflow: hidden;
}

.ps-result-list > div {
  overflow-y: auto;
}

.ps-result-close {
  margin-left: 10px;
}

.search-result-enter-from {
  transform: translateY(-100%);
}

.search-result-leave-to {
  transform: translateY(-100%);
}
</style>
