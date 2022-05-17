<template>
  <div class="poi-search" :style="{'width':`calc(${width} + ${height} + 10px)`}">
    <div class="ps-query" :style="{'height':height}">
      <div class="ps-query-input ps-component" :style="{'width':width}">
        <div class="ps-query-logo" :style="{'width':height}">
          <div class="ps-icon-div"><i class="el-icon-s-promotion"></i></div>
        </div>
        <div class="ps-query-search" :style="{'width':height}" @click="search">
          <div class="ps-icon-div"><i style="cursor: pointer"
                                      :class="{'el-icon-map-location':!inSearching,'el-icon-loading':inSearching}"></i>
          </div>
        </div>
        <div class="ps-query-text" :style="{'margin-left':height,'margin-right':height}">
          <input type="text" v-model="key" :placeholder="placeholder">
        </div>
      </div>
      <div class="ps-query-locate ps-component" :style="{'width':height}">
        <div class="ps-icon-div"><i style="cursor: pointer" class="el-icon-aim" @click="onLocate"></i></div>
      </div>
    </div>
    <div class="ps-result">
      <transition name="search-result">
        <div v-if="searchResult!==null&&searchResult.length>0" class="ps-result-list ps-component" :style="{'width':width}">
          <div :style="{'max-height':`calc(${maxHeight} - ${height} - 10px)`}">
            <PoiComponent v-for="(poi,index) in searchResult" :key="index" :poi="poi" @choose="onSelect(poi)"
                 :style="{'border-top':index===0?'none':'1px #aaa solid'}"></PoiComponent>
          </div>
        </div>
      </transition>
      <transition name="search-result">
        <div v-if="searchResult!==null&&searchResult.length>0" class="ps-result-close ps-component" :style="{'width':height,'height':height}">
          <div class="ps-icon-div"><i style="cursor: pointer" class="el-icon-close" @click="onClose"></i></div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script lang="ts" setup>
import API from "../http/api";
import notice from "../util/notice";
import PoiComponent from "./Poi.vue";
import { shallowRef} from "vue";
import { Poi } from "../entity/Poi";
import { SimpleResponse } from "../entity/SimpleResponse";

const props = withDefaults(defineProps<{
  placeholder: string
  width: string
  height: string
  maxHeight: string
}>(), {placeholder: "输入查询", width: "330px", height: "45px", maxHeight: "500px"});
const emit = defineEmits(["locate", "select", "search", "close"]);

let key = shallowRef("");
let searchResult = shallowRef<Poi[]>([]);

let inSearching = shallowRef(false);

const search = () => {
  if (!inSearching.value) {
    if (key.value !== null && key.value !== undefined && key.value.trim().length !== 0) {
      inSearching.value = true;
      API.searchPoi(key.value).then(resp => {
        inSearching.value = false;
        let data=resp.data as SimpleResponse<Poi[]>;
        if (data.status === 0) {
          searchResult.value = data.data;
          emit("search", searchResult.value);
        } else {
          notice.error(data.message);
        }
      }).catch(e => {
        inSearching.value = false;
      })
    }
  }
};
const onLocate = () => {
  if ("geolocation" in navigator) {
    /* 地理位置服务可用 */
    navigator.geolocation.getCurrentPosition(function (position) {
      emit("locate", {pid:"0",name:"pos",address:"your postion",type:"000000",lat: position.coords.latitude, lon: position.coords.longitude})
    }, function (error) {
      notice.error(`未能成功获取到您的地理位置 ERROR_MESSAGE : ${error.message}`)
    });
  } else {
    notice.warn("您的浏览器不支持地理位置服务");
  }
};
const onClose = () => {
  searchResult.value = [];
  emit("close");
};
const onSelect = (poi:Poi) => {
  emit("select", poi);
}
</script>

<style scoped>
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