package com.ruoyi.onvif.requests;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifType;

/**
 * ONVIF 查找录像请求
 *
 * @FileName FindRecordingsRequest
 * @Description
 **/
public class FindRecordingsRequest implements OnvifRequest {

    private final OnvifResponseListener listener;
    private final String scope;
    private final String maxMatches;
    private final String keepAliveTime;

    public FindRecordingsRequest(String scope, String maxMatches, String keepAliveTime, OnvifResponseListener listener) {
        this.scope = scope;
        this.maxMatches = maxMatches;
        this.keepAliveTime = keepAliveTime;
        this.listener = listener;
    }

    public FindRecordingsRequest(OnvifResponseListener listener) {
        this.scope = null;
        this.maxMatches = "10";
        this.keepAliveTime = "PT60S";
        this.listener = listener;
    }

    public OnvifResponseListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tse:FindRecordings xmlns:tse=\"http://www.onvif.org/ver10/search/wsdl\">");
        
        if (scope != null && !scope.isEmpty()) {
            sb.append("<tse:Scope>").append(scope).append("</tse:Scope>");
        }
        
        sb.append("<tse:MaxMatches>").append(maxMatches).append("</tse:MaxMatches>");
        sb.append("<tse:KeepAliveTime>").append(keepAliveTime).append("</tse:KeepAliveTime>");
        sb.append("</tse:FindRecordings>");
        
        return sb.toString();
    }

    @Override
    public OnvifType getType() {
        return OnvifType.FIND_RECORDINGS;
    }
}
