package com.ruoyi.haikang.isup.service.haikang.alarm;

import com.sun.jna.*;

public interface HCISUPAlarm extends Library {

    /***宏定义***/
    //常量
    public static final int MAX_DEVICE_ID_LEN = 256;
    public static final int NAME_LEN = 32;
    public static final int NET_EHOME_SERIAL_LEN = 12;

    public static final int MAX_TIME_LEN = 32;
    public static final int MAX_REMARK_LEN = 64;
    public static final int MAX_URL_LEN = 512;
    public static final int CID_DES_LEN = 32;
    public static final int MAX_FILE_PATH_LEN = 256;
    public static final int MAX_UUID_LEN = 64;
    public static final int CID_DES_LEN_EX = 256;
    public static final int MAX_PICTURE_NUM = 5;
    public static final int MAX_VIDEO_TYPE_LEN = 16;
    public static final int MAX_SUBSYSTEM_LEN = 64;

    public static final int EHOME_ALARM_UNKNOWN = 0;
    public static final int EHOME_ALARM = 1;
    public static final int EHOME_ALARM_HEATMAP_REPORT = 2;
    public static final int EHOME_ALARM_FACESNAP_REPORT = 3;
    public static final int EHOME_ALARM_GPS = 4;
    public static final int EHOME_ALARM_CID_REPORT = 5;
    public static final int EHOME_ALARM_NOTICE_PICURL = 6;
    public static final int EHOME_ALARM_NOTIFY_FAIL = 7;
    public static final int EHOME_ALARM_SELFDEFINE = 9;
    public static final int EHOME_ALARM_DEVICE_NETSWITCH_REPORT = 10;
    public static final int EHOME_ALARM_ACS = 11;
    public static final int EHOME_ALARM_WIRELESS_INFO = 12;
    public static final int EHOME_ISAPI_ALARM = 13;
    public static final int EHOME_INFO_RELEASE_PRIVATE = 14;
    public static final int EHOME_ALARM_MPDCDATA = 15;
    public static final int EHOME_ALARM_QRCODE = 20;
    public static final int EHOME_ALARM_FACETEMP = 21;

    public static final int ALARM_TYPE_DEV_CHANGED_STATUS = 700;
    public static final int ALARM_TYPE_CHAN_CHANGED_STATUS = 701;
    public static final int ALARM_TYPE_HD_CHANGED_STATUS = 702;
    public static final int ALARM_TYPE_DEV_TIMING_STATUS = 703;
    public static final int ALARM_TYPE_CHAN_TIMING_STATUS = 704;
    public static final int ALARM_TYPE_HD_TIMING_STATUS = 705;
    public static final int ALARM_TYPE_RECORD_ABNORMAL = 706;

    public static class NET_EHOME_IPADDRESS extends Structure {
        public byte[] szIP = new byte[128];
        public short wPort;
        public byte[] byRes = new byte[2];
    }

    public static class BYTE_ARRAY extends Structure {
        public byte[] byValue;

        public BYTE_ARRAY(int iLen) {
            byValue = new byte[iLen];
        }
    }

    public static class NET_EHOME_ALARM_MSG extends Structure {
        public int dwAlarmType;
        public Pointer pAlarmInfo;
        public int dwAlarmInfoLen;
        public Pointer pXmlBuf;
        public int dwXmlBufLen;
        public byte[] sSerialNumber = new byte[NET_EHOME_SERIAL_LEN];
        public Pointer pHttpUrl;
        public int dwHttpUrlLen;
        public byte[] byRes = new byte[12];
    }

    public static class NET_EHOME_ALARM_ISAPI_INFO extends Structure {
        public Pointer pAlarmData;
        public int dwAlarmDataLen;
        public byte byDataType;
        public byte byPicturesNumber;
        public byte[] byRes = new byte[2];
        public Pointer pPicPackData;
        public byte[] byRes1 = new byte[32];
    }

    public static class NET_EHOME_HEATMAP_REPORT extends Structure {
        public int dwSize;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public int dwVideoChannel;
        public byte[] byStartTime = new byte[MAX_TIME_LEN];
        public byte[] byStopTime = new byte[MAX_TIME_LEN];
        public NET_EHOME_HEATMAP_VALUE struHeatmapValue;
        public NET_EHOME_PIXEL_ARRAY_SIZE struPixelArraySize;
        public byte[] byPixelArrayData = new byte[MAX_URL_LEN];
        public byte byRetransFlag;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public byte[] byRes = new byte[61];
    }

    public static class NET_EHOME_HEATMAP_VALUE extends Structure {
        public int dwMaxHeatMapValue;
        public int dwMinHeatMapValue;
        public int dwTimeHeatMapValue;
    }

    public static class NET_EHOME_PIXEL_ARRAY_SIZE extends Structure {
        public int dwLineValue;
        public int dwColumnValue;
    }

    public static class NET_EHOME_FACESNAP_REPORT extends Structure {
        public int dwSize;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public int dwVideoChannel;
        public byte[] byAlarmTime = new byte[MAX_TIME_LEN];
        public int dwFacePicID;
        public int dwFaceScore;
        public int dwTargetID;
        public NET_EHOME_ZONE struTarketZone;
        public NET_EHOME_ZONE struFacePicZone;
        public NET_EHOME_HUMAN_FEATURE struHumanFeature;
        public int dwStayDuration;
        public int dwFacePicLen;
        public byte[] byFacePicUrl = new byte[MAX_URL_LEN];
        public int dwBackgroudPicLen;
        public byte[] byBackgroudPicUrl = new byte[MAX_URL_LEN];
        public byte byRetransFlag;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public byte[] byRes = new byte[61];
    }

    public static class NET_EHOME_ZONE extends Structure {
        public int dwX;
        public int dwY;
        public int dwWidth;
        public int dwHeight;
    }

    public static class NET_EHOME_HUMAN_FEATURE extends Structure {
        public byte byAgeGroup;
        public byte bySex;
        public byte byEyeGlass;
        public byte byMask;
        public byte[] byRes = new byte[12];
    }

    public static class NET_EHOME_GPS_INFO extends Structure {
        public int dwSize;
        public byte[] bySampleTime = new byte[MAX_TIME_LEN];
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public byte[] byDivision = new byte[2];
        public byte bySatelites;
        public byte byPrecision;
        public int dwLongitude;
        public int dwLatitude;
        public int dwDirection;
        public int dwSpeed;
        public int dwHeight;
        public byte byRetransFlag;
        public byte byLocateMode;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public int dwMileage;
        public byte[] byRes = new byte[56];
    }

    public static class NET_EHOME_CID_INFO extends Structure {
        public int dwSize;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public int dwCIDCode;
        public int dwCIDType;
        public int dwSubSysNo;
        public byte[] byCIDDescribe = new byte[CID_DES_LEN];
        public byte[] byTriggerTime = new byte[MAX_TIME_LEN];
        public byte[] byUploadTime = new byte[MAX_TIME_LEN];
        public NET_EHOME_CID_PARAM struCIDParam;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public byte byExtend;
        public byte[] byRes1 = new byte[5];
        public Pointer pCidInfoEx;
        public Pointer pPicInfoEx;
        public byte[] byRes = new byte[44];
    }

    public static class NET_EHOME_CID_PARAM extends Structure {
        public int dwUserType;
        public int lUserNo;
        public int lZoneNo;
        public int lKeyboardNo;
        public int lVideoChanNo;
        public int lDiskNo;
        public int lModuleAddr;
        public byte[] byUserName = new byte[NAME_LEN];
        public byte[] byRes = new byte[32];
    }

    public static class NET_EHOME_CID_INFO_INTERNAL_EX extends Structure {
        public byte byRecheck;
        public byte[] byRes = new byte[3];
        public byte[] byUUID = new byte[MAX_UUID_LEN];
        public byte[] byVideoURL = new byte[MAX_URL_LEN];
        public byte[] byCIDDescribeEx = new byte[CID_DES_LEN_EX];
        public byte[] byVideoType = new byte[MAX_VIDEO_TYPE_LEN];
        public byte[] byLinkageSubSystem = new byte[MAX_SUBSYSTEM_LEN];
        public byte[] byRes1 = new byte[176];
    }

    public static class NET_EHOME_CID_INFO_PICTUREINFO_EX extends Structure {
        public byte[][] byPictureURL = new byte[MAX_PICTURE_NUM][MAX_URL_LEN];
        public byte[] byRes1 = new byte[512];
    }

    public static interface EHomeMsgCallBack extends Callback {
        public boolean invoke(int iHandle, NET_EHOME_ALARM_MSG pAlarmMsg, Pointer pUser);
    }

    public static class NET_EHOME_ALARM_LISTEN_PARAM extends Structure {
        public NET_EHOME_IPADDRESS struAddress;
        public EHomeMsgCallBack fnMsgCb;
        public Pointer pUserData;
        public byte byProtocolType;
        public byte byUseCmsPort;
        public byte byUseThreadPool;
        public byte byRes[] = new byte[29];
    }

    public static class NET_EHOME_LOCAL_GENERAL_CFG extends Structure {
        public byte byAlarmPictureSeparate;
        public byte[] byRes = new byte[127];
    }

    public static class NET_EHOME_ALARM_INFO extends Structure {
        public int dwSize;
        public byte[] szAlarmTime = new byte[MAX_TIME_LEN];
        public byte[] szDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public int dwAlarmType;
        public int dwAlarmAction;
        public int dwVideoChannel;
        public int dwAlarmInChannel;
        public int dwDiskNumber;
        public byte[] byRemark = new byte[MAX_REMARK_LEN];
        public byte byRetransFlag;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public byte byRes1;
        public byte[] szAlarmUploadTime = new byte[MAX_TIME_LEN];
        public NET_EHOME_ALARM_STATUS_UNION uStatusUnion;
        public byte[] byRes2 = new byte[16];
    }

    public static class NET_EHOME_ALARM_STATUS_UNION extends Structure {
        public byte[] byRes = new byte[12];
        public NET_EHOME_DEV_STATUS_CHANGED struDevStatusChanged;
        public NET_EHOME_CHAN_STATUS_CHANGED struChanStatusChanged;
        public NET_EHOME_HD_STATUS_CHANGED struHdStatusChanged;
        public NET_EHOME_DEV_TIMING_STATUS struDevTimeStatus;
        public NET_EHOME_CHAN_TIMING_STATUS_SINGLE struChanTimeStatus;
        public NET_EHOME_HD_TIMING_STATUS_SINGLE struHdTimeStatus;
    }

    public static class NET_EHOME_DEV_STATUS_CHANGED extends Structure {
        public byte byDeviceStatus;
        public byte[] byRes = new byte[11];
    }

    public static class NET_EHOME_CHAN_STATUS_CHANGED extends Structure {
        public short wChanNO;
        public byte byChanStatus;
        public byte[] byRes = new byte[9];
    }

    public static class NET_EHOME_HD_STATUS_CHANGED extends Structure {
        public int dwVolume;
        public short wHDNo;
        public byte byHDStatus;
        public byte[] byRes = new byte[5];
    }

    public static class NET_EHOME_DEV_TIMING_STATUS extends Structure {
        public int dwMemoryTotal;
        public int dwMemoryUsage;
        public byte byCPUUsage;
        public byte byMainFrameTemp;
        public byte byBackPanelTemp;
        public byte byRes;
    }

    public static class NET_EHOME_CHAN_TIMING_STATUS_SINGLE extends Structure {
        public int dwBitRate;
        public short wChanNO;
        public byte byLinkNum;
        public byte[] byRes = new byte[5];
    }

    public static class NET_EHOME_HD_TIMING_STATUS_SINGLE extends Structure {
        public int dwHDFreeSpace;
        public short wHDNo;
        public byte[] byRes = new byte[6];
    }

    public static class NET_EHOME_NOTICE_PICURL extends Structure {
        public int dwSize;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public short wPicType;
        public short wAlarmType;
        public int dwAlarmChan;
        public byte[] byAlarmTime = new byte[MAX_TIME_LEN];
        public int dwCaptureChan;
        public byte[] byPicTime = new byte[MAX_TIME_LEN];
        public byte[] byPicUrl = new byte[MAX_URL_LEN];
        public int dwManualSnapSeq;
        public byte byRetransFlag;
        public byte byTimeDiffH;
        public byte byTimeDiffM;
        public byte[] byRes = new byte[29];
    }

    public static class NET_EHOME_NOTIFY_FAIL_INFO extends Structure {
        public int dwSize;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public short wFailedCommand;
        public short wPicType;
        public int dwManualSnapSeq;
        public byte byRetransFlag;
        public byte[] byRes = new byte[31];
    }

    public static class NET_EHOME_ALARMWIRELESSINFO extends Structure {
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public int dwDataTraffic;
        public byte bySignalIntensity;
        public byte[] byRes = new byte[127];
    }

    public static class NET_EHOME_ALARM_MPDCDATA extends Structure {
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public byte[] bySampleTime = new byte[MAX_TIME_LEN];
        public byte byTimeZoneIdx;
        public byte byRetranseFlag;
        public byte[] byRes = new byte[2];
        public NET_EHOME_MPGPS struGpsInfo;
        public NET_EHOME_MPDATA struMPData;
    }

    public static class NET_EHOME_MPGPS extends Structure {
        public int iLongitude;
        public int iLatitude;
        public int iSpeed;
        public int iDirection;
    }

    public static class NET_EHOME_MPDATA extends Structure {
        public byte byIndex;
        public byte byVideoChannel;
        public byte byRes;
        public byte byLevel;
        public byte[] byStarttime = new byte[MAX_TIME_LEN];
        public byte[] byStoptime = new byte[MAX_TIME_LEN];
        public int dwEnterNum;
        public int dwLeaveNum;
        public int dwCount;
    }

    boolean NET_EALARM_Init();

    boolean NET_EALARM_Fini();

    boolean NET_EALARM_SetSDKLocalCfg(int enumType, Pointer lpInbuffer);

    int NET_EALARM_StartListen(NET_EHOME_ALARM_LISTEN_PARAM pAlarmListenParam);

    int NET_EALARM_GetLastError();

    boolean NET_EALARM_StopListen(int iListenHandle);

    boolean NET_EALARM_SetDeviceSessionKey(Pointer pDeviceKey);

    boolean NET_EALARM_SetLogToFile(int iLogLevel, String strLogDir, boolean bAutoDel);

    boolean NET_EALARM_SetSDKInitCfg(int enumType, Pointer lpInBuff);
}
