package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 窗口轮巡控制输入参数, 对应NET_SPLIT_OPERATE_SET_TOUR
*/
public class NET_IN_SPLIT_SET_TOUR extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 输出通道号或融合屏虚拟通道号, pszCompositeID为NULL时有效
    */
    public int              nChannel;
    /**
     * 融合屏ID
    */
    public Pointer          pszCompositeID;
    /**
     * 窗口号
    */
    public int              nWindow;
    /**
     * 轮巡动作,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_WINDOW_TOUR_ACTION}
    */
    public int              emAction;

    public NET_IN_SPLIT_SET_TOUR() {
        this.dwSize = this.size();
    }
}

