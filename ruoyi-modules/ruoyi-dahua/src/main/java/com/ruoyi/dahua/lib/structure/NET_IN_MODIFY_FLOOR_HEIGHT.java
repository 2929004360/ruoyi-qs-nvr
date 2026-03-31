package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ModifyFloorHeight 接口输入参数
*/
public class NET_IN_MODIFY_FLOOR_HEIGHT extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 视频通道号
    */
    public int              nChannel;
    /**
     * 需要修改层高的楼层号个数
    */
    public int              nFloorsNum;
    /**
     * 需要修改的楼层号层高个数
    */
    public int              nHeightNum;
    /**
     * 需要修改层高的楼层号数组
    */
    public int[]            nFloors = new int[32];
    /**
     * 需要修改的楼层号的对应层高，单位m
    */
    public double[]         dbHeight = new double[32];

    public NET_IN_MODIFY_FLOOR_HEIGHT() {
        this.dwSize = this.size();
    }
}

