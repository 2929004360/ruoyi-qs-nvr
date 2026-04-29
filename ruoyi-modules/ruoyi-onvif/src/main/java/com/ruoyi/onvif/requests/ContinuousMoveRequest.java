package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * ONVIF 云台连续移动请求
 *
 * @FileName ContinuousMoveRequest
 * @Description
 **/
public class ContinuousMoveRequest implements OnvifRequest {

    private final String profileToken;
    private final float pan;     // 水平移动速度 (-1 到 1)
    private final float tilt;    // 垂直移动速度 (-1 到 1)
    private final float zoom;    // 变倍速度 (-1 到 1)
    private final OnvifResponseListener listener;

    public ContinuousMoveRequest(String profileToken, float pan, float tilt, float zoom, OnvifResponseListener listener) {
        this.profileToken = profileToken;
        this.pan = pan;
        this.tilt = tilt;
        this.zoom = zoom;
        this.listener = listener;
    }

    public OnvifResponseListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<ContinuousMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                "<ProfileToken>" + profileToken + "</ProfileToken>" +
                "<Velocity>" +
                "<PanTilt xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + pan + "\" y=\"" + tilt + "\"/>" +
                "<Zoom xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + zoom + "\"/>" +
                "</Velocity>" +
                "</ContinuousMove>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.CONTINUOUS_MOVE;
    }
}
