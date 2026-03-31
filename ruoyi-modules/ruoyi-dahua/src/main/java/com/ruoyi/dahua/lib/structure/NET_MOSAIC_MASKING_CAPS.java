package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_PRIVACY_MASKING_TYPE;

/**
 * @author 251823
 * @description 马赛克隐私遮挡块的能力集
 * @date 2022/07/21 13:40:56
 */
public class NET_MOSAIC_MASKING_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持马赛克隐私遮挡块
	 */
    public int              bSupport;
	/**
	 * 系统支持的最大马赛克遮挡块个数
	 */
    public int              nMaxNum;
	/**
	 * 支持的遮挡块形状个数
	 */
    public int              nSupportTypeNum;
	/**
	 * 支持的遮挡块形状,数组int值,参考枚举{@link EM_PRIVACY_MASKING_TYPE}
	 */
    public int[]            emSupportType = new int[8];
	/**
	 * 系统支持的多边形遮挡块最大顶点数（SupportType支持"Polygon"时该能力有效）
	 */
    public int              nMaxPolygonPoints;
	/**
	 * 支持的马赛克类型个数
	 */
    public int              nSupportMosaicTypeNum;
	/**
	 * 支持的马赛克类型(SetMosaicSupport为true时该能力有效，没有该项配置时默认支持24x24大小马赛克)
	 */
    public int[]            nSupportMosaicType = new int[8];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[128];

	public NET_MOSAIC_MASKING_CAPS() {
	}
}

