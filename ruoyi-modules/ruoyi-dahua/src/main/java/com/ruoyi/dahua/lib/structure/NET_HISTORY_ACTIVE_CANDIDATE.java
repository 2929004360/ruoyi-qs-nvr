package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库以图搜图主动上报的候选人信息
*/
public class NET_HISTORY_ACTIVE_CANDIDATE extends NetSDKLib.SdkStructure
{
    /**
     * 相似度
    */
    public int              nSimilarity;
    /**
     * 抓拍通道号
    */
    public int              nChannelID;
    /**
     * 历史库人员信息,参见结构体定义 {@link NET_HISTORY_PERSON_INFO}
    */
    public NET_HISTORY_PERSON_INFO stuHistoryPerson = new NET_HISTORY_PERSON_INFO();
    /**
     * 查询人员出现的时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuTime = new NetSDKLib.NET_TIME();
    /**
     * 目标全景图,参见结构体定义 {@link NET_FACE_IMAGE_INFO}
    */
    public NET_FACE_IMAGE_INFO stuSceneImage = new NET_FACE_IMAGE_INFO();
    /**
     * 通道号
    */
    public byte[]           szChannelString = new byte[32];
    /**
     * 历史库中人员出现的时间(UTC时间格式),参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 事件类型
    */
    public byte[]           szEventType = new byte[64];
    /**
     * 预留字节数
    */
    public byte[]           bReserved = new byte[1116];

    public NET_HISTORY_ACTIVE_CANDIDATE() {
    }
}

