package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 按键消息
*/
public class NET_KEY_MESSAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 客户端类型  0: 专用键盘, 1: 主屏, 2: 辅屏1, 3：平台
    */
    public int              nClientType;
    /**
     * 键值
    */
    public int              nKey;
    /**
     * 按键动作  0: 按键弹起, 1: 按键按下
    */
    public int              nAction;
    /**
     * 仅用于字节对齐, 无意义
    */
    public byte[]           byReserved = new byte[4];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[1024];

    public NET_KEY_MESSAGE_INFO() {
    }
}

