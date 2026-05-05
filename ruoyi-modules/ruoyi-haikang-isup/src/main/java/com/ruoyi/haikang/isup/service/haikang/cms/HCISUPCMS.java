/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HCNetSDK.java
 *
 * Created on 2009-9-14, 19:31:34
 */

/**
 * @author Xubinfeng
 */
package com.ruoyi.haikang.isup.service.haikang.cms;

import com.ruoyi.haikang.isup.service.haikang.HIKSDKStructure;
import com.sun.jna.*;
import com.sun.jna.examples.win32.W32API;

import java.util.Arrays;
import java.util.List;

//SDK接口说明,HCNetSDK.dll

//windows gdi接口,gdi32.dll in system32 folder, 在设置遮挡区域,移动侦测区域等情况下使用
public interface HCISUPCMS extends Library {

    /***宏定义***/
    //常量
    public static final int NET_SDK_MAX_FILE_PATH = 256;

    public static class NET_DVR_LOCAL_SDK_PATH extends HIKSDKStructure {
        public byte[] sPath = new byte[NET_SDK_MAX_FILE_PATH];
        public byte[] byRes = new byte[128];
    }


    public static final int MAX_NAMELEN = 16;    //DVR本地登陆名
    public static final int MAX_RIGHT = 32;    //设备支持的权限（1-12表示本地权限，13-32表示远程权限）
    public static final int NAME_LEN = 32;    //用户名长度
    public static final int PASSWD_LEN = 16;    //密码长度
    public static final int MAX_MASTER_KEY_LEN = 16;
    public static final int MAX_DEVICE_ID_LEN = 256;    //设备ID长度
    public static final int MAX_DEVNAME_LEN = 32; //设备名最大长度
    public static final int MAX_DEVNAME_LEN_EX = 64; //设备名最大长度

    public static final int MAX_FULL_SERIAL_NUM_LEN = 64;      //最大完整序列号长度
    public static final int NET_EHOME_SERIAL_LEN = 12;  //序列号长度

    public static final int MAX_ANALOG_CHANNUM = 32;
    public static final int MAX_DIGIT_CHANNUM = 480;
    public static final int MAX_ANALOG_ALARMOUT = 32;


    public static interface EHOME_REGISTER_TYPE {
        public static final int ENUM_DEV_ON = 0;  //设备上线回调
        public static final int ENUM_DEV_OFF = 1; //设备下线回调
        public static final int ENUM_DEV_ADDRESS_CHANGED = 2;  //设备地址发生变化
        public static final int ENUM_DEV_AUTH = 3;  //Ehome5.0设备认证回调
        public static final int ENUM_DEV_SESSIONKEY = 4;  //Ehome5.0设备Sessionkey回调
        public static final int ENUM_DEV_DAS_REQ = 5; //Ehome5.0设备重定向请求回调
        public static final int ENUM_DEV_SESSIONKEY_REQ = 6;  //EHome5.0设备sessionkey请求回调
        public static final int ENUM_DEV_DAS_REREGISTER = 7;  //设备重注册回调
        public static final int ENUM_DEV_DAS_PINGREO = 8; //设备注册心跳
        public static final int ENUM_DEV_DAS_EHOMEKEY_ERROR = 9; //校验密码失败
        public static final int ENUM_DEV_SESSIONKEY_ERROR = 10;  //Sessionkey交互异常
        public static final int ENUM_DEV_SLEEP = 11; //设备进入休眠状态（注：休眠状态下，设备无法做预览、回放、语音对讲、配置等CMS中的信令作响应；设备可通过NET_ECMS_WakeUp接口进行唤醒）
    }

    public static class NET_EHOME_DEV_SESSIONKEY extends HIKSDKStructure {
        public byte[] sDeviceID = new byte[MAX_DEVICE_ID_LEN];        //设备ID/*256*/
        public byte[] sSessionKey = new byte[MAX_MASTER_KEY_LEN];     //设备Sessionkey/*16*/

    }


    public static class NET_EHOME_DEV_REG_INFO_V12 extends HIKSDKStructure {
        public NET_EHOME_DEV_REG_INFO struRegInfo;
        public NET_EHOME_IPADDRESS struRegAddr;
        public byte[] sDevName = new byte[MAX_DEVNAME_LEN_EX];
        public byte[] byDeviceFullSerial = new byte[MAX_FULL_SERIAL_NUM_LEN];
        public byte[] byRes = new byte[128];

    }


    public static class NET_EHOME_DEV_REG_INFO extends HIKSDKStructure {
        public int dwSize;
        public int dwNetUnitType;
        public byte[] byDeviceID = new byte[MAX_DEVICE_ID_LEN];
        public byte[] byFirmwareVersion = new byte[24];
        public NET_EHOME_IPADDRESS struDevAdd;
        public int dwDevType;
        public int dwManufacture;
        public byte[] byPassWord = new byte[32];
        public byte[] sDeviceSerial = new byte[NET_EHOME_SERIAL_LEN];
        public byte byReliableTransmission;
        public byte byWebSocketTransmission;
        public byte bySupportRedirect;               //设备支持重定向注册 0-不支持 1-支持
        public byte[] byDevProtocolVersion = new byte[6];         //设备协议版本
        public byte[] bySessionKey = new byte[16];//Ehome5.0设备SessionKey
        public byte byMarketType; //0-无效（未知类型）,1-经销型，2-行业型
        public byte[] byRes = new byte[26];
    }

    public static class NET_EHOME_SERVER_INFO extends HIKSDKStructure {
        public int dwSize;
        public int dwKeepAliveSec;
        public int dwTimeOutCount;
        public NET_EHOME_IPADDRESS struTCPAlarmSever = new NET_EHOME_IPADDRESS();
        public NET_EHOME_IPADDRESS struUDPAlarmSever = new NET_EHOME_IPADDRESS();
        public int dwAlarmServerType;
        public NET_EHOME_IPADDRESS struNTPSever = new NET_EHOME_IPADDRESS();
        public int dwNTPInterval;
        public NET_EHOME_IPADDRESS struPictureSever = new NET_EHOME_IPADDRESS();
        public int dwPicServerType;
        public NET_EHOME_BLACKLIST_SEVER struBlackListServer = new NET_EHOME_BLACKLIST_SEVER();
        public byte[] byRes = new byte[128];
    }

    public static class NET_EHOME_SERVER_INFO_V50 extends HIKSDKStructure {
        public int dwSize;
        public int dwKeepAliveSec;         //心跳间隔（单位：秒,0:默认为15S）
        public int dwTimeOutCount;         //心跳超时次数（0：默认为6）
        public NET_EHOME_IPADDRESS struTCPAlarmSever = new NET_EHOME_IPADDRESS();      //报警服务器地址（TCP协议）
        public NET_EHOME_IPADDRESS struUDPAlarmSever = new NET_EHOME_IPADDRESS();      //报警服务器地址（UDP协议）
        public int dwAlarmServerType;      //报警服务器类型0-只支持UDP协议上报，1-支持UDP、TCP两种协议上报
        public NET_EHOME_IPADDRESS struNTPSever = new NET_EHOME_IPADDRESS();           //NTP服务器地址
        public int dwNTPInterval;          //NTP校时间隔（单位：秒）
        public NET_EHOME_IPADDRESS struPictureSever = new NET_EHOME_IPADDRESS();       //图片服务器地址
        public int dwPicServerType;        //图片服务器类型图片服务器类型，1-VRB图片服务器，0-Tomcat图片服务,2-云存储3,3-KMS
        public NET_EHOME_BLACKLIST_SEVER struBlackListServer = new NET_EHOME_BLACKLIST_SEVER();//黑名单服务器
        public NET_EHOME_IPADDRESS struRedirectSever = new NET_EHOME_IPADDRESS();      //Redirect Server
        public byte[] byClouldAccessKey = new byte[64];  //云存储AK
        public byte[] byClouldSecretKey = new byte[64];  //云存储SK
        public byte byClouldHttps;          //云存储HTTPS使能 1-HTTPS 0-HTTP
        public byte[] byRes1 = new byte[3];
        public int dwAlarmKeepAliveSec;    //报警心跳间隔（单位：秒,0:默认为30s）
        public int dwAlarmTimeOutCount;    //报警心跳超时次数（0：默认为3）
        public int dwClouldPoolId;         //云存储PoolId
        public byte[] byRes = new byte[368];
    }


    public static class NET_EHOME_BLACKLIST_SEVER extends HIKSDKStructure {
        public NET_EHOME_IPADDRESS struAdd = new NET_EHOME_IPADDRESS();
        public byte[] byServerName = new byte[32];
        public byte[] byUserName = new byte[32];
        public byte[] byPassWord = new byte[32];
        public byte[] byRes = new byte[64];
    }

    public static class NET_EHOME_LOCAL_ACCESS_SECURITY extends HIKSDKStructure {
        public int dwSize;
        public byte byAccessSecurity;
        public byte[] byRes = new byte[127];
    }

    public static class NET_EHOME_AMS_ADDRESS extends HIKSDKStructure {
        public int dwSize;
        public byte byEnable;
        public byte[] byRes1 = new byte[3];
        public NET_EHOME_IPADDRESS struAddress = new NET_EHOME_IPADDRESS();
        public byte[] byRes2 = new byte[32];
    }

    public static class NET_EHOME_IPADDRESS extends HIKSDKStructure {
        public byte[] szIP = new byte[128];
        public short wPort;     //端口
        public byte[] byRes = new byte[2];
    }

    public static class NET_EHOME_VOICE_TALK_IN extends HIKSDKStructure {
        public int dwVoiceChan;                                  //通道号
        public NET_EHOME_IPADDRESS struStreamSever;                //流媒体地址
        public byte[] byEncodingType = new byte[9];    //语音对讲编码类型
        public byte byLinkEncrypt;  //
        public byte byBroadcast;  //语音广播标识,设备接收到本标识为1后不进行音频采集发送给对端
        public byte byBroadLevel;//语音广播优先级标识,0~15优先级从低到高,当存在byBroadcast为1时,0标识最低优先级。当存在byBroadcast为0时，本节点无意义为保留字节
        public byte byBroadVolume; //语音广播音量,0~15音量从低到高,当存在byBroadcast为1时,0标识最低音量。当存在byBroadcast为0时，本节点无意义为保留字节
        public byte byAudioSamplingRate; //音频采样率 0-默认, 1-16kHZ, 2-32kHZ, 3-48kHZ, 4-44.1kHZ, 5-8kHZ
        public byte[] byRes = new byte[114];
    }

    public static class NET_EHOME_VOICE_TALK_OUT extends HIKSDKStructure {
        /**
         * 语音对讲请求的会话ID，由设备返回
         */
        public int lSessionID;
        /**
         * 输出参数句柄，在异步模式中作为异步回调的
         * 标识。对应 NET_EHOME_CMSCB_DATA 中的
         * dwHandle。
         */
        public int lHandle;
        /**
         * 保留，设为0。最大长度为124 字节。
         */
        public byte[] byRes = new byte[124];
    }


    public static interface DEVICE_REGISTER_CB extends Callback {
        public boolean invoke(int lUserID, int dwDataType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer, int dwInLen, Pointer pUser);
    }

    public static class NET_EHOME_CMS_LISTEN_PARAM extends HIKSDKStructure {
        public NET_EHOME_IPADDRESS struAddress;  //本地监听信息，IP为0.0.0.0的情况下，默认为本地地址，多个网卡的情况下，默认为从操作系统获取到的第一个
        public DEVICE_REGISTER_CB fnCB; //报警信息回调函数
        public Pointer pUserData;   //用户数据
        public int dwKeepAliveSec; //心跳间隔（单位：秒,0:默认为30S）
        public int dwTimeOutCount; //心跳超时次数（0：默认为3）
        public byte[] byRes = new byte[24];
    }

    public static interface fVoiceDataCallBack extends Callback {
        public void invoke(int iVoiceHandle, byte[] pRecvDataBuffer, int dwBufSize, int dwEncodeType, byte byAudioFlag, Pointer pUser);
    }

    public static class NET_EHOME_VOICETALK_PARA extends HIKSDKStructure {
        public boolean bNeedCBNoEncData; //需要回调的语音类型：0-编码后语音，1-编码前语音（语音转发时不支持）
        public fVoiceDataCallBack cbVoiceDataCallBack; //用于回调音频数据的回调函数
        public int dwEncodeType;    //SDK赋值,SDK的语音编码类型,0- OggVorbis，1-G711U，2-G711A，3-G726，4-AAC，5-MP2L2，6-PCM
        public Pointer pUser;    //用户参数
        public byte byVoiceTalk;    //0-语音对讲,1-语音转发
        public byte byDevAudioEnc;  //输出参数，设备的音频编码方式 0- OggVorbis，1-G711U，2-G711A，3-G726，4-AAC，5-MP2L2，6-PCM
        public byte[] byRes = new byte[62];//Reserved, set as 0. 0
    }

    //预览请求
    public static class NET_EHOME_PREVIEWINFO_IN extends HIKSDKStructure {
        public int iChannel;                        //通道号
        public int dwStreamType;                    // 码流类型，0-主码流，1-子码流, 2-第三码流
        public int dwLinkMode;                        // 0：TCP方式,1：UDP方式,2: HRUDP方式
        public NET_EHOME_IPADDRESS struStreamSever;     //流媒体地址
    }

    public static class NET_EHOME_PREVIEWINFO_IN_V11 extends HIKSDKStructure {
        public int iChannel;
        public int dwStreamType;
        public int dwLinkMode;
        public NET_EHOME_IPADDRESS struStreamSever;
        public byte byDelayPreview;
        public byte[] byRes = new byte[31];
    }

    public static class NET_EHOME_PREVIEWINFO_OUT extends HIKSDKStructure {
        public int lSessionID;
        public byte[] byRes = new byte[128];
    }

    public static class NET_EHOME_PUSHSTREAM_IN extends HIKSDKStructure {
        public int dwSize;
        public int lSessionID;
        public byte[] byRes = new byte[128];
    }


    public static class NET_EHOME_PUSHVOICE_IN extends HIKSDKStructure {
        public int dwSize;
        public int lSessionID;
        public byte[] byToken = new byte[64];
        public byte[] byRes = new byte[64];
    }

    public static class NET_EHOME_PUSHVOICE_OUT extends HIKSDKStructure {
        public int dwSize;
        public int lHandle;
        public byte[] byRes = new byte[124];
    }


    public static class NET_EHOME_PUSHSTREAM_OUT extends HIKSDKStructure {
        public int dwSize;
        public byte[] byRes = new byte[128];
    }


    public static class NET_EHOME_PTXML_PARAM extends HIKSDKStructure {
        public Pointer pRequestUrl;        //请求URL
        public int dwRequestUrlLen;    //请求URL长度
        public Pointer pCondBuffer;        //条件缓冲区（XML格式数据）
        public int dwCondSize;         //条件缓冲区大小
        public Pointer pInBuffer;          //输入缓冲区（XML格式数据）
        public int dwInSize;           //输入缓冲区大小
        public Pointer pOutBuffer;         //输出缓冲区（XML格式数据）
        public int dwOutSize;          //输出缓冲区大小
        public int dwReturnedXMLLen;   //实际从设备接收到的XML数据的长度
        public int dwRecvTimeOut;      //默认5000ms
        public int dwHandle;           //（输出参数）设置了回放异步回调之后，该值为消息句柄，回调中用于标识（新增）
        public byte[] byRes = new byte[24];          //保留
    }


    public static class NET_EHOME_XML_CFG extends HIKSDKStructure {
        public Pointer pCmdBuf;    //字符串格式命令，参见1.2.3
        public int dwCmdLen;   //pCmdBuf长度
        public Pointer pInBuf;     //输入数据，远程配置报文公用定义
        public int dwInSize;   //输入数据长度
        public Pointer pOutBuf;    //输出缓冲<ConfigXML>
        public int dwOutSize;  //输出缓冲区长度
        public int dwSendTimeOut;  //数据发送超时时间,单位ms，默认5s
        public int dwRecvTimeOut;  //数据接收超时时间,单位ms，默认5s
        public Pointer pStatusBuf;     //返回的状态参数(XML格式),如果不需要,可以置NULL
        public int dwStatusSize;   //状态缓冲区大小(内存大小)
        public byte[] byRes = new byte[24];
    }

    public static class NET_EHOME_XML_REMOTE_CTRL_PARAM extends HIKSDKStructure {
        public int dwSize;
        public Pointer lpInbuffer;
        public int dwInBufferSize;
        public int dwSendTimeOut;
        public int dwRecvTimeOut;
        public Pointer lpOutBuffer;     //输出缓冲区
        public int dwOutBufferSize;  //输出缓冲区大小
        public Pointer lpStatusBuffer;   //状态缓冲区,若不需要可置为NULL
        public int dwStatusBufferSize;  //状态缓冲区大小
        public byte[] byRes = new byte[16];
    }

    ;
    //云台相关结构体
    public static final int NET_EHOME_PTZ_CTRL = 1000;   //云镜控制
    public static final int NET_EHOME_PRESET_CTRL = 1001;   //预置点控制

    public static class NET_EHOME_REMOTE_CTRL_PARAM extends HIKSDKStructure {
        public int dwSize;
        public Pointer lpCondBuffer;        //条件参数缓冲区
        public int dwCondBufferSize;    //条件参数缓冲区长度
        public Pointer lpInbuffer;          //控制参数缓冲区
        public int dwInBufferSize;      //控制参数缓冲区长度
        public byte[] byRes = new byte[32];
    }

    ;

    public static class NET_EHOME_PTZ_PARAM extends HIKSDKStructure {
        public int dwSize;
        public byte byPTZCmd; //云台命令，参见EN_PTZ_CMD
        public byte byAction; //云台动作，0-开始云台动作，1-停止云台动作
        public byte bySpeed;  //云台速度，0-7，数值越大速度越快
        public byte[] byRes = new byte[29];
    }
    
    ;
    
    public static class NET_EHOME_PRESET_PARAM extends HIKSDKStructure {
        public int dwSize;                 //结构体大小
        public byte byPresetCmd;           //预置点控制命令：1-设置预置点，2-删除预置点，3-调用预置点
        public byte[] byRes1 = new byte[3];//保留，设为0。最大长度为3字节
        public int dwPresetIndex;          //预置点编号
        public byte[] byRes2 = new byte[32];//保留，设为0。最大长度为32字节
    }

    ;


    public static class NET_DVR_STRING_POINTER extends HIKSDKStructure {
        public byte[] byString = new byte[2 * 1024];
    }


    public static class NET_EHOME_ALARMIN_COND extends HIKSDKStructure {
        public int dwSize;
        public int dwAlarmInNum;
        public int dwPTZChan;
        public byte[] byRes = new byte[20];
    }

    public static class NET_EHOME_ALARMIN_CFG extends HIKSDKStructure {
        public int dwSize;                     //结构体大小
        public byte[] sAlarmInName = new byte[NAME_LEN];     //报警输入名称
        public byte byAlarmInType;              //报警器类型：0：常开；1：常闭
        public byte byUseAlarmIn;               //是否处理，0：不使用；1：使用
        public byte[] byRes1 = new byte[2];                  //保留
        public NET_EHOME_ALARMIN_LINKAGE_TYPE struLinkageType = new NET_EHOME_ALARMIN_LINKAGE_TYPE();    //联动模式
        public NET_EHOME_RECORD_CHAN struRecordChan = new NET_EHOME_RECORD_CHAN();//关联录像通道
        public byte[] byRes2 = new byte[128];                //保留
    }

    public static class NET_EHOME_RECORD_CHAN extends HIKSDKStructure {
        public byte byAnalogChanNum;                    //只读，模拟通道数
        public byte[] byAnalogChan = new byte[MAX_ANALOG_CHANNUM];   //模拟通道，0：不使用；1：使用
        public byte[] byRes1 = new byte[3];                          //保留
        public short wDigitChanNum;                      //只读，数字通道数
        public byte[] byDigitChan = new byte[MAX_DIGIT_CHANNUM];     //数字通道，0：不使用；1：使用
        public byte[] byRes2 = new byte[62];
    }

    public static class NET_EHOME_ALARMIN_LINKAGE_TYPE extends HIKSDKStructure {
        public byte byMonitorAlarm;     //监视器上警告，0：不使用；1：使用
        public byte bySoundAlarm;       //声音报警，0：不使用；1：使用
        public byte byUpload;           //上传中心，0：不使用；1：使用
        public byte byAlarmout;         //触发报警输出，0：不使用；1：使用-
        public byte byEmail;            //邮件联动，0：不使用；1：使用
        public byte[] byRes1 = new byte[3];          //保留
        public NET_EHOME_LINKAGE_PTZ struPTZLinkage = new NET_EHOME_LINKAGE_PTZ();    //PTZ联动
        public NET_EHOME_LINKAGE_ALARMOUT struAlarmOut = new NET_EHOME_LINKAGE_ALARMOUT();    //报警输出联动
        public byte[] byRes = new byte[128];
    }


    public static class NET_EHOME_LINKAGE_PTZ extends HIKSDKStructure {
        public byte byUsePreset;    //是否调用预置点，0：不使用；1：使用
        public byte byUseCurise;    //是否调用巡航，0：不使用；1：使用
        public byte byUseTrack;     //是否调用轨迹，0：不使用；1：使用
        public byte byRes1;         //保留
        public short wPresetNo;      //预置点号，范围：1~256，协议中规定是1～256，实际已有设备支持300
        public short wCuriseNo;      //巡航路径号，范围：1~16
        public short wTrackNo;       //轨迹号，范围：1~16
        public byte[] byRes2 = new byte[6];      //保留
    }

    public static class NET_EHOME_LINKAGE_ALARMOUT extends HIKSDKStructure {
        public int dwAnalogAlarmOutNum;                    //只读，模拟报警数量
        public byte[] byAnalogAlarmOut = new byte[MAX_ANALOG_ALARMOUT];  //模拟报警输出，0：不使用；1：使用
        public byte[] byRes = new byte[5000];
    }

    public static class NET_EHOME_CONFIG extends HIKSDKStructure {
        public Pointer pCondBuf;
        public int dwCondSize;
        public Pointer pInBuf;
        public int dwInSize;
        public Pointer pOutBuf;
        public int dwOutSize;
        public byte[] byRes = new byte[40];
    }

    public static class NET_EHOME_DEVICE_CFG extends HIKSDKStructure {
        public int dwSize;
        public byte[] sServerName = new byte[32];
        public int dwServerID;
        public int dwRecycleRecord;
        public int dwServerType;
        public int dwChannelNum;
        public int dwHardDiskNum;
        public int dwAlarmInNum;
        public int dwAlarmOutNum;
        public int dwRS232Num;
        public int dwRS485Num;
        public int dwNetworkPortNum;
        public int dwAuxoutNum;
        public int dwAudioNum;
        public byte[] sSerialNumber = new byte[128];
        public int dwMajorScale;
        public int dwMinorScale;
        public byte[] byRes = new byte[292];
    }

    public static class NET_EHOME_DEVICE_INFO extends HIKSDKStructure {
        public int dwSize;
        public int dwChannelNumber;
        public int dwChannelAmount;
        public int dwDevType;
        public int dwDiskNumber;
        public byte[] sSerialNumber = new byte[128];
        public int dwAlarmInPortNum;
        public int dwAlarmInAmount;
        public int dwAlarmOutPortNum;
        public int dwAlarmOutAmount;
        public int dwStartChannel;
        public int dwAudioChanNum;
        public int dwMaxDigitChannelNum;
        public int dwAudioEncType;  // 语音对讲的音频格式：0-G.722，1-G.711U，2-G.711A，3-G.726，4-AAC，5-MP2L2。
        public byte[] sSIMCardSN = new byte[128];
        public byte[] sSIMCardPhoneNum = new byte[32];
        public int dwSupportZeroChan;
        public int dwStartZeroChan;
        public int dwSmartType;
        public byte[] byRes1 = new byte[2];
        public byte byStartDTalkChan;
        public byte[] byRes = new byte[157];
    }

    ;


    // 定义枚举值
    public int ENUM_SEARCH_TYPE_ERR = -1;
    public int ENUM_SEARCH_RECORD_FILE = 0;
    public int ENUM_SEARCH_PICTURE_FILE = 1;
    public int ENUM_SEARCH_FLOW_INFO = 2;
    public int ENUM_SEARCH_DEV_LOG = 3;
    public int ENUM_SEARCH_ALARM_HOST_LOG = 4;


    public static class NET_EHOME_REC_FILE_COND extends HIKSDKStructure {
        public int dwChannel; //通道号
        public int dwRecType;//录像类型：0xff-全部类型，0-定时录像，1-移动报警，2-报警触发，3-报警|动测，4-报警&动测，5-命令触发，6-手动录像，7-震动报警，8-环境报警，9-智能报警（或者取证录像），10（0x0a）-PIR报警，11（0x0b）-无线报警，12（0x0c）-呼救报警，13（0x0d）-全部报警
        public NET_EHOME_TIME struStartTime = new NET_EHOME_TIME();//开始时间
        public NET_EHOME_TIME struStopTime = new NET_EHOME_TIME();//结束时间
        public int dwStartIndex;//查询起始位置，从0开始
        public int dwMaxFileCountPer;//单次搜索最大文件个数，最大文件个数，需要确定实际网络环境，建议最大个数为8
        public byte byLocalOrUTC; // 时间类型：0-设备本地时间，即设备OSD时间；1-UTC时间。
        public byte[] byRes = new byte[63];
    }

    ;

    public static class NET_EHOME_REC_FILE extends HIKSDKStructure {
        public int dwSize;
        public byte[] sFileName = new byte[100];
        public NET_EHOME_TIME struStartTime = new NET_EHOME_TIME();
        public NET_EHOME_TIME struStopTime = new NET_EHOME_TIME();
        public int dwFileSize;
        public int dwFileMainType;
        public int dwFileSubType;
        public int dwFileIndex;
        public byte[] byRes = new byte[128];
    }

    ;

    public static class NET_EHOME_TIME extends HIKSDKStructure {
        public short wYear;//年
        public byte byMonth;//月
        public byte byDay;//日
        public byte byHour;//时
        public byte byMinute;//分
        public byte bySecond;//秒
        public byte byRes1;//保留
        public short wMSecond;//毫秒
        public byte[] byRes2 = new byte[2];
    }

    public static class NET_EHOME_PLAYBACK_INFO_IN extends HIKSDKStructure {
        public int dwSize;
        public int dwChannel;                    //回放的通道号
        public byte byPlayBackMode;               //回放下载模式 0－按名字 1－按时间
        public byte byStreamPackage;              //回放码流类型，设备端发出的码流格式 0－PS（默认） 1－RTP
        public byte[] byRes = new byte[2];
        public NET_EHOME_PLAYBACKMODE unionPlayBackMode;
        public NET_EHOME_IPADDRESS struStreamSever;
    }

    public static class NET_EHOME_PLAYBACKMODE extends Union {
        public byte[] byLen = new byte[512];
        public NET_EHOME_PLAYBACKBYNAME struPlayBackbyName;
        public NET_EHOME_PLAYBACKBYTIME struPlayBackbyTime;
    }

    public static class NET_EHOME_PLAYBACKBYNAME extends HIKSDKStructure {
        public byte[] szFileName = new byte[100/*MAX_FILE_NAME_LEN*/];   //回放的文件名
        public int dwSeekType;                      //0-按字节长度计算偏移量  1-按时间（秒数）计算偏移量
        public int dwFileOffset;                    //文件偏移量，从哪个位置开始下载，如果dwSeekType为0，偏移则以字节计算，为1则以秒数计算
        public int dwFileSpan;                      //下载的文件大小，为0时，表示下载直到该文件结束为止，如果dwSeekType为0，大小则以字节计算，为1则以秒数计算
    }

    public static class NET_EHOME_PLAYBACKBYTIME extends HIKSDKStructure {
        public NET_EHOME_TIME struStartTime;  // 按时间回放的开始时间
        public NET_EHOME_TIME struStopTime;   // 按时间回放的结束时间
        public byte byLocalOrUTC;           //0-设备本地时间，即设备OSD时间  1-UTC时间
        public byte byDuplicateSegment;     //byLocalOrUTC为1时无效 0-重复时间段的前段 1-重复时间段后端
    }

    public static class NET_EHOME_PLAYBACK_INFO_OUT extends HIKSDKStructure {
        public int lSessionID;     //目前协议不支持，返回-1
        public int lHandle;  //设置了回放异步回调之后，该值为消息句柄，回调中用于标识
        public byte[] byRes = new byte[124];
    }


    public static class NET_EHOME_PLAYBACK_PAUSE_RESTART_PARAM extends HIKSDKStructure {
        public int lSessionID;
        public int lHandle;
        public byte[] byRes = new byte[120];
    }

    boolean NET_ECMS_PlayBackOperate(int lUserID, int enumMode, Pointer pOperateParam);

    public static class NET_EHOME_PUSHPLAYBACK_IN extends HIKSDKStructure {
        public int dwSize;
        public int lSessionID;
        public byte[] byKeyMD5 = new byte[32];//码流加密秘钥,两次MD5
        public byte[] byRes = new byte[96];
    }

    public static class NET_EHOME_PUSHPLAYBACK_OUT extends HIKSDKStructure {
        public int dwSize;
        public int lHandle;
        public byte[] byRes = new byte[124];
    }

    public static class BYTE_ARRAY extends HIKSDKStructure {
        public byte[] byValue;

        public BYTE_ARRAY(int iLen) {
            byValue = new byte[iLen];
        }

        @Override
        protected List<String> getFieldOrder() {
            // TODO Auto-generated method stub
            return Arrays.asList("byValue");
        }
    }


    //初始化，反初始化
    boolean NET_ECMS_Init();

    boolean NET_ECMS_Fini();

    boolean NET_ECMS_SetSDKInitCfg(int enumType, Pointer lpInBuff);

    //获取错误码
    int NET_ECMS_GetLastError();

    //获取版本号
    int NET_ECMS_GetBuildVersion();

    boolean NET_ECMS_SetSDKLocalCfg(int enumType, Pointer lpInBuff);

    boolean NET_ECMS_GetSDKLocalCfg(int enumType, Pointer lpOutBuff);

    boolean NET_ECMS_SetDeviceSessionKey(Pointer pDeviceKey);

    //开启关闭监听
    int NET_ECMS_StartListen(NET_EHOME_CMS_LISTEN_PARAM lpCMSListenPara);

    boolean NET_ECMS_StopListen(int iHandle);

    boolean NET_ECMS_GetDevConfig(int lUserID, int dwCommand, Pointer lpConfig, int dwConfigSize);

    //注销设备
    boolean NET_ECMS_ForceLogout(int lUserID)
    ;

    boolean NET_ECMS_SetLogToFile(int iLogLevel, String strLogDir, boolean bAutoDel);

    int NET_ECMS_StartVoiceTalk(int lUserID, int dwVoiceChan, NET_EHOME_VOICETALK_PARA pVoiceTalkPara);

    boolean NET_ECMS_StartVoiceWithStmServer(int lUserID, NET_EHOME_VOICE_TALK_IN lpVoiceTalkIn, NET_EHOME_VOICE_TALK_OUT lpVoiceTalkOut);

    boolean NET_ECMS_StartPushVoiceStream(int lUserID, NET_EHOME_PUSHVOICE_IN lpPushParamIn, NET_EHOME_PUSHVOICE_OUT lpPushParamOut);

    boolean NET_ECMS_StopVoiceTalk(int iVoiceHandle);

    boolean NET_ECMS_StopVoiceTalkWithStmServer(int lUserID, int lSessionID);

    boolean NET_ECMS_SendVoiceTransData(int iVoiceHandle, String pSendBuf, int dwBufSize);

    boolean NET_ECMS_StartGetRealStream(int lUserID, NET_EHOME_PREVIEWINFO_IN pPreviewInfoIn, NET_EHOME_PREVIEWINFO_OUT pPreviewInfoOut); //lUserID由SDK分配的用户ID，由设备注册回调时fDeviceRegisterCallBack返回

    boolean NET_ECMS_StartGetRealStreamV11(int lUserID, NET_EHOME_PREVIEWINFO_IN_V11 pPreviewInfoIn, NET_EHOME_PREVIEWINFO_OUT pPreviewInfoOut);

    boolean NET_ECMS_StopGetRealStream(int lUserID, int lSessionID);

    boolean NET_ECMS_StartPushRealStream(int lUserID, NET_EHOME_PUSHSTREAM_IN pPushInfoIn, NET_EHOME_PUSHSTREAM_OUT pPushInfoOut);

    boolean NET_ESTREAM_StopListenPreview(int lPreivewListenHandle);

    boolean NET_ECMS_GetPTXMLConfig(int iUserID, NET_EHOME_PTXML_PARAM lpPTXMLParam);

    boolean NET_ECMS_PutPTXMLConfig(int iUserID, NET_EHOME_PTXML_PARAM lpPTXMLParam);

    boolean NET_ECMS_PostPTXMLConfig(int iUserID, NET_EHOME_PTXML_PARAM lpPTXMLParam);

    boolean NET_ECMS_DeletePTXMLConfig(int iUserID, NET_EHOME_PTXML_PARAM lpPTXMLParam);

    boolean NET_ECMS_XMLConfig(int iUserID, NET_EHOME_XML_CFG pXmlCfg, int dwConfigSize);

    boolean NET_ECMS_XMLRemoteControl(int lUserID, NET_EHOME_XML_REMOTE_CTRL_PARAM lpCtrlParam, int dwCtrlSize);

    boolean NET_ECMS_ISAPIPassThrough(int lUserID, NET_EHOME_PTXML_PARAM lpParam);

    boolean NET_ECMS_RemoteControl(int lUserID, int dwCommand, NET_EHOME_REMOTE_CTRL_PARAM lpCtrlParam);


    //获取查下句柄
    int NET_ECMS_StartFindFile_V11(int lUserID, int lSearchType, Pointer pFindCond, int dwCondSize);

    int NET_ECMS_FindNextFile_V11(int lHandle, Pointer pFindData, int dwDataSize);

    boolean NET_ECMS_StopFindFile(int lHandle);

    boolean NET_ECMS_StartPlayBack(int lUserID, NET_EHOME_PLAYBACK_INFO_IN pPlaybackInfoIn, NET_EHOME_PLAYBACK_INFO_OUT pPlaybackInfoOut);

    boolean NET_ECMS_StartPushPlayBack(int lUserID, NET_EHOME_PUSHPLAYBACK_IN struPushPlayBackIn, NET_EHOME_PUSHPLAYBACK_OUT struPushPlayBackOut);

    boolean NET_ECMS_StopPlayBack(int lUserID, int lSessionID);


}

interface GDI32 extends W32API {
    GDI32 INSTANCE = (GDI32) Native.loadLibrary("gdi32", GDI32.class, DEFAULT_OPTIONS);

    public static final int TRANSPARENT = 1;

    int SetBkMode(HDC hdc, int i);

    HANDLE CreateSolidBrush(int icolor);
}
