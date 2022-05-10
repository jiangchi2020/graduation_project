import axios from "./request";
import {URL_POI_DETAIL, URL_POI_SEARCH} from "./api-url";

function searchPoi(query:string){
    return axios({
        url: URL_POI_SEARCH+"?query="+query,
        method: "GET"
    });
}

function poiDetail(pid:string,pcode:string){
    return axios({
        url: URL_POI_DETAIL+pcode+"/"+pid,
        method: "GET"
    });
}

const API={
    searchPoi,poiDetail
};

export default API;