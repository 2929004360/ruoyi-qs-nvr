package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 机动车信息
 * @date 2022/11/24 17:12:01
 */
public class NET_HISTORY_TRAFFIC_CAR_INFO extends NetSDKLib.SdkStructure {
    /**
     * 非机动车唯一标识符
     */
    public byte[]           szUID = new byte[64];
    /**
     * 包围盒(8192坐标系)
     */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     * 车牌号码
     */
    public byte[]           szPlateNumber = new byte[32];
    /**
     * 车牌类型
     */
    public byte[]           szPlateType = new byte[32];
    /**
     * 车牌颜色
     */
    public byte[]           szPlateColor = new byte[32];
    /**
     * 车身颜色
     */
    public byte[]           szVehicleColor = new byte[32];
    /**
     * 车辆类型
     */
    public byte[]           szCategory = new byte[32];
    /**
     * 特种车辆
     */
    public byte[]           szSpecialCar = new byte[32];
    /**
     * 车辆标识
     */
    public byte[]           szVehicleSign = new byte[64];
    /**
     * 车辆子品牌
     */
    public int              nSubBrand;
    /**
     * 车辆年款
     */
    public int              nBrandYear;
    /**
     * 摆件数量
     */
    public int              nFurnitureCount;
    /**
     * 挂件数量
     */
    public int              nPendantCount;
    /**
     * 年检标数量
     */
    public int              nAnnualInspectionCount;
    /**
     * 年检标顺序, 0: 未知  1: 乱排  2: 横排  3: 竖排
     */
    public int              nAnnualInspectionShape;
    /**
     * 主驾驶遮阳板状态 {@link com.ruoyi.dahua.lib.enumeration.NET_SUNSHADE_STATE}
     */
    public int              emSunShade;
    /**
     * 副驾驶遮阳板状态 {@link com.ruoyi.dahua.lib.enumeration.NET_SUNSHADE_STATE}
     */
    public int              emSubSeatSunShade;
    /**
     * 卡片数量
     */
    public int              nCardCount;
    /**
     * 主驾驶安全带 {@link com.ruoyi.dahua.lib.enumeration.NET_SAFEBELT_STATE}
     */
    public int              emSafeBelt;
    /**
     * 是否在打电话, 0: 未知 1: 否 2: 是
     */
    public int              nCalling;
    /**
     * 是否在玩手机, 0: 未知 1: 否 2: 是
     */
    public int              nPlayPhone;
    /**
     * 是否在抽烟, 0: 未知 1: 否 2: 是
     */
    public int              nSmoking;
    /**
     * 副驾驶是否有人, 0: 未知 1: 否 2: 是
     */
    public int              nSubSeatPeople;
    /**
     * 副驾驶安全带 {@link com.ruoyi.dahua.lib.enumeration.NET_SAFEBELT_STATE}
     */
    public int              emSubSeatSafeBelt;
    /**
     * 是否抱小孩, 0: 未知 1: 否 2: 是
     */
    public int              nHoldBaby;
    /**
     * 是否有天窗, 0: 未知 1: 否 2: 是
     */
    public int              nSunroof;
    /**
     * 是否有行李架, 0: 未知 1: 否 2: 是
     */
    public int              nLuggageRack;
    /**
     * 是否有车辆撞损, 0: 未知 1: 否 2: 是
     */
    public int              nVehicleCollision;
    /**
     * 是否有喷绘, 0: 未知 1: 否 2: 是
     */
    public int              nVehiclePrint;
    /**
     * 是否有备胎, 0: 未知 1: 否 2: 是
     */
    public int              nBackupTire;
    /**
     * 后备箱状态, 0: 未知 1: 关闭 2: 打开
     */
    public int              nTrunk;
    /**
     * 车牌污损状态, 0: 未知 1: 正常 2: 无牌 3: 部分遮挡/污损  4: 完全遮挡/污损
     */
    public int              nPlateAttribute;
    /**
     * 渣土车遮盖状态, 0: 未知 1: 有遮盖 2: 无遮盖空载 3: 无遮盖满载
     */
    public int              nMuskHide;
    /**
     * 机动车图片信息
     */
    public SDK_PIC_INFO_EX  stuImage = new SDK_PIC_INFO_EX();
    /**
     * 车辆停车是否压线, 0: 未知  1: 未压线停车 2: 压线停车
     */
    public int              nPressParkingStatus;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_HISTORY_TRAFFIC_CAR_INFO() {
    }
}

