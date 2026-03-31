package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 文件信息扩展字段
*/
public class NET_EMERGENCY_FILE_INFO_EXTERN extends NetSDKLib.SdkStructure
{
    /**
     * 用户ID，具有唯一性
    */
    public byte[]           szUid = new byte[32];
    /**
     * 用户名，不同账户体系下可能重名
    */
    public byte[]           szUserName = new byte[32];
    /**
     * 文件分辨率
    */
    public byte[]           szVideoResolution = new byte[32];
    /**
     * VKEKID数组数量
    */
    public int              nVideoKeyEncryptionKeyIdsNum;
    /**
     * VKEKID数组
    */
    public BYTE_ARRAY_64[]  szVideoKeyEncryptionKeyIds = new BYTE_ARRAY_64[8];
    /**
     * 案件编号
    */
    public byte[]           szCaseCode = new byte[64];
    /**
     * MD5值
    */
    public byte[]           szMd5 = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[508];

    public NET_EMERGENCY_FILE_INFO_EXTERN() {
        for(int i = 0; i < szVideoKeyEncryptionKeyIds.length; i++){
            szVideoKeyEncryptionKeyIds[i] = new BYTE_ARRAY_64();
        }
    }
}

