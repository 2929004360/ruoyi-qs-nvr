package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 分组人员统计--获取摘要信息 输出参数
*/
public class NET_OUT_NUMBERSTATGROUPSUMMARY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 摘要信息,参见结构体定义 {@link NET_NUMBERSTATGROUP_SUMMARY}
    */
    public NET_NUMBERSTATGROUP_SUMMARY stuSummary = new NET_NUMBERSTATGROUP_SUMMARY();

    public NET_OUT_NUMBERSTATGROUPSUMMARY_INFO() {
        this.dwSize = this.size();
    }
}

