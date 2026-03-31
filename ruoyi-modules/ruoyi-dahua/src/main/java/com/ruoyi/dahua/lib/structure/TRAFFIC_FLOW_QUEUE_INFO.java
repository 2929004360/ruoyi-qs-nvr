package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 区域路口排队信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_FLOW_QUEUE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 检测器状态 0-正常 1-故障
     */
    public int              nDecStatus;
    /**
     * 区域号
     */
    public int              nAreaNo;
    /**
     * 路口号
     */
    public int              nJunNo;
    /**
     * 方向号，北东南西分别为0/1/2/3
     */
    public int              nDirNo;
    /**
     * 进出口类型，进口道 0，出口道 1
     */
    public int              nLaneDirType;
    /**
     * 车道信息个数
     */
    public int              nQueueInfoNum;
    /**
     * 0~7车道的信息
     */
    public TRAFFIC_QUEUE_INFO[] stuQueueInfo = (TRAFFIC_QUEUE_INFO[]) new TRAFFIC_QUEUE_INFO().toArray(32);
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[256];

    @Override
    public String toString() {

        String stuQueueInfos = "";
        if(nQueueInfoNum > 0){
            stuQueueInfos += "stuQueueInfo[0] = ";
            stuQueueInfos += stuQueueInfo[0].toString();
        }
        for(int i = 1; i < nQueueInfoNum; i ++){
            stuQueueInfos += ",stuQueueInfo[" + i + "] = ";
            stuQueueInfos += stuQueueInfo[i].toString();
        }
        return "TRAFFIC_FLOW_QUEUE_INFO{" +
                "nDecStatus=" + nDecStatus +
                ", nAreaNo=" + nAreaNo +
                ", nJunNo=" + nJunNo +
                ", nDirNo=" + nDirNo +
                ", nLaneDirType=" + nLaneDirType +
                ", nQueueInfoNum=" + nQueueInfoNum +
                ", stuQueueInfo=" + stuQueueInfos +
                '}';
    }
}

