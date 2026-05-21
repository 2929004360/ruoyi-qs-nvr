package com.ruoyi.gb28181.api.domain;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备配置对象
 */
@Data
public class DeviceConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 命令类型
     */
    private String cmdType;

    /**
     * 命令序列号
     */
    private String sn;

    /**
     * 目标设备编码
     */
    private String deviceId;

    /**
     * 查询结果标志
     */
    private String result;

    /**
     * 基本参数
     */
    private JSONObject basicParam;

    /**
     * 视频参数范围
     */
    private JSONObject videoParamOpt;

    /**
     * SVAC编码配置
     */
    private JSONObject svacEncodeConfig;

    /**
     * SVAC解码配置
     */
    private JSONObject svacDecodeConfig;

    /**
     * 视频参数属性配置
     */
    private JSONObject videoParamAttribute;

    /**
     * 录像计划配置
     */
    private JSONObject videoRecordPlan;

    /**
     * 报警录像配置
     */
    private JSONObject videoAlarmRecord;

    /**
     * 视频画面遮挡配置
     */
    private JSONObject pictureMask;

    /**
     * 画面翻转配置
     */
    private JSONObject frameMirror;

    /**
     * 报警上报开关配置
     */
    private JSONObject alarmReport;

    /**
     * 前端OSD设置
     */
    private JSONObject osdConfig;

    /**
     * 图像抓拍配置
     */
    private JSONObject snapshot;

    /**
     * 看守位配置
     */
    private JSONObject homePosition;

    /**
     * 巡航轨迹列表
     */
    private JSONObject cruiseTrackList;
}
