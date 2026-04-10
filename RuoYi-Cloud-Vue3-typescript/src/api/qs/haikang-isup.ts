import request from '@/utils/request'
import {HaikangIsupDevice} from "@/types/api";
import {AjaxResult} from "@/types";

// 查询海康isup设备列表
export function listHaiKangIsupDevice(): Promise<AjaxResult<HaikangIsupDevice[]>> {
    return request({
        url: '/haikangIsup/device/list',
        method: 'get',
    })
}