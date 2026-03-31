package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryDevInfo NET_QUERY_PTZ_CURRENT_FOV_VALUE 类型接口输入参数
 * @date 2022/09/22 15:28:55
 */
public class NET_OUT_PTZ_CURRENT_FOV_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 当前倍率水平视场角单位0.01度，扩大100倍表示
	 */
    public int              nValue;
	/**
	 * 镜头最小水平视场角单位同nValue
	 */
    public int              nMinValue;
	/**
	 * 镜头最大水平视场角单位同nValue
	 */
    public int              nMaxValue;

	public NET_OUT_PTZ_CURRENT_FOV_VALUE() {
		this.dwSize = this.size();
	}
}

