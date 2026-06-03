package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.StorageInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取存储能力请求
 */
public class GetStorageCapabilitiesRequest implements OnvifRequest {

    public static final String TAG = GetStorageCapabilitiesRequest.class.getSimpleName();

    private final StorageInfoListener listener;

    public GetStorageCapabilitiesRequest(StorageInfoListener listener) {
        this.listener = listener;
    }

    public StorageInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetStorageCapabilities xmlns=\"http://www.onvif.org/ver10/device/wsdl\" />";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STORAGE_CAPABILITIES;
    }
}
