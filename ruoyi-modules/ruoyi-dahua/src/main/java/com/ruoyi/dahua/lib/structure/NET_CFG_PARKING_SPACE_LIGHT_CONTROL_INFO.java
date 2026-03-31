package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * NET_CFG_PARKING_SPACE_LIGHT_CONTROL_INFO 车位状态指示灯控制配置
*/
public class NET_CFG_PARKING_SPACE_LIGHT_CONTROL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 指示灯控制配置数组个数
    */
    public int              nParkingSpaceLightControlNum;
    /**
     * 指示灯控制配置数组指针，由用户申请释放，大小为sizeof(NET_PARKING_SPACE_LIGHT_CONTROL)*nParkingSpaceLightControlNum,参见结构体定义 {@link NET_PARKING_SPACE_LIGHT_CONTROL}
    */
    public Pointer          pstuParkingSpaceLightControl;

    public NET_CFG_PARKING_SPACE_LIGHT_CONTROL_INFO() {
        this.dwSize = this.size();
    }
}

