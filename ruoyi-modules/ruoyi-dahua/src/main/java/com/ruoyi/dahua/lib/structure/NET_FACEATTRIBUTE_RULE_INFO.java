package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EVENT_IVSS_FACEATTRIBUTE (目标属性检测) 对应的规则配置结构体
*/
public class NET_FACEATTRIBUTE_RULE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 检测区域顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.POINTCOORDINATE}
    */
    public NetSDKLib.POINTCOORDINATE[] stuDetectRegion = new NetSDKLib.POINTCOORDINATE[20];
    /**
     * 最短持续时间	单位：秒，0~65535
    */
    public int              nMinDuration;
    /**
     * 触发报警的目标个数
    */
    public int              nTriggerTargets;
    /**
     * 灵敏度, 取值1-10，值越小灵敏度越低
    */
    public int              nSensitivity;
    /**
     * 报告时间间隔,单位秒 0~600;等于0表示不重复报警, 默认值30
    */
    public int              nReportInterval;
    /**
     * 规则特定的尺寸过滤器是否有效
    */
    public int              bSizeFileter;
    /**
     * 规则特定的尺寸过滤器,参见结构体定义 {@link NET_CFG_SIZEFILTER_INFO}
    */
    public NET_CFG_SIZEFILTER_INFO stuSizeFileter = new NET_CFG_SIZEFILTER_INFO();
    /**
     * 需要检测的目标属性个数
    */
    public int              nFaceFeatureNum;
    /**
     * 需检测的目标属性,参见枚举定义 {@link NetSDKLib.NET_EM_FACEFEATURE_TYPE}
    */
    public int[]            emFaceFeatureType = new int[32];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[4096];

    public NET_FACEATTRIBUTE_RULE_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.POINTCOORDINATE();
        }
    }
}

