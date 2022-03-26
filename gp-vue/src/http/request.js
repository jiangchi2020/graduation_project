import axios from "axios";
import notice from "@/util/notice";

// axios.defaults.baseURL="http://localhost:4523/mock/673921/test";
axios.defaults.baseURL="http://localhost:8081";

axios.interceptors.response.use(resp=>{
    return resp;
},error => {
    notice.error(error.message);
    if (error.response) {
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
    } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // `error.request` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        console.log(error.request);
    } else {
        // 发送请求时出了点问题
        console.log('Error', error.message);
    }
    console.log(error.config);
})

export default axios;