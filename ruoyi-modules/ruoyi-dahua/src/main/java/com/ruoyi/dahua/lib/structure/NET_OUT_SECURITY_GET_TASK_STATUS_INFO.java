package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SecurityGetTaskStatus接口出参
*/
public class NET_OUT_SECURITY_GET_TASK_STATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 当前任务类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SECURITY_TASK_TYPE}
    */
    public int              nTaskType;
    /**
     * 当前任务状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SECURITY_TASK_STATUS}
    */
    public int              nStatus;
    /**
     * 当任务状态"Status"为"Failed"时, 填写错误信息, 例如:"File content error!" 文件错误
    */
    public byte[]           szErrorCode = new byte[128];
    /**
     * 当前任务执行进度百分比
    */
    public int              nProgress;

    public NET_OUT_SECURITY_GET_TASK_STATUS_INFO() {
        this.dwSize = this.size();
    }
}

