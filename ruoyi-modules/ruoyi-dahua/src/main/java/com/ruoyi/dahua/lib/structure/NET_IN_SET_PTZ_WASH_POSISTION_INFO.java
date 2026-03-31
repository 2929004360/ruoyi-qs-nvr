package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 标定球机和蓄水池污点位置, 入参
 * @since ： Created in 2021/11/29 10:47
 */

public class NET_IN_SET_PTZ_WASH_POSISTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 0 - 标定喷水枪位置, 1 - 标定污点位置
     */
    public int              nIndex;
    /**
     * 球机到蓄水池底的高度
     */
    public float            fHeight;
    /**
     * 通道ID
     */
    public int              nChannelID;

    public NET_IN_SET_PTZ_WASH_POSISTION_INFO() {
        this.dwSize = this.size();
    }
}

