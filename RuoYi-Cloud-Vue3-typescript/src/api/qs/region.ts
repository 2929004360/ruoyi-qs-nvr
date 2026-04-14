import {AjaxResult} from "@/types";
import request from "@/utils/request";
import {QsRegion} from "@/types/api";

/**
 * 添加区域
 *
 * @param data
 */
export function addRegion(data: QsRegion): Promise<AjaxResult> {
    return request({
        url: '/qs/region/add',
        method: 'post',
        data: data
    })
}

/**
 * 更新区域
 *
 * @param data
 */
export function updateRegion(data: QsRegion): Promise<AjaxResult> {
    return request({
        url: '/qs/region/update',
        method: 'post',
        data: data
    })
}

/**
 * 删除区域
 *
 * @param id
 */
export function deleteRegion(id: number): Promise<AjaxResult> {
    return request({
        url: '/qs/region/delete/' + id,
        method: 'delete',
    })
}

/**
 * 查询区域节点
 *
 * @param id
 */
export function queryForRegionTree(query: any): Promise<AjaxResult> {
    return request({
        url: '/qs/region/tree/list',
        method: 'get',
        params: query
    })
}

/**
 * 查询区域节点设备
 *
 * @param id
 */
export function queryRegionForDevice(): Promise<AjaxResult> {
    return request({
        url: '/qs/region/device/list',
        method: 'get',
    })
}

/**
 * 查询区域节点设备
 *
 * @param id
 */
export function queryForRegionQuery(query): Promise<AjaxResult<QsRegion[]>> {
    return request({
        url: '/qs/region/tree/query',
        method: 'get',
        params: query
    })
}

/**
 * 获取所属的行政区划下的行政区划
 *
 * @param parent
 */
export function getAllChild(parent: String): Promise<AjaxResult<QsRegion[]>> {
    return request({
        url: '/qs/region/base/child/list',
        method: 'get',
        params: parent
    })
}
