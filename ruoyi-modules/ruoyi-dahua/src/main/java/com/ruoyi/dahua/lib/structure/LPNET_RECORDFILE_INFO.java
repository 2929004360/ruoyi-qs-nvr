package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 录像文件信息
 *
 * @author 47040
 * @since Created at 2021/5/31 15:47
 */
public class LPNET_RECORDFILE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              ch;
    /**
     * 文件名
     */
    public byte[]           filename = new byte[124];
    /**
     * 文件总帧数
     */
    public int              framenum;
    /**
     * 文件长度, 单位为 Kbyte
     */
    public int              size;
    /**
     * 开始时间
     */
    public NET_TIME         starttime;
    /**
     * 结束时间
     */
    public NET_TIME         endtime;
    /**
     * 磁盘号(区分网络录像和本地录像的类型,0－127表示本地录像,其中64表示光盘1,128表示网络录像)
     */
    public int              driveno;
    /**
     * 起始簇号
     */
    public int              startcluster;
    /**
     * 录象文件类型  0：普通录象；1：报警录象；2：移动检测；3：卡号录象；4：图片, 5: 智能录像, 19: POS录像, 255:所有录像
     */
    public byte             nRecordFileType;
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
}

