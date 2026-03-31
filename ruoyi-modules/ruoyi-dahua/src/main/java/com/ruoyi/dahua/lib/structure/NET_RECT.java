package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class NET_RECT extends NetSDKLib.SdkStructure {
    /**
     *    int             nLeft;
     */
    public int              nLeft;
    /**
     *    int             nTop;
     */
    public int              nTop;
    /**
     *    int             nRight;
     */
    public int              nRight;
    /**
     *    int             nBottom;
     */
    public int              nBottom;

    @Override
    public String toString() {
        return "NET_RECT{" +
                "nLeft=" + nLeft +
                ", nTop=" + nTop +
                ", nRight=" + nRight +
                ", nBottom=" + nBottom +
                '}';
    }
}

