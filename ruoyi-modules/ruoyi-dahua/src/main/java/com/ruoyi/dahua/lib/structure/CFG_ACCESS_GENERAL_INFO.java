package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

import java.util.Arrays;

import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.MAX_ADDRESS_LEN;
import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.MAX_PASSWORD_LEN;

/**
 * @author 47081
 * @version 1.0
 * @description 门禁基本配置
 * @date 2021/2/7
 */
public class CFG_ACCESS_GENERAL_INFO extends NetSDKLib.SdkStructure {
  /** 开门音频文件路径 */
    public byte[]           szOpenDoorAudioPath = new byte[MAX_ADDRESS_LEN];
  /** 关门音频文件路径 */
    public byte[]           szCloseDoorAudioPath = new byte[MAX_ADDRESS_LEN];
  /** 有人音频文件路径 */
    public byte[]           szInUsedAuidoPath = new byte[MAX_ADDRESS_LEN];
  /** 暂停使用音频文件路径 */
    public byte[]           szPauseUsedAudioPath = new byte[MAX_ADDRESS_LEN];
  /** 门未关音频文件路径 */
    public byte[]           szNotClosedAudioPath = new byte[MAX_ADDRESS_LEN];
  /** 等待提示音频文件路径 */
    public byte[]           szWaitingAudioPath = new byte[MAX_ADDRESS_LEN];
  /** 开锁命令响应间隔时间,单位秒，取值10、15(默认值)、20 */
    public int              nUnlockReloadTime;
  /** 开锁输出保持时间,单位秒，取值1、2(默认)、3、4、5、6、9、15。 */
    public int              nUnlockHoldTime;                      
  // 能力,bool对应java中byte
    public byte             abProjectPassword;
    public byte             abAccessProperty;
    public byte             abABLockInfo;
    public byte             byReserved;
  /** 工程密码 */
    public byte[]           szProjectPassword = new byte[MAX_PASSWORD_LEN];
  /** 门禁通道单双向配置,对应枚举{@link com.ruoyi.dahua.lib.enumeration.CFG_ACCESS_PROPERTY_TYPE} */
    public int              emAccessProperty;
  /** AB互锁信息 */
    public CFG_ABLOCK_INFO  stuABLockInfo;
  /** 胁迫密码 */
    public byte[]           szDuressPassword = new byte[MAX_PASSWORD_LEN];
  /** 胁迫使能 */
    public int              bDuressEnable;
  /** 是否启用个性化密码 */
    public int              bCustomPasswordEnable;
  /** 公共密码 */
    public byte[]           szCommonPassword = new byte[MAX_PASSWORD_LEN];
  /** 梯控高峰时间段, 值为CFG_ACCESS_TIMESCHEDULE_INFO配置的数组下标 */
    public int              nPeakTimeSection;
  /** 是否为梯控高峰期, TRUE:高峰期, FALSE:平峰期 */
    public int              bPeakState;
  /** 远程校验超时时间,单位:秒, 默认值:5秒，范围[1-15] */
    public int              nRemoteAuthTimeOut;
  /** 楼层权限信息，每一个bit对应一个楼层，bit值1表示公共层，0表示权限楼层 */
    public byte[]           arrFloorPermission = new byte[64];
  /** arrFloorPermission 数组个数 */
    public int              nFloorPermission;
  /** 门通道控制闸机参数 */
    public CFG_ACCESS_CONTROL_ASG stuAccessControlASG;
  /** 门禁开门提示音 */
    public CFG_ACCESS_VOICE stuAccessVoice;
  /**
   * 门磁类型,参考{ @link EM_ACCESS_SENSOR_TYPE }
   */
    public int              emSensorType;
    /**
     * 负楼层偏移：0~15
    */
    public int              nFloorOffset;
    /**
     * 开门按钮使能
    */
    public int              bButtonExitEnable;
    /**
     * 是否启用个性化开门方式
    */
    public int              bCustomMethodEnable;
    /**
     * -1:未知,0: 只允许密码开锁,1: 只允许刷卡开锁,2: 密码或刷卡开锁,3: 先刷卡后密码开锁,4: 先密码后刷卡开锁。,5: 只允许信息开锁,6: 密码或刷卡或信息开锁,7: 密码+刷卡+信息组合开锁,8: 密码+信息组合开锁,9: 刷卡+信息组合开锁
    */
    public int              nMethod;
    /**
     * 闭锁前是否检测门磁信号
    */
    public int              bCheckSensorBeforeLock;
    /**
     * 开锁记录是否上传
    */
    public int              bUnlockRecordUpload;
    /**
     * 门磁检测时间
    */
    public int              nCheckSensorTime;
    /**
     * 发卡密码
    */
    public byte[]           szSendCardPassword = new byte[64];
    /**
     * 录像使能
    */
    public int              bRecordEnable;
    /**
     * 呼梯使能
    */
    public int              bCallLiftEnable;
    /**
     * 呼梯的协议类型
    */
    public byte[]           szCallLiftType = new byte[32];
    /**
     * 抓图上传使能
    */
    public int              bSnapshotUpload;
    /**
     * 呼叫自动抓拍使能
    */
    public int              bAutoSnapshot;
    /**
     * Dtmf 开门命令
    */
    public byte[]           szOpenDoorCommand = new byte[32];
    /**
     * Dtmf 第二个开门命令
    */
    public byte[]           szOpenDoorSecondCommand = new byte[32];
    /**
     * 门禁协议 0:未知 1 :本机开关量控制 2:门禁协议(串口协议) 3:门禁udp开锁 4:私有串口
    */
    public int              nAccessProtocol;
    /**
     * 0:未知 1 :开本地锁 2:485外接的锁
    */
    public int              nAccessProtocolSecond;
    /**
     * 门禁控制器下的子通道nAccessProtocol为2有效
    */
    public int              nEntranceLockChannel;
    /**
     * 开锁方式数量
    */
    public int              nUnlockRecordTypeNum;
    /**
     * 开锁方式 0：未知 1:Password 2:Button 3:FgerPrint 4:QRCode 5:Card 6:Remote 7:BlueTooth 8:Fce 9:DTMF 10:QRCodeEx 11:CustomPassword
    */
    public int[]            nUnlockRecordType = new int[32];
    /**
     * 密钥数组
    */
    public BYTE_ARRAY_32[]  szICKeyValue = new BYTE_ARRAY_32[2];
    /**
     * 密钥数量
    */
    public int              nICKeyValueNum;
    /**
     * 检验IC卡密码功能使能
    */
    public int              bICKeyEnable;
    /**
     * 控制是否使用AB互锁信息扩展
    */
    public int              bABLockInfo;
    /**
     * AB互锁信息扩展,由用户申请内存,大小为sizeof(CFG_ABLOCK_INFO_EX),参见结构体定义 {@link CFG_ABLOCK_INFO_EX}
    */
    public Pointer          pstuABLockInfoEx;
  /** 保留字节 */
    public byte[]           szReserved = new byte[696-NetSDKLib.POINTERSIZE];

  @Override
  public String toString() {
    return "CFG_ACCESS_GENERAL_INFO{" +
            "szOpenDoorAudioPath=" + Arrays.toString(szOpenDoorAudioPath) +
            ", szCloseDoorAudioPath=" + Arrays.toString(szCloseDoorAudioPath) +
            ", szInUsedAuidoPath=" + Arrays.toString(szInUsedAuidoPath) +
            ", szPauseUsedAudioPath=" + Arrays.toString(szPauseUsedAudioPath) +
            ", szNotClosedAudioPath=" + Arrays.toString(szNotClosedAudioPath) +
            ", szWaitingAudioPath=" + Arrays.toString(szWaitingAudioPath) +
            ", nUnlockReloadTime=" + nUnlockReloadTime +
            ", nUnlockHoldTime=" + nUnlockHoldTime +
            ", abProjectPassword=" + abProjectPassword +
            ", abAccessProperty=" + abAccessProperty +
            ", abABLockInfo=" + abABLockInfo +
            ", byReserved=" + byReserved +
            ", szProjectPassword=" + Arrays.toString(szProjectPassword) +
            ", emAccessProperty=" + emAccessProperty +
            ", stuABLockInfo=" + stuABLockInfo +
            ", szDuressPassword=" + Arrays.toString(szDuressPassword) +
            ", bDuressEnable=" + bDuressEnable +
            ", bCustomPasswordEnable=" + bCustomPasswordEnable +
            ", szCommonPassword=" + Arrays.toString(szCommonPassword) +
            ", nPeakTimeSection=" + nPeakTimeSection +
            ", bPeakState=" + bPeakState +
            ", nRemoteAuthTimeOut=" + nRemoteAuthTimeOut +
            ", arrFloorPermission=" + Arrays.toString(arrFloorPermission) +
            ", nFloorPermission=" + nFloorPermission +
            ", stuAccessControlASG=" + stuAccessControlASG +
            ", stuAccessVoice=" + stuAccessVoice +
            '}';
  }
}

