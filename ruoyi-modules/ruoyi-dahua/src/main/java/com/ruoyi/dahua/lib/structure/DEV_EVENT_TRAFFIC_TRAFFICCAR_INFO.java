package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description TrafficCar 交通车辆信息
 * @date 2022/07/29 11:20:55
 */
public class DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO extends NetSDKLib.SdkStructure {
    /**
     * 车牌号码
     */
    public byte[]           szPlateNumber = new byte[32];
    /**
     * 号牌类型 "Unknown" 未知; "Normal" 蓝牌黑牌; "Yellow" 黄牌; "DoubleYellow" 双层黄尾牌
     * "Police" 警牌;"SAR" 港澳特区号牌; "Trainning" 教练车号牌; "Personal" 个性号牌; "Agri" 农用牌
     * "Embassy" 使馆号牌; "Moto" 摩托车号牌; "Tractor" 拖拉机号牌; "Other" 其他号牌
     */
    public byte[]           szPlateType = new byte[32];
    /**
     * 车牌颜色    "Blue","Yellow", "White","Black","YellowbottomBlackText","BluebottomWhiteText","BlackBottomWhiteText","ShadowGreen","YellowGreen"
     */
    public byte[]           szPlateColor = new byte[32];
    /**
     * 车身颜色    "White", "Black", "Red", "Yellow", "Gray", "Blue","Green"
     */
    public byte[]           szVehicleColor = new byte[32];
    /**
     * 速度    单位Km/H
     */
    public int              nSpeed;
    /**
     * 触发的相关事件    参见事件列表Event List,只包含交通相关事件。
     */
    public byte[]           szEvent = new byte[64];
    /**
     * 违章代码    详见TrafficGlobal.ViolationCode
     */
    public byte[]           szViolationCode = new byte[32];
    /**
     * 违章描述
     */
    public byte[]           szViolationDesc = new byte[64];
    /**
     * 速度下限
     */
    public int              nLowerSpeedLimit;
    /**
     * 速度上限
     */
    public int              nUpperSpeedLimit;
    /**
     * 限高速宽限值    单位：km/h
     */
    public int              nOverSpeedMargin;
    /**
     * 限低速宽限值    单位：km/h
     */
    public int              nUnderSpeedMargin;
    /**
     * 车道    参见事件列表Event List中卡口和路口事件。
     */
    public int              nLane;
    /**
     * 车辆大小,-1表示未知,否则按位
     * 第0位:"Light-duty", 小型车
     * 第1位:"Medium", 中型车
     * 第2位:"Oversize", 大型车
     * 第3位:"Minisize", 微型车
     * 第4位:"Largesize", 长车
     */
    public int              nVehicleSize;
    /**
     * 车辆长度    单位米
     */
    public float            fVehicleLength;
    /**
     * 抓拍方式    0-未分类,1-全景,2-近景,4-同向抓拍,8-反向抓拍,16-号牌图像
     */
    public int              nSnapshotMode;
    /**
     * 本地或远程的通道名称,可以是地点信息    来源于通道标题配置ChannelTitle.Name
     */
    public byte[]           szChannelName = new byte[32];
    /**
     * 本地或远程设备名称    来源于普通配置General.MachineName
     */
    public byte[]           szMachineName = new byte[256];
    /**
     * 机器分组或叫设备所属单位    默认为空,用户可以将不同的设备编为一组,便于管理,可重复。
     */
    public byte[]           szMachineGroup = new byte[256];
    /**
     * 道路编号
     */
    public byte[]           szRoadwayNo = new byte[64];
    /**
     * 行驶方向 , "DrivingDirection" : ["Approach", "", ""],
     * "Approach"-上行,即车辆离设备部署点越来越近；"Leave"-下行,
     * 即车辆离设备部署点越来越远,第二和第三个参数分别代表上行和
     * 下行的两个地点
     */
    public byte[]           szDrivingDirection = new byte[3 * 256];
    /**
     * 设备地址,OSD叠加到图片上的,来源于配置TrafficSnapshot.DeviceAddress,'\0'结束
     */
    public Pointer          szDeviceAddress;
    /**
     * 车辆标识, 例如 "Unknown"-未知, "Audi"-奥迪, "Honda"-本田 ...
     */
    public byte[]           szVehicleSign = new byte[32];
    /**
     * 由车检器产生抓拍信号冗余信息
     */
    public DH_SIG_CARWAY_INFO_EX stuSigInfo = new DH_SIG_CARWAY_INFO_EX();
    /**
     * 设备部署地点
     */
    public Pointer          szMachineAddr;
    /**
     * 当前图片曝光时间,单位为毫秒
     */
    public float            fActualShutter;
    /**
     * 当前图片增益,范围为0~100
     */
    public byte             byActualGain;
    /**
     * 车道方向,0-南向北 1-西南向东北 2-西向东 3-西北向东南 4-北向南 5-东北向西南 6-东向西 7-东南向西北 8-未知 9-自定义
     */
    public byte             byDirection;
    public byte[]           byReserved = new byte[2];
    /**
     * 详细地址, 作为szDeviceAddress的补充
     */
    public Pointer          szDetailedAddress;
    /**
     * 图片防伪码
     */
    public byte[]           szDefendCode = new byte[64];
    /**
     * 关联禁止名单数据库记录默认主键ID, 0,无效；> 0,禁止名单数据记录
     */
    public int              nTrafficBlackListID;
    /**
     * 车身颜色RGBA
     */
    public NET_COLOR_RGBA   stuRGBA = new NET_COLOR_RGBA();
    /**
     * 抓拍时间
     */
    public NET_TIME         stSnapTime = new NET_TIME();
    /**
     * 记录编号
     */
    public int              nRecNo;
    /**
     * 自定义车位号（停车场用）
     */
    public byte[]           szCustomParkNo = new byte[33];
    public byte[]           byReserved1 = new byte[3];
    /**
     * 车板位号
     */
    public int              nDeckNo;
    /**
     * 空闲车板数量
     */
    public int              nFreeDeckCount;
    /**
     * 占用车板数量
     */
    public int              nFullDeckCount;
    /**
     * 总共车板数量
     */
    public int              nTotalDeckCount;
    /**
     * 违章名称
     */
    public byte[]           szViolationName = new byte[64];
    /**
     * 车重(单位 Kg)
     */
    public int              nWeight;
    /**
     * 自定义车道方向,byDirection为9时有效
     */
    public byte[]           szCustomRoadwayDirection = new byte[32];
    /**
     * 物理车道号,取值0到5
     */
    public byte             byPhysicalLane;
    public byte[]           byReserved2 = new byte[3];
    /**
     * 车辆行驶方向 {@link com.ruoyi.dahua.lib.enumeration.EM_TRAFFICCAR_MOVE_DIRECTION}
     */
    public int              emMovingDirection;
    /**
     * 对应电子车牌标签信息中的过车时间(ThroughTime)
     */
    public NET_TIME         stuEleTagInfoUTC = new NET_TIME();
    /**
     * 车窗包围盒，0~8191
     */
    public NET_RECT         stuCarWindowBoundingBox = new NET_RECT();
    /**
     * 允许名单信息
     */
    public NetSDKLib.NET_TRAFFICCAR_WHITE_LIST stuWhiteList = new NetSDKLib.NET_TRAFFICCAR_WHITE_LIST();
    /**
     * 车辆类型 {@link com.ruoyi.dahua.lib.enumeration.EM_TRAFFICCAR_CAR_TYPE}
     */
    public int              emCarType;
    /**
     * 车道类型 {@link com.ruoyi.dahua.lib.enumeration.EM_TRAFFICCAR_LANE_TYPE}
     */
    public int              emLaneType;
    /**
     * 车系年款翻译后文本内容
     */
    public byte[]           szVehicleBrandYearText = new byte[64];
    /**
     * 车辆子类型
     */
    public byte[]           szCategory = new byte[32];
    /**
     * 禁止名单信息
     */
    public NetSDKLib.NET_TRAFFICCAR_BLACK_LIST stuBlackList = new NetSDKLib.NET_TRAFFICCAR_BLACK_LIST();
    /**
     * 车流量方向 {@link NetSDKLib.EM_VEHICLE_DIRECTION}
     */
    public int              emFlowDirection;
    /**
     * 收费公路车辆通行费车型分类 {@link com.ruoyi.dahua.lib.enumeration.EM_TOLLS_VEHICLE_TYPE}
     */
    public int              emTollsVehicleType;
    /**
     * 轴型代码,参考轴型国标 0代表其他
     */
    public int              nAxleType;
    /**
     * 车轴数量
     */
    public int              nAxleCount;
    /**
     * 车轮数量
     */
    public int              nWheelNum;
    /**
     * 车身抠图
     */
    public NET_TRAFFICCAR_ORIGINAL_VEHICLE stuOriginalVehicle = new NET_TRAFFICCAR_ORIGINAL_VEHICLE();
    /**
     * 按功能划分的车辆类型 {@link NetSDKLib.EM_VEHICLE_TYPE_BY_FUNC}
     */
    public int              emVehicleTypeByFunc;
    /**
     * 车辆子品牌
     */
    public short            nSunBrand;
    /**
     * 车辆年款
     */
    public short            nBrandYear;
    /**
     * 交通灯类型,仅在EVENT_IVS_TRAFFIC_RUNREDLIGHT中有效, 0;未知, 1:箭头灯, 2:圆形灯
     */
    public int              nTrafficLightType;
    /**
     * 车牌属性 {@link com.ruoyi.dahua.lib.enumeration.EM_PLATE_ATTRIBUTE}
     */
    public int              emPlateAttribute;
    /**
     * 交通车辆信息扩展, refer to {@link NET_TRAFFICCAR_INFO_EXTERN}
     */
    public Pointer          pTrafficInfoExtern;
    /**
     * 分组类别,出租车、网约车、私家车等等
    */
    public byte[]           szCarNoGroupType = new byte[64];
    /**
     * 车辆是否冲出检测区域(0-未知 1-冲出 2-未冲出)
    */
    public int              nOverShoot;
    /**
     * 保留字节,留待扩展
     */
    public byte[]           bReserved = new byte[64-NetSDKLib.POINTERSIZE];
}

