package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.NetworkInfoListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * 获取网络接口配置请求
 */
public class GetNetworkInterfacesRequest implements OnvifRequest {
    public static final String TAG = GetNetworkInterfacesRequest.class.getSimpleName();
    private final NetworkInfoListener listener;

    public GetNetworkInterfacesRequest(NetworkInfoListener listener) {
        this.listener = listener;
    }

    public NetworkInfoListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetNetworkInterfaces xmlns=\"http://www.onvif.org/ver10/device/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_NETWORK_INTERFACES;
    }
}

