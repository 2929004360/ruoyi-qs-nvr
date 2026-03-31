package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.callback.fRedirectServerCallBackEx;
import com.sun.jna.Pointer;

/**
 * @author 47081
 * @version 1.0
 * @description CLIENT_StartRedirectServiceEx接口输入参数, {@link
 *     NetSDKLib#CLIENT_StartRedirectServiceEx(Pointer, Pointer)}
 * @date 2021/3/13
 */
public class NET_IN_START_REDIRECT_SERVICE extends NetSDKLib.SdkStructure {
  /** 结构体大小, 需要赋值为当前结构体所在大小 */
    public int              dwSize;
  /** 预留字节 */
    public byte[]           byReserved = new byte[4];
  /** 主动注册重定向服务器IP */
    public byte[]           szIP = new byte[64];
  /** 主动注册重定向服务器端口 */
    public short            nPort;
  /** 预留字节 */
    public byte[]           byReserved1 = new byte[6];
  /** 设备重定向回调函数(扩展) */
    public fRedirectServerCallBackEx cbFuncEx;
  /** 用户参数, 与fRedirectServerCallBackEx绑定 */
    public Pointer          dwUserData;

  public NET_IN_START_REDIRECT_SERVICE() {
    this.dwSize = this.size();
  }
}

