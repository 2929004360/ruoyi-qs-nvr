package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * NET_VEHICLE_OBJECT szDrivingDirection 的拆解字段
 * {@link NET_VEHICLE_OBJECT#szDrivingDirection}
 *
 * @author ： 47040
 * @since ： Created in 2020/12/17 13:42
 */
public class NET_VEHICLE_DRIVING_DIRECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 参数细节
     */
    public byte[]           info = new byte[32];

    @Override
    public String toString() {
        return new String(info).trim();
    }
}

