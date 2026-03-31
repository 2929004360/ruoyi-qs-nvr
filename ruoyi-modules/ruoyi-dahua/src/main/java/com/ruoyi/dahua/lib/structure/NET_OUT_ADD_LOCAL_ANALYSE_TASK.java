package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_RecordSecondaryAnalyseAddLocalAnalyseTask接口输出参数
*/
public class NET_OUT_ADD_LOCAL_ANALYSE_TASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 任务id，0表示添加失败
    */
    public int              nTaskID;
    /**
     * 错误码，taskID为0时有效, -1:未知, 0:无错误, 1:未知错误, 2:参数错误, 3:录像不存在
    */
    public int              nErrorCode;
    /**
     * 重复分析信息个数
    */
    public int              nRepeatCount;
    /**
     * 重复分析信息,参见结构体定义 {@link NET_LOCAL_ANALYSE_TASK_REPEAT_INFO}
    */
    public NET_LOCAL_ANALYSE_TASK_REPEAT_INFO[] stuRepeatInfo = new NET_LOCAL_ANALYSE_TASK_REPEAT_INFO[16];

    public NET_OUT_ADD_LOCAL_ANALYSE_TASK() {
        this.dwSize = this.size();
        for(int i = 0; i < stuRepeatInfo.length; i++){
            stuRepeatInfo[i] = new NET_LOCAL_ANALYSE_TASK_REPEAT_INFO();
        }
    }
}

