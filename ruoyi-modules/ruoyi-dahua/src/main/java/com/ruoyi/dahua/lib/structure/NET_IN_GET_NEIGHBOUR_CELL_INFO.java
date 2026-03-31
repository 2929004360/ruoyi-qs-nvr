package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetNeighbourCellInfo 接口输入参数
*/
public class NET_IN_GET_NEIGHBOUR_CELL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 蜂窝模块的网卡名称,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_WIRELESS_MODE}
    */
    public int              emEthName;

    public NET_IN_GET_NEIGHBOUR_CELL_INFO() {
        this.dwSize = this.size();
    }
}

