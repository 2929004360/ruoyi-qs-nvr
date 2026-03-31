package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 订阅二次录像分析实时结果输入参数
*/
public class NET_IN_ATTACH_SECONDARY_ANALYSE_RESULT extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 需要订阅的任务个数, 0表示订阅全部
    */
    public int              nTaskNum;
    /**
     * 任务ID
    */
    public int[]            nTaskID = new int[32];
    /**
     * 需要订阅的事件个数
    */
    public int              nEventCount;
    /**
     * 需要订阅的事件,参见枚举定义 {@link NetSDKLib.EM_ANALYSE_EVENT_TYPE}
    */
    public int[]            emEventType = new int[32];
    /**
     * 是否需要图片数据
    */
    public int              bNeedPicData;
    /**
     * 二次录像分析实时结果订阅函数,参见回调函数定义 {@link NetSDKLib.fAnalyseResultCallBack}
    */
    public NetSDKLib.fAnalyseResultCallBack cbAnalyseResult;
    /**
     * 用户数据
    */
    public Pointer          dwUser;

    public NET_IN_ATTACH_SECONDARY_ANALYSE_RESULT() {
        this.dwSize = this.size();
    }
}

