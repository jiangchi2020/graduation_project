export interface Poi{
    pid: string,
    name: string,
    address: string,
    type: string,
    typeCode: string,
    lon: number,
    lat: number
}
export interface PoiDetail{
    pid: string,
    tel: string,
    description: string,
    photo: string[]
}