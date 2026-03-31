package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 房屋信息
*/
public class FACERECOGNITION_CUSTOM_HOUSE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 物业费缴纳状态, 0-未知 1-已缴纳 2-未缴纳
    */
    public int              nHousePayState;
    /**
     * 楼层
    */
    public int              nFloor;
    /**
     * 房屋编号
    */
    public byte[]           szHouseNo = new byte[64];
    /**
     * 用户单元
    */
    public byte[]           szUnitNo = new byte[64];
    /**
     * 小区名称
    */
    public byte[]           szVillageName = new byte[128];
    /**
     * 楼栋编号
    */
    public byte[]           szBuildingNo = new byte[64];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[696];

    public FACERECOGNITION_CUSTOM_HOUSE_INFO() {
    }
}

