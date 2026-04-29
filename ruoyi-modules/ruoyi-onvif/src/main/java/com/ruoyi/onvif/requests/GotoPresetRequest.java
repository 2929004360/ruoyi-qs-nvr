package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * ONVIF 调用预置点请求
 *
 * @FileName GotoPresetRequest
 * @Description
 **/
public class GotoPresetRequest implements OnvifRequest {

    private final String profileToken;
    private final String presetToken;
    private final float speed;
    private final OnvifResponseListener listener;

    public GotoPresetRequest(String profileToken, String presetToken, float speed, OnvifResponseListener listener) {
        this.profileToken = profileToken;
        this.presetToken = presetToken;
        this.speed = speed;
        this.listener = listener;
    }

    public OnvifResponseListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GotoPreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                "<ProfileToken>" + profileToken + "</ProfileToken>" +
                "<PresetToken>" + presetToken + "</PresetToken>" +
                "<Speed>" +
                "<PanTilt xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + speed + "\" y=\"" + speed + "\"/>" +
                "<Zoom xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + speed + "\"/>" +
                "</Speed>" +
                "</GotoPreset>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GOTO_PRESET;
    }
}
