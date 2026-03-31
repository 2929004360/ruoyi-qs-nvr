package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 抓包过滤规则
*/
public class NET_SNIFFER_FILTER extends NetSDKLib.SdkStructure
{
    /**
     * 有效的stElemetsge个数
    */
    public int              nMaxElemet;
    /**
     * 过滤五元组的数组,参见结构体定义 {@link NET_SNIFFER_FILTER_ELEMENT}
    */
    public NET_SNIFFER_FILTER_ELEMENT[] stuElemets = new NET_SNIFFER_FILTER_ELEMENT[8];
    /**
     * 0: 取上述全部五元组的或集作为过滤条件; 1: 取上述五元组或非作为过滤条件。
    */
    public int              nFilterType;
    /**
     * 保留字段
    */
    public byte[]           byReserved = new byte[256];

    public NET_SNIFFER_FILTER() {
        for(int i = 0; i < stuElemets.length; i++){
            stuElemets[i] = new NET_SNIFFER_FILTER_ELEMENT();
        }
    }
}

