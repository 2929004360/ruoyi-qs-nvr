package com.ruoyi.haikang.isup.service.haikang.stream;

import com.ruoyi.haikang.isup.service.haikang.HIKSDKStructure;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface HCISUPStream extends Library {

    public static final int NET_EHOME_SYSHEAD = 1;//系统头数据
    public static final int NET_EHOME_STREAMDATA = 2;//视频流数据（包括复合流和音视频分开的视频流数据）
    public static final int NET_EHOME_STREAMEND = 3;//视频流结束标记


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

    public class NET_EHOME_PLAYBACK_LISTEN_PARAM extends HIKSDKStructure {
        public HCISUPCMS.NET_EHOME_IPADDRESS struIPAdress; //本地监听信息，IP为0.0.0.0的情况下，默认为本地地址，多个网卡的情况下，默认为从操作系统获取到的第一个
        public PLAYBACK_NEWLINK_CB fnNewLinkCB; //预览请求回调函数，当收到预览连接请求后，SDK会回调该回调函数。
        public Pointer pUser;        // 用户参数，在fnNewLinkCB中返回出来
        public byte byLinkMode;   //0：TCP，1：UDP 2: HRUDP方式
        public byte[] byRes = new byte[127];
    }


    public class NET_EHOME_LISTEN_PREVIEW_CFG extends HIKSDKStructure {
        public HCISUPCMS.NET_EHOME_IPADDRESS struIPAdress; //本地监听信息，IP为0.0.0.0的情况下，默认为本地地址，多个网卡的情况下，默认为从操作系统获取到的第一个
        public PREVIEW_NEWLINK_CB fnNewLinkCB; //预览请求回调函数，当收到预览连接请求后，SDK会回调该回调函数。
        public Pointer pUser;        // 用户参数，在fnNewLinkCB中返回出来
        public byte byLinkMode;   //0：TCP，1：UDP 2: HRUDP方式
        public byte[] byRes = new byte[127];
    }

    public class NET_EHOME_NEWLINK_CB_MSG extends HIKSDKStructure {
        public byte[] szDeviceID = new byte[HCISUPCMS.MAX_DEVICE_ID_LEN];   //设备标示符
        public int iSessionID;     //设备分配给该取流会话的ID
        public int dwChannelNo;    //设备通道号
        public byte byStreamType;   //0-主码流，1-子码流
        public byte[] byRes1 = new byte[3];
        public byte[] sDeviceSerial = new byte[HCISUPCMS.NET_EHOME_SERIAL_LEN];    //设备序列号，数字序列号
        public byte[] byRes = new byte[112];
    }

    public class NET_EHOME_PREVIEW_CB_MSG extends HIKSDKStructure {
        public byte byDataType;       //NET_DVR_SYSHEAD(1)-码流头，NET_DVR_STREAMDATA(2)-码流数据
        public byte[] byRes1 = new byte[3];
        public Pointer pRecvdata;      //码流头或者数据
        public int dwDataLen;      //数据长度
        public byte[] byRes2 = new byte[128];
    }

    public class NET_EHOME_PREVIEW_DATA_CB_PARAM extends HIKSDKStructure {
        public PREVIEW_DATA_CB fnPreviewDataCB;    //数据回调函数
        public Pointer pUserData;         //用户参数, 在fnPreviewDataCB回调出来
        public byte[] byRes = new byte[128];          //保留
    }

    public static final int NET_EHOME_DEVICEID_LEN = 256;  //设备ID长度
    public static final int NET_EHOME_SERIAL_LEN = 12;

    public class NET_EHOME_PLAYBACK_NEWLINK_CB_INFO extends HIKSDKStructure {
        public byte[] szDeviceID = new byte[NET_EHOME_DEVICEID_LEN];
        public int lSessionID;     //设备分配给该回放会话的ID，0表示无效(出参)
        public int dwChannelNo;    //设备通道号，0表示无效(出参)
        public byte[] sDeviceSerial = new byte[NET_EHOME_SERIAL_LEN/*12*/]; //设备序列号，数字序列号(出参)
        public byte byStreamFormat;         //码流封装格式：0-PS 1-RTP(入参)
        public byte[] byRes1 = new byte[3];
        public PLAYBACK_DATA_CB fnPlayBackDataCB;
        public Pointer pUserData;
        public byte[] byRes = new byte[88];
    }


    public class NET_EHOME_PLAYBACK_DATA_CB_PARAM extends HIKSDKStructure {
        public PLAYBACK_DATA_CB fnPlayBackDataCB;   //数据回调函数
        public Pointer pUserData;          //用户参数, 在fnPlayBackDataCB回调出来
        public byte byStreamFormat;     //码流封装格式：0-PS 1-RTP
        public byte[] byRes = new byte[127];         //保留
    }

    public class NET_EHOME_PLAYBACK_DATA_CB_INFO extends HIKSDKStructure {
        public int dwType;     //类型 1-头信息 2-码流数据 3-回放停止信令
        public Pointer pData;      //数据指针
        public int dwDataLen;  //数据长度
        public byte[] byRes = new byte[128]; //保留
    }


    public interface PLAYBACK_DATA_CB extends Callback {
        public boolean invoke(int iPlayBackLinkHandle, NET_EHOME_PLAYBACK_DATA_CB_INFO pDataCBInfo, Pointer pUserData);
    }

    public interface PREVIEW_NEWLINK_CB extends Callback {
        public boolean invoke(int lLinkHandle, NET_EHOME_NEWLINK_CB_MSG pNewLinkCBMsg, Pointer pUserData) throws IOException;
    }

    public interface PLAYBACK_NEWLINK_CB extends Callback {
        public boolean invoke(int lPlayBackLinkHandle, NET_EHOME_PLAYBACK_NEWLINK_CB_INFO pNewLinkCBMsg, Pointer pUserData);
    }


    public interface PREVIEW_DATA_CB extends Callback {
        public void invoke(int iPreviewHandle, NET_EHOME_PREVIEW_CB_MSG pPreviewCBMsg, Pointer pUserData) throws IOException;
    }

    public interface fExceptionCallBack extends Callback {
        public void invoke(int dwType, int iUserID, int iHandle, Pointer pUser);
    }

    public interface VOICETALK_NEWLINK_CB extends Callback {
        public boolean invoke(int lHandle, NET_EHOME_VOICETALK_NEWLINK_CB_INFO pNewLinkCBInfo, Pointer pUserData);
    }

    public interface VOICETALK_DATA_CB extends Callback {
        public boolean invoke(int lHandle, NET_EHOME_VOICETALK_DATA_CB_INFO pNewLinkCBInfo, Pointer pUserData);
    }


    public static class StringPointer extends HIKSDKStructure {
        public byte[] data;

        public StringPointer() {
        }

        public StringPointer(String sInput) {
            this.data = new byte[sInput.length()];
            this.data = sInput.getBytes();
        }
    }

    public static class NET_EHOME_LISTEN_VOICETALK_CFG extends HIKSDKStructure {
        public HCISUPCMS.NET_EHOME_IPADDRESS struIPAdress;       //本地监听信息，IP为0.0.0.0的情况下，默认为本地地址，
        //多个网卡的情况下，默认为从操作系统获取到的第一个
        public VOICETALK_NEWLINK_CB fnNewLinkCB;   //新连接回调函数
        public Pointer pUser;                   //用户参数，在fnNewLinkCB中返回出来
        public byte byLinkMode;     //0：TCP，1：UDP (UDP保留)
        public byte byLinkEncrypt;  //是否启用链路加密,TCP通过TLS传输，UDP(包括NPQ)使用DTLS传输，0-不启用，1-启用
        public byte[] byRes = new byte[126];
    }

    public static class NET_EHOME_VOICETALK_NEWLINK_CB_INFO extends HIKSDKStructure {
        public byte[] szDeviceID = new byte[NET_EHOME_DEVICEID_LEN/*256*/];   //设备标示符(出参)
        public int dwEncodeType; // //SDK赋值,当前对讲设备的语音编码类型,0- G722_1，1-G711U，2-G711A，3-G726，4-AAC，5-MP2L2，6-PCM, 7-MP3, 8-G723, 9-MP1L2, 10-ADPCM, 99-RAW(未识别类型)(出参)
        public byte[] sDeviceSerial = new byte[NET_EHOME_SERIAL_LEN/*12*/];    //设备序列号，数字序列号(出参)
        public int dwAudioChan; //对讲通道(出参)
        public int lSessionID;  //设备分配给该回放会话的ID，0表示无效(出参)
        public byte[] byToken = new byte[64];
        public VOICETALK_DATA_CB fnVoiceTalkDataCB;   //数据回调函数(入参)
        public Pointer pUserData;         //用户参数, 在fnVoiceTalkDataCB回调出来(入参)
        public byte[] byRes = new byte[48];
    }


    public static class NET_EHOME_VOICETALK_DATA_CB_PARAM extends HIKSDKStructure {
        public VOICETALK_DATA_CB fnVoiceTalkDataCB;  //数据回调函数
        public Pointer pUserData;  //用户参数, 在fnVoiceTalkDataCB回调出来
        public byte[] byRes = new byte[128]; //保留
    }

    public static class NET_EHOME_VOICETALK_DATA_CB_INFO extends HIKSDKStructure {
        public Pointer pData;          //数据指针
        public int dwDataLen;      //数据长度
        public byte[] byRes = new byte[128];     //保留
    }


    public static class NET_EHOME_VOICETALK_DATA extends HIKSDKStructure {
        public Pointer pData;          //数据指针
        public int dwDataLen;         //数据长度
        public byte[] byRes = new byte[128];     //保留
    }


    public boolean NET_ESTREAM_Init();

    public boolean NET_ESTREAM_SetSDKLocalCfg(int enumType, Pointer lpInBuff);

    public boolean NET_ESTREAM_SetSDKInitCfg(int enumType, Pointer lpInBuff);


    public boolean NET_ESTREAM_Fini();

    public int NET_ESTREAM_GetLastError();

    public boolean NET_ESTREAM_SetExceptionCallBack(int dwMessage, int hWnd, fExceptionCallBack cbExceptionCallBack, Pointer pUser);

    public boolean NET_ESTREAM_SetLogToFile(int iLogLevel, String strLogDir, boolean bAutoDel);

    //获取版本号
    public int NET_ESTREAM_GetBuildVersion();

    public int NET_ESTREAM_StartListenPreview(NET_EHOME_LISTEN_PREVIEW_CFG pListenParam);

    public int NET_ESTREAM_StartListenPlayBack(NET_EHOME_PLAYBACK_LISTEN_PARAM pListenParam);

    public boolean NET_ESTREAM_SetPlayBackDataCB(int iPlayBackLinkHandle, NET_EHOME_PLAYBACK_DATA_CB_PARAM pDataCBParam);

    public boolean NET_ESTREAM_StopListenPreview(int iListenHandle);

    public boolean NET_ESTREAM_StopListenVoiceTalk(int lListenHandle);

    public boolean NET_ESTREAM_StopPreview(int iPreviewHandle);

    public boolean NET_ESTREAM_StopVoiceTalk(int lHandle);

    public boolean NET_ESTREAM_SetPreviewDataCB(int iHandle, NET_EHOME_PREVIEW_DATA_CB_PARAM pStruCBParam);

    boolean NET_ESTREAM_StopPlayBack(int iPlayBackLinkHandle);

    boolean NET_ESTREAM_StopListenPlayBack(int iPlaybackListenHandle);

    public int NET_ESTREAM_StartListenVoiceTalk(NET_EHOME_LISTEN_VOICETALK_CFG pListenParam);

    public boolean NET_ESTREAM_SetVoiceTalkDataCB(int lHandle, NET_EHOME_VOICETALK_DATA_CB_PARAM pStruCBParam);

    public int NET_ESTREAM_SendVoiceTalkData(int lHandle, NET_EHOME_VOICETALK_DATA pVoicTalkData);


}
