package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取视频源配置请求
 */
public class GetVideoSourceConfigsRequest implements OnvifRequest {
    public static final String TAG = GetVideoSourceConfigsRequest.class.getSimpleName();
    private final MediaInfoListener listener;

    public GetVideoSourceConfigsRequest(MediaInfoListener listener) {
        this.listener = listener;
    }

    public MediaInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetVideoSourceConfigurations xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_VIDEO_SOURCE_CONFIGS;
    }
}

