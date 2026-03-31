package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SetRadiometryRule 接口入参
*/
public class NET_IN_SET_RADIOMETRY_RULE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 通道号，热成像通道有效
    */
    public int              nChannel;
    /**
     * 单条测温规则信息,参见结构体定义 {@link NET_RADIOMETRY_MANAGER_RULE_INFO}
    */
    public NET_RADIOMETRY_MANAGER_RULE_INFO stuRuleInfo = new NET_RADIOMETRY_MANAGER_RULE_INFO();
    /**
     * 预置点编号，从1开始编号，枪类设备无预置点，编号填0
    */
    public int              nPresetId;
    /**
     * 规则编号，从1开始编号到12
    */
    public int              nRuleId;

    public NET_IN_SET_RADIOMETRY_RULE_INFO() {
        this.dwSize = this.size();
    }
}

