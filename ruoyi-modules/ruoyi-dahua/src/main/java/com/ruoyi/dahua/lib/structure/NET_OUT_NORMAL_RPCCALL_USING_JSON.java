package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_NormalRpcCallUsingJson 接口输出参数
*/
public class NET_OUT_NORMAL_RPCCALL_USING_JSON extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 参数类型， 0：结构体  1：json
    */
    public int              nParamType;
    /**
     * 出参指针，nParamType为0时使用，由用户申请空间，对应关系见EM_SUPPORT_RPC_TYPE
    */
    public Pointer          pstuOutParams;
    /**
     * Json形式出参，nParamType为1时使用，由用户申请空间
    */
    public Pointer          pstuOutJson;
    /**
     * 数据缓冲区长度, 单位:字节
    */
    public int              nstuOutJsonLen;
    /**
     * 实际返回的数据缓冲区长度, 单位:字节
    */
    public int              nstuOutJsonRetLen;

    public NET_OUT_NORMAL_RPCCALL_USING_JSON() {
        this.dwSize = this.size();
    }
}

