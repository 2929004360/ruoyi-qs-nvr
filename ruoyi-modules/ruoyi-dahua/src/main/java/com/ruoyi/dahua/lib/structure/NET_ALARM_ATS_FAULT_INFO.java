package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description  报警传输系统故障上报事件 (对应 DH_ALARM_ATS_FAULT)
 * @date 2022/07/25 15:31:47
 */
public class NET_ALARM_ATS_FAULT_INFO extends NetSDKLib.SdkStructure {
  /**
   事件动作, 1:开始 2:停止
   */
    public			int            nAction;
  /**
   通道号
   */
    public			int            nChannel;
  /**
   事件发生的时间,标准的（不带时区偏差的）UTC时间
   */
    public			NET_TIME_EX    stuUTC = new NET_TIME_EX();
  /**
   保留字节
   */
    public			byte[]         szReserved = new byte[1024];

  public			NET_ALARM_ATS_FAULT_INFO(){
  }
}

