package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 职员工装信息
*/
public class NET_EMPLOYEE_CLOTHES_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 触发报警的工装库名称
    */
    public byte[]           szLinkGroupName = new byte[128];
    /**
     * 触发报警的工装库ID
    */
    public byte[]           szLinkGroupID = new byte[128];
    /**
     * 联动报警的工装库信息,参见结构体定义 {@link NET_LINK_GROUP_INFO}
    */
    public NET_LINK_GROUP_INFO[] stuLinkGroupInfo = new NET_LINK_GROUP_INFO[16];
    /**
     * 联动报警的工装库信息个数
    */
    public int              nLinkGroupInfoNum;
    /**
     * 优选方案,0 全身 ,1 上半身
    */
    public int              nCutoutPolicy;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_EMPLOYEE_CLOTHES_INFO() {
        for(int i = 0; i < stuLinkGroupInfo.length; i++){
            stuLinkGroupInfo[i] = new NET_LINK_GROUP_INFO();
        }
    }
}

