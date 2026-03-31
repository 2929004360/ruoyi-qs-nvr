package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 离线视频文件信息（第三方导入的文件）
*/
public class NET_OFFLINE_VIDEO_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 智能任务启动规则,参见枚举定义 {@link NetSDKLib.EM_ANALYSE_TASK_START_RULE}
    */
    public int              emStartRule;
    /**
     * 分析规则信息,参见结构体定义 {@link NetSDKLib.NET_ANALYSE_RULE}
    */
    public NetSDKLib.NET_ANALYSE_RULE stuRuleInfo = new NetSDKLib.NET_ANALYSE_RULE();
    /**
     * 远程文件访问协议类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_REMOTE_FILE_PROTOCOL_TYPE}
    */
    public int              emRemoteFileProtocolType;
    /**
     * 文件路径
    */
    public byte[]           szPath = new byte[260];
    /**
     * 文件ID
    */
    public byte[]           szFileID = new byte[128];
    /**
     * IP 地址
    */
    public byte[]           szIp = new byte[64];
    /**
     * 端口号
    */
    public short            wPort;
    /**
     * 用户名
    */
    public byte[]           szUser = new byte[64];
    /**
     * 密码
    */
    public byte[]           szPwd = new byte[64];
    /**
     * 任务数据
    */
    public byte[]           szTaskUserData = new byte[256];
    /**
     * MQ配置信息，参考Paas协议配置中心-算子配置。当远程访问类型为RabbitMq时,尝试从该字段获取MQ配置
    */
    public byte[]           szMQConfig = new byte[4096];
    /**
     * 是否许可重复,0默认是可以重复,1表示不能重复
    */
    public int              nIsRepeat;
    /**
     * 分析进度,有效范围0~100，100表示分析完成
    */
    public int              nVideoAnalysisProcess;
    /**
     * 文件大小，单位字节
    */
    public long             nFileSize;
    /**
     * 是否上报相对时间戳，true为上报，false为不上报
    */
    public int              bIsReportPTS;
    /**
     * 预留，作8字节对齐
    */
    public byte[]           szReserved = new byte[2];
    /**
     * 端口号
    */
    public int              nPort;
    /**
     * 是否使用nPort作为端口号
    */
    public int              bUseNewPort;
    /**
     * 分析文件的加密密钥对列表个数
    */
    public int              nVideoKeyEncryptionKeysCount;
    /**
     * 分析文件的加密密钥对列表,参见结构体定义 {@link NET_ANALYSE_TASK_VIDEO_KEY_ENCRYPTION_KEYS}
    */
    public NET_ANALYSE_TASK_VIDEO_KEY_ENCRYPTION_KEYS[] stuVideoKeyEncryptionKeys = new NET_ANALYSE_TASK_VIDEO_KEY_ENCRYPTION_KEYS[8];
    /**
     * 是否使用透传
    */
    public int              bUseTransmit;
    /**
     * 分析规则内容
    */
    public Pointer          pszRules;
    /**
     * 全局配置内容
    */
    public Pointer          pszGlobal;
    /**
     * 模块配置内容
    */
    public Pointer          pszModule;

    public NET_OFFLINE_VIDEO_FILE_INFO() {
        this.dwSize = this.size();
    }
}

