package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description VTOInfo的格式
 * @origin autoTool
 * @date 2023/10/26 09:34:52
 */
public class NET_TALK_DEV_FRONT_DEV_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 是否使能
     */
    public int              bEnable;
    /**
     * / Ip地址
     */
    public byte[]           szAddress = new byte[40];
    /**
     * / 对讲协议端口
     */
    public int              nPort;
    /**
     * / 前端设备类型类型, "Vto": 单元门口机, "Wall": 围墙机, "Visitor": 访客机
     */
    public byte[]           szType = new byte[32];
    /**
     * / 中号
     */
    public byte[]           szMiddleNumber = new byte[16];
    /**
     * / 设备所处位置
     */
    public byte[]           szMachineAddress = new byte[64];
    /**
     * / 设备呼叫室内机，室内机的铃声文件
     */
    public byte[]           szRingFile = new byte[64];
    /**
     * / 设备呼叫室内机，室内机的铃声大小
     */
    public int              nRingVolume;
    /**
     * / 设备的Rtsp端口
     */
    public int              nRtspPort;
    /**
     * / Dtmf1开锁码
     */
    public byte[]           szUnlockDtmf1 = new byte[32];
    /**
     * / Dtmf2开锁码
     */
    public byte[]           szUnlockDtmf2 = new byte[32];
    /**
     * / 三代登陆用户
     */
    public byte[]           szUsername = new byte[32];
    /**
     * / 三代登陆密码
     */
    public byte[]           szPassword = new byte[32];
    /**
     * / 设备可支持锁数量
     */
    public int              nLockNumber;
    /**
     * / 联动IPC的配置信息
     */
    public byte[]           szLinkIPC = new byte[32];
    /**
     * / 设备设备序列号
     */
    public byte[]           szSN = new byte[32];
    /**
     * / 是否使用设备所处位置扩展字段
     */
    public int              bMachineAddressEx;
    /**
     * / 设备所处位置扩展
     */
    public byte[]           szMachineAddressEx = new byte[256];

    public NET_TALK_DEV_FRONT_DEV_INFO() {
    }
}

