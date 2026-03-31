package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_PRIVACY_MASKING_TYPE;

/**
 * @author 251823
 * @description 纯色隐私遮挡块的能力集
 * @date 2022/07/21 13:40:56
 */
public class NET_COLOR_MASKING_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持纯色隐私遮挡块
	 */
    public int              bSupport;
	/**
	 * 系统支持的最大纯色块个数
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
	 * 是否支持遮挡块颜色设置
	 */
    public int              bSetColorSupport;
	/**
	 * 是否支持遮挡块颜色相互独立
	 */
    public int              bSetColorIndependent;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[128];

	public NET_COLOR_MASKING_CAPS() {
	}
}

