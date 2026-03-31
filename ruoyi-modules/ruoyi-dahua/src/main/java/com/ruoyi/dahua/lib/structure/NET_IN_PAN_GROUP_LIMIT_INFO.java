package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设置水平旋转边界值 输入参数
*/
public class NET_IN_PAN_GROUP_LIMIT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 正确通道号，范围 0~设备通道数
    */
    public int              nChannelID;
    /**
     * 旋转组内序号从1开始
    */
    public int              nIndex;
    /**
     * 水平旋转组限界类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_PAN_GROUP_LIMIT_MODE}
    */
    public int              emPanGroupLimitMode;

    public NET_IN_PAN_GROUP_LIMIT_INFO() {
        this.dwSize = this.size();
    }
}

