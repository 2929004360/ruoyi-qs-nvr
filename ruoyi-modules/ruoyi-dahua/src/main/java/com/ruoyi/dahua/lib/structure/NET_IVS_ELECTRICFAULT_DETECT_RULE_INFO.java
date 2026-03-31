package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description EVENT_IVS_ELECTRICFAULT_DETECT(仪表类缺陷检测事件)对应的规则配置
 * @date 2022/06/29 20:26:01
 */
public class NET_IVS_ELECTRICFAULT_DETECT_RULE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 挂空悬浮物检测使能
     */
    public int              bAirborneDetectEnable;
    /**
     * 鸟巢检测使能
     */
    public int              bNestDetectEnable;
    /**
     * 表盘检测(表盘模糊)使能
     */
    public int              bDialDetectEnable;
    /**
     * 渗漏检测使能
     */
    public int              bLeakageDetectEnable;
    /**
     * 箱门检测使能
     */
    public int              bDoorDetectEnable;
    /**
     * 呼吸器检测使能
     */
    public int              bRespiratorDetectEnable;
    /**
     * 吸烟检测使能
     */
    public int              bSmokingDetectEnable;
    /**
     * 绝缘子检测使能
     */
    public int              bInsulatorDetectEnable;
    /**
     * 盖板检测使能
     */
    public int              bCoverPlateDetectEnable;
    /**
     * 压板开合检测使能
     */
    public int              bPressingPlateDetectEnable;
    /**
     * 金属锈蚀检测使能
     */
    public int              bMetalCorrosionEnable;
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
     * 保留字节
     */
    public byte[]           bReserved = new byte[2048];

    public NET_IVS_ELECTRICFAULT_DETECT_RULE_INFO() {
        for (int i = 0; i < stuDetectRegion.length; i++) {
            stuDetectRegion[i] = new POINTCOORDINATE();
        }
    }
}

