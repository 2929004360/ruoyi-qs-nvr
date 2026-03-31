package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_CFG_POLE_CAMERA 智慧杆Camera配置
*/
public class NET_CFG_POLE_CAMERA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 端口号
    */
    public int              nPort;
    /**
     * 相机SN
    */
    public byte[]           szSn = new byte[64];
    /**
     * 相机ip
    */
    public byte[]           szIp = new byte[64];
    /**
     * 网关
    */
    public byte[]           szGateway = new byte[64];
    /**
     * 掩码
    */
    public byte[]           szNetmask = new byte[64];
    /**
     * 用户名
    */
    public byte[]           szUser = new byte[64];
    /**
     * 加密后的密码
    */
    public byte[]           szPwd = new byte[64];
    /**
     * 相机通道号
    */
    public int              nChannel;
    /**
     * 连接状态
    */
    public int              bConnectedState;
    /**
     * 相机类型camera SD
    */
    public byte[]           szType = new byte[64];
    /**
     * 需要修改成的Ip地址
    */
    public byte[]           szIpModify = new byte[64];
    /**
     * 需要修改成的网关
    */
    public byte[]           szGatewayModify = new byte[64];
    /**
     * 需要修改成的掩码
    */
    public byte[]           szNetmaskModify = new byte[64];
    /**
     * 密码是否加密(兼容以前的版本)
    */
    public int              bPwdEncrypt;

    public NET_CFG_POLE_CAMERA_INFO() {
        this.dwSize = this.size();
    }
}

