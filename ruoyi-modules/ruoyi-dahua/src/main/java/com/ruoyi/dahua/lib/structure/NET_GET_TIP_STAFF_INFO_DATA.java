package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_GET_TIP_STAFF_INFO_DATA 执法人列表信息
*/
public class NET_GET_TIP_STAFF_INFO_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 指纹Id列表
    */
    public int[]            nFingerPrintId = new int[3];
    /**
     * 指纹Id列表数量
    */
    public int              nFingerPrintIdNum;
    /**
     * 部门ID
    */
    public byte[]           szDepartId = new byte[32];
    /**
     * 部门名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 执法人Id
    */
    public byte[]           szTipStaffId = new byte[32];
    /**
     * 人脸Id
    */
    public int              nFaceId;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_GET_TIP_STAFF_INFO_DATA() {
    }
}

