package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_REAL_DATA_TYPE;
import com.sun.jna.Pointer;

import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.MAX_PATH;

/**
 * 自适应下载录像 按文件 {@link NetSDKLib#CLIENT_DownloadByFileSelfAdapt} 入参
 * 出参 {@link NET_OUT_DOWNLOAD_BYFILE_SELFADAPT}
 *
 * @author 47040
 * @since Created at 2021/6/19 10:35
 */
public class NET_IN_DOWNLOAD_BYFILE_SELFADAPT extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 录像类型枚举值 {@link NetSDKLib.EM_QUERY_RECORD_TYPE}
     */
    public int              emRecordType;
    /**
     * 下载的文件路径
     */
    public byte[]           szFileName = new byte[MAX_PATH];
    /**
     * 文件长度, 单位为 Kbyte
     */
    public int              size;
    /**
     * 进度回调
     */
    public NetSDKLib.fDownLoadPosCallBack cbDownLoadPos;
    /**
     * 进度回调用户信息
     */
    public Pointer          dwPosUser;
    /**
     * 数据回调
     */
    public NetSDKLib.fDataCallBack fDownLoadDataCallBack;
    /**
     * 回调的数据类型 枚举值 {@link EM_REAL_DATA_TYPE}
     */
    public int              emDataType;
    /**
     * 数据回调用户信息
     */
    public Pointer          dwDataUser;
    /**
     * 扩展字段 文件总帧数
     */
    public int              nFrameNum;
    /**
     * 开始时间
     */
    public NetSDKLib.NET_TIME stuStartTime;
    /**
     * 结束时间
     */
    public NetSDKLib.NET_TIME stuEndTime;
    /**
     * 磁盘号(区分网络录像和本地录像的类型,0－127表示本地录像,其中64表示光盘1,128表示网络录像)
     */
    public int              nDriveno;
    /**
     * 起始簇号
     */
    public int              nStartCluster;
    /**
     * 0:普通录像 1:重要录像
     */
    public byte             bImportantRecID;
    /**
     * 文件定位索引(nRecordFileType==4<图片>时,bImportantRecID<<8 +bHint ,组成图片定位索引 )
     */
    public byte             bHint;
    /**
     * 0-主码流录像 1-辅码1流录像 2-辅码流2 3-辅码流3录像
     */
    public byte             bRecType;
    /**
     * 加密录像密码，密码长度不小于8
     */ 
    public byte[]           szEncryptPasswd = new byte[64];
    /**
     * VK信息回调
     */
    public NetSDKLib.fVKInfoCallBack pVKInfoCallBack;
	/**
	 * VK信息回调用户信息
	 */
    public Pointer          dwVKInfoUser;

    public NET_IN_DOWNLOAD_BYFILE_SELFADAPT() {
        dwSize = this.size();
    }
}

