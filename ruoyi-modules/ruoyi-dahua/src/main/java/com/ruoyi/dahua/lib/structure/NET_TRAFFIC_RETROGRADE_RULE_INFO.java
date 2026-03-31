package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.DH_POINT;
import com.ruoyi.dahua.lib.NetSDKLib.POINTCOORDINATE;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_TRAFFIC_RETROGRADE_RULE_INFO extends SdkStructure {
	/**
     * 检测区域
     */
    public POINTCOORDINATE[] stuDetectRegion = (POINTCOORDINATE[]) new POINTCOORDINATE().toArray(20);
    /**
     * 检测区域顶点数
     */
    public int              nDetectRegionPoint;
    public byte[]           byReserved1 = new byte[4];            // 字节对齐
	/**
	 * 起点，从起点到终点是正向行驶，反过来是逆行，点的坐标归一化到[0,8192)区间。
	 */
    public	DH_POINT         stuDirectionStart;
	/**
	 * 终点，从起点到终点是正向行驶，反过来是逆行，点的坐标归一化到[0,8192)区间。 
	 */
    public	DH_POINT         stuDirectionEnd;
    public int              nMinDuration;                         // 最短触发时间,单位：秒
    public int              nLaneNumber;                          // 车道编号 与场景中的车道号对应
    public int              bLegal;                               // 逆行是否违章
    public int              nPositionDistinctness;                // 抓拍位置变化区分度
    public int              nSensitivity;                         // 灵敏度，值越小灵敏度越低。取决于方向夹角，取值1-10
    public int              bZoomEnable;                          // 变倍抓拍，违停球做规则判断时，默认会变倍
    public int              bSnapMotorcycle;                      // 是否抓拍摩托车
    public int              nSnapNonMotor;                        // 是否抓拍非机动车
    public int              nSnapNoPlateMotor;                    // 是否抓拍无牌机动车
    public int              emSnapPlateMotor;                     // 是否抓拍机动车(NET_EM_SNAP_PLATEMOTOR_TYPE)
    public int              nSnapBicycle;                         // 是否抓拍自行车
    public int              nDurationTime;                        // 事件检测模式下，逆行时间超过这个值后开始报警，单位:秒，范围0-3600
    public NET_CFG_SIZEFILTER_INFO stuSizeFileter;                // 规则特定的尺寸过滤器
    public int              bSizeFileter;                         // 规则特定的尺寸过滤器是否有效
    public int              nReverseDisplacement;                 // 逆行位移，默认1024坐标系， 范围是1-1023 单位是像素， 若无该字段或该字段配置为0时，算法设置默认值
    public byte[]           byReserved = new byte[4096];          // 保留字节
}

