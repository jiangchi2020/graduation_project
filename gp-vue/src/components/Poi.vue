<template>
  <div class="poi">
    <p @click="emit('choose')" :title="poi.name">{{ split(poi.name,20) }}</p><br/>
    <label style="font-size: small;color: #aaa">({{ poi.type }})</label>
    <p style="color: #2c3e50;cursor: default;white-space: nowrap" :title="poi.address">地址：{{ split(poi.address,15) }}</p>
  </div>
</template>

<script lang="ts" setup>
import {Poi} from "../entity/Poi";

const props=defineProps<{
  poi: Poi
}>();
const emit = defineEmits(["choose"]);

const split = (address:string,len:number)=>{
  if(address.length>len){
    return address.substring(0,15)+'...';
  }
  return address;
}

</script>

<style scoped>
.poi {
  width: 100%;
  height: 90px;
  padding: 10px 0 0 15px;
  overflow: hidden;
}

.poi > p:first-child {
  color: #2c3e50;
  display: inline-block;
  cursor: pointer;
}

.poi > p:first-child:active{
  color: dodgerblue;
}
</style>