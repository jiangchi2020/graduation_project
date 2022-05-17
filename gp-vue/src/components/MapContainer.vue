<template>
  <div id="container"></div>
</template>

<script lang="ts" setup>
import AMapLoader from '@amap/amap-jsapi-loader';
import stationList from "../assets/station";
import {Poi} from "../entity/Poi";
import {onMounted, watch} from "vue";
import {COUNTRY_OGC_URL, RAILWAY_OGC_URL} from "../http/ogc-url";

const props = defineProps<{
  pois: Poi[],
  poi: Poi
}>();
const emit = defineEmits(["select"]);

let poiMarkers:any=null;
let AMap:any=null;
let map:any=null;

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
      url: COUNTRY_OGC_URL,
      blend: false,
      tileSize: 256,
      params: {
        VERSION: '1.1.1',
        'LAYERS': 'gp:discountry',
        SRS: "EPSG:3857",
      }
    });
    // 中国铁路线路图层
    let railway = new AMap.TileLayer.WMS({
      url: RAILWAY_OGC_URL,
      blend: false,
      tileSize: 256,
      params: {
        VERSION: '1.1.1',
        'LAYERS': 'gp:cn_railway_gcj02_3857',
        SRS: "EPSG:3857",
      }
    });
    // 地图初始化
    map = new AMap.Map("container", {
      zooms: [2, 16],
      zoom: 5,
      center: [105.095028,36.505157],
      // layers: [background, countryBoundary, railway]
      layers: [background]
    });
    map.on('zoomchange',function (e:any){
      console.log(e.target.getZoom());
    })
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
    stationMarks.on('mouseover', function (e:any) {
      marker.setPosition(e.data.lnglat);
      marker.setLabel({content: e.data.name + '站', offset: markerOffset});
      map.add(marker);
    });
    stationMarks.on('mouseout', function (e:any) {
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
    map.setZoom(9);
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
