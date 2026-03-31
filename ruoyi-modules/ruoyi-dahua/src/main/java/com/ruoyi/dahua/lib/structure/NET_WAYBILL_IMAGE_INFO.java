package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 运单图片信息
*/
public class NET_WAYBILL_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片类型, -1:未知, 0:运单大图, 1:运单二维码图,
    */
    public int              nType;
    /**
     * 二进制数据偏移
    */
    public int              nOffset;
    /**
     * 图片大小
    */
    public int              nLength;
    /**
     * 件数
    */
    public int              nQuantity;
    /**
     * 代理人
    */
    public byte[]           szAgent = new byte[32];
    /**
     * 品名
    */
    public byte[]           szGoodsName = new byte[32];
    /**
     * 货物类型
    */
    public byte[]           szGoodsType = new byte[32];
    /**
     * 航班号
    */
    public byte[]           szFlightNumber = new byte[32];
    /**
     * 始发站
    */
    public byte[]           szDeparture = new byte[32];
    /**
     * 目的站
    */
    public byte[]           szArrival = new byte[32];
    /**
     * 航班日期
    */
    public byte[]           szFlightDate = new byte[32];
    /**
     * 重量（千克）
    */
    public float            fWeight;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[260];

    public NET_WAYBILL_IMAGE_INFO() {
    }
}

