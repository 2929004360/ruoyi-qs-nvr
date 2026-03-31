package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.POINTCOORDINATE;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_TRAFFIC_PARKING_RULE_INFO extends SdkStructure {
	/**
     * 检测区域
     */
    public POINTCOORDINATE[] stuDetectRegion = (POINTCOORDINATE[]) new POINTCOORDINATE().toArray(20);
    /**
     * 检测区域顶点数
     */
    public int              nDetectRegionPoint;
    public int              nLaneNumber;                          // 车道编号 与场景中的车道号对应
    public int              nDelay;                               // 检测到报警发生到开始上报的时间,单位：秒 范围1~65535
    public int              nReportTimes;                         // 上报次数 1~255
    public int              nParkingAllowedTime;                  // 允许停车时间 单位：秒，范围1~65535
    public int              nSensitivity;                         // 灵敏度,范围[1,10],灵敏度越高越容易检测
    public int              nParkingNumThreshold;                 // 停车数阈值，画面中停车数量大于此值时不报警 取值1-128
    public int              bZoomEnable;                          // 变倍抓拍，违停球做规则判断时，默认会变倍
    public int              bSnapMotorcycle;                      // 是否抓拍摩托车
    public int              nDectRegionNumber;                    // 检测区域号 与场景中的检测区域编号对应
    public int              nShadeSnap;                           // 遮挡抓拍，默认为0 0：不支持1：支持
    public int              nControlMoreAlerts;                   // 是否开启抑制同一个目标重复多报功能，0：不开启该功能，1：开启功能
    public int              nReduceUnderreporting;                // 是否开启减少id跳变导致的漏报功能，0：不开启该功能，1：开启功能
    public byte[]           byReserved = new byte[4096];          // 保留字节
}

