package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.StorageInfo;

/**
 * 存储信息监听器
 */
public interface StorageInfoListener {

    void onStorageInfoReceived(OnvifDevice device, StorageInfo storageInfo);

    void onError(OnvifDevice device, int errorCode, String errorMessage);
}
