package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import java.io.UnsupportedEncodingException;

/**
 * @author ： 260611
 * @description ： 交通路口车道统计事件 (对应 DH_ALARM_TRAFFIC_FLOW_STAT)
 * @since ： Created in 2022/03/10 10:12
 */

public class ALARM_TRAFFIC_FLOW_STAT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 事件动作 0:脉冲
     */
    public int              nAction;
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
     */
    public int              PTS;
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 序号
     */
    public int              nSequence;
    /**
     * 流量状态数量
     */
    public int              nStateNum;
    /**
     * 流量状态, 每个车道对应数组中一个元素
     */
    public NET_TRAFFIC_FLOW_STATE[] stuStates = (NET_TRAFFIC_FLOW_STATE[]) new NET_TRAFFIC_FLOW_STATE().toArray(8);
    /**
     * 静止车辆数，当前时刻检测范围内车速小于某个阀值的车辆数，单位：辆
     */
    public int              nStopVehiclenum;
    /**
     * 车辆总数，当前时刻检测范围内检测到的所有车道内的车辆总数，单位：辆
     */
    public int              nDetectionAreaVehicleNum;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[1024];

    @Override
    public String toString() {
        String stuStatesStr = "";
        if(stuStates.length > 0){
            stuStatesStr += "stuStates[0] = ";
            stuStatesStr += stuStates[0].toString();
        }
        for(int i = 1; i < stuStates.length; i ++){
            stuStatesStr += ",stuStates[" + i + "] = ";
            stuStatesStr += stuStates[i].toString();
        }
        try {
            return "ALARM_TRAFFIC_FLOW_STAT_INFO{" +
                    "nAction=" + nAction +
                    ", nChannelID=" + nChannelID +
                    ", szName UTF-8=" + new String(szName,"UTF-8").trim() +
                    ", szName GBK=" + new String(szName,"GBK").trim() +
                    ", PTS=" + PTS +
                    ", nEventID=" + nEventID +
                    ", stuUTC=" + stuUTC.toStringTime() +
                    ", nSequence=" + nSequence +
                    ", nStateNum=" + nStateNum +
                    ", stuStates=" + stuStatesStr +
                    ", nStopVehiclenum=" + nStopVehiclenum +
                    ", nDetectionAreaVehicleNum=" + nDetectionAreaVehicleNum +
                    '}';
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

