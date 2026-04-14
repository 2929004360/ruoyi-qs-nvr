import request from '@/utils/request'
import type {AjaxResult, DeviceQueryParams, QsDevice, TableDataInfo} from '@/types'
import {RecordPlanParam} from "@/types/api";

// 查询视频监控设备列表
export function listDevice(query: DeviceQueryParams): Promise<TableDataInfo<QsDevice[]>> {
    return request({
        url: '/qs/device/list',
        method: 'get',
        params: query
    })
}

// 根据行政区域获取视频监控设备列表
export function queryListByCivilCode(query: DeviceQueryParams): Promise<TableDataInfo<QsDevice[]>> {
    return request({
        url: '/qs/device/queryListByCivilCode',
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
export function listPlanRecord(query: QsDevice): Promise<TableDataInfo<QsDevice[]>> {
    return request({
        url: '/qs/device/listPlanRecord',
        method: 'get',
        params: query
    })
}

// 设备关联录制计划
export function link(data: RecordPlanParam): Promise<AjaxResult> {
    return request({
        url: '/qs/device/link',
        method: 'post',
        data: data
    })
}

// 添加行政区划设备
export function addDeviceToRegion(data): Promise<AjaxResult> {
    return request({
        url: `/qs/device/region/add`,
        method: 'post',
        data: data
    })
}

// 删除行政区划设备
export function deleteDeviceToRegion(data): Promise<AjaxResult> {
    return request({
        url: `/qs/device/region/delete`,
        method: 'post',
        data: data
    })
}

// 存在行政区划但无法挂载的设备列表
export function getUnusualCivilCodeList(query): Promise<TableDataInfo<QsDevice[]>> {
    return request({
        url: `/qs/device/civilCode/unusual/list`,
        method: 'get',
        params: query
    })
}

// 清除存在行政区划但无法挂载的设备列表
export function clearDeviceCivilCode(data): Promise<AjaxResult> {
    return request({
        url: `/qs/device/civilCode/unusual/clear`,
        method: 'post',
        data: data
    })
}

// 获取编码列表
export function getNetworkIdentificationTypeList(): Promise<AjaxResult> {
    return request({
        url: `/qs/device/network/identification/list`,
        method: 'get',
    })
}

// 获取编码列表
export function getDeviceTypeList(): Promise<AjaxResult> {
    return request({
        url: `/qs/device/type/list`,
        method: 'get',
    })
}

// 获取行业编码列表
export function getIndustryCodeList() {
    return request({
        url: `/qs/device/industry/list`,
        method: 'get',
    })
}

// 根据ParentId获取设备列表
export function queryListByParentId(query): Promise<TableDataInfo<QsDevice[]>> {
    return request({
        url: `/qs/device/parent/list`,
        method: 'get',
        params: query
    })
}

// 添加业务分组设备信息
export function addDeviceIdToGroup(data): Promise<AjaxResult> {
    return request({
        url: `/qs/device/group/add`,
        method: 'post',
        data: data
    })
}

// 删除业务分组设备
export function deleteDeviceToGroup(data): Promise<AjaxResult> {
    return request({
        url: `/qs/device/group/delete`,
        method: 'post',
        data: data
    })
}

// 存在父节点编号但无法挂载的设备列表
export function queryListByParentForUnusual(query) {
    return request({
        url: `/qs/device/parent/unusual/list`,
        method: 'get',
        params: query
    })
}

// 清除存在分组节点但无法挂载的设备列表
export function clearDeviceParent(data) {
    return request({
        url: `/qs/device/parent/unusual/clear`,
        method: 'post',
        data: data
    })
}