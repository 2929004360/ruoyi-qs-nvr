package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description Area状态信息
 * @date 2023/03/15 21:47:17
 */
public class NET_AREAS_INFO extends NetSDKLib.SdkStructure {
	/** 
	子系统当前的布撤防模式
	"T" : Total布防/外出布防模式
	"p1" : Partial1布防/在家布防模式
	"p2" : Partial2布防/自定义模式
	"P" : Partial1+2布防
	"t" : 强制布防
	"D" : 撤防
	*/
    public byte[]           szMode = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[32];

	public NET_AREAS_INFO() {
	}
}

