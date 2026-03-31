package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 雪花屏检测
 * @date 2021/2/22
 */
public class CFG_VIDEO_SNOWFLAKE_DETECTION extends NetSDKLib.SdkStructure {
  /** 使能配置 */
    public int              bEnable;
  /** 预警阈值, 范围:1~100 */
    public int              nWarnThreshold;
  /** 报警阈值, 范围:1~100 */
    public int              nAlarmThreshold;
  /** 最短持续时间, 单位：秒, 取值: 0~65535 */
    public int              nMinDuration;
}

