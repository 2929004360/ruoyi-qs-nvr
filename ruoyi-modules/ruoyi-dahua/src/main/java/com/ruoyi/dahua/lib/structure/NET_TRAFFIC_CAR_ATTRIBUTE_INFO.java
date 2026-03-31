package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 车辆以图搜图时可选择的属性过滤条件
 * @date 2022/11/21 17:53:18
 */
public class NET_TRAFFIC_CAR_ATTRIBUTE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 车身颜色的个数,最大值是32
     */
    public int              nVehicleColorArrayNums;
    /**
     * 车身颜色，参考EM_OBJECT_COLOR_TYPE
     */
    public int[]            emVehicleColorArray = new int[32];
    /**
     * 车辆类型的个数,最大值是32
     */
    public int              nCategoryArrayNums;
    /**
     * 车辆类型,参考EM_NET_VEHICLE_TYPE
     */
    public int[]            emCategoryArray = new int[32];
    /**
     * 车标
     */
    public byte[]           szVehicleSign = new byte[64];
    /**
     * 车辆子品牌 需要通过映射表得到真正的子品牌 映射表详见开发手册
     */
    public int              nSubBrand;
    /**
     * 车辆年款 需要通过映射表得到真正的年款 映射表详见开发手册
     */
    public int              nBrandYear;
    /**
     * 是否有摆件, 0: 不限  1: 无  2: 有
     */
    public int              nFurniture;
    /**
     * 是否有挂件, 0: 不限  1: 无  2: 有
     */
    public int              nPendant;
    /**
     * 是否有年检标, 0: 不限  1: 无  2: 有
     */
    public int              nAnnualInspection;
    /**
     * 是否有遮阳板, 0: 不限  1: 无  2: 有
     */
    public int              nSunShade;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[256];

    public NET_TRAFFIC_CAR_ATTRIBUTE_INFO() {
    }
}

