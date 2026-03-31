package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标轨迹过滤规则
*/
public class NET_OBJECT_TRACK_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 目标过滤规则坐标点个数
    */
    public int              nDetectRuleCount;
    /**
     * 目标过滤规则坐标点集合,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRule = new NetSDKLib.NET_POINT[32];
    /**
     * 规则类型, 0,区域内,DetectRule字段为描述不规则区域的各顶点坐标, 1,过线,DetectRule字段为描述线段点，并且Direction字段表示目标经过方向
    */
    public int              nRuleType;
    /**
     * 检测方向, 1:"LeftToRight", 2:"RightToLeft", 3:"Both", 当RuleType字段值为1时，该字段有效
    */
    public int              nDirection;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_OBJECT_TRACK_FILTER() {
        for(int i = 0; i < stuDetectRule.length; i++){
            stuDetectRule[i] = new NetSDKLib.NET_POINT();
        }
    }
}

