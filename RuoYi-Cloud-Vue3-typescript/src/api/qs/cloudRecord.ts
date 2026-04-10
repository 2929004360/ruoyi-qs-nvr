import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, CloudRecordQueryParams, ZlmCloudRecord } from '@/types'

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


