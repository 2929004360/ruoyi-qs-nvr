package com.ruoyi.dahua.lib.callback;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.structure.NET_CB_REDIRECT_SERVER_CALLBACK_INFO;
import com.ruoyi.dahua.lib.structure.NET_IN_START_REDIRECT_SERVICE;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * @author 47081
 * @version 1.0
 * @description 重定向服务器回调函数原形扩展
 * @date 2021/3/13
 */
public interface fRedirectServerCallBackEx extends Callback {
  /**
   * @param lDevHandle 设备句柄
   * @param pInParam 设备信息,对应结构体{@link NET_CB_REDIRECT_SERVER_CALLBACK_INFO}
   * @param dwUserData 自定义数据,该自定义数据为{@link
   *     NET_IN_START_REDIRECT_SERVICE#dwUserData}传入的数据,注意保证dwUser不会被jvm回收,
   *     否则回调中接收到的dwUser可能乱码，甚至导致程序崩溃
   * @return
   */
  default int callback(NetSDKLib.LLong lDevHandle, Pointer pInParam, Pointer dwUserData) {
    NET_CB_REDIRECT_SERVER_CALLBACK_INFO info = new NET_CB_REDIRECT_SERVER_CALLBACK_INFO();
    ToolKits.GetPointerDataToStruct(pInParam, 0, info);
    dealWithData(lDevHandle.longValue(), info, dwUserData);
    return 0;
  }

  /**
   * 数据处理
   *
   * @param devHandler 设备句柄
   * @param info 主动注册重定向设备信息
   * @param userData 用户数据
   */
  void dealWithData(long devHandler, NET_CB_REDIRECT_SERVER_CALLBACK_INFO info, Pointer userData);
}
