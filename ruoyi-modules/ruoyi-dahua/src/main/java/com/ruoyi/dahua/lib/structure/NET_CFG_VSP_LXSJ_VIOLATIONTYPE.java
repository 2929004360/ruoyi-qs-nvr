 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

public class NET_CFG_VSP_LXSJ_VIOLATIONTYPE extends NetSDKLib.SdkStructure {
/** 超速*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficOverSpeed = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 违法变道*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficCrossLane = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 违法停车*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficParking = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 违法倒车*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficBacking = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 逆行*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficRetrograde = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 违停预警*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuParkingWarning = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 卡口*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficJunction = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 礼让行人*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficPedestrainPriority = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 闯红灯*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficRunRedLight = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 不按车道行驶*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficWrongRoute = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 车辆拥堵禁入*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficJamForbidInto = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 压白线*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficOverLine = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 有车占道*/
    public			NET_CFG_VIOLATIONTYPE_NORMAL stuTrafficVehicleInRoute = new NET_CFG_VIOLATIONTYPE_NORMAL();
/** 预留*/
    public			byte[]         byReserved = new byte[13824];
}

