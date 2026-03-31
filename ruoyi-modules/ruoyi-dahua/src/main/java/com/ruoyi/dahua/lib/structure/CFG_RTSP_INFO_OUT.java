package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  RTSP的配置 
* @date 2022/12/15 17:00:18
*/
public class CFG_RTSP_INFO_OUT extends NetSDKLib.SdkStructure {
    public			int            nStructSize;
/** 
整个功能是否使能
*/
    public			int            bEnable;
/** 
RTSP服务端口
*/
    public			int            nPort;
/** 
RTP起始端口
*/
    public			int            nRtpStartPort;
/** 
RTP结束端口
*/
    public			int            nRtpEndPort;
/** 
RtspOverHttp使能
*/
    public			int            bHttpEnable;
/** 
RtspOverHttp端口
*/
    public			int            nHttpPort;

    @Override
    public String toString() {
        return "CFG_RTSP_INFO_OUT{" +
                "nStructSize=" + nStructSize +
                ", bEnable=" + bEnable +
                ", nPort=" + nPort +
                ", nRtpStartPort=" + nRtpStartPort +
                ", nRtpEndPort=" + nRtpEndPort +
                ", bHttpEnable=" + bHttpEnable +
                ", nHttpPort=" + nHttpPort +
                '}';
    }

    public CFG_RTSP_INFO_OUT(){
        this.nStructSize=size();
}
}

