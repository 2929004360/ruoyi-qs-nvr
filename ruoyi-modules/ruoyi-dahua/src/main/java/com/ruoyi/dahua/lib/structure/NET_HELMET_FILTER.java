package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 安全帽过滤信息
*/
public class NET_HELMET_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 安全帽颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int[]            emColors = new int[16];
    /**
     * emColors 数量
    */
    public int              nColorsCount;
    /**
     * 安全帽状态,参见枚举定义 {@link NetSDKLib.EM_WORK_HELMET_STATE}
    */
    public int[]            emStates = new int[8];
    /**
     * emStates 个数
    */
    public int              nStatesCount;
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[512];

    public NET_HELMET_FILTER() {
    }
}

