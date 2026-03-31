package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 联动信息，保存其他设备传输的信息
 * @date 2022/07/22 11:28:18
 */
public class NET_LINK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 联动物体ID
	 */
    public int              nLinkObjectID;
	/**
	 * 联动事件ID
	 */
    public int              nLinkEventID;
	/**
	 * 联动物体速度，单位米/秒，扩大100倍
	 */
    public int              nSpeedValue;
	/**
	 * 报警类型, 第0bit位表示是否超速(1表示超速,0表示未超速), 第1bit位表示是否AIS匹配, 第2bit位表示是否禁行, 第3bit位表示是否逆行
	 */
    public int              nAlarmType;
	/**
	 * 联动物体经度，(单位是百万分之度,范围0-360度)如东经120.178274度表示为300178274
	 */
    public int              nLongitude;
	/**
	 * 联动物体纬度，(单位是百万分之度,范围0-180度)如北纬30.183382度表示为120183382 经纬度的具体转换方式可以参考结构体
	 * NET_WIFI_GPS_INFO 中的注释
	 */
    public int              nLatitude;
	/**
	 * null
	 */
    public int              nDistance;
	/**
	 * 跟踪物体类型
	 */
    public byte[]           szObjectType = new byte[16];
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[212];

	public NET_LINK_INFO() {
	}
}

