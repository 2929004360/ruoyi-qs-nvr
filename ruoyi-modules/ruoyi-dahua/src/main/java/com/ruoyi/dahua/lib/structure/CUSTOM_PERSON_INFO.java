package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.DH_MAX_PERSON_INFO_LEN;

/**
 * className：CUSTOM_PERSON_INFO
 * description：
 * author：251589
 * createTime：2020/12/28 11:08
 *
 * @version v1.0
 */
public class CUSTOM_PERSON_INFO extends NetSDKLib.SdkStructure {
    public byte[]           szPersonInfo = new byte[DH_MAX_PERSON_INFO_LEN]; //人员扩展信息
    public byte[]           byReserved = new byte[124];           // 保留字节
}

