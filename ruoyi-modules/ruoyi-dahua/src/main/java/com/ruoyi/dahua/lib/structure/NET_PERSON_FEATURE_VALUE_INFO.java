package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：NET_PERSON_FEATURE_VALUE_INFO description：人员特征信息 author：251589 createTime：2020/12/28
 * 11:19
 *
 * @version v1.0
 */
public class NET_PERSON_FEATURE_VALUE_INFO extends NetSDKLib.SdkStructure {
  /** 二进制数据块中的偏移值 */
    public int              nOffset;
  /** 特征值大小 */
    public int              nLength;
  /** 保留字节 */
    public byte[]           byReserved = new byte[128];
}

