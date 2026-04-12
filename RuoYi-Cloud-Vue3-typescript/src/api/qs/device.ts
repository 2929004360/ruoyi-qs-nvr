import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, DeviceQueryParams, QsDevice } from '@/types'
import {RecordPlanParam} from "@/types/api";

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


// 状态修改
export function changeDeviceStatus(id: number, status: string): Promise<AjaxResult> {
  const data = {
      id,
    status
  }
  return request({
    url: '/qs/device/changeStatus',
    method: 'put',
    data: data
  })
}

// 获取本地mp4截图
export function getVideoSnapshot(id: number) {
  return request({
    url: '/qs/device/getVideoSnapshot/' + id,
    method: 'put'
  })
}

// 获取计划记录对应的视频监控设备
export function listPlanRecord(query:QsDevice) : Promise<TableDataInfo<QsDevice[]>>{
  return request({
    url: '/qs/device/listPlanRecord',
    method: 'get',
    params: query
  })
}

// 设备关联录制计划
export function link(data: RecordPlanParam) : Promise<AjaxResult>{
  return request({
    url: '/qs/device/link',
    method: 'post',
    data: data
  })
}
