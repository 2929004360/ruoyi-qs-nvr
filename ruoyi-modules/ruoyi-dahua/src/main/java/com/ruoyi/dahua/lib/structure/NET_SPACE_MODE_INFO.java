package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车位模式统计信息
 * @date 2021/12/18
 */
public class NET_SPACE_MODE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  车位编号
     */
    public byte[]           szParkNo = new byte[32];
    /**
     *  车位类型，参考{ @link EM_SPACE_TYPE}
     */
    public int              emSpaceType;
    /**
     *  该车位是否有车
     */
    public int              bUsed;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[256];
}

