package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 驾驶辅助场景配置
 * @date 2021/2/22
 */
public class CFG_DETAIL_DRIVEASSISTANT_INFO extends NetSDKLib.SdkStructure {
  /** 是否有效 */
    public int              bValid;
  /** 车宽 0-5000mm 单位mm */
    public int              nVehicleWidth;
  /** 相机高度 0-5000mm 单位mm */
    public int              nCamHeight;
  /** 车头到相机的距离 0-5000mm 单位mm */
    public int              nCamToCarHead;
    /**
     * 车道中心点,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON stuCenterPoint = new NetSDKLib.CFG_POLYGON();
  /** 保留 */
    public byte[]           byReserved = new byte[48];
}

