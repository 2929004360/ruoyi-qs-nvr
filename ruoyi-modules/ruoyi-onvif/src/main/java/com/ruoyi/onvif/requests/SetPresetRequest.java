package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * ONVIF 设置预置点请求
 *
 * @FileName SetPresetRequest
 * @Description
 **/
public class SetPresetRequest implements OnvifRequest {

    private final String profileToken;
    private final String presetToken;
    private final String presetName;
    private final OnvifResponseListener listener;

    public SetPresetRequest(String profileToken, String presetToken, String presetName, OnvifResponseListener listener) {
        this.profileToken = profileToken;
        this.presetToken = presetToken;
        this.presetName = presetName;
        this.listener = listener;
    }

    public OnvifResponseListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<SetPreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">");
        sb.append("<ProfileToken>").append(profileToken).append("</ProfileToken>");
        if (presetToken != null && !presetToken.isEmpty()) {
            sb.append("<PresetToken>").append(presetToken).append("</PresetToken>");
        }
        if (presetName != null && !presetName.isEmpty()) {
            sb.append("<PresetName>").append(presetName).append("</PresetName>");
        }
        sb.append("</SetPreset>");
        return sb.toString();
    }

    @Override
    public OnvifType getType() {
        return OnvifType.SET_PRESET;
    }
}
