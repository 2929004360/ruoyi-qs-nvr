 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

 /**
  * @author 291189
  * @version 1.0
  * @description 事件类型 EVENT_IVS_TRAFFIC_DRIVER_ABNORMAL (驾驶员异常报警事件) 对应的数据块描述信息
  * @date 2021/11/4
  */

public class DEV_EVENT_TRAFFIC_DRIVER_ABNORMAL_INFO extends NetSDKLib.SdkStructure {
/** 通道号*/
    public			int            nChannelID;
/** 事件动作, 0表示脉冲事件, 1表示持续性事件开始, 2表示持续性事件结束*/
    public			int            nAction;
/** GPS信息*/
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatusInfo = new NetSDKLib.NET_GPS_STATUS_INFO();
/** 事件发生的时间*/
    public			NET_TIME_EX    UTC = new NET_TIME_EX();
/** 违章关联视频FTP上传路径*/
    public			byte[]         szVideoPath = new byte[256];
/** 预留字节*/
    public			byte[]         bReserved = new byte[768];
}

