package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EM_RPC_NETAPP_TYPE_GET_NET_RESOURCE_STAT 出参
*/
public class NET_OUT_NETAPP_GET_NET_RESOURCE_STAT extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * IP通道接入          单位：kbps
    */
    public int              nIPChanneIn;
    /**
     * 网络接收剩余        单位：kbps
    */
    public int              nNetRemain;
    /**
     * 网络接收总能力      单位：Mbps
    */
    public int              nNetCapability;
    /**
     * 远程预览            单位：kbps
    */
    public int              nRemotePreview;
    /**
     * 远程回放及下载      单位：kbps
    */
    public int              nRemotePlayAndDownload;
    /**
     * 远程发送剩余        单位：kbps
    */
    public int              nRemoteSendRemain;
    /**
     * 远程发送总能力      单位：Mbps
    */
    public int              nRemoteSendCapability;
    /**
     * IP通道接入
    */
    public byte[]           szIPChanneIn = new byte[16];
    /**
     * 网络接收剩余
    */
    public byte[]           szNetRemain = new byte[16];
    /**
     * 网络接收总能力
    */
    public byte[]           szNetCapability = new byte[16];
    /**
     * 远程预览
    */
    public byte[]           szRemotePreview = new byte[16];
    /**
     * 远程回放及下载
    */
    public byte[]           szRemotePlayAndDownload = new byte[16];
    /**
     * 远程发送剩余
    */
    public byte[]           szRemoteSendRemain = new byte[16];
    /**
     * 远程发送总能力
    */
    public byte[]           szRemoteSendCapability = new byte[16];
    /**
     * TRUE表示szIPChanneIn字段有效, FALSE表示nIPChanneIn字段有效
    */
    public int              bIPChanneIn;
    /**
     * TRUE表示szNetRemain字段有效, FALSE表示nNetRemain字段有效
    */
    public int              bNetRemain;
    /**
     * TRUE表示szNetCapability字段有效, FALSE表示nNetCapability字段有效
    */
    public int              bNetCapability;
    /**
     * TRUE表示szRemotePreview字段有效, FALSE表示nRemotePreview字段有效
    */
    public int              bRemotePreview;
    /**
     * TRUE表示szRemotePlayAndDownload字段有效, FALSE表示nRemotePlayAndDownload字段有效
    */
    public int              bRemotePlayAndDownload;
    /**
     * TRUE表示szRemoteSendRemain字段有效, FALSE表示nRemoteSendRemain字段有效
    */
    public int              bRemoteSendRemain;
    /**
     * TRUE表示szRemoteSendCapability字段有效, FALSE表示nRemoteSendCapability字段有效
    */
    public int              bRemoteSendCapability;

    public NET_OUT_NETAPP_GET_NET_RESOURCE_STAT() {
        this.dwSize = this.size();
    }
}

