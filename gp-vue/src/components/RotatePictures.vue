<template>
  <div class="poi-photo-list" :style="{'height':height,'width':width}">
    <div class="poi-photo-list-container" :style="{'width':'calc(100% * '+pictures.length+')','left':'calc(-100% * '+photoIndex+')'}">
      <img class="poi-photo"
           v-for="(photo,index) in pictures" :key="index"
           :src="photo" :style="{'width':width}"/>
    </div>
    <div class="poi-photo-controller" style="left: 0">
      <div></div>
      <i class="el-icon-arrow-left" @click="previousPhoto"></i>
    </div>
    <div class="poi-photo-controller" style="right: 0">
      <div></div>
      <i class="el-icon-arrow-right" @click="nextPhoto"></i>
    </div>
  </div>
</template>

<script setup>
import {defineProps, shallowRef, watch} from "vue";
const props = defineProps({
  pictures:{
    type:Array,
    default(){
      return [];
    }
  },
  width:{type:String,require:true},
  height:{type:String,require:true}
});
let photoIndex=shallowRef(0);
const previousPhoto = () => {
  if(photoIndex.value>0){
    photoIndex.value--;
  }
};
const nextPhoto = () => {
  if(photoIndex.value<props.pictures.length-1){
    photoIndex.value++;
  }
}
watch(()=>props.pictures,()=>{
  photoIndex.value=0;
})
</script>

<style scoped>
.poi-photo-list {
  background-color: black;
}

.poi-photo-list-container{
  position: relative;
  height: 100%;
  transition: left 500ms;
  font-size: 0;
}

.poi-photo{
  display: inline-block;
  height: 100%;
  position: relative;
  object-fit: contain;
}

.poi-photo-controller {
  height: 100%;
  width: 10%;
  position: absolute;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: opacity 300ms;
  opacity: 0;
}

.poi-photo-controller:hover{
  opacity: 1;
}

.poi-photo-controller > div {
  width: 60%;
  height: 0;
  padding-bottom: 60%;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.3);
  position: absolute;
}

.poi-photo-controller > i{
  position: absolute;
  font-size: large;
  color: #fff;
  cursor: pointer;
}
</style>