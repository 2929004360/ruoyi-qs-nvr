package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * 
 * @author 119178
 * 备份目的
 */
public class NET_BACKUP_TARGET extends SdkStructure {
    public int              emTargetType;                         //  目标类型(EM_BACKUP_TARGET_TYPE)
    public byte[]           Reserved = new byte[4];               //  字节对齐
    public byte[]           szPath = new byte[256];               //  备份的目标路径
    public byte[]           szRename = new byte[256];             //  备份后的文件名，如果无该字段，保留原文件名
    public byte[]           byReserved = new byte[1024];          //  保留字节
}

