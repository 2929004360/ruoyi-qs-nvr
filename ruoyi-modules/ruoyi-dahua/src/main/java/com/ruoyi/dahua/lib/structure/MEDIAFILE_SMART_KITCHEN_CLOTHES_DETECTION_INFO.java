package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * @author 291189
 * @version 1.0
 * @description 智慧厨房查询结果 ( CLIENT_FindFileEx + NET_FILE_QUERY_SMART_KITCHEN_CLOTHES_DETECTION )
 * @date 2021/7/26 16:00
 */
public class MEDIAFILE_SMART_KITCHEN_CLOTHES_DETECTION_INFO extends NetSDKLib.SdkStructure {
    public  int             dwSize;                               // 结构体大小
    public 	int             nChannelID;                           // 通道号从0开始,-1表示查询所有通道
    public  NetSDKLib.NET_TIME stuStartTime;                      // 开始时间
    public 	NetSDKLib.NET_TIME stuEndTime;                        // 结束时间
    public  int             emHasMask;                            /** 厨师口罩状态 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_STATE} */
    public 	int             emHasChefHat;                         /** 厨师帽状态 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_STATE} */
    public 	int             emHasChefClothes;                     /** 厨师服状态 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_STATE} */
    public  int             emChefClothesColor;                   /**厨师服颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_COLOUR} */
    public 	byte[]          szFaceImagePath = new byte[260];      // 人脸小图路径
    public 	int             nFaceImageLength;                     // 人脸小图图片大小，单位字节
    public  byte[]          szHumanImagePath = new byte[260];     // 人体小图路径
    public  int             nHumanImageLength;                    // 人体小图图片大小，单位字节
    public  byte[]          szSceneImagePath = new byte[260];     // 人体小图对应的全景图路径
    public 	int             nSceneImageLength;                    // 人体小图对应的全景图图片大小，单位字节
    public  int             bRealUTC;                             // 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC), 为FALSE表示仅stuStartTime和stuEndTime有效(仅使用stuStartTime和stuEndTime)
    public  NetSDKLib.NET_TIME stuStartTimeRealUTC;               // UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用
    public  NetSDKLib.NET_TIME stuEndTimeRealUTC;                 // UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用

    public MEDIAFILE_SMART_KITCHEN_CLOTHES_DETECTION_INFO() {
        this.dwSize = this.size();
    }
}

