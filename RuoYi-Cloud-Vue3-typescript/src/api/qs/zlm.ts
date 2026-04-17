import request from '@/utils/request'
import {MediaInfo, MediaServer, PullConfig, RTPServerParam, Snap, StreamContent} from "@/types/api";
import {AjaxResult} from "@/types";

// 拉流播放
export function streamPullPlay(data: PullConfig): Promise<AjaxResult<StreamContent>> {
    return request({
        url: '/zlm/streamPullPlay',
        method: 'post',
        data,
        timeout: 20000
    })
}

// 停止拉流播放
export function stopStreamPullPlay(data: PullConfig) {
    return request({
        url: '/zlm/stopStreamPullPlay',
        method: 'post',
        data,
        timeout: 20000
    })
}

// 获取截图
export function getSnap(data: Snap) {
    return request({
        url: '/zlm/getSnap',
        method: 'post',
        data,
        timeout: 20000
    })
}

// rtp播放
export function rtpPlay(data: RTPServerParam): Promise<AjaxResult<StreamContent>> {
    return request({
        url: '/zlm/rtpPlay',
        method: 'post',
        data,
        timeout: 20000
    })
}

// 停止rtp播放
export function stopRtpPlay(data: RTPServerParam) {
    return request({
        url: '/zlm/stopRtpPlay',
        method: 'post',
        data,
        timeout: 20000
    })
}


// 获取流媒体服务器列表
export function getMediaServerList(): Promise<AjaxResult<MediaServer[]>> {
    return request({
        url: '/zlm/list',
        method: 'get',
    })
}

// 移除流媒体服务
export function delMediaServer(id: string) {
    return request({
        url: '/zlm/delete/' + id,
        method: 'delete',
    })
}

// 保存流媒体服务
export function saveMediaServer(data: MediaServer) {
    return request({
        url: '/zlm/save',
        method: 'post',
        data
    })
}

// 测试流媒体服务
export function checkMediaServer(ip: string, port: number, secret: string, type: string) {
    return request({
        url: '/zlm/check',
        method: 'get',
        params: {
            ip,
            port,
            secret,
            type,
        }
    })
}

// 获取流媒体服务
export function getMediaServer(id: string): Promise<AjaxResult<MediaServer>> {
    return request({
        url: '/zlm/one/' + id,
        method: 'get',
    })
}

// 获取流信息
export function getMediaInfo(app: string, stream: string, mediaServerId: string): Promise<AjaxResult<MediaInfo>> {
    return request({
        url: '/zlm/media_info',
        method: 'get',
        params: {
            app,
            stream,
            mediaServerId,
        }
    })
}

// 加载文件形成播放地址
export function loadRecord(id: string): Promise<AjaxResult<StreamContent>> {
    return request({
        url: '/zlm/loadRecord/' + id,
        method: 'get',
    })
}

// 关闭流文件形成播放地址
export function closeStreams(id: string): Promise<AjaxResult> {
    return request({
        url: '/zlm/closeStreams/' + id,
        method: 'get',
    })
}

/**
 * 获取负载信息
 */
export function getMediaLoad(): Promise<AjaxResult> {
    return request({
        url: '/zlm/server/media_server/load',
        method: 'get',
    })
}

/**
 * 重启流媒体
 */
export function restartServer(id): Promise<AjaxResult> {
    return request({
        url: '/zlm/restartServer/' + id,
        method: 'get',
    })
}

/**
 * 获取所有在线媒体服务器
 */
export function getAllOnlineMediaServe(): Promise<AjaxResult> {
    return request({
        url: '/zlm/getAllOnlineMediaServe',
        method: 'get',
    })
}

/**
 * 生成推流地址
 */
export function getStreamPushAddress(id: number, callId: string): Promise<AjaxResult> {
    return request({
        url: '/zlm/getStreamPushAddress/' + id,
        method: 'get',
        params: {
            callId
        }
    })
}

/**
 * 推流播放
 */
export function streamPullPush(id: number): Promise<AjaxResult> {
    return request({
        url: '/zlm/streamPullPush',
        method: 'get',
        params: {
            id
        }
    })
}
