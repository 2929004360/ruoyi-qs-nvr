import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, RecordPlanQueryParams, ZlmRecordPlan } from '@/types'

// 查询录像计划列表
export function listRecordPlan(query: RecordPlanQueryParams): Promise<TableDataInfo<ZlmRecordPlan[]>> {
  return request({
    url: '/zlm/recordPlan/list',
    method: 'get',
    params: query
  })
}

// 查询录像计划详细
export function getRecordPlan(id: number): Promise<AjaxResult<ZlmRecordPlan>> {
  return request({
    url: '/zlm/recordPlan/' + id,
    method: 'get'
  })
}

// 新增录像计划
export function addRecordPlan(data: ZlmRecordPlan): Promise<AjaxResult> {
  return request({
    url: '/zlm/recordPlan',
    method: 'post',
    data: data
  })
}

// 修改录像计划
export function updateRecordPlan(data: ZlmRecordPlan): Promise<AjaxResult> {
  return request({
    url: '/zlm/recordPlan',
    method: 'put',
    data: data
  })
}

// 删除录像计划
export function delRecordPlan(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/zlm/recordPlan/' + id,
    method: 'delete'
  })
}


