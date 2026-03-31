package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 录像/图片存储点配置, 对应 NET_EM_CFG_RECORD_STORAGE_POINT
*/
public class NET_CFG_RECORD_STORAGE_POINT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 定时录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuTimingRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 手动录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuManualRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 动检、遮挡、丢失录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuVideoDetectRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 报警录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuAlarmRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 卡号录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuCardRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 其他事件录像存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuEventRecord = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 定时抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuTimingSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 手动抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuManualSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 动检、遮挡、丢失抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuVideoDetectSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 报警抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuAlarmSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 卡号抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuCardSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();
    /**
     * 其他事件抓图存储点,参见结构体定义 {@link NET_RECORD_STORAGE_POINT_DETAIL_INFO}
    */
    public NET_RECORD_STORAGE_POINT_DETAIL_INFO stuEventSnapShot = new NET_RECORD_STORAGE_POINT_DETAIL_INFO();

    public NET_CFG_RECORD_STORAGE_POINT_INFO() {
        this.dwSize = this.size();
    }
}

