package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 隐私遮挡信息
 * @date 2022/07/21 16:59:51
 */
public class NET_PRIVACY_MASKING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 遮档块编号，从0开始
	 */
    public int              nIndex;
	/**
	 * 隐私遮挡开关标志 1 开，0关
	 */
    public int              nEnable;
	/**
	 * 每个遮挡块可命名
	 */
    public byte[]           szName = new byte[64];
	/**
	 * 形状类型为矩形，Rect项有效(默认),形状类型为多边形，此时Polygon有效
	 * {@link com.ruoyi.dahua.lib.enumeration.EM_PRIVACY_MASKING_TYPE}
	 */
    public int              emShapeType;
	/**
	 * 矩形区域,使用相对坐标体系，取值均为0-8192
	 */
    public NetSDKLib.DH_RECT stuRect = new  NetSDKLib.DH_RECT();
	/**
	 * 多边形顶点坐标，多边形顶点不超过64, 使用相对坐标体系，取值均为0-8192
	 */
    public NET_UINT_POINT[] stuPolygon = new NET_UINT_POINT[64];
	/**
	 * 多边形顶点坐标个数
	 */
    public int              nPointNum;
	/**
	 * 遮挡块颜色,下发时nMosaic为0则必填
	 */
    public NET_COLOR_RGBA   stuColor = new NET_COLOR_RGBA();
	/**
	 * 遮挡块马赛克类型，马赛克时颜色Color无效,具体值参考获取能力后NET_MOSAIC_MASKING_CAPS的nSupportMosaicType字段
	 */
    public int              nMosaic;
	/**
	 * 屏蔽倍率，度数扩大10倍表示
	 */
    public int              nShieldZoom;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[512];

	public NET_PRIVACY_MASKING_INFO() {
		for (int i = 0; i < stuPolygon.length; i++) {
			stuPolygon[i] = new NET_UINT_POINT();
		}
	}
}

