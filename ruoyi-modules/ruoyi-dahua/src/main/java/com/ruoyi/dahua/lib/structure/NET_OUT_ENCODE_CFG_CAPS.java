package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.GetDevCaps_Type;
import com.ruoyi.dahua.lib.enumeration.REC_TYPE;
import com.ruoyi.dahua.lib.enumeration.SNAP_TYPE;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_N_ENCODE_AUX;

/**
 * 获取设备编码配置对应能力输出参数
 * 能力集枚举 {@link GetDevCaps_Type#NET_ENCODE_CFG_CAPS}
 * 能力集接口 {@link NetSDKLib#CLIENT_GetDevCaps}
 *
 * @author 47040
 * @since Created at 2021/5/25 22:55
 */
public class NET_OUT_ENCODE_CFG_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 主码流配置对应能力 如果有多个:
     * 第一个表示普通录像码流
     * 第二个表示动检录像码流
     * 第三个表示报警录像码流
     * 有效长度由 {@link #nMainFormCaps} 决定
     */
    public NET_STREAM_CFG_CAPS[] stuMainFormatCaps = new NET_STREAM_CFG_CAPS[REC_TYPE.NET_REC_TYPE_NUM.getValue()];
    /**
     * 辅码流配置对应能力 如果有多个:
     * 第一个表示辅码流 1
     * 第二个表示辅码流 2
     * 第三个表示辅码流 3
     * 有效长度由 {@link #nExtraFormCaps} 决定
     */
    public NET_STREAM_CFG_CAPS[] stuExtraFormatCaps = new NET_STREAM_CFG_CAPS[NET_N_ENCODE_AUX];
    /**
     * 抓图码流配置对应能力:如果有多个:
     * 第一个表示普通抓图
     * 第二个表示报警抓图
     * 有效长度由 {@link #nSnapFormatCaps} 决定
     */
    public NET_STREAM_CFG_CAPS[] stuSnapFormatCaps = new NET_STREAM_CFG_CAPS[SNAP_TYPE.SNAP_TYP_NUM.getValue()];
    /**
     * 有效的主码流配置对应的能力个数
     * 代表了 {@link #stuMainFormatCaps} 的有效长度
     */
    public int              nMainFormCaps;
    /**
     * 有效的辅码流配置对应的能力个数
     * 代表了 {@link #stuExtraFormatCaps} 的有效长度
     */
    public int              nExtraFormCaps;
    /**
     * 有效的抓图码流配置对应的能力个数
     * 代表了 {@link #stuSnapFormatCaps} 的有效长度
     */
    public int              nSnapFormatCaps;

    public NET_OUT_ENCODE_CFG_CAPS() {
        dwSize = this.size();
        for (int i = 0; i < stuMainFormatCaps.length; i++) {
            stuMainFormatCaps[i] = new NET_STREAM_CFG_CAPS();
        }
        for (int i = 0; i < stuExtraFormatCaps.length; i++) {
            stuExtraFormatCaps[i] = new NET_STREAM_CFG_CAPS();
        }
        for (int i = 0; i < stuSnapFormatCaps.length; i++) {
            stuSnapFormatCaps[i] = new NET_STREAM_CFG_CAPS();
        }
    }
}

