package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 云台可视域状态结构
 * @date 2022/10/13 16:01:59
 */
public class DH_OUT_PTZ_VIEW_RANGE_STATUS extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 可视距离, 单位:米
     */
    public double           dbDistance;
    /**
     * 水平可视角度, 0~1800, 单位:十分之一度
     */
    public int              nAngelH;
    /**
     * 水平方位角度, 0~3600, 单位:十分之一度
     */
    public int              nAzimuthH;
    /**
     * 水平倾斜角度, -900~900, 单位:十分之一度
     */
    public int              nInclinationH;
    /**
     * 垂直可视角度, 0~1800, 单位:十分之一度
     */
    public int              nAngelV;
    /**
     * 垂直方位角度, 0~3600, 单位:十分之一度
     */
    public int              nAzimuthV;
    /**
     * 水平角度，扩大100倍值，[0-36000]
     */
    public int              nPan;
    /**
     * 垂直角度，扩大100倍值，[-18000-18000]
     */
    public int              nTilt;
    /**
     * 显示放大倍数，扩大100倍值，[0-MaxZoom*100]
     */
    public int              nZoom;

    public DH_OUT_PTZ_VIEW_RANGE_STATUS() {
        this.dwSize = this.size();
    }

    @Override
    public String toString() {
        return "DH_OUT_PTZ_VIEW_RANGE_STATUS{" +
                "dwSize=" + dwSize +
                ", dbDistance=" + dbDistance +
                ", nAngelH=" + nAngelH +
                ", nAzimuthH=" + nAzimuthH +
                ", nInclinationH=" + nInclinationH +
                ", nAngelV=" + nAngelV +
                ", nAzimuthV=" + nAzimuthV +
                '}';
    }
}

