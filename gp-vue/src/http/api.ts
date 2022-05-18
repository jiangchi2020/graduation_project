import axios from "./request";
import {URL_POI_DETAIL, URL_POI_SEARCH, URL_POI_SEARCH_MORE} from "./api-url";

function searchPoi(query:string,c:number){
    return axios({
        url: URL_POI_SEARCH+"query="+query+"&c="+c,
        method: "GET"
    });
}

function searchMorePoi(key:string,p:string,c:number){
    return axios({
        url: URL_POI_SEARCH_MORE+key+"&p="+p+"&c="+c,
        method: "GET"
    })
}

function poiDetail(pid:string,pcode:string){
    return axios({
        url: URL_POI_DETAIL+pcode+"/"+pid,
        method: "GET"
    });
}

const API={
    searchPoi,searchMorePoi,poiDetail
};

export default API;