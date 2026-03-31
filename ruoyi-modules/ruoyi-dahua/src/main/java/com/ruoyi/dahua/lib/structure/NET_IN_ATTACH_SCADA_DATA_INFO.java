package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_AttachSCADAData 接口入参
 * @date 2022/12/13 10:20:50
 */
public class NET_IN_ATTACH_SCADA_DATA_INFO extends NetSDKLib.SdkStructure {
    /**
     * 此结构体大小,必须赋值
     */
    public int              dwSize;
    /**
     * 回调函数, 设备传回的遥测数据
     */
    public NetSDKLib.fNotifySCADAData cbfNotifySCADAData;
    /**
     * 用户自定义参数
     */
    public Pointer          dwUser;

    public NET_IN_ATTACH_SCADA_DATA_INFO() {
        this.dwSize = this.size();
    }
}

