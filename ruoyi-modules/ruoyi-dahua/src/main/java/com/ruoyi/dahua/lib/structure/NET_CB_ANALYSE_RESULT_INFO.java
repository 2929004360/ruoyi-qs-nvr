package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 二次录像分析实时结果订阅回调结果结构体
*/
public class NET_CB_ANALYSE_RESULT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 任务ID
    */
    public int              nTaskID;
    /**
     * 图片时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuPicTime = new NetSDKLib.NET_TIME();
    /**
     * 实际的事件个数
    */
    public int              nRetEventCount;
    /**
     * 录像二次分析事件信息,参见结构体定义 {@link NetSDKLib.NET_SECONDARY_ANALYSE_EVENT_INFO}
    */
    public NetSDKLib.NET_SECONDARY_ANALYSE_EVENT_INFO[] stuEventInfo = new NetSDKLib.NET_SECONDARY_ANALYSE_EVENT_INFO[8];
    /**
     * 用户数据
    */
    public Pointer          dwUser;
    /**
     * 视频源数据 标示视频源信息 对应addPollingTask中UserData字段。
    */
    public byte[]           szUserData = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[960];

    public NET_CB_ANALYSE_RESULT_INFO() {
        for(int i = 0; i < stuEventInfo.length; i++){
            stuEventInfo[i] = new NetSDKLib.NET_SECONDARY_ANALYSE_EVENT_INFO();
        }
    }
}

