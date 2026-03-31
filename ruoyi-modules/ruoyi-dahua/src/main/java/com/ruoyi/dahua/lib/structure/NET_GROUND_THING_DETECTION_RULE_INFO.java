package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_GROUND_THING_DETECTION(地物识别事件)对应的规则配置
*/
public class NET_GROUND_THING_DETECTION_RULE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 多个检测区域个数
    */
    public int              nDetectRegionNum;
    /**
     * 多个检测区域,参见结构体定义 {@link NET_MUILTI_DETECT_REGION_INFO}
    */
    public NET_MUILTI_DETECT_REGION_INFO[] stuMultiDetectRegion = new NET_MUILTI_DETECT_REGION_INFO[64];

    public NET_GROUND_THING_DETECTION_RULE_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuMultiDetectRegion.length; i++){
            stuMultiDetectRegion[i] = new NET_MUILTI_DETECT_REGION_INFO();
        }
    }
}

