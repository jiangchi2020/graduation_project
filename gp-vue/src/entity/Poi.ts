export interface Poi{
    pid: string,
    name: string,
    address: string,
    type: string,
    lon: number,
    lat: number
}
export interface PoiDetail{
    poi: string,
    tel: string,
    description: string,
    photo: string[]
}