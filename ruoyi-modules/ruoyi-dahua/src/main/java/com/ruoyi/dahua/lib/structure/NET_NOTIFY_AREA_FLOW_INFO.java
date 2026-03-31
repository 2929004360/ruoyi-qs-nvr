package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域流量数据
*/
public class NET_NOTIFY_AREA_FLOW_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 各个区域流量统计信息,参见结构体定义 {@link NET_FLOW_PACKET_INFO}
    */
    public NET_FLOW_PACKET_INFO[] stuFlowPackets = new NET_FLOW_PACKET_INFO[64];
    /**
     * 各个区域流量统计信息个数
    */
    public int              nFlowPacketsCount;
    /**
     * 区域行人/非机动车流量随帧信息,参见结构体定义 {@link NET_AREA_FLOW_INFO}
    */
    public NET_AREA_FLOW_INFO stuAreaFlowInfo = new NET_AREA_FLOW_INFO();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_NOTIFY_AREA_FLOW_INFO() {
        for(int i = 0; i < stuFlowPackets.length; i++){
            stuFlowPackets[i] = new NET_FLOW_PACKET_INFO();
        }
    }
}

