package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description EVENT_IVS_DIALRECOGNITION(仪表检测事件)对应的规则配置
 * @date 2022/06/30 11:30:54
 */
public class NET_IVS_DIALRECOGNITION_RULE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 仪表类型 {@link com.ruoyi.dahua.lib.enumeration.EM_DIALDETECT_TYPE}
     */
    public int              emType;
    /**
     * 规则特定的尺寸过滤器是否有效
     */
    public int              bSizeFileter;
    /**
     * 规则特定的尺寸过滤器
     */
    public NET_CFG_SIZEFILTER_INFO stuSizeFileter = new NET_CFG_SIZEFILTER_INFO();
    /**
     * 检测区域
     */
    public POINTCOORDINATE[] stuDetectRegion = new POINTCOORDINATE[20];
    /**
     * 检测区域顶点数
     */
    public int              nDetectRegionNum;
    /**
     * 敞开式隔离开关有效,分夹角阈值, 单位度,取值范围0~90, 建议20
     */
    public int              nKinfeOpenAngleThreshold;
    /**
     * 敞开式隔离开关有效,合夹角阈值, 单位度,取值范围0~90, 建议10
     */
    public int              nKinfeClossAngleThreshold;
    /**
     * 保留字节
     */
    public byte[]           bReserved = new byte[2044];

    public NET_IVS_DIALRECOGNITION_RULE_INFO() {
        for (int i = 0; i < stuDetectRegion.length; i++) {
            stuDetectRegion[i] = new POINTCOORDINATE();
        }
    }
}

