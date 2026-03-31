package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 删除录像二次分析任务输出参数
*/
public class NET_OUT_SECONDARY_ANALYSE_REMOVETASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;

    public NET_OUT_SECONDARY_ANALYSE_REMOVETASK() {
        this.dwSize = this.size();
    }
}

