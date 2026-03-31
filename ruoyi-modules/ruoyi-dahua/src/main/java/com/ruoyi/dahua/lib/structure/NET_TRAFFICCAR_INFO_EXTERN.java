package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 交通车辆信息扩展
 * @origin autoTool
 * @date 2023/07/13 16:22:13
 */
public class NET_TRAFFICCAR_INFO_EXTERN extends NetSDKLib.SdkStructure {
	/**
	 * 记录集装箱编号个数
	 */
    public int              nContainerNumber;
	/**
	 * 记录集装箱编号的文本内容数量
	 */
    public int              nContainerTextNum;
	/**
	 * 记录集装箱编号的文本内容
	 */
    public byte[]           szContainerText = new byte[8 * 64];
	/**
	 * dAverageSpeed字段是否有效
	 */
    public int              bAverageSpeed;
	/**
	 * dSpaceOcpRatio字段是否有效
	 */
    public int              bSpaceOcpRatio;
	/**
	 * 平均车速，单位km/h -1表示无车通过，0表示拥堵
	 */
    public double           dAverageSpeed;
	/**
	 * 空间占有率，即按百分率计量的车辆长度总和除以时间间隔内车辆平均行驶距离
	 */
    public double           dSpaceOcpRatio;
	/**
	 表示集装箱的类型，具体取值以算法输出为准
	 */
    public			int            nContainerType;
    /**
     * 字节对齐
    */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 车牌所属国家的省、州等地区名
    */
    public byte[]           szProvince = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1952];

	public NET_TRAFFICCAR_INFO_EXTERN() {
	}
}

