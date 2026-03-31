package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetTipStaffManagerDepartmentInfo 接口输入参数
*/
public class NET_IN_GET_DEPARTMENT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 字节对齐
    */
    public byte[]           szAlign = new byte[4];
    /**
     * 部门Id, ""表示不下发此字段
    */
    public byte[]           szDepartId = new byte[32];

    public NET_IN_GET_DEPARTMENT_INFO() {
        this.dwSize = this.size();
    }
}

