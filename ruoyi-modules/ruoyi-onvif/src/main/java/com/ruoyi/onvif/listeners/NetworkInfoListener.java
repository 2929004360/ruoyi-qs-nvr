package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.NetworkInfo;

/**
 * 网络信息监听器
 */
public interface NetworkInfoListener {
    /**
     * 收到网络信息
     */
    void onNetworkInfoReceived(OnvifDevice device, NetworkInfo info);

    /**
     * 出错
     */
    void onError(OnvifDevice device, int errorCode, String errorMessage);
}

