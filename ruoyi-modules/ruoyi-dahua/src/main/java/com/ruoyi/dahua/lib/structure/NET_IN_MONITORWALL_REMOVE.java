package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_OperateMonitorWall接口输入参数=>NET_MONITORWALL_OPERATE_REMOVE
*/
public class NET_IN_MONITORWALL_REMOVE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 要删除的电视墙数量
    */
    public int              nMonitorWallNum;
    /**
     * 电视墙名称
    */
    public BYTE_ARRAY_128[] szNames = new BYTE_ARRAY_128[32];

    public NET_IN_MONITORWALL_REMOVE() {
        this.dwSize = this.size();
        for(int i = 0; i < szNames.length; i++){
            szNames[i] = new BYTE_ARRAY_128();
        }
    }
}

