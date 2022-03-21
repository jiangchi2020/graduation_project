<template>
  <MapContainer :style="{'width':poiDetailVisible?'60vw':'100vw'}" :pois="pois" :poi="poi" @select="showDetail"></MapContainer>
  <PoiSearch style="position: absolute;margin: 20px 0 0 20px" placeholder="输入查询"
             @search="onSearch" @locate="onLocate" @select="showDetail" @close="clearMarkers"></PoiSearch>
  <transition name="poi-detail">
    <PoiDetail v-if="poiDetailVisible" :poi="poi" @close="closeDetail"></PoiDetail>
  </transition>
</template>

<script setup>

import MapContainer from "@/components/MapContainer";
import PoiDetail from "@/components/PoiDetail";
import PoiSearch from "@/components/PoiSearch";
import {ref,shallowRef} from "vue";

let searchResult=shallowRef(null);
let poiDetailVisible=shallowRef(false);
let pois=shallowRef(null);
let poi=shallowRef(null);

const onLocate = (pos) => {

}
const showDetail = (_poi) => {
  poi.value=_poi;
  poiDetailVisible.value=true;
}
const closeDetail = (_poi) => {
  poiDetailVisible.value=false;
}
const onSearch = (_pois) => {
  pois.value=_pois;
}
const clearMarkers = () => {
  pois.value=null;
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.poi-detail-enter-from{
  transform: translateX(100%);
}
.poi-detail-leave-to{
  transform: translateX(100%);
}
</style>