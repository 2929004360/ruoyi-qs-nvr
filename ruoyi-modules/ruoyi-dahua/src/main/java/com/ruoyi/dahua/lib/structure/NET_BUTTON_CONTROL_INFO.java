package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 按钮控制鉴权信息
*/
public class NET_BUTTON_CONTROL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 按钮操作类型，0-无操作, 1-开门，2-关门，3-复位
    */
    public int              nOperate;
    /**
     * 操作的门序号，0x00-未知, 0x01-门1, 0x02-门2, 0x03-门1+门2
    */
    public int              nDoorIndex;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[56];

    public NET_BUTTON_CONTROL_INFO() {
    }
}

