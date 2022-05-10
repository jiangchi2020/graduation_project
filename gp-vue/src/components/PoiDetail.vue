<template>
  <div class="poi-detail">
    <RotatePictures :pictures="detail.photo" width="40vw" height="40vh" style="position: relative;top: 0;left: 0;"></RotatePictures>
    <div style="width: 100%;height: 60vh;padding: 20px;color: #2c3e50;overflow: auto">
      <h1>{{poi.name}}</h1>
      <p style="color: #aaaaaa;font-size: small">（{{poi.type}}）</p>
      <p>地址：{{poi.address}}</p>
      <p>联系电话：{{detail.tel}}</p>

      <pre style="margin-top: 20px;white-space: pre-wrap;font-size: large;">&emsp;&emsp;{{detail.description}}</pre>
    </div>
    <div style="position: absolute;left: 0;bottom: 0;height: 20px;width: 20px;text-align: center;">
      <i class="el-icon-close" style="line-height: 20px;cursor: pointer;color: #aaa" @click="emit('close');"
         title="关闭详情"></i>
    </div>
  </div>
</template>

<script lang="ts" setup>
import API from "../http/api";
import notice from "../util/notice";
import RotatePictures from "./RotatePictures.vue"
import {onMounted, watch, ref, shallowRef} from "vue";
import {Poi} from "../entity/Poi";

const props = defineProps<{
  poi: Poi
}>();
const emit = defineEmits(["close"]);

let detail=shallowRef({
  pid: "",
  tel: "",
  description: "",
  photo: []
});

const flushContent = (pid,pcode) => {
  API.poiDetail(pid,pcode).then(resp => {
    if (resp.data.status === 0) {
      detail.value = resp.data.data;
    } else {
      notice.error(resp.data.message);
    }
  })
};

onMounted(()=>{flushContent(props.poi.pid,props.poi.type)});

watch(()=>props.poi,(newPoi)=>{flushContent(newPoi.pid,newPoi.type)})

</script>

<style scoped>
.poi-detail {
  position: fixed;
  height: 100vh;
  width: 40vw;
  top: 0;
  left: 60vw;
  background-color: white;
  transition: transform 600ms;
  overflow: hidden;
  box-sizing: content-box;
  border-left: 1px #aaaaaa solid;
}
</style>