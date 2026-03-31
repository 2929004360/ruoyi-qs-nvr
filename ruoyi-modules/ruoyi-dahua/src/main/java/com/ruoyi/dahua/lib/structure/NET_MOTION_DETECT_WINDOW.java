package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 动检支持的视频窗口
*/
public class NET_MOTION_DETECT_WINDOW extends NetSDKLib.SdkStructure
{
    /**
     * 面积阀值，取值[0, 100]
    */
    public int              nThreshold;
    /**
     * 灵敏度，取值[0, 100]
    */
    public int              nSensitive;
    /**
     * 动态检测区域的行数
    */
    public int              nMotionRow;
    /**
     * 动态检测区域的列数
    */
    public int              nMotionCol;
    /**
     * 动检检测区域块
    */
    public BYTE_ARRAY_32[]  nRegion = new BYTE_ARRAY_32[32];
    /**
     * 动态窗口ID号
    */
    public int              nId;
    /**
     * 动态窗口名称
    */
    public byte[]           szName = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[256];

    public NET_MOTION_DETECT_WINDOW() {
    }
}

