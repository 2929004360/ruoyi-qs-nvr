package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 视频丢帧检测
 * @date 2022/7/21 16:21
 */
public class NET_VIDEO_LOSS_FRAME_DETECTION_RESULT extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public int              nValue;                               // 检测结果量化值
    public int              emState;                              // 检测结果状态 NET_STATE_TYPE
    public int              nDuration;                            // 状态持续时间，单位:秒
    public byte[]           szPicUrl = new byte[256];             // 异常检测结果图片地址

    public NET_VIDEO_LOSS_FRAME_DETECTION_RESULT(){
        this.dwSize=this.size();
    }
}

