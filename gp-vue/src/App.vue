<template>
  <MapContainer :style="{'width':poiDetailVisible?'60vw':'100vw'}" :pois="pois" :poi="poi" @select="showDetail"></MapContainer>
  <PoiSearch style="position: absolute;margin: 20px 0 0 20px" placeholder="输入查询"
             @search="onSearch" @locate="onLocate" @select="showDetail" @close="clearMarkers"></PoiSearch>
  <transition name="poi-detail">
    <PoiDetail v-if="poiDetailVisible" :poi="poi" @close="closeDetail"></PoiDetail>
  </transition>
</template>

<script lang="ts" setup>

import MapContainer from "./components/MapContainer.vue";
import PoiDetail from "./components/PoiDetail.vue";
import PoiSearch from "./components/PoiSearch.vue";
import {ref,shallowRef} from "vue";
import { Poi } from "./entity/Poi";

let poiDetailVisible=shallowRef(false);
let pois=shallowRef<Poi[]>([]);
let poi=shallowRef<Poi>({pid:"",name:"",address:"",type:"",lon:0,lat:0});

const onLocate = (pos:Poi) => {
  poi.value=pos;
}
const showDetail = (_poi:Poi) => {
  poi.value=_poi;
  poiDetailVisible.value=true;
}
const closeDetail = () => {
  poiDetailVisible.value=false;
}
const onSearch = (_pois:Poi[]) => {
  pois.value=_pois;
}
const clearMarkers = () => {
  pois.value=[];
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