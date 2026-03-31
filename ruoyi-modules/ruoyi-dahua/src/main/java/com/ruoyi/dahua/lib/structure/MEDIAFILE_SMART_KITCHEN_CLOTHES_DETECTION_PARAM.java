package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  智慧厨房查询条件 ( CLIENT_FindFileEx + NET_FILE_QUERY_SMART_KITCHEN_CLOTHES_DETECTION )
 * @date 2021/7/26 15:35
 */
public class MEDIAFILE_SMART_KITCHEN_CLOTHES_DETECTION_PARAM extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public 	int             nChannelID;                           // 通道号从0开始,-1表示查询所有通道
    public 	NetSDKLib.NET_TIME stuBeginTime;                      // 开始时间
    public 	NetSDKLib.NET_TIME stuEndTime;                        // 结束时间
    public  NET_SMART_KITCHEN_CLOTHES_CHEF_MASK stuChefMask = new NET_SMART_KITCHEN_CLOTHES_CHEF_MASK(); // 口罩相关属性状态信息
    public  NET_SMART_KITCHEN_CLOTHES_CHEF_HAT stuChefHat = new NET_SMART_KITCHEN_CLOTHES_CHEF_HAT(); // 厨师帽相关属性状态信息
    public 	NET_SMART_KITCHEN_CLOTHES_CHEF_CLOTHES stuChefClothes = new NET_SMART_KITCHEN_CLOTHES_CHEF_CLOTHES(); // 厨师服相关属性状态信息
    public int              bOnlySupportRealUTC;                  // 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stuBeginTime, stuEndTime), 为FALSE表示仅下发stuBeginTime, stuEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
    public NET_TIME         stuStartTimeRealUTC;                  // UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥
    public NET_TIME         stuEndTimeRealUTC;                    // UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥

    public MEDIAFILE_SMART_KITCHEN_CLOTHES_DETECTION_PARAM() {
        this.dwSize = this.size();
    }
}

