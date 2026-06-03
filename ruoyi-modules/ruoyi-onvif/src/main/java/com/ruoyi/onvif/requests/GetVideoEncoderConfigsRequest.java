package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取视频编码器配置请求
 */
public class GetVideoEncoderConfigsRequest implements OnvifRequest {
    public static final String TAG = GetVideoEncoderConfigsRequest.class.getSimpleName();
    private final MediaInfoListener listener;

    public GetVideoEncoderConfigsRequest(MediaInfoListener listener) {
        this.listener = listener;
    }

    public MediaInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetVideoEncoderConfigurations xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_VIDEO_ENCODER_CONFIGS;
    }
}

