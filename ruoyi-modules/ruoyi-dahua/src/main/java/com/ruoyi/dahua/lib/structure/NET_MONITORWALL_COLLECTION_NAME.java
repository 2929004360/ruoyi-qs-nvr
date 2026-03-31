package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_DEVICE_NAME_LEN;

/**
 * 电视墙预案名称 拆分自{@link NET_IN_MONITORWALL_GET_COLL_SCHD}
 *
 * @author ： 47040
 * @since ： Created in 2020/10/19 9:28
 */
public class NET_MONITORWALL_COLLECTION_NAME extends NetSDKLib.SdkStructure {
    /**
     * 电视墙预案名称
     */
    public byte[]           collectionName = new byte[NET_DEVICE_NAME_LEN];
}

