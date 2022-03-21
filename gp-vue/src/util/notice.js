function info(message){
    let notice = createElement(message,"border-color: #e1f3d8;background-color: #f0f9eb;color: #67c23a;","el-icon-success");
    alert(notice);
}
function warn(message){
    let notice = createElement(message,"border-color: #faecd8;background-color: #fdf6ec;color: #e6a32c;","el-icon-warning");
    alert(notice);
}
function error(message){
    let notice = createElement(message,"border-color: #fde2e2;background-color: #fef0f0;color: #f56c6c;","el-icon-error");
    alert(notice);
}
function createElement(message,style,iconClass){
    let notice = document.createElement("div");
    notice.setAttribute("class","notice");
    notice.setAttribute("style", style);
    notice.innerHTML=`<i class="${iconClass}"></i><p>${message}</p>`;
    return notice;
}
function alert(element){
    let tipsContainer = document.getElementById("notice-container-APEX4869");
    if(!tipsContainer){
        tipsContainer=document.createElement("div");
        tipsContainer.setAttribute("class","notice-container");
        tipsContainer.setAttribute("id","notice-container-APEX4869");
        document.body.appendChild(tipsContainer);
    }
    tipsContainer.appendChild(element);
    setTimeout(function () {
        element.classList.add("notice-disappear");
        setTimeout(function (){
            element.remove();
        },500)
    },3000)
}
const notice={
    info,warn,error
}
export default notice;
