package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetIVSEventDetail接口入参
*/
public class NET_IN_GET_IVSEVENT_DETAIL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 待查询事件ID数量
    */
    public int              nIdNum;
    /**
     * 待查询事件ID列表
    */
    public int[]            nId = new int[128];
    /**
     * 回调函数,参见回调函数定义 {@link NetSDKLib.fNotifyIVSEventDetail}
    */
    public NetSDKLib.fNotifyIVSEventDetail cbNotifyIVSEventDetail;
    /**
     * 用户自定义参数
    */
    public Pointer          dwUser;

    public NET_IN_GET_IVSEVENT_DETAIL_INFO() {
        this.dwSize = this.size();
    }
}

