package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_PostLoginTask 输入参数
*/
public class NET_POST_LOGIN_TASK extends NetSDKLib.SdkStructure
{
    /**
     * 登录句柄
    */
    public NetSDKLib.LLong  lLoginID;
    /**
     * 设备IP
    */
    public Pointer          pchDVRIP;
    /**
     * 设备端口
    */
    public int              nDVRPort;
    /**
     * 设备状态 bOnline为TRUE表示登录成功，为FALSE时表示登录失败。
    */
    public int              bOnline;
    /**
     * 设备信息扩展,参见结构体定义 {@link NetSDKLib.NET_DEVICEINFO_Ex}
    */
    public NetSDKLib.NET_DEVICEINFO_Ex stuDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();
    /**
     * 当登陆失败时（bOnline为FALSE），nError 的错误码含义说明参考 CLIENT_Login 中的 error 说明，同时，当nError为 LOGIN_TYPE_DEVICE_NOT_SUPPORT 时，表示设备不支持此种登录方式
    */
    public int              nError;
    /**
     * 保留字段
    */
    public byte[]           szReserve = new byte[1024];

    public NET_POST_LOGIN_TASK() {
    }
}

