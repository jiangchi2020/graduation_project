/*
 Navicat Premium Data Transfer

 Source Server         : PostGIS
 Source Server Type    : PostgreSQL
 Source Server Version : 120009
 Source Host           : 1.14.105.203:5432
 Source Catalog        : gp
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120009
 File Encoding         : 65001

 Date: 07/06/2022 17:23:09
*/


-- ----------------------------
-- Table structure for train
-- ----------------------------
DROP TABLE IF EXISTS "public"."train";
CREATE TABLE "public"."train" (
  "train_no" char(12) COLLATE "pg_catalog"."default",
  "train_code" char(4) COLLATE "pg_catalog"."default" NOT NULL,
  "from_station_id" int8,
  "to_station_id" int8,
  "total_num" int4,
  "date" char(8) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."train" OWNER TO "postgres";

-- ----------------------------
-- Table structure for stop
-- ----------------------------
DROP TABLE IF EXISTS "public"."stop";
CREATE TABLE "public"."stop" (
  "station_id" int8,
  "train_code" varchar(10) COLLATE "pg_catalog"."default",
  "arrive_time" char(5) COLLATE "pg_catalog"."default",
  "start_time" char(5) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."stop" OWNER TO "postgres";

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS "public"."station";
CREATE TABLE "public"."station" (
  "sid" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "lon" float8,
  "lat" float8,
  "origin" varchar(10) COLLATE "pg_catalog"."default",
  "wkb_geometry" geometry(POINT, 3857)
)
;
ALTER TABLE "public"."station" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_code
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_code";
CREATE TABLE "public"."poi_code" (
  "type_code" int4 NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."poi_code" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_110000_photo
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_110000_photo";
CREATE TABLE "public"."poi_110000_photo" (
  "pid" int8 NOT NULL,
  "photo_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."poi_110000_photo" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_110000
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_110000";
CREATE TABLE "public"."poi_110000" (
  "pid" int8 NOT NULL,
  "lat" float8 NOT NULL,
  "lon" float8 NOT NULL,
  "address" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "tel" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "type" char(6) COLLATE "pg_catalog"."default" DEFAULT NULL::bpchar,
  "wkb_geometry" geometry(GEOMETRY) NOT NULL,
  "type_code" int4 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."poi_110000" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_100000_photo
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_100000_photo";
CREATE TABLE "public"."poi_100000_photo" (
  "pid" int8 NOT NULL,
  "photo_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."poi_100000_photo" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_100000
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_100000";
CREATE TABLE "public"."poi_100000" (
  "pid" int8 NOT NULL,
  "lat" float8 NOT NULL,
  "lon" float8 NOT NULL,
  "address" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "tel" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "type" char(6) COLLATE "pg_catalog"."default" DEFAULT NULL::bpchar,
  "wkb_geometry" geometry(GEOMETRY) NOT NULL,
  "type_code" int4 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."poi_100000" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_050000_photo
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_050000_photo";
CREATE TABLE "public"."poi_050000_photo" (
  "pid" int8 NOT NULL,
  "photo_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."poi_050000_photo" OWNER TO "postgres";

-- ----------------------------
-- Table structure for poi_050000
-- ----------------------------
DROP TABLE IF EXISTS "public"."poi_050000";
CREATE TABLE "public"."poi_050000" (
  "pid" int8 NOT NULL,
  "lat" float8 NOT NULL,
  "lon" float8 NOT NULL,
  "address" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "tel" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "type" char(6) COLLATE "pg_catalog"."default" DEFAULT NULL::bpchar,
  "wkb_geometry" geometry(GEOMETRY) NOT NULL,
  "type_code" int4 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."poi_050000" OWNER TO "postgres";

-- ----------------------------
-- Table structure for cn_railway_gcj02_3857
-- ----------------------------
DROP TABLE IF EXISTS "public"."cn_railway_gcj02_3857";
CREATE TABLE "public"."cn_railway_gcj02_3857" (
  "rid" int4 NOT NULL DEFAULT nextval('cn_railway_gcj02_3857_ogc_fid_seq'::regclass),
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "wkb_geometry" geometry(MULTILINESTRING, 3857)
)
;
ALTER TABLE "public"."cn_railway_gcj02_3857" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table train
-- ----------------------------
ALTER TABLE "public"."train" ADD CONSTRAINT "train_pkey" PRIMARY KEY ("train_code");

-- ----------------------------
-- Indexes structure for table stop
-- ----------------------------
CREATE INDEX "stop_train_code_idx" ON "public"."stop" USING hash (
  "train_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops"
);

-- ----------------------------
-- Indexes structure for table station
-- ----------------------------
CREATE INDEX "station_name_idx" ON "public"."station" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "station_wkb_grometry_idx" ON "public"."station" USING gist (
  "wkb_geometry" "public"."gist_geometry_ops_2d"
);

-- ----------------------------
-- Primary Key structure for table station
-- ----------------------------
ALTER TABLE "public"."station" ADD CONSTRAINT "station_pkey" PRIMARY KEY ("sid");

-- ----------------------------
-- Indexes structure for table poi_code
-- ----------------------------
CREATE INDEX "poi_code_type_code_idx" ON "public"."poi_code" USING btree (
  "type_code" "pg_catalog"."int4_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table poi_code
-- ----------------------------
ALTER TABLE "public"."poi_code" ADD CONSTRAINT "poi_code_pkey" PRIMARY KEY ("type_code");

-- ----------------------------
-- Indexes structure for table poi_110000_photo
-- ----------------------------
CREATE INDEX "poi_110000_photo_pid_idx" ON "public"."poi_110000_photo" USING btree (
  "pid" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table poi_110000
-- ----------------------------
CREATE INDEX "poi_110000_sp_gist_idx" ON "public"."poi_110000" USING spgist (
  "wkb_geometry" "public"."spgist_geometry_ops_2d"
);

-- ----------------------------
-- Primary Key structure for table poi_110000
-- ----------------------------
ALTER TABLE "public"."poi_110000" ADD CONSTRAINT "poi_110000_pkey" PRIMARY KEY ("pid");

-- ----------------------------
-- Indexes structure for table poi_100000_photo
-- ----------------------------
CREATE INDEX "poi_100000_photo_pid_idx" ON "public"."poi_100000_photo" USING btree (
  "pid" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table poi_100000
-- ----------------------------
CREATE INDEX "poi_100000_sp_gist_idx" ON "public"."poi_100000" USING spgist (
  "wkb_geometry" "public"."spgist_geometry_ops_2d"
);

-- ----------------------------
-- Primary Key structure for table poi_100000
-- ----------------------------
ALTER TABLE "public"."poi_100000" ADD CONSTRAINT "poi_100000_pkey" PRIMARY KEY ("pid");

-- ----------------------------
-- Indexes structure for table poi_050000_photo
-- ----------------------------
CREATE INDEX "poi_050000_photo_pid_idx" ON "public"."poi_050000_photo" USING btree (
  "pid" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table poi_050000
-- ----------------------------
CREATE INDEX "poi_050000_sp_gist_idx" ON "public"."poi_050000" USING spgist (
  "wkb_geometry" "public"."spgist_geometry_ops_2d"
);
CREATE INDEX "poi_050000_type_code_idx" ON "public"."poi_050000" USING btree (
  "type_code" "pg_catalog"."int4_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table poi_050000
-- ----------------------------
ALTER TABLE "public"."poi_050000" ADD CONSTRAINT "poi_050000_pkey" PRIMARY KEY ("pid");

-- ----------------------------
-- Indexes structure for table cn_railway_gcj02_3857
-- ----------------------------
CREATE INDEX "cn_railway_gcj02_3857_name_idx" ON "public"."cn_railway_gcj02_3857" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "cn_railway_gcj02_3857_wkb_geometry_geom_idx" ON "public"."cn_railway_gcj02_3857" USING gist (
  "wkb_geometry" "public"."gist_geometry_ops_2d"
);

-- ----------------------------
-- Primary Key structure for table cn_railway_gcj02_3857
-- ----------------------------
ALTER TABLE "public"."cn_railway_gcj02_3857" ADD CONSTRAINT "cn_railway_gcj02_3857_pkey" PRIMARY KEY ("rid");
