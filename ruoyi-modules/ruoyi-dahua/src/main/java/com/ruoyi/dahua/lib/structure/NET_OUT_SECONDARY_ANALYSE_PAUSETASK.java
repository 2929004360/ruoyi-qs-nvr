package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 暂停录像二次分析任务输出参数
*/
public class NET_OUT_SECONDARY_ANALYSE_PAUSETASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;

    public NET_OUT_SECONDARY_ANALYSE_PAUSETASK() {
        this.dwSize = this.size();
    }
}

