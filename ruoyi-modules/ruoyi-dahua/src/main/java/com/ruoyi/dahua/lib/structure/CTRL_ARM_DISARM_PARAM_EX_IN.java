package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 报警布撤防控制扩展输入参数
 * @date 2021/10/29
 */
public class CTRL_ARM_DISARM_PARAM_EX_IN extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 布撤防状态，参考枚举 { @link com.ruoyi.dahua.lib.NetSDKLib.NET_ALARM_MODE}
     */
    public int              emState;
    /**
     * 用户密码
     */
    public Pointer          szDevPwd;
    /**
     * 情景模式，参考枚举 { @link com.ruoyi.dahua.lib.NetSDKLib.NET_SCENE_MODE}
     */
    public int              emSceneMode;
    /**
     * 转发目标设备ID,为NULL表示不转发
     */
    public Pointer          szDevID;

    public CTRL_ARM_DISARM_PARAM_EX_IN(){
        this.dwSize = this.size();
    }
}

