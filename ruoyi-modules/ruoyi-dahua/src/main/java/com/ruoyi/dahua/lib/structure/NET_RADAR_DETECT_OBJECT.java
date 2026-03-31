package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 雷达检测对象
 *
 * @author ： 251823
 * @since ： Created in 2021/7/21
 */
public class NET_RADAR_DETECT_OBJECT extends NetSDKLib.SdkStructure {
	 /**
     *  物体ID
     */
    public int              nObjectID;
    /**
     *  物体类型 ,参考枚举{ @link EM_RADAR_DETECT_OBJECT_TYPE }
     */
    public int              emObjectType;
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[1024];
}

