package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * AIS融合目标信息扩展
*/
public class NET_AIS_FUSE_OBJECT_EX extends NetSDKLib.SdkStructure
{
    /**
     * 船的宽度，单位米 取值范围0-255
    */
    public int              nBreadth;
    /**
     * 导航状态: 0-未知 1-锚泊 2-未操纵 3-有限适航性 4-受船舶吃水限制 5-系泊 6-搁浅 7-从事捕捞 8-航行中 , 9-留做将来修正导航状态 10-留做将来修正导航状态 11-机动船尾推作业（区域使用） , 12-机动船顶推或侧推作业（区域使用）， 13-留做将来用， 14-AIS-SART（现行的）、 MOB-AIS、 EPIRB-AIS 15-发动机使用中
    */
    public int              nNavigationalStatus;
    /**
     * 船舶类型 0表示未知
    */
    public int              nShipType;
    /**
     * 目前最大静态吃水, 以1/10 m为单位, 255 = 吃水25.5 m或更大, 0 = 不可用 = 默认值
    */
    public int              nStaticDraft;
    /**
     * 船只AIS信息中的航首向
    */
    public double           dbCourseOverGround;
    /**
     * 英文船名
    */
    public byte[]           szBoatCodeEnglish = new byte[64];
    /**
     * 目的地
    */
    public byte[]           szDestination = new byte[128];
    /**
     * 船只呼号
    */
    public byte[]           szWail = new byte[64];
    /**
     * 预计到达时间
    */
    public byte[]           szEstimatedTimeArrival = new byte[20];
    /**
     * AIS状态: 0-未知 1-关闭 2-正常
    */
    public int              nStatus;
    /**
     * IMO编号
    */
    public long             nIMONumber;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[120];

    public NET_AIS_FUSE_OBJECT_EX() {
    }
}

