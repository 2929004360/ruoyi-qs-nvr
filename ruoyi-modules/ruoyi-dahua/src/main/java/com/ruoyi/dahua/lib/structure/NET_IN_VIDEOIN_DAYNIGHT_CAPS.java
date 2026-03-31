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
 * @description CLIENT_GetDevCaps NET_VIDEOIN_DAYNIGHT_CAPS 命令入参,日夜模式能力集
 * @date 2021/4/9
 * @version 1.0
 */
public class NET_IN_VIDEOIN_DAYNIGHT_CAPS extends NetSDKLib.SdkStructure {
  /** 用户使用时赋值为结构体大小 */
    public int              dwSize;
  /** 通道号 */
    public int              nChannel;

  public NET_IN_VIDEOIN_DAYNIGHT_CAPS() {
    this.dwSize = size();
  }
}

