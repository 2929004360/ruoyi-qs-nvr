package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_RecordSecondaryAnalyseAddLocalAnalyseTask接口输入参数
*/
public class NET_IN_ADD_LOCAL_ANALYSE_TASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 是否检查重复分析
    */
    public int              bCheck;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 任务名称
    */
    public byte[]           szName = new byte[256];
    /**
     * 监控点
    */
    public byte[]           szSourceName = new byte[256];
    /**
     * 分析规则个数
    */
    public int              nRuleCount;
    /**
     * 分析规则, 内存由用户申请，大小为size(NET_LOCAL_ANALYSE_TASK_RULE)*nRuleCount,参见结构体定义 {@link NET_LOCAL_ANALYSE_TASK_RULE}
    */
    public Pointer          pstuRule;
    /**
     * 录像开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 录像结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
    /**
     * 录像文件路径
    */
    public byte[]           szVideoPath = new byte[256];

    public NET_IN_ADD_LOCAL_ANALYSE_TASK() {
        this.dwSize = this.size();
    }
}

