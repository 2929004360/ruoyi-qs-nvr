package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.MediaInfo;

/**
 * 媒体信息监听器
 */
public interface MediaInfoListener {
    /**
     * 收到媒体信息
     */
    void onMediaInfoReceived(OnvifDevice device, MediaInfo info);

    /**
     * 出错
     */
    void onError(OnvifDevice device, int errorCode, String errorMessage);
}

