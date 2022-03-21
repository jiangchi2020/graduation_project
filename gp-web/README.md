# 高铁沿线旅游信息查询后端模块

## API概要

1. POI详情

```js
request = {
    url: "/api/v1/poi/detail/{pid}",
    method: "GET",
    body: null
};
response = {
    Status_Code: 200,
    Content_Type: "application/json",
    Response_Body: {
        status: Integer,
        msg: String,
        data: {
            pid: String,
            tel: String,
            description: String,
            photo: Array
        }
    }
}
```

2. POI查询

POI查询即自然语言分析后的查询，接口接收请求后，首先向NLP服务发起远程调用获取查询要素，查询要素(交互内容约定)包括：

```js
response = {
    status: Integer,
    msg: String,
    data: {
        type: String, // 查询类型，可选值：POINT，LINE，POLYGON
        centet: String, // 查询中心名称，如成都东站，合安高铁等
        distance: Integer, // 缓冲区班级，即范围距离等，精度米
        class: Integer, // POI类别，可选值见amap_poicode.xlsx
    }
}
```

```js
request = {
    url: "/api/v1/poi/search?query={query}",
    method: "GET",
    body: null
};
response = {
    Status_Code: 200,
    Content_Type: "application/json",
    Redponse_Body: {
        status: Integer,
        msg: String,
        data: [
            {
                pid: String,
                name: String,
                type: String,
                address: String,
                lon: Number,
                lat: Number
            }
        ]
    }
};
```

3. POI编辑

```js
request = {
    url: "/api/v1/poi/edit/{pid}",
    method: "POST",
    body: null
};
response = {
    Status_Code: 200,
    Content_Type: "application/json",
    Redponse_Body: {
        status: Integer,
        msg: String,
        data: null
    }
};
```

