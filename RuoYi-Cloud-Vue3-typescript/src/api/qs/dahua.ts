import request from '@/utils/request'
import {DaHuaDevice} from "@/types/api/qs/dahua";

// 查询大华设备列表
export function listDaHusDevice(): Promise<DaHuaDevice[]> {
    return request({
        url: '/dahua/device/list',
        method: 'get',
    })
}