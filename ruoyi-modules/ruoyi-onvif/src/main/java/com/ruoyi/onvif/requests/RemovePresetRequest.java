package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * ONVIF 删除预置点请求
 *
 * @FileName RemovePresetRequest
 * @Description
 **/
public class RemovePresetRequest implements OnvifRequest {

    private final String profileToken;
    private final String presetToken;
    private final OnvifResponseListener listener;

    public RemovePresetRequest(String profileToken, String presetToken, OnvifResponseListener listener) {
        this.profileToken = profileToken;
        this.presetToken = presetToken;
        this.listener = listener;
    }

    public OnvifResponseListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<RemovePreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                "<ProfileToken>" + profileToken + "</ProfileToken>" +
                "<PresetToken>" + presetToken + "</PresetToken>" +
                "</RemovePreset>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.REMOVE_PRESET;
    }
}
