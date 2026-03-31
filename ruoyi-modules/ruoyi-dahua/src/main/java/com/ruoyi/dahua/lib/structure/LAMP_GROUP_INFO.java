package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯组灯色信息
 * @date 2021/09/01
 */
public class LAMP_GROUP_INFO extends NetSDKLib.SdkStructure {
	/**
     *  灯组编号
     */
    public int              nLampNo;
    /**
     *  灯组类型，参考{ @link EM_LAMP_TYPE }
     */
    public int              emLampType;
    /**
     *  信号灯组灯色剩余时间，整数，单位为秒（s）
     */
    public int              nRemainTime;
    /**
     *  灯组灯色个数
     */
    public int              nLampColorCount;
    /**
     *  灯组灯色 0：无灯1：灭灯2：亮灯3：闪烁 ，参考{ @link EM_LAMP_COLOR}
     */
    public int[]            nLampColor = new int[4];
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

