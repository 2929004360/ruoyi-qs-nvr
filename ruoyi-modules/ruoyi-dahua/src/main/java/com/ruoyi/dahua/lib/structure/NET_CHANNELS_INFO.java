package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通道列表信息
 * @date 2021/09/02
 */
public class NET_CHANNELS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  通道编号
     */
    public int              nChannelNumber;
    /**
     *  通道关联的相位或跟随相位，相位编号从1开始
     */
    public int              nChannelPhase;
    /**
     *  通道所接灯组的类型 | 16 圆盘灯 | 17 左转箭头灯 | 18 直行箭头灯 | 19 右转箭头灯 | 20 掉头箭头灯 | 21 单个行人灯| 22 左转非机动车灯 | 23 直行非机动车灯 | 24 公交信号灯 | 25 入口行人灯，进口为实线有箭头线的斑马线区 | 26 出口行人灯，出口为虚线无箭头线的斑马线区 |
     */
    public int              nLampType;
    /**
     *  通道所接灯组的所在路口;0-单路口，1-短连接路口1，2-短连接路口
     */
    public int              nCrossType;
    /**
     *  进口方向 | 0 北 | 1 东北 | 2 东| 3 东南 | 4 南 | 5 西南 | 6 西 | 7 西北 |
     */
    public int              nRoadSectionDir;
    /**
     *  通道类型;1-其他，2-机动车，3-行人
     */
    public int              nChannelControlType;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

