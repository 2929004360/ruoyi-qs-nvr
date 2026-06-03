package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取视频输出配置请求
 */
public class GetVideoOutputConfigsRequest implements OnvifRequest {
    public static final String TAG = GetVideoOutputConfigsRequest.class.getSimpleName();
    private final MediaInfoListener listener;

    public GetVideoOutputConfigsRequest(MediaInfoListener listener) {
        this.listener = listener;
    }

    public MediaInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetVideoOutputConfigurations xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_VIDEO_OUTPUT_CONFIGS;
    }
}

