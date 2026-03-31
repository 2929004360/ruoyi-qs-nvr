package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 可供任务调度的总的智能能力
 * @date 2021/2/8
 */
public class NET_REMAIN_ANALYSE_TOTAL_CAPACITY extends NetSDKLib.SdkStructure {
  /** 业务大类{@link NetSDKLib.EM_SCENE_CLASS_TYPE} */
    public int              emClassType;
  /** 任务调度总共能分析的视频流数目 */
    public int              nNumber;
  /** 预留字节 */
    public byte[]           byReserved = new byte[256];
}

