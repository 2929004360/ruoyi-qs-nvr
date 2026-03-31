package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SecurityGetTaskStatus接口入参
*/
public class NET_IN_SECURITY_GET_TASK_STATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 任务数据类型, 13:预置点信息
    */
    public int              nType;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 是否下发通道号
    */
    public int              bChannel;

    public NET_IN_SECURITY_GET_TASK_STATUS_INFO() {
        this.dwSize = this.size();
    }
}

