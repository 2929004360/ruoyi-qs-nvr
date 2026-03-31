package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 网络应用能力集
*/
public class CFG_CAP_NETAPP extends NetSDKLib.SdkStructure
{
    /**
     * 网卡数量
    */
    public int              nNetCardCount;
    /**
     * 网络类型个数
    */
    public int              nNetTypeNumber;
    /**
     * 具体网络类型, "PPPoE"，"WIFI"，"3G"，"LAN"
    */
    public BYTE_ARRAY_64[]  szNetType = new BYTE_ARRAY_64[8];

    public CFG_CAP_NETAPP() {
        for(int i = 0; i < szNetType.length; i++){
            szNetType[i] = new BYTE_ARRAY_64();
        }
    }
}

