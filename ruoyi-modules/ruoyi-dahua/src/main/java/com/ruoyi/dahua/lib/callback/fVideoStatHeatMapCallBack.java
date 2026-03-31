package com.ruoyi.dahua.lib.callback;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * @author 47081
 * @version 1.0
 * @description 热度图数据回调
 * @date 2020/9/21
 */
public interface fVideoStatHeatMapCallBack extends Callback {
  /**
   * @param lAttachHandle 订阅句柄
   * @param pBuf 回调上来的数据，对应结构体{@link com.ruoyi.dahua.lib.structure.NET_CB_VIDEOSTAT_HEATMAP}
   * @param pBinData 回调上来的二进制数据
   * @param dwBinDataLen 二进制数据长度
   * @param dwUser 用户数据
   */
  void invoke(
      NetSDKLib.LLong lAttachHandle,
      Pointer pBuf,
      Pointer pBinData,
      int dwBinDataLen,
      Pointer dwUser);
}
