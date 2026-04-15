import {QsGroup} from "@/types/api";
import {AjaxResult} from "@/types";
import request from "@/utils/request";

/**
 * 添加分组
 *
 * @param data
 */
export function addGroup(data: QsGroup): Promise<AjaxResult> {
    return request({
        url: '/qs/group/add',
        method: 'post',
        data: data
    })
}

/**
 * 更新分组
 *
 * @param data
 */
export function updateGroup(data: QsGroup): Promise<AjaxResult> {
    return request({
        url: '/qs/group/update',
        method: 'post',
        data: data
    })
}

/**
 * 删除分组
 *
 * @param id
 */
export function deleteGroup(id: number): Promise<AjaxResult> {
    return request({
        url: '/qs/group/delete/' + id,
        method: 'delete',
    })
}

// 查询分组
export function queryForGroupQuery(query): Promise<AjaxResult> {
    return request({
        url: `/qs/group/tree/query`,
        method: 'get',
        params: query
    })
}

// 查询分组节点
export function queryForGroupTree(query): Promise<AjaxResult> {
    return request({
        url: `/qs/group/tree/list`,
        method: 'get',
        params: query
    })
}

// 查询分组节点设备
export function queryGroupForDevice(): Promise<AjaxResult> {
    return request({
        url: `/qs/group/device/list`,
        method: 'get',
    })
}
