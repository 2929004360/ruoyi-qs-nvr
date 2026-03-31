package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 雷球校准配置入参(对应 EM_RADAR_OPERATE_TYPE_SETCALIBRATION)
 * @date 2022/08/04 10:13:32
 */
public class NET_IN_RADAR_SETCALIBRATION extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[4];
    /**
     * 图片中校准点的像素坐标
     */
    public NET_RADAR_PIXELPOINT stuPixelPoint = new NET_RADAR_PIXELPOINT();
    /**
     * 球机的安装高度，单位米
     */
    public double           dInstallHeigt;

    public NET_IN_RADAR_SETCALIBRATION() {
        this.dwSize = this.size();
    }
}

