import request from '@/utils/request'
import type {AjaxResult, CloudRecordQueryParams, TableDataInfo, ZlmCloudRecord} from '@/types'

// 查询云端录像列表
export function listCloudRecord(query: CloudRecordQueryParams): Promise<TableDataInfo<ZlmCloudRecord[]>> {
    return request({
        url: '/zlm/cloudRecord/list',
        method: 'get',
        params: query
    })
}

// 查询云端录像详细
export function getCloudRecord(id: number): Promise<AjaxResult<ZlmCloudRecord>> {
    return request({
        url: '/zlm/cloudRecord/' + id,
        method: 'get'
    })
}

// 删除云端录像
export function delCloudRecord(id: number | number[]): Promise<AjaxResult> {
    return request({
        url: '/zlm/cloudRecord/' + id,
        method: 'delete'
    })
}

// 播放云端录像
export function loadRecord(id: number | number[]): Promise<AjaxResult> {
    return request({
        url: '/zlm/cloudRecord/loadRecord/' + id,
        method: 'get'
    })
}

// 关闭流文件形成播放地址
export function closeStreams(id: number | number[]): Promise<AjaxResult> {
    return request({
        url: '/zlm/cloudRecord/closeStreams/' + id,
        method: 'get'
    })
}

// 定位录像播放到制定位置
export function seekCloudRecord(query: any): Promise<AjaxResult> {
    return request({
        url: '/zlm/cloudRecord/seek',
        method: 'get',
        params: query
    })
}

// 设置录像播放速度
export function setCloudRecordSpeed(query: any): Promise<AjaxResult> {
    return request({
        url: '/zlm/cloudRecord/speed',
        method: 'get',
        params: query
    })
}