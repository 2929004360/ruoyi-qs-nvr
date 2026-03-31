package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ：251823
 * @description ：区域内目标列表
 * @since ： Created in 2022/05/09 14:46
 */
public class NET_ANIMAL_OBJECT_LIST_INFO extends NetSDKLib.SdkStructure {
    /**
     *  目标包围盒,8192坐标系
     */
    public NET_RECT         stuBoundingBox;
    /**
     *  目标中心点
     */
    public NetSDKLib.NET_POINT stuPoint;
    /**
     *  目标ID
     */
    public int              nObjectID;
    /**
     *  目标编号
     */
    public int              nObjectNumber;
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[256];
}

