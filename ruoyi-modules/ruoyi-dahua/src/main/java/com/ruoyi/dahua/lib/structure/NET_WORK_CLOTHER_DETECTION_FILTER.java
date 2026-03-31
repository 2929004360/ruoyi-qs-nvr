package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 作业管控查询过滤条件
*/
public class NET_WORK_CLOTHER_DETECTION_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 安全帽过滤信息,参见结构体定义 {@link NET_HELMET_FILTER}
    */
    public NET_HELMET_FILTER stuHelmetFilter = new NET_HELMET_FILTER();
    /**
     * 工作服过滤信息,参见结构体定义 {@link NET_CLOTHES_FILTER}
    */
    public NET_CLOTHES_FILTER stuClothesFilter = new NET_CLOTHES_FILTER();
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[1024];

    public NET_WORK_CLOTHER_DETECTION_FILTER() {
    }
}

