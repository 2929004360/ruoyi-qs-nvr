import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, DeviceQueryParams, QsDevice } from '@/types'

// 查询视频监控设备列表
export function listDevice(query: DeviceQueryParams): Promise<TableDataInfo<QsDevice[]>> {
  return request({
    url: '/qs/device/list',
    method: 'get',
    params: query
  })
}

// 查询视频监控设备详细
export function getDevice(id: number): Promise<AjaxResult<QsDevice>> {
  return request({
    url: '/qs/device/' + id,
    method: 'get'
  })
}

// 新增视频监控设备
export function addDevice(data: QsDevice): Promise<AjaxResult> {
  return request({
    url: '/qs/device',
    method: 'post',
    data: data
  })
}

// 修改视频监控设备
export function updateDevice(data: QsDevice): Promise<AjaxResult> {
  return request({
    url: '/qs/device',
    method: 'put',
    data: data
  })
}

// 删除视频监控设备
export function delDevice(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/qs/device/' + id,
    method: 'delete'
  })
}


