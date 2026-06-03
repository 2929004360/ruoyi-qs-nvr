package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.StorageInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取存储配置请求
 */
public class GetStorageConfigurationsRequest implements OnvifRequest {

    public static final String TAG = GetStorageConfigurationsRequest.class.getSimpleName();

    private final StorageInfoListener listener;

    public GetStorageConfigurationsRequest(StorageInfoListener listener) {
        this.listener = listener;
    }

    public StorageInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetStorageConfigurations xmlns=\"http://www.onvif.org/ver10/device/wsdl\" />";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STORAGE_CONFIGURATIONS;
    }
}
