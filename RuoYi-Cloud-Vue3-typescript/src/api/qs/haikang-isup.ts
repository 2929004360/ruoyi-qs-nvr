import request from '@/utils/request'
import {HaikangIsupDevice} from "@/types/api";

// 查询海康isup设备列表
export function listHaiKangIsupDevice(): Promise<HaikangIsupDevice[]> {
    return request({
        url: '/haikangIsup/device/list',
        method: 'get',
    })
}