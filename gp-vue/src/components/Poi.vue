<template>
  <div class="poi">
    <p @click="emit('choose')">{{ poi.name }}</p>
    <label style="font-size: small;color: #aaa">({{ poi.type }})</label>
    <p style="color: #2c3e50;cursor: default;white-space: nowrap">地址：{{ poi.address }}</p>
  </div>
</template>

<script setup>
import {defineProps,defineEmits} from "vue";

const props=defineProps({
  poi: {
    type: Object,
    default: null
  }
});
const emit = defineEmits(["choose"]);

</script>

<style scoped>
.poi {
  width: 100%;
  height: 80px;
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