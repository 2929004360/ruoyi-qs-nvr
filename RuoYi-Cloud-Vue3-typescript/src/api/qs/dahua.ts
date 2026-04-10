import request from '@/utils/request'
import {DaHuaDevice} from "@/types/api/qs/dahua";
import {AjaxResult} from "@/types";

// 查询大华设备列表
export function listDaHusDevice(): Promise<AjaxResult<DaHuaDevice[]>> {
    return request({
        url: '/dahua/device/list',
        method: 'get',
    })
}