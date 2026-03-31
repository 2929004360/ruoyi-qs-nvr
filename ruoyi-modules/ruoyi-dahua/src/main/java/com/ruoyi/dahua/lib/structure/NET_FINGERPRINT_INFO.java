package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 信息数组信息
 * @date 2021/2/22
 */
public class NET_FINGERPRINT_INFO extends NetSDKLib.SdkStructure {
  /** 信息个数 */
    public int              nFingerNum;
  /** 信息信息 */
    public byte[]           szFingerInfo = new byte[8 * 2048];
  /** 保留字节 */
    public byte[]           byReserved = new byte[1024];
}

