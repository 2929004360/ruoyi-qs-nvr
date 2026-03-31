package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 云台水平旋转组扫描配置 NET_EM_CFG_PTZ_HORIZONTAL_ROTATION_SCAN
*/
public class NET_CFG_HORIZONTAL_ROTATION_GROUP_SCAN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 云台水平旋转组限制信息,参见结构体定义 {@link NET_CFG_HORIZONTAL_ROTATION_LIMIT_INFO}
    */
    public NET_CFG_HORIZONTAL_ROTATION_LIMIT_INFO stuHorizontalRotationInfo = new NET_CFG_HORIZONTAL_ROTATION_LIMIT_INFO();

    public NET_CFG_HORIZONTAL_ROTATION_GROUP_SCAN_INFO() {
        this.dwSize = this.size();
    }
}

