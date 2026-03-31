package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  物体在雷达坐标系中的信息 
* @date 2022/05/30 14:16:15
*/
public class NET_EVENT_RADAR_INFO extends NetSDKLib.SdkStructure {
/** 
X轴坐标(横向距离)，单位：米
*/
    public			float          fCoordinateX;
/** 
Y轴坐标（纵向距离），单位：米
*/
    public			float          fCoordinateY;
    /**
     * 雷达目标横向加速度ax, 横向指设备视角的右手方向; 数据为正表示车辆加速, 数据为负表示车辆减速; 单位为m/s2
    */
    public float            fAccelerationX;
    /**
     * 雷达目标纵向加速度ay，纵向指设备视角的正前方向；数据为正表示车辆加速，数据为负表示车辆减速；单位为m/s2
    */
    public float            fAccelerationY;
/** 
预留字节
*/
    public			byte[]         bReserved = new byte[16];

public			NET_EVENT_RADAR_INFO(){
}
}

