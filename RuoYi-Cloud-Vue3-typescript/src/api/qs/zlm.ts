import request from '@/utils/request'
import {PullConfig, RTPServerParam, Snap, StreamContent} from "@/types/api";

// 拉流播放
export function streamPullPlay(data: PullConfig): Promise<StreamContent> {
    return request({
        url: '/zlm/streamPullPlay',
        method: 'post',
        data,
    })
}

// 停止拉流播放
export function stopStreamPullPlay(data: PullConfig) {
    return request({
        url: '/zlm/stopStreamPullPlay',
        method: 'post',
        data,
    })
}

// 获取截图
export function getSnap(data: Snap) {
    return request({
        url: '/zlm/getSnap',
        method: 'post',
        data,
    })
}

// rtp播放
export function rtpPlay(data: RTPServerParam) : Promise<StreamContent>{
    return request({
        url: '/zlm/rtpPlay',
        method: 'post',
        data,
    })
}

// 停止rtp播放
export function stopRtpPlay(data: RTPServerParam) {
    return request({
        url: '/zlm/stopRtpPlay',
        method: 'post',
        data,
    })
}