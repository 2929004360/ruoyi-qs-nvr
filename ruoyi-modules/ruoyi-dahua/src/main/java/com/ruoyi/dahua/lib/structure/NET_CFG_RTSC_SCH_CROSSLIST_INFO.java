package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 路口数据列表配置,对应配置项NET_EM_CFG_RTSC_SCH_CROSSLIST
 * @date 2021/09/02
 */
public class NET_CFG_RTSC_SCH_CROSSLIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  路口数据列表信息个数
     */
    public int              nCrossListInfoNum;
    /**
     *  路口数据列表信息
     */
    public NET_CROSS_LIST_INFO[] stuCrossListInfo = (NET_CROSS_LIST_INFO[]) new NET_CROSS_LIST_INFO().toArray(2);

    public NET_CFG_RTSC_SCH_CROSSLIST_INFO(){
        this.dwSize = this.size();
    }
}

