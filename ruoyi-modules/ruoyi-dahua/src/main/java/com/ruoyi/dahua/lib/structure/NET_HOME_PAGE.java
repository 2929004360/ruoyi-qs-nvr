package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 主页配置选项
*/
public class NET_HOME_PAGE extends NetSDKLib.SdkStructure
{
    /**
     * 主菜单按钮是否可见
    */
    public int              bButtonMainMenuVisible;
    /**
     * 密码按钮是否可见
    */
    public int              bButtonPasswordVisible;
    /**
     * 二维码按钮是否可见
    */
    public int              bButtonQRCodeVisible;
    /**
     * 对讲按钮是否可见
    */
    public int              bButtonCallVisible;
    /**
     * 对讲按钮呼叫类型, 1：呼叫房间  2：呼叫管理机  3：呼叫固定房间  4：呼叫门铃
    */
    public int              nButtonCallType;

    public NET_HOME_PAGE() {
    }
}

