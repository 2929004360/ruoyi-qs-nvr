package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 *
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class NET_POINT_PAIR_EX extends NetSDKLib.SdkStructure {
    /**
     *  标定场景序号，拼接后的全景画面可以分成多个场景进行标定
     */
    public int              nCaliSceneID;
    /**
     *  标定第一目时，球机转到和第一目场景近似的地方，记下此时球机中心点坐标
     */
    public PTZ_SPACE_UNIT   stuSlaveCenter = new PTZ_SPACE_UNIT();
    /**
     *  在该场景固定球机后，记录两个画面的像素点坐标对应关系 
     */
    public NET_POINT_INFO   stuPoint[] = (NET_POINT_INFO[]) new NET_POINT_INFO().toArray(16);
    /**
     *  stuPoint 有效个数
     */
    public int              nEffectiveNumber;
    /**
     *  天枢相机(枪球一体设备)，枪机具备变倍功能，智能场景恢复时，根据这个值调整倍率,范围[0,1]
     */
    public float            fMasterZoom;
    /**
     *  保留字段
     */
    public byte             byReserved[] = new byte[252];
}

