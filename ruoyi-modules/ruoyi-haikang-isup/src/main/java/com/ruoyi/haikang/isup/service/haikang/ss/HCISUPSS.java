package com.ruoyi.haikang.isup.service.haikang.ss;

import com.ruoyi.haikang.isup.service.haikang.HIKSDKStructure;
import com.ruoyi.haikang.isup.utils.CommonMethod;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Union;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface HCISUPSS extends Library {

	public static final int MAX_URL_LEN_SS = 4096;
	public static final int MAX_KMS_USER_LEN = 512;
	public static final int MAX_KMS_PWD_LEN = 512;
	public static final int MAX_CLOUD_AK_SK_LEN = 64;
	public static final int MAX_PATH = 260;
	public static final int NET_EHOME_SERIAL_LEN = 12;

	public static final int NET_EHOME_SS_MSG_TOMCAT = 1;
	public static final int NET_EHOME_SS_MSG_KMS_USER_PWD = 2;
	public static final int NET_EHOME_SS_MSG_CLOUD_AK = 3;

	public static final int NET_EHOME_SS_CLIENT_TYPE_TOMCAT = 1;
	public static final int NET_EHOME_SS_CLIENT_TYPE_VRB = 2;
	public static final int NET_EHOME_SS_CLIENT_TYPE_KMS = 3;
	public static final int NET_EHOME_SS_CLIENT_TYPE_CLOUD = 4;

	public static final int NET_EHOME_SS_INIT_CFG_SDK_PATH = 1;
	public static final int NET_EHOME_SS_INIT_CFG_CLOUD_TIME_DIFF = 2;

	public enum NET_EHOME_SS_MSG_TYPE {
		NET_EHOME_SS_MSG_TOMCAT,
		NET_EHOME_SS_MSG_KMS_USER_PWD,
		NET_EHOME_SS_MSG_CLOUD_AK
	}

	public enum NET_EHOME_SS_CLIENT_TYPE {
		NET_EHOME_SS_CLIENT_TYPE_TOMCAT,
		NET_EHOME_SS_CLIENT_TYPE_VRB,
		NET_EHOME_SS_CLIENT_TYPE_KMS,
		NET_EHOME_SS_CLIENT_TYPE_CLOUD
	}

	public enum NET_EHOME_SS_INIT_CFG_TYPE {
		NET_EHOME_SS_INIT_CFG_SDK_PATH,
		NET_EHOME_SS_INIT_CFG_CLOUD_TIME_DIFF
	}

	public static class NET_EHOME_SS_TOMCAT_MSG extends HIKSDKStructure {
		public byte[] szDevUri = new byte[MAX_URL_LEN_SS];
		public int dwPicNum;
		public String pPicURLs;
		public byte[] byRes = new byte[64];
	}

	public static class NET_EHOME_SS_LISTEN_PARAM extends HIKSDKStructure {
		public NET_EHOME_IPADDRESS struAddress = new NET_EHOME_IPADDRESS();
		public byte[] szKMS_UserName = new byte[MAX_KMS_USER_LEN];
		public byte[] szKMS_Password = new byte[MAX_KMS_PWD_LEN];
		public EHomeSSStorageCallBack fnSStorageCb;
		public EHomeSSMsgCallBack fnSSMsgCb;
		public byte[] szAccessKey = new byte[MAX_CLOUD_AK_SK_LEN];
		public byte[] szSecretKey = new byte[MAX_CLOUD_AK_SK_LEN];
		public Pointer pUserData;
		public byte byHttps;
		public byte[] byRes1 = new byte[3];
		public EHomeSSRWCallBack fnSSRWCb;
		public EHomeSSRWCallBackEx fnSSRWCbEx;
		public byte bySecurityMode;
		public byte[] byRes = new byte[51];
	}

	public static class NET_EHOME_IPADDRESS extends HIKSDKStructure {
		public byte[] szIP = new byte[128];
		public short wPort;
		public byte[] byRes = new byte[2];
	}

	public static class NET_EHOME_SS_LISTEN_HTTPS_PARAM extends HIKSDKStructure {
		public byte byHttps;
		public byte byVerifyMode;
		public byte byCertificateFileType;
		public byte byPrivateKeyFileType;
		public byte[] szUserCertificateFile = new byte[MAX_PATH];
		public byte[] szUserPrivateKeyFile = new byte[32];
		public int dwSSLVersion;
		public byte[] byRes3 = new byte[360];
	}

	public static class NET_EHOME_SS_CLIENT_PARAM extends HIKSDKStructure {
		public int enumType;
		public NET_EHOME_IPADDRESS struAddress;
		public byte byHttps;
		public byte[] byRes = new byte[63];
	}

	public static class NET_EHOME_SS_LOCAL_SDK_PATH extends HIKSDKStructure {
		public byte[] sPath = new byte[MAX_PATH];
		public byte[] byRes = new byte[128];
	}

	public static class NET_EHOME_SS_RW_PARAM extends HIKSDKStructure {
		public Pointer pFileName;
		public Pointer pFileBuf;
		public IntByReference dwFileLen;
		public Pointer pFileUrl;
		public Pointer pUser;
		public byte byAct;
		public byte byUseRetIndex;
		public byte[] byRes1 = new byte[2];
		public Pointer pRetIndex;
		public byte[] byRes = new byte[56];
	}

	public static class StringPointer extends HIKSDKStructure {
		public byte[] sData;

		public StringPointer() {}

		public StringPointer(int dwLength) {
			if(dwLength == 0) {
				throw new NullPointerException("Data length can`t be zero");
			}
			this.sData = new byte[dwLength];
		}

		public StringPointer(String sContent) {
			if(sContent == null) {
				throw new NullPointerException("Content can`t be null");
			}
			this.sData = new byte[256];
			System.arraycopy(sContent.getBytes(), 0, this.sData, 0, sContent.length());
		}

		public StringPointer(byte[] byData) {
			this.sData = new byte[256];
			System.arraycopy(byData, 0, this.sData, 0, byData.length);
		}

		public String GetString() {
			return CommonMethod.byteToString(this.sData);
		}

		public byte[] GetByteArray() {
			return this.sData;
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("sData");
		}
	}

	public static class NET_EHOME_SS_EX_PARAM extends HIKSDKStructure {
		public byte byProtoType;
		public byte[] byRes = new byte[23];
		public NET_EHOME_SS_Union unionStoreInfo = new NET_EHOME_SS_Union();
	}

	public static class NET_EHOME_SS_Union extends Union {
		public NET_EHOME_SS_CLOUD_PARAM struCloud = new NET_EHOME_SS_CLOUD_PARAM();
	}

	public static class NET_EHOME_SS_CLOUD_PARAM extends HIKSDKStructure {
		public String pPoolId;
		public byte byPoolIdLength;
		public int dwErrorCode;
		public byte[] byRes = new byte[503];
	}

	boolean NET_ESS_Init();

	boolean NET_ESS_Fini();

	public static interface EHomeSSMsgCallBack extends Callback {
		public boolean invoke(int iHandle, int enumType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer,
				int dwInLen, Pointer pUser);
	}

	public static interface EHomeSSStorageCallBack extends Callback {
		public boolean invoke(int iHandle, String pFileName, Pointer pFileBuf, int dwOutLen, Pointer pFilePath,
				Pointer pUser) throws IOException;
	}

	public static interface EHomeSSRWCallBack extends Callback {
		public boolean invoke(int iHandle, byte byAct, String pFileName, Pointer pFileBuf, int dwFileLen, String pFileUrl,
				Pointer pUser);
	}

	public static interface EHomeSSRWCallBackEx extends Callback {
		public boolean invoke(int iHandle, NET_EHOME_SS_RW_PARAM pRwParam, NET_EHOME_SS_EX_PARAM pExStruct);
	}

	int NET_ESS_GetLastError();

	boolean NET_ESS_SetLogToFile(int iLogLevel, String strLogDir, boolean bAutoDel);

	boolean NET_ESS_SetSDKInitCfg(int enumType, Pointer lpInBuff);

	int NET_ESS_GetBuildVersion();

	boolean NET_ESS_SetListenHttpsParam(NET_EHOME_SS_LISTEN_HTTPS_PARAM pSSHttpsParam);

	int NET_ESS_StartListen(NET_EHOME_SS_LISTEN_PARAM pSSListenParam);

	boolean NET_ESS_StopListen(int lListenHandle);

	int NET_ESS_CreateClient(NET_EHOME_SS_CLIENT_PARAM pClientParam);

	boolean NET_ESS_ClientSetTimeout(int lHandle, int dwSendTimeout, int dwRecvTimeout);

	boolean NET_ESS_ClientSetParam(int lHandle, String strParamName, String strParamVal);

	boolean NET_ESS_ClientDoUpload(int lHandle, byte[] strUrl, int dwUrlLen);

	boolean NET_ESS_ClientDoDownload(int lHandle, String strUrl, PointerByReference pFileContent, IntByReference dwContentLen);

	boolean NET_ESS_DestroyClient(int lHandle);

	boolean NET_ESS_HAMSHA256(String pSrc, String pSecretKey, String pSingatureOut, int dwSingatureLen);

}
