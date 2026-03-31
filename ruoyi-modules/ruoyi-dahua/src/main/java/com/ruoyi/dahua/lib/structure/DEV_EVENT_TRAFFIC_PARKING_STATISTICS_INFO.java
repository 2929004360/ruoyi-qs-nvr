package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_TRAFFIC_PARKING_STATISTICS（车位统计事件） 对应的数据描述信息
 * @date 2021/12/18
 */
public class DEV_EVENT_TRAFFIC_PARKING_STATISTICS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  0:脉冲
     */
    public int              nAction;
    /**
     *  通道号
     */
    public int              nChannel;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     *  时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     *  事件发生的时间
     */
    public NET_TIME_EX      UTC;
    /**
     *  事件ID
     */
    public int              nEventID;
    /**
     *  区域模式统计信息 区域;emStatisticsMode为 EM_STATISTICS_MODE_AREA 时有效
     */
    public NET_AREA_MODE_INFO[] stuAreaModeInfo = (NET_AREA_MODE_INFO[]) new NET_AREA_MODE_INFO().toArray(5);
    /**
     *  区域模式统计信息个数
     */
    public int              nAreaModeInfoNum;
    /**
     *  车位模式统计信息,每个元素表示一个车位；emStatisticsMode为 EM_STATISTICS_MODE_SPACE 时有效
     */
    public NET_SPACE_MODE_INFO[] stuSpaceModeInfo = (NET_SPACE_MODE_INFO[]) new NET_SPACE_MODE_INFO().toArray(99);
    /**
     *  车位模式统计信息个数
     */
    public int              nSpaceModeInfoNum;
    /**
     *  统计模式,参考{ @link EM_STATISTICS_MODE }
     */
    public int              emStatisticsMode;
    /**
     *  区域或车位更新信息
     */
    public NET_UPDATE_INFO[] stuUpdateInfo = (NET_UPDATE_INFO[]) new NET_UPDATE_INFO().toArray(99);
    /**
     *  区域或车位更新信息个数
     */
    public int              nUpdateInfoNum;
	/**
	 * 图片信息数组 ,结构体NET_IMAGE_INFO_EX2数组指针
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1008];
}

