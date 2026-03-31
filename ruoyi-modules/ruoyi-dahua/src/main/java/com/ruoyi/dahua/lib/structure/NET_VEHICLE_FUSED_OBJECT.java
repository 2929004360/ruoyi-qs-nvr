package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 目标属性
 * @origin autoTool
 * @date 2023/02/14 20:02:01
 */
public class NET_VEHICLE_FUSED_OBJECT extends NetSDKLib.SdkStructure {
    /**
     * 目标ID
     */
    public int              nObjectID;
    /**
     * 物体类型 {@link NetSDKLib.EM_ANALYSE_OBJECT_TYPE}
     */
    public int              emObjectType;
    /**
     * 目标速度
     */
    public float            fSpeed;
    /**
     * 目标当前GPS坐标
     */
    public NET_GPS_POSITION stuGPSPosition = new NET_GPS_POSITION();
    /**
     * 车牌信息
     */
    public NetSDKLib.NET_MSG_OBJECT stuPlate = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 机动车信息
     */
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 非机动车信息
     */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
    /**
     * 车道ID
     */
    public int              nLaneID;
    /**
     * 加速度，标量 m/s2
     */
    public float            fAcceleration;
    /**
     * 3D坐标,设备中心坐标系
     */
    public NET_3DFLOAT_POINT stu3DPointPosition = new NET_3DFLOAT_POINT();
    /**
     * 3D尺寸,单位米
     */
    public NET_3DFLOAT_SIZE stu3DSize = new NET_3DFLOAT_SIZE();
    /**
     * 3D姿态, 与正东方向的的逆时针夹角，单位度(0~360)
     */
    public NET_3DFLOAT_POSTURE stu3DPosture = new NET_3DFLOAT_POSTURE();
    /**
     * 速度矢量，m/s
     */
    public NET_3DFLOAT_POINT stuVelocity = new NET_3DFLOAT_POINT();
    /**
     * 车系
     */
    public byte[]           szBrandYearText = new byte[64];
    /**
     * 目标当前所处的区域信息
     */
    public NET_LOCATED_AREAS[] stuLocatedAreas = new NET_LOCATED_AREAS[4];
    /**
     * 目标当前所处的区域信息数量
     */
    public int              nLocatedAreasNum;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[3540];

    public NET_VEHICLE_FUSED_OBJECT() {
        for (int i = 0; i < stuLocatedAreas.length; i++) {
            stuLocatedAreas[i] = new NET_LOCATED_AREAS();
        }
    }
}

