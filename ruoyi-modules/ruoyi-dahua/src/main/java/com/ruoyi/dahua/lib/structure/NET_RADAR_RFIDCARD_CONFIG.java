 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

 /**
 * @author ： 260611
 * @description ： 雷达RFID卡片信息
 * @since ： Created in 2022/02/16 14:42
 */

public class NET_RADAR_RFIDCARD_CONFIG extends NetSDKLib.SdkStructure {
     /** 
      * 卡片ID
      */
    public			byte[]         szCardID = new byte[24];
     /** 
      * 卡片有效时间的utc时间戳，单位秒
      */
    public			int            nValidTime;
     /** 
      * 卡片过期时间的utc时间戳，单位秒
      */
    public			int            nInvalidTime;
     /** 
      * 保留字节
      */
    public			byte[]         byReserved = new byte[256];
}

