package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 删除车牌库中的车辆 过滤条件
* @date 2022/02/25 15:03:27
*/
public class NET_DELETE_VEHICLE_CONDITION_INFO extends NetSDKLib.SdkStructure {
/** 
车辆所在国家,2字节，符合ISO3166规范
*/
    public			byte[]         szPlateCountry = new byte[4];
/** 
车牌号码
*/
    public			byte[]         szPlateNumber = new byte[64];
/** 
车型(轿车、卡车等)
 // 001  巡逻车
 // 002  交警车辆
 // 003  消防车
 // 004  单兵
 // 005  其他警车
 // 006  其他设备
 // 020  政府车辆
 // 031  校车
 // 032  运钞车
 // 033  客运车辆
 // 034  公交车
 // 035  出租车
 // 036  危险品车辆
*/
    public			int            nVehicleType;
/** 
车辆车标,需要通过映射表得到真正的车标.同卡口事件的CarLogoIndex
*/
    public			int            nBrand;
/** 
车色 第一个元素表示红色分量值； 第二个元素表示绿色分量值； 第三个元素表示蓝色分量值；
 注意：第四个元素不再表示透明度分量，而用来表示车色字段是否是一个有效的过滤条件，0 - 无效的过滤条件，非0 - 有效的过滤条件
*/
    public NET_COLOR_RGBA   stuVehicleColor = new NET_COLOR_RGBA();
/** 
车主名称
*/
    public			byte[]         szOwnerName = new byte[64];
/** 
车主证件号码,工号,或其他编号
*/
    public			byte[]         szPersonID = new byte[32];
/** 
车主电话号码
*/
    public			byte[]         szPhoneNo = new byte[128];
/** 
车主电子邮箱
*/
    public			byte[]         szEmail = new byte[32];
/** 
车主家庭地址(IVSS需求)
*/
    public			byte[]         szHomeAddress = new byte[128];
/** 

*/
    public			byte[]         bReserved = new byte[512];
}

