package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetNeighbourCellInfo 接口输出参数
*/
public class NET_OUT_GET_NEIGHBOUR_CELL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 蜂窝网络邻区信息数组个数
    */
    public int              nRetNeighbourCellInfoNum;
    /**
     * 获取蜂窝网络邻区信息数组，最多获取10个邻区信息,参见结构体定义 {@link NET_NeighbourCellInfo}
    */
    public NET_NeighbourCellInfo[] stuNeighbourCellInfo = new NET_NeighbourCellInfo[10];

    public NET_OUT_GET_NEIGHBOUR_CELL_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuNeighbourCellInfo.length; i++){
            stuNeighbourCellInfo[i] = new NET_NeighbourCellInfo();
        }
    }
}

