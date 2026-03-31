package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 工作服过滤信息
*/
public class NET_CLOTHES_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 衣服颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int[]            emColors = new int[16];
    /**
     * emColors 数量
    */
    public int              nColorsCount;
    /**
     * 衣服状态,参见枚举定义 {@link NetSDKLib.EM_WORKCLOTHES_STATE}
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

    public NET_CLOTHES_FILTER() {
    }
}

