package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.NET_CFG_Color;

/**
 * @author 251823
 * @description
 * @date 2022/07/21 14:56:20
 */
public class CGF_MASKING_INFO extends NetSDKLib.SdkStructure {
    /**
     *  隐私遮挡开关标志 true开，false关
     */
    public int              bEnable;
    /**
     *  云台方向与放大倍数
     */
    public PTZ_SPEED        stuPosition;
    /**
     *  形状类型,参考枚举{ @link EM_SHAPE_TYPE}
     */
    public int              stuShapeType;
    /**
     *  矩形区域, 坐标取值0~8192
     */
    public DH_RECT_REGION   stuRect;
    /**
     *  多边形顶点数
     */
    public int              nPolygonPoint;
    /**
     *  多边形顶点坐标,坐标取值0~8192
     */
    public NetSDKLib.CFG_POLYGON[] stuPolygon = (NetSDKLib.CFG_POLYGON[]) new NetSDKLib.CFG_POLYGON().toArray(NetSDKLib.MAX_POLYGON_NUM);
    /**
     *  遮挡块色
     */
    public NET_CFG_Color stuColor;
    /**
     *  遮挡块马赛克类型，马赛克时遮挡块色字段无效,参考枚举{ @link EM_MOSAIC_TYPE}
     */
    public int              stuMosaicType;
    /**
     *  球机水平视场角, 范围[0.0, 360.0]，单位：度
     */
    public double           dViewAngle;

	public CGF_MASKING_INFO() {
		for (int i = 0; i < stuPolygon.length; i++) {
			stuPolygon[i] = new NetSDKLib.CFG_POLYGON();
		}
	}
}

