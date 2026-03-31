package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SecurityGetCaps接口出参
*/
public class NET_OUT_SECURITY_GETCAPS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 返回支持类型
    */
    public int              nTypesRealNum;
    /**
     * 0	车牌允许名单, 1	车牌禁止名单, 2	车牌禁止和允许名单, 3	对讲通信录, 4	自定义密码, 5	卡片记录集, 6	门禁目标信息, 7	门禁信息, 8	对讲IPC信息, 9	对讲人员信息, 10	公共密码记录集, 11	运维信息, 13	预置点信息, 14	管理机设备管理信息
    */
    public int[]            nSupportTypes = new int[32];

    public NET_OUT_SECURITY_GETCAPS_INFO() {
        this.dwSize = this.size();
    }
}

