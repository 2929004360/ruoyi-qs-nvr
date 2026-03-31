package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_OCR_DETECTION(地物识别事件)对应的规则配置
*/
public class NET_OCR_DETECTION_RULE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 检测区顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 类型值, 0: 数字, 1: 字符, 2: 数字+字符, 3: 含中文, 4: 未知
    */
    public int              nType;
    /**
     * 当type为0时有效,参见结构体定义 {@link NET_OCR_DETECTION_NUMBER_INFO}
    */
    public NET_OCR_DETECTION_NUMBER_INFO stuNumber = new NET_OCR_DETECTION_NUMBER_INFO();
    /**
     * 最短持续时间, 单位: 秒 (1S-3600S)
    */
    public int              nMinDuration;
    /**
     * 重复报警间隔, 单位: 秒 (0S-3600S)
    */
    public int              nReportInterval;
    /**
     * 算法上报间隔时间
    */
    public int              nRepeatedReport;
    /**
     * 使用场景类型值
    */
    public int              nFuncType;
    /**
     * 报警类型值
    */
    public int              nAlarmType;
    /**
     * 最短字符串长度，0-不使用该字段
    */
    public int              nMinStringLength;
    /**
     * 最大字符串长度，0-不使用该字段
    */
    public int              nMaxStringLength;
    /**
     * 检测灵敏度, 范围1~10
    */
    public int              nSensitivity;
    /**
     * 警戒线顶点数
    */
    public int              nDetectLineNum;
    /**
     * 警戒线,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectLine = new NET_POINT_EX[20];
    /**
     * 检测方向, 0:未知, 1:LeftToRight, 2:RightToLeft, 3:Both
    */
    public int              nDirection;
    /**
     * 目标稳定时间阀值, 单位是秒, 0表示不使用该字段
    */
    public int              nObjStableSecond;
    /**
     * 检测区数组第一维大小
    */
    public int              nRecogRegionFirstNum;
    /**
     * 检测区数组第二维大小
    */
    public int              nRecogRegionSecondNum;
    /**
     * 检测区数组,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX_ARRAY_20[] stuRecogRegion = new NET_POINT_EX_ARRAY_20[50];
    /**
     * 检测区域ID个数
    */
    public int              nRegionIDNum;
    /**
     * 检测区域ID
    */
    public int[]            nRegionID = new int[50];

    public NET_OCR_DETECTION_RULE_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuDetectLine.length; i++){
            stuDetectLine[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuRecogRegion.length; i++){
            stuRecogRegion[i] = new NET_POINT_EX_ARRAY_20();
        }
    }
}

