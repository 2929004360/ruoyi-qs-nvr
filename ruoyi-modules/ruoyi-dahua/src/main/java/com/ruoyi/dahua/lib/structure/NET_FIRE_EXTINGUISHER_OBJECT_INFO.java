package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 目标区域内物体计数信息
 * @date 2021/12/27
 */
public class NET_FIRE_EXTINGUISHER_OBJECT_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  区域内目标总数
     */
    public int              nAlertNum;
    /**
     *  区域内目标物体子类型信息个数
     */
    public int              nCategoryInfoNum;
    /**
     *  区域内目标物体子类型信息
     */
    public NET_FIRE_EXTINGUISHER_CATEGORY_INFO[] stuCategoryInfo = (NET_FIRE_EXTINGUISHER_CATEGORY_INFO[]) new NET_FIRE_EXTINGUISHER_CATEGORY_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[256];
}

