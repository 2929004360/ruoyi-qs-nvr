package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AsyncCheckFaultCheck 接口入参
*/
public class NET_IN_ASYNC_CHECK_FAULT_CHECK extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 是否检测所有
    */
    public int              bCheckAll;
    /**
     * 检测项列表个数
    */
    public int              nItemsCount;
    /**
     * 检测项列表, bCheckAll为false时设置该字段
    */
    public BYTE_ARRAY_64[]  szItems = new BYTE_ARRAY_64[1024];
    /**
     * 标示前端一键诊断功能
    */
    public int              bOnekeyDiagnosis;

    public NET_IN_ASYNC_CHECK_FAULT_CHECK() {
        this.dwSize = this.size();
    }
}

