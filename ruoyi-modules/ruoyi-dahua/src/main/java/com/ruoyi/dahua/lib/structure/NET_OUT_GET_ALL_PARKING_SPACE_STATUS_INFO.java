package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_QueryDevInfo NET_QUERY_GET_ALL_PARKING_SPACE_STATUS 类型接口输入参数
*/
public class NET_OUT_GET_ALL_PARKING_SPACE_STATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 字节对齐
    */
    public byte[]           byReserved = new byte[4];
    /**
     * 车位信息（需要用户主动申请内存，在调用前填写）,参见结构体定义 {@link NET_PARKING_SPACE_INFO}
    */
    public Pointer          pstuParkingSpaceInfo;
    /**
     * pstuParkingStatusInfo中分配的NET_PARKING_SPACE_INFO数组个数,需要调用接口前填写
    */
    public int              nMaxParkingSpace;
    /**
     * 实际返回车位数
    */
    public int              nParkingSpaceNum;
    /**
     * 场景类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKING_SPACE_SCENE_TYPE}
    */
    public int              emSceneType;
    /**
     * 统计模式, emSceneType为EM_PARKING_SPACE_SCENE_TYPE_PARKING_STATISTICS时有效,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKING_SPACE_STATISTICS_MODE}
    */
    public int              emStatisticsMode;
    /**
     * 区域状态, emSceneType为EM_PARKING_SPACE_SCENE_TYPE_PARKING_STATISTICS时且emStatisticsMode为EM_PARKING_SPACE_STATISTICS_MODE_AREA_MODE时有效,参见结构体定义 {@link NET_PARKING_SPACE_AREA_STATUS}
    */
    public NET_PARKING_SPACE_AREA_STATUS[] stuAreaStatus = new NET_PARKING_SPACE_AREA_STATUS[10];
    /**
     * 实际返回区域状态个数
    */
    public int              nAreaStatusNum;
    /**
     * 字节对齐
    */
    public byte[]           szReserved = new byte[4];

    public NET_OUT_GET_ALL_PARKING_SPACE_STATUS_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuAreaStatus.length; i++){
            stuAreaStatus[i] = new NET_PARKING_SPACE_AREA_STATUS();
        }
    }
}

