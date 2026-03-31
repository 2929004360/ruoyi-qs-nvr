package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 雷达控制盒支持的协议能力信息
 * @date 2021/9/26
 */
public class NET_RADAR_PROTOCAL_CAP extends NetSDKLib.SdkStructure {
	 /**
     *  是否支持切换协议能力
     */
    public int              bSupport;
    /**
     *  支持的协议类型个数, 即szPtotoList 数组的有效元素个数
     */
    public int              nProtocalNum;
    /**
     *  雷达控制盒支持的协议列表
     */    
    public PtotoListByteArr[] szPtotoList = (PtotoListByteArr[])new PtotoListByteArr().toArray(10);
    /**
     *  经度，用整型传输，放大1000000倍，小数点后6位有效，不足6位用0补齐，例如120125400代表120.1254
     */
    public int              nLongitude;
    /**
     *  纬度，用整型传输，放大1000000倍，小数点后6位有效，不足6位用0补齐，例如120125400代表120.1254
     */
    public int              nLatitude;
    /**
     *  雷达地图朝向角，放大100倍，例如112897，实际是128.97度
     */
    public int              nAngle;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[1020];
}

