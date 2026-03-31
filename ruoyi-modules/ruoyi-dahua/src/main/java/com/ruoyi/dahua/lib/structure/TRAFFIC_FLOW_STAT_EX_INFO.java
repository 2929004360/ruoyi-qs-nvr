package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 路口车道统计信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_FLOW_STAT_EX_INFO extends NetSDKLib.SdkStructure {
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
     * 检测断面号
     */
    public int              nSecNo;
    /**
     * 0~7车道的车道统计信息
     */
    public TRAFFIC_FLOW_LANE_INFO[] stuLaneInfo = (TRAFFIC_FLOW_LANE_INFO[]) new TRAFFIC_FLOW_LANE_INFO().toArray(32);
    /**
     * 车道统计信息个数
     */
    public int              nLaneInfoNum;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[252];

    @Override
    public String toString() {
        String stuLaneInfos = "";
        if(nLaneInfoNum > 0){
            stuLaneInfos += "stuLaneInfo[0] = ";
            stuLaneInfos += stuLaneInfo[0].toString();
        }
        for(int i = 1; i < nLaneInfoNum; i ++){
            stuLaneInfos += ",stuLaneInfo[" + i + "] = ";
            stuLaneInfos += stuLaneInfo[i].toString();
        }
        return "TRAFFIC_FLOW_STAT_EX_INFO{" +
                "nAreaNo=" + nAreaNo +
                ", nJunNo=" + nJunNo +
                ", nDirNo=" + nDirNo +
                ", nSecNo=" + nSecNo +
                ", stuLaneInfo=" + stuLaneInfos +
                ", nLaneInfoNum=" + nLaneInfoNum +
                '}';
    }
}

