package com.ruoyi.dahua.lib.structure;
/**
 * @author 47081
 * @desc
 * @version 1.0.0
 * @date 2021/4/9
 */
import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @description CLIENT_GetDevCaps NET_VIDEOIN_BACKLIGHT_CAPS 命令出参
 * @date 2021/4/9
 * @version 1.0
 */
public class NET_OUT_VIDEOIN_DAYNIGHT_CAPS extends NetSDKLib.SdkStructure {
  /** 用户使用时赋值为结构体大小 */
    public int              dwSize;
  /** 球机机芯日夜设置能力 */
    public int              bSupport;
  /** 实际支持的ICR切换类型个数 */
    public int              nICRType;
  /** ICR切换类型列表 */
    public int[]            emICRType = new int[8];
  /** 实际支持的彩转黑模式个数 */
    public int              nColorBlackMode;
  /** 支持的彩转黑模式列表 */
    public int[]            emColorBlackMode = new int[8];
  /** 彩转黑灵敏度最小值 */
    public int              nSensitivityRangeMin;
  /** 彩转黑灵敏度最大值 */
    public int              nSensitivityRangeMax;
  /** 日夜模式切换延时最小值 */
    public int              nDelayRangeMin;
  /** 日夜模式切换延时最大值 */
    public int              nDelayRangeMax;

  public NET_OUT_VIDEOIN_DAYNIGHT_CAPS() {
    this.dwSize = size();
  }
}

