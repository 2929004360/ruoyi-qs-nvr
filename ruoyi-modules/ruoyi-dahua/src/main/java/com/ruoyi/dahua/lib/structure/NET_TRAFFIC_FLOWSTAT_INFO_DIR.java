package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import java.io.UnsupportedEncodingException;

/**
 * @author ： 260611
 * @description ： 车辆流量统计车辆行驶方向信息
 * @since ： Created in 2022/03/10 10:12
 */

public class NET_TRAFFIC_FLOWSTAT_INFO_DIR extends NetSDKLib.SdkStructure {
    /**
     * 行驶方向
     */
    public int              emDrivingDir;
    /**
     * 上行地点
     */
    public byte[]           szUpGoing = new byte[16];
    /**
     * 下行地点
     */
    public byte[]           szDownGoing = new byte[16];
    /**
     * 保留字节
     */
    public byte[]           reserved = new byte[32];

    @Override
    public String toString() {
        try {
            return "NET_TRAFFIC_FLOWSTAT_INFO_DIR{" +
                    "emDrivingDir=" + emDrivingDir +
                    ", szUpGoing UTF-8=" + new String(szUpGoing,"UTF-8").trim() +
                    ", szUpGoing GBK=" + new String(szUpGoing,"GBK").trim() +
                    ", szDownGoing UTF-8=" + new String(szDownGoing,"UTF-8").trim() +
                    ", szDownGoing GBK=" + new String(szDownGoing,"GBK").trim() +
                    '}';
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

