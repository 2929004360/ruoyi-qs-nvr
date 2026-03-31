package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

/**
 * 
 * @author 119178
 *  备份源
 */
public class NET_BACKUP_SOURCE extends SdkStructure {
    public int              emSourceType;                         //  源类型(EM_BACKUP_SOURCE_TYPE)
    public byte[]           Reserved = new byte[4];               //  字节对齐
    public byte[]           szPath = new byte[256];               //  源路径，emSourceType为EM_BACKUP_SOURCE_TYPE_REALTIME时，对应格式"通道号/码流类型"。例："1/Main"
	                                                        //	码流类型：Main、Extra1、Extra2、Extra3
    public byte[]           szRename = new byte[256];             //  备份后的文件名
    public byte[]           byReserved = new byte[1024];          //  保留字节
}

