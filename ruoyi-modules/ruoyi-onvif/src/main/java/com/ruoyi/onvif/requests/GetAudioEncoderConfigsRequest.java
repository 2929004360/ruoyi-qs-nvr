package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取音频编码器配置请求
 */
public class GetAudioEncoderConfigsRequest implements OnvifRequest {
    public static final String TAG = GetAudioEncoderConfigsRequest.class.getSimpleName();
    private final MediaInfoListener listener;

    public GetAudioEncoderConfigsRequest(MediaInfoListener listener) {
        this.listener = listener;
    }

    public MediaInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetAudioEncoderConfigurations xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_AUDIO_ENCODER_CONFIGS;
    }
}

