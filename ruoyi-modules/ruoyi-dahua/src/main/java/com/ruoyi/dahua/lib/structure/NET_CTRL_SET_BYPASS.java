package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_ControlDevice接口的 DH_CTRL_SET_BYPASS 命令参数
 * @date 2021/10/29
 */
public class NET_CTRL_SET_BYPASS extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 登入设备的密码
     */
    public Pointer          szDevPwd;
	/**
     * 通道状态,参考枚举 { @link com.ruoyi.dahua.lib.NetSDKLib.NET_BYPASS_MODE}
     */
    public int              emMode;
    /**
     * 本地报警输入通道个数
     */
    public int              nLocalCount;
    /**
     * 本地报警输入通道号 ,int数组转化为指针,数组长度为本地报警输入通道个数nLocalCount
     */
    public Pointer          pnLocal;
    /**
     * 扩展模块报警输入通道个数
     */
    public int              nExtendedCount;
    /**
     * 扩展模块报警输入通道号 ,int数组转化为指针,数组长度为扩展模块报警输入通道个数nExtendedCount
     */
    public Pointer          pnExtended;

    public NET_CTRL_SET_BYPASS(){
        this.dwSize = this.size();
    }
}

