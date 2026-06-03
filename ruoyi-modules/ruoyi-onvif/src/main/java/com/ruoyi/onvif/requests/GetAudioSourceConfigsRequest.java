package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取音频源配置请求
 */
public class GetAudioSourceConfigsRequest implements OnvifRequest {
    public static final String TAG = GetAudioSourceConfigsRequest.class.getSimpleName();
    private final MediaInfoListener listener;

    public GetAudioSourceConfigsRequest(MediaInfoListener listener) {
        this.listener = listener;
    }

    public MediaInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetAudioSourceConfigurations xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_AUDIO_SOURCE_CONFIGS;
    }
}

