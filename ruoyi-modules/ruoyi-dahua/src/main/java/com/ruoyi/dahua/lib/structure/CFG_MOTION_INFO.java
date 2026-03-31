package com.ruoyi.dahua.lib.structure;
/**
 * @author 47081
 * @desc
 * @version 1.0.0
 * @date 2021/4/9
 */
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;

import static com.ruoyi.dahua.lib.NetSDKLib.MAX_REC_TSECT;
import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.*;

/**
 * @author 47081
 * @description 动态监测报警配置,对应接口 {@link
 *     NetSDKLib#CLIENT_GetNewDevConfig(NetSDKLib.LLong, String, int, byte[], int,
 *     IntByReference, int)}和{@link NetSDKLib#CLIENT_SetNewDevConfig(NetSDKLib.LLong, String, int,
 *     byte[], int, IntByReference, IntByReference, int)}
 * @date 2021/4/9
 * @version 1.0
 */
public class CFG_MOTION_INFO extends NetSDKLib.SdkStructure {
  /** 报警通道号(0开始), nVersion=1时，此字段无效 */
    public int              nChannelID;
  /** 使能开关 */
    public int              bEnable;
  /** 一代动检灵敏度1～6 */
    public int              nSenseLevel;
  /** 一代动态检测区域的行数 */
    public int              nMotionRow;
  /** 一代动态检测区域的列数 */
    public int              nMotionCol;
  /** 一代检测区域，最多32*32块区域 */
    public BYTE_32[]        byRegion = new BYTE_32[MAX_MOTION_ROW];
  /** 报警联动 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
  /** 事件响应时间段，时间段获取和设置以此成员为准，忽略 stuEventHandler 中的stuTimeSection */
    public NetSDKLib.CFG_TIME_SECTION[] stuTimeSection = new NetSDKLib.CFG_TIME_SECTION[WEEK_DAY_NUM * MAX_REC_TSECT];
  /** 0, 1, 由能力集确定, 只读, 等于1时以下字段有效 */
    public int              nVersion;
  /** 只读，决定nSenseLevel是否有效 */
    public int              bSenseLevelEn;
  /** 只读，面积占用比使能， 决定nVolumeRatio是否有效 */
    public int              bVRatioEn;
  /** 一代动检的面积占用比,1-100 */
    public int              nVolumeRatio;
  /** 只读，灵敏度使能，决定nSubRatio值是否有效 */
    public int              bSRatioEn;
  /** 一代动检的残差阈值, 1-100 */
    public int              nSubRatio;
  /** 此字段及以下两个字段已废弃 */
    public int              abWindow;
  /** 视频窗口个数 */
    @Deprecated public int  nWindowCount;
  /** 视频窗口数组 */
    @Deprecated public CFG_MOTION_WINDOW[] stuWindows = new CFG_MOTION_WINDOW[MAX_MOTION_WINDOW];
  /** 只读， 1：nRegionCount，stuRegion有效 0：nMotionRow，nMotionCol，byRegion有效 */
    public int              abDetectRegion;
  /** 三代动态检测区域个数 */
    public int              nRegionCount;
  /** 三代动态检测区域 */
    public CFG_DETECT_REGION[] stuRegion = new CFG_DETECT_REGION[MAX_MOTION_WINDOW];
  /** 前端动态检测联动 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuRemoteEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
  /** 前端动态检测联动, 事件响应时间段，时间段获取和设置以此成员为准，忽略 stuRemoteEventHandler中的stuTimeSection */
    public NetSDKLib.CFG_TIME_SECTION[] stuRemoteTimeSection = new NetSDKLib.CFG_TIME_SECTION[WEEK_DAY_NUM * MAX_REC_TSECT];

  @Override
  public String toString() {
    return "CFG_MOTION_INFO{" +
            "nChannelID=" + nChannelID +
            ", bEnable=" + bEnable +
            ", nSenseLevel=" + nSenseLevel +
            ", nMotionRow=" + nMotionRow +
            ", nMotionCol=" + nMotionCol +
            ", byRegion=" + Arrays.toString(byRegion) +
            ", stuEventHandler=" + stuEventHandler +
            ", stuTimeSection=" + Arrays.toString(stuTimeSection) +
            ", nVersion=" + nVersion +
            ", bSenseLevelEn=" + bSenseLevelEn +
            ", bVRatioEn=" + bVRatioEn +
            ", nVolumeRatio=" + nVolumeRatio +
            ", bSRatioEn=" + bSRatioEn +
            ", nSubRatio=" + nSubRatio +
            ", abWindow=" + abWindow +
            ", nWindowCount=" + nWindowCount +
            ", stuWindows=" + Arrays.toString(stuWindows) +
            ", abDetectRegion=" + abDetectRegion +
            ", nRegionCount=" + nRegionCount +
            ", stuRegion=" + Arrays.toString(stuRegion) +
            ", stuRemoteEventHandler=" + stuRemoteEventHandler +
            ", stuRemoteTimeSection=" + Arrays.toString(stuRemoteTimeSection) +
            '}';
  }

      public  CFG_MOTION_INFO(){
            for(int i=0;i<byRegion.length;i++){
              byRegion[i]=new BYTE_32();
            }


            for(int i=0;i<stuTimeSection.length;i++){
              stuTimeSection[i]=new  NetSDKLib.CFG_TIME_SECTION();
            }

            for(int i=0;i<stuWindows.length;i++){
              stuWindows[i]=new CFG_MOTION_WINDOW();
            }

            for(int i=0;i<stuRegion.length;i++){
              stuRegion[i]= new  CFG_DETECT_REGION();
            }
            for(int i=0;i<stuRemoteTimeSection.length;i++){
              stuRemoteTimeSection[i]=new NetSDKLib.CFG_TIME_SECTION();
            }

      }
}

