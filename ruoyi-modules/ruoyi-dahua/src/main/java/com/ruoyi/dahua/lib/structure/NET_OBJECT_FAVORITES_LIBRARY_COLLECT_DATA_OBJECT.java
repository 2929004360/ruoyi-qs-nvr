package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标信息
*/
public class NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA_OBJECT extends NetSDKLib.SdkStructure
{
    /**
     * 唯一记录ID, 用于关联历史库
    */
    public int              nUID;
    /**
     * 目标类型: 1:人体, 2:机动车, 3:非机动车, 4:动物
    */
    public int              nObjectType;
    /**
     * 目标上报时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuObjectTime = new NetSDKLib.NET_TIME();
    /**
     * 目标缩略图URL
    */
    public byte[]           szObjectUrl = new byte[256];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[1024];

    public NET_OBJECT_FAVORITES_LIBRARY_COLLECT_DATA_OBJECT() {
    }
}

