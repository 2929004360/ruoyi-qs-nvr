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
 * @description 动检支持的视频窗口配置
 * @date 2021/4/9
 * @version 1.0
 */
public class CFG_MOTION_WINDOW extends NetSDKLib.SdkStructure {
  /** 面积阀值，取值[0, 100] */
    public int              nThreshold;
  /** 灵敏度，取值[0, 100] */
    public int              nSensitive;
  /** 动检窗口区域, 坐标位置取值[0, 8192) */
    public NetSDKLib.CFG_RECT stuWindow;
}

