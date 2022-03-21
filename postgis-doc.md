# PostGIS 学习手册

本文档中的标号和官方文档的标号对应，但不包含部分内容

[PostGIS官方文档 https://postgis.net/docs/](https://postgis.net/docs/)

## 4 数据管理

### 4.1 空间数据模型

#### 4.1.1 OGC标准的地理数据类型
1. Point点
2. LineString线
3. LinearRing环
4. Polygon面
5. MultiPoint点集
6. MultiLineString多段线
7. MultiPolygon多个面
8. GeometryCollection地理集合
9. PolyhedralSurface
10. Triangle三角形
11. TIN三角网

#### 4.1.3 WKT与WKB

WKT是文本的地理要素表示方法，postgis中有函数完成地理对象与WKT表示的对象之间的转换
```sql
# text WKT = ST_AsText(geometry);
# geometry = ST_GeomFromText(text WKT, SRID);
INSERT INTO geotable ( geom, name )
VALUES ( ST_GeomFromText('POINT(-126.4 45.32)', 4326), 'A Place');
```
WKB是二进制的表示方法，同样有两个函数
```sql
# bytea WKB = ST_AsBinary(geometry);
# geometry = ST_GeomFromWKB(bytea WKB, SRID);
```

### 4.2 4.3 Geometry 与 Geography

长话短说，Geometry是包括投影的地理要素类型，符合OGC标准，在笛卡尔坐标系下运算

Geography使用经纬度存储地理要素，该类型的功能不如前者全面，而且在计算距离时（以及类似操作，比如缓冲区分析）效率较低

### 4.7 导入/导出 空间数据

使用sql的方式不说了，类似于mysql中的source命令执行sql文件

#### 从shp导入
命令：shp2pgsql

|参数|描述|
|:---:|:---|
|-c|无参数，创建一个表（默认模式）|
|-a|无参数，追加到一个表|
|-d|无参数，删掉原来的再创建一个新的|
|-p|无参数，只生成sql代码而不实际操作数据库|
|-s [<FROM_SRID>:]<SRID>|指定一个空间参考|
|-l|在几何列上创建GIST空间索引|
|-S|生成简单的几何图形而不是 MULTI 几何图形。仅当所有几何实际上都是单一的（即具有单个面的 MULTIPOLYGON 或具有单个点的 MULTIPOINT）时才会成功。|

大概写这些，具体可去官网查看，还有pgsql2shp命令用于将数据库导出成shp格式

## 省流小助手

在进行地理编码后，我们得到的一般是经纬度坐标，一般是WGS84坐标，GCJ02也算一种WGS84的变种吧

但进行计算和展示时，一般使用投影后的坐标系，比如WGS84+Web莫卡托投影也就是EPSG:3857

为了将lon/lat转为3857下的坐标:
1. 通过lon，lat构建geometry对象：st_point(lon,lat)
2. 将其**强制声明**为4326坐标系：st_setsrid(st_point(lon, lat), 4326)
3. 通过投影变换将其转为3857坐标：st_transform(st_setsrid(st_point(lon, lat), 4326),3857)

当然，1，2步可以通过其他方法来实现

```sql
UPDATE station SET wkb_geometry = st_transform(st_setsrid(st_point(lon, lat), 4326),3857);
```
