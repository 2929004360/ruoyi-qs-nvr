import request from '@/utils/request'
import {OnvifDevice, WSDiscoveryDevice, WSOnvifDevice} from "@/types/api";
import {AjaxResult} from "@/types";

// 获取onvif设备列表
export function getOnvifDeviceList(): Promise<AjaxResult<WSDiscoveryDevice[]>> {
    return request({
        url: '/onvif/device/getOnvifDeviceList',
        method: 'get',
        timeout: 20000
    })
}

// 验证登录onvif设备
export function onvifLogin(data: WSOnvifDevice): Promise<AjaxResult<OnvifDevice>> {
    return request({
        url: '/onvif/device/login',
        method: 'post',
        data,
        timeout: 20000
    })
}