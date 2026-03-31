package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 分组人员统计--获取摘要信息 输入参数
*/
public class NET_IN_NUMBERSTATGROUPSUMMARY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * GroupID
    */
    public byte[]           szGroupID = new byte[64];

    public NET_IN_NUMBERSTATGROUPSUMMARY_INFO() {
        this.dwSize = this.size();
    }
}

