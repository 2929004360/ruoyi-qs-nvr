package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 区域模式统计信息
 * @date 2021/12/18
 */
public class NET_AREA_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
     *  区域名称
     */
    public byte[]           szName = new byte[32];
    /**
     *  该区域的总车位个数
     */
    public int              nCount;
    /**
     *  车位类型，参考{ @link EM_SPACE_TYPE}
     */
    public int              emSpaceType;
    /**
     *  区域剩余可用车位个数
     */
    public int              nRemainCnt;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[252];
}

