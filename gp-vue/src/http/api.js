import axios from "@/http/request";
import {URL_POI_DETAIL, URL_POI_SEARCH} from "@/http/api-url";

function searchPoi(query){
    return axios({
        url: URL_POI_SEARCH+"?query="+query,
        method: "GET"
    });
}

function poiDetail(pid,pcode){
    return axios({
        url: URL_POI_DETAIL+pcode+"/"+pid,
        method: "GET"
    });
}

const API={
    searchPoi,poiDetail
};

export default API;