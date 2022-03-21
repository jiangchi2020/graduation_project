<template>
  <div id="container"></div>
</template>

<script setup>
import AMapLoader from '@amap/amap-jsapi-loader';
import stationList from "@/assets/station";
import {onMounted, watch, defineProps, defineEmits} from "vue";

const props = defineProps({
  pois: {
    type: Array,
    default() {
      return null;
    }
  },
  poi: {
    type: Object,
    default() {
      return null;
    }
  }
});
const emit = defineEmits(["select"]);

let poiMarkers=null;
let AMap=null;
let map=null;

onMounted(()=>{
  AMapLoader.load({
    key: "cf2d2ff077412980012a103e45cf3032",
    version: "2.0",
  }).then((_AMap) => {
    AMap=_AMap;
    // 背景卫星图层
    let background = new AMap.TileLayer.Satellite();
    // 中国国境线图层
    let countryBoundary = new AMap.TileLayer.WMS({
      url: 'http://localhost:8080/geoserver/wms',
      blend: false,
      tileSize: 256,
      params: {
        VERSION: '1.1.1',
        'LAYERS': 'gp:country',
        SRS: "EPSG:3857",
      }
    });
    // 中国省界线图层
    let provinceBoundary = new AMap.TileLayer.WMS({
      url: 'http://localhost:8080/geoserver/wms',
      blend: false,
      tileSize: 256,
      zooms: [5, 16],
      params: {
        VERSION: '1.1.1',
        'LAYERS': 'gp:province',
        SRS: "EPSG:3857",
      }
    });
    // 中国铁路线路图层
    let railway = new AMap.TileLayer.WMS({
      url: 'http://localhost:8080/geoserver/wms',
      blend: false,
      tileSize: 256,
      params: {
        VERSION: '1.1.1',
        'LAYERS': 'gp:railway',
        SRS: "EPSG:3857",
      }
    });
    // 地图初始化
    map = new AMap.Map("container", {
      zooms: [2, 16],
      zoom: 5,
      center: [0,0],
      layers: [background, /*countryBoundary, provinceBoundary, railway*/]
    });
    // 车站标记样式
    let stationStyle = {
      url: '/station2.ico',
      size: new AMap.Size(24, 24),
      anchor: new AMap.Pixel(12, 12)
    }
    // 车站标记对象
    let stationMarks = new AMap.MassMarks(stationList, {
      zIndex: 111,
      zooms: [8, 16],
      style: stationStyle
    });
    // 车站标记注释
    let marker = new AMap.Marker({content: ' '});
    let markerOffset = new AMap.Pixel(9, 0);
    stationMarks.on('mouseover', function (e) {
      marker.setPosition(e.data.lnglat);
      marker.setLabel({content: e.data.name + '站', offset: markerOffset});
      map.add(marker);
    });
    stationMarks.on('mouseout', function (e) {
      map.remove(marker);
    });
    stationMarks.setMap(map);
  }).catch(e => {
    console.log(e);
  })
})

watch(() => props.pois ,(newValue,oldValue)=>{
  if(oldValue!==null) {
    map.remove(poiMarkers)
    poiMarkers=null;
  }
  if(newValue!==null){
    let poiMarks=[];
    let maxLon=0,minLon=360,maxLat=0,minLat=360;
    for(let poi of newValue){
      if(poi.lon>maxLon){maxLon=poi.lon;}
      if(poi.lon<minLon){minLon=poi.lon;}
      if(poi.lat>maxLat){maxLat=poi.lat;}
      if(poi.lat<minLat){minLat=poi.lat;}
      let marker = new AMap.Marker({position: new AMap.LngLat(poi.lon,poi.lat),title: poi.name});
      marker.on("dblclick",() => {
        map.setCenter([poi.lon, poi.lat]);
        map.setZoom(14);
        emit("select",poi);
      });
      poiMarks.push(marker);
    }
    let center=[(maxLon+minLon)/2,(maxLat+minLat)/2];
    map.add(poiMarks);
    map.setCenter(center);
    poiMarkers=poiMarks;
  }
})

watch(()=>props.poi,(newPoi)=>{
  map.setCenter([newPoi.lon, newPoi.lat]);
  map.setZoom(14);
})

</script>

<style scoped>
#container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  transition: width 500ms;
}
</style>