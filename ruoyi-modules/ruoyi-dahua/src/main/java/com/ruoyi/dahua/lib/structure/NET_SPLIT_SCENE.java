package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 拼接屏场景
*/
public class NET_SPLIT_SCENE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 拼接屏ID
    */
    public byte[]           szCompositeID = new byte[128];
    /**
     * 控制ID
    */
    public byte[]           szControlID = new byte[128];
    /**
     * 分割模式,参见枚举定义 {@link NetSDKLib.NET_SPLIT_MODE}
    */
    public int              emSplitMode;
    /**
     * 窗口信息数组, 用户分配内存,大小为sizeof(DH_SPLIT_WINDOW)*nMaxWndCount,参见结构体定义 {@link NET_SPLIT_WINDOW}
    */
    public Pointer          pstuWnds;
    /**
     * 窗口信息数组大小, 用户填写
    */
    public int              nMaxWndCount;
    /**
     * 返回的窗口数量
    */
    public int              nRetWndCount;
    /**
     * 窗口信息数组-扩展, 用户分配内存,大小为sizeof(DH_SPLIT_WINDOW_EX)*nMaxWndCount,pstuWndsEx 与 pstuWnds选一个使用即可,参见结构体定义 {@link NET_SPLIT_WINDOW_EX}
    */
    public Pointer          pstuWndsEx;
    /**
     * 窗口信息数组大小-扩展, 用户填写
    */
    public int              nMaxWndCountEx;
    /**
     * 返回的窗口数量-扩展
    */
    public int              nRetWndCountEx;

    public NET_SPLIT_SCENE() {
        this.dwSize = this.size();
    }
}

