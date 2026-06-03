package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.StorageInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取存储状态请求
 */
public class GetStorageStateRequest implements OnvifRequest {

    public static final String TAG = GetStorageStateRequest.class.getSimpleName();

    private final StorageInfoListener listener;

    public GetStorageStateRequest(StorageInfoListener listener) {
        this.listener = listener;
    }

    public StorageInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetStorageState xmlns=\"http://www.onvif.org/ver10/device/wsdl\" />";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STORAGE_STATE;
    }
}
