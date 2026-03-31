package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

/** 尺寸过滤器 */
public class NET_CFG_SIZEFILTER_INFO extends SdkStructure {
  /** 校准框个数 */
    public int              nCalibrateBoxNum;
  /** 校准框(远端近端标定模式下有效) */
    public NET_CFG_CALIBRATEBOX_INFO[] stuCalibrateBoxs = new NET_CFG_CALIBRATEBOX_INFO[10];
  /** 计量方式参数是否有效 */
    public byte             bMeasureModeEnable;
  /** 计量方式,0-像素，不需要远端、近端标定, 1-实际长度，单位：米, 2-远端近端标定后的像素 */
    public byte             bMeasureMode;
  /** 过滤类型参数是否有效 */
    public byte             bFilterTypeEnable;                    
  // ByArea,ByRatio仅作兼容，使用独立的ByArea和ByRatio选项代替 2012/03/06
  /** 过滤类型:0:"ByLength",1:"ByArea", 2"ByWidthHeight" */
    public byte             bFilterType;
  /** 物体最小尺寸参数是否有效 */
    public byte             bFilterMinSizeEnable;
  /** 物体最大尺寸参数是否有效 */
    public byte             bFilterMaxSizeEnable;
    public byte             abByArea;
    public byte             abMinArea;
    public byte             abMaxArea;
    public byte             abMinAreaSize;
    public byte             abMaxAreaSize;
  /** 是否按面积过滤 通过能力ComplexSizeFilter判断是否可用 */
    public byte             bByArea;
  /** 物体最小尺寸 "ByLength"模式下表示宽高的尺寸，"ByArea"模式下宽表示面积，高无效(远端近端标定模式下表示基准框的宽高尺寸)。 */
    public NET_CFG_SIZE     stuFilterMinSize = new NET_CFG_SIZE();
  /** 物体最大尺寸 "ByLength"模式下表示宽高的尺寸，"ByArea"模式下宽表示面积，高无效(远端近端标定模式下表示基准框的宽高尺寸)。 */
    public NET_CFG_SIZE     stuFilterMaxSize = new NET_CFG_SIZE();
  /** 最小面积 */
    public float            nMinArea;
  /** 最大面积 */
    public float            nMaxArea;
  /** 最小面积矩形框尺寸 "计量方式"为"像素"时，表示最小面积矩形框的宽高尺寸；"计量方式"为"远端近端标定模式"时，表示基准框的最小宽高尺寸； */
    public NET_CFG_SIZE     stuMinAreaSize = new NET_CFG_SIZE();
  /** 最大面积矩形框尺寸, 同上 */
    public NET_CFG_SIZE     stuMaxAreaSize = new NET_CFG_SIZE();
    public byte             abByRatio;
    public byte             abMinRatio;
    public byte             abMaxRatio;
    public byte             abMinRatioSize;
    public byte             abMaxRatioSize;
  /** 是否按宽高比过滤 通过能力ComplexSizeFilter判断是否可用 */
    public byte             bByRatio;
    public byte[]           bReserved1 = new byte[2];
  /** 最小宽高比 */
    public double           dMinRatio;
  /** 最大宽高比 */
    public double           dMaxRatio;
  /** 最小宽高比矩形框尺寸，最小宽高比对应矩形框的宽高尺寸。 */
    public NET_CFG_SIZE     stuMinRatioSize = new NET_CFG_SIZE();
  /** 最大宽高比矩形框尺寸，同上 */
    public NET_CFG_SIZE     stuMaxRatioSize = new NET_CFG_SIZE();
  /** 面积校准框个数 */
    public int              nAreaCalibrateBoxNum;
  /** 面积校准框 */
    public NET_CFG_CALIBRATEBOX_INFO[] stuAreaCalibrateBoxs = new NET_CFG_CALIBRATEBOX_INFO[10];
  /** 宽高校准框个数 */
    public int              nRatioCalibrateBoxs;
  /** 宽高校准框 */
    public NET_CFG_CALIBRATEBOX_INFO[] stuRatioCalibrateBoxs = new NET_CFG_CALIBRATEBOX_INFO[10];
  /** 长宽过滤使能参数是否有效 */
    public byte             abBySize;
  /** 长宽过滤使能 */
    public byte             bBySize;
  /** 保留字段 */
    public byte[]           bReserved = new byte[518];

  public NET_CFG_SIZEFILTER_INFO(){

    for(int i=0;i<stuCalibrateBoxs.length;i++){
      stuCalibrateBoxs[i]=new  NET_CFG_CALIBRATEBOX_INFO();

    }

    for(int i=0;i<stuAreaCalibrateBoxs.length;i++){
      stuAreaCalibrateBoxs[i]=new NET_CFG_CALIBRATEBOX_INFO();
    }

    for (int i=0;i<stuRatioCalibrateBoxs.length;i++){
      stuRatioCalibrateBoxs[i]=new NET_CFG_CALIBRATEBOX_INFO();
    }
  }
}

