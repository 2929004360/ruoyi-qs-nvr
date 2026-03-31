package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 联动报警的工装库信息
*/
public class NET_LINK_GROUP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 联动报警的工装库ID
    */
    public byte[]           szGroupID = new byte[128];
    /**
     * 联动报警特征名称
    */
    public byte[]           szFeatureName = new byte[128];
    /**
     * 联动报警库相似度
    */
    public int              nSimilarity;
    /**
     * 样本属性, 0: 未知, 1: 正样本 2: 负样本
    */
    public int              nSampleAttributes;
    /**
     * 联动报警的工装库名称
    */
    public byte[]           szGroupName = new byte[128];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_LINK_GROUP_INFO() {
    }
}

