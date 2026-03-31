package com.ruoyi.dahua.lib.structure;
/**
 * @author 47081
 * @desc
 * @version 1.0.0
 * @date 2021/4/9
 */
import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.CFG_COMMON_STRING_64;
import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.MAX_MOTION_ROW;

/**
 * @author 47081
 * @description
 * @date 2021/4/9
 * @version 1.0
 */
public class CFG_DETECT_REGION extends NetSDKLib.SdkStructure {
  /** 区域ID */
    public int              nRegionID;
  /** 动态窗口名称 */
    public byte[]           szRegionName = new byte[CFG_COMMON_STRING_64];
  /** 面积阀值，取值[0, 100] */
    public int              nThreshold;
  /** 灵敏度1～6 */
    public int              nSenseLevel;
  /** 动态检测区域的行数 */
    public int              nMotionRow;
  /** 动态检测区域的列数 */
    public int              nMotionCol;
  /** 检测区域，最多32*32块区域 */
    public BYTE_32[]        byRegion = new BYTE_32[MAX_MOTION_ROW];

  public CFG_DETECT_REGION(){
    for(int i=0;i<byRegion.length;i++){
      byRegion[i]=new BYTE_32();
    }
  }
}

