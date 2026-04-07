import request from '@/utils/request'
import {PullConfig, StreamContent} from "@/types/api";

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
