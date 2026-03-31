package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 智能套餐方案信息
*/
public class NET_SCHEME_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 智能套餐方案ID
    */
    public int              nSchemeID;
    /**
     * 智能套餐方案状态 TRUE C 开启 FALSE - 关闭
    */
    public int              bSchemeState;
    /**
     * 智能套餐方案名称
    */
    public byte[]           szSchemeName = new byte[64];
    /**
     * 智能主从模式,多通道设备有效 0:未知 1:不存在主从关系 2:独立模式 3:联动模式
    */
    public int              nManSubMode;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[252];

    public NET_SCHEME_INFO() {
    }
}

