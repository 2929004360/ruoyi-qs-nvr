package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetTipStaffManagerDepartmentInfo 接口输出参数
*/
public class NET_OUT_GET_DEPARTMENT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 执法人部门列表数量
    */
    public int              nRetDataNum;
    /**
     * 执法人部门列表,参见结构体定义 {@link NET_GET_DEPARTMENT_INFO_DATA}
    */
    public NET_GET_DEPARTMENT_INFO_DATA[] stuData = new NET_GET_DEPARTMENT_INFO_DATA[110];

    public NET_OUT_GET_DEPARTMENT_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuData.length; i++){
            stuData[i] = new NET_GET_DEPARTMENT_INFO_DATA();
        }
    }
}

