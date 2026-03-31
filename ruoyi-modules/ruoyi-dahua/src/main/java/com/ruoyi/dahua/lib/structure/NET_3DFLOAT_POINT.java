package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 三维空间点类型
 * @origin autoTool
 * @date 2023/02/14 20:02:01
 */
public class NET_3DFLOAT_POINT extends NetSDKLib.SdkStructure {
	/**
	 * x坐标
	 */
    public float            fXPosition;
	/**
	 * y坐标
	 */
    public float            fYPosition;
	/**
	 * z坐标
	 */
    public float            fZPosition;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[28];

	public NET_3DFLOAT_POINT() {
	}

	@Override
	public String toString() {
		return "{" + "fXPosition=" + fXPosition + ", fYPosition=" + fYPosition + ", fZPosition=" + fZPosition + '}';
	}
}

