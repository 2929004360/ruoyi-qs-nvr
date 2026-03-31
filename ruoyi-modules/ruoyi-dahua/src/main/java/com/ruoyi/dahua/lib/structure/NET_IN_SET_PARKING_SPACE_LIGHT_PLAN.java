package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 设置车位指示灯亮灯计划入参
*/
public class NET_IN_SET_PARKING_SPACE_LIGHT_PLAN extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 物理车位号，请优先使用此字段.值为负数时表示此字段无效
    */
    public int              nPhysicalLane;
    /**
     * 自定义车位编号，物理车位号字段无效时，使用此字段
    */
    public byte[]           szCustomParkNo = new byte[32];
    /**
     * 亮灯计划数目
    */
    public int              nLightPlanNum;
    /**
     * 亮灯计划信息，由用户申请和释放内存,参见结构体定义 {@link NET_PARKING_SPACE_LIGHT_PLAN_INFO}
    */
    public Pointer          pstuLightPlan;
    /**
     * 通道号，从0开始。存在该字段标明前端设备添加到存储设备上的通道号
    */
    public int              nChannel;

    public NET_IN_SET_PARKING_SPACE_LIGHT_PLAN() {
        this.dwSize = this.size();
    }
}

