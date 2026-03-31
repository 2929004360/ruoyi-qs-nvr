package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 烟火检测查询过滤条件
*/
public class NET_SMOKE_FIRE_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 智能分析规则，当szRule不为""时，MEDIAFILE_SMOKE_FIRE_PARAM 中 nEventList 无效, 通配符"*"代表任意规则、"SmokeDetection"、"FireDetection"
    */
    public byte[]           szRule = new byte[64];
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[1024];

    public NET_SMOKE_FIRE_FILTER() {
    }
}

