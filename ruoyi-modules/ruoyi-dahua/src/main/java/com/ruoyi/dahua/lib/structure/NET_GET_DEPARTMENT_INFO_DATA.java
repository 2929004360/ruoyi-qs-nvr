package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_GET_DEPARTMENT_INFO_DATA 执法人部门列表信息
*/
public class NET_GET_DEPARTMENT_INFO_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 部门ID
    */
    public byte[]           szDepartId = new byte[32];
    /**
     * 部门名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_GET_DEPARTMENT_INFO_DATA() {
    }
}

