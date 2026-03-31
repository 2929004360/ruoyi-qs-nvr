package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_EVENT_IVS;
import com.ruoyi.dahua.lib.enumeration.EM_EVENT_IVS_TYPE;

/**
 * 按照事件类型抓图 入参
 * 接口 {@link NetSDKLib#CLIENT_SnapPictureByEvent}
 * 出参 {@link NET_OUT_SNAP_BY_EVENT}
 *
 * @author 47040
 * @since Created in 2020/11/18 17:21
 */
public class NET_IN_SNAP_BY_EVENT extends NetSDKLib.SdkStructure {
    /**
     * 本结构体大小, 初始化时必须填写
     */
    public int              dwSize;
    /**
     * 视频通道号, 从 0开始
     */
    public int              nChannel;
    /**
     * 参考 CLIENT_RealLoadPicEx 的事件类型
     * {@link NetSDKLib#EVENT_IVS_ALL ...}
     * {@link EM_EVENT_IVS}
     * {@link EM_EVENT_IVS_TYPE}
     */
    public int              dwEventID;
    /**
     * 抓图序列号,用于和回调事件作匹配
     */
    public byte[]           szSerialNo = new byte[128];

    public NET_IN_SNAP_BY_EVENT() {
        dwSize = this.size();
    }
}

