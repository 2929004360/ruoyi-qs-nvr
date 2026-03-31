package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_FindGroupFormVehicleRegisterDB 接口输出参数
 * @date 2022/10/22 10:20
 */
public class NET_OUT_FIND_GROUP_FROM_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public int              nGroupListNum;                        // 返回的车辆组数量
    public 	NET_VEHICLE_GROUP_FOUND_INFO[] stuGroupList = new NET_VEHICLE_GROUP_FOUND_INFO[128]; //返回的车辆组信息 /

    public NET_OUT_FIND_GROUP_FROM_VEHICLE_REG_DB(){
        for(int i=0;i<stuGroupList.length;i++){
            stuGroupList[i]=new NET_VEHICLE_GROUP_FOUND_INFO();
        }
        dwSize=this.size();
    }
}

