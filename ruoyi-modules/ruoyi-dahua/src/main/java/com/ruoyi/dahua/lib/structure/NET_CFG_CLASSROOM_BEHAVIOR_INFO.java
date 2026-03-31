package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EVENT_IVS_CLASSROOM_BEHAVIOR(课堂行为分析事件)对应的规则配置
*/
public class NET_CFG_CLASSROOM_BEHAVIOR_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 规则名称,不同规则不能重名
    */
    public byte[]           szName = new byte[128];
    /**
     * 规则使能
    */
    public int              bEnable;
    /**
     * 相应物体类型个数
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 报警联动,参见结构体定义 {@link NetSDKLib.NET_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.NET_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.NET_ALARM_MSG_HANDLE();
    /**
     * 云台预置点编号  0~65535
    */
    public int              nPtzPresetId;
    /**
     * 联动业务个数
    */
    public int              nLink;
    /**
     * 联动业务大类选项,参见结构体定义 {@link NET_LINK_CLASS_TYPE}
    */
    public NET_LINK_CLASS_TYPE[] stuLinkClassType = new NET_LINK_CLASS_TYPE[16];
    /**
     * 跟踪条件规则使能
    */
    public int              bTrackEnable;
    /**
     * 检测区顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 最短持续时间, 单位:秒(5s-60s)
    */
    public int              nMinDuration;
    /**
     * 持续抓拍间隔, 单位:秒(0s-108000s)
    */
    public int              nSnapInterval;
    /**
     * 灵敏度, 值越小灵敏度越低, 取值1-10
    */
    public int              nSensitivity;
    /**
     * 需要检测的行为个数
    */
    public int              nActionListNum;
    /**
     * 需要检测的行为
    */
    public BYTE_ARRAY_16[]  szActionList = new BYTE_ARRAY_16[32];
    /**
     * 是否进行抓图,默认true
    */
    public int              bFaceSnapEnable;
    /**
     * 是否开属性识别,默认true
    */
    public int              bFeatureEnable;
    /**
     * 配置要检测哪些属性，开启属性时有效,默认所有属性全选
    */
    public BYTE_ARRAY_16[]  szFeatureList = new BYTE_ARRAY_16[32];
    /**
     * 配置要检测哪些属性个数
    */
    public int              nFeatureListNum;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_CFG_CLASSROOM_BEHAVIOR_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuLinkClassType.length; i++){
            stuLinkClassType[i] = new NET_LINK_CLASS_TYPE();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

