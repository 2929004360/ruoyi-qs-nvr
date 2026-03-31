package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 采集文件可修改备注
*/
public class NET_MEDIA_FILE_REMARK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 上传平台状态,1-未上传;2-已上传
    */
    public int              nUpLoadState;
    /**
     * 图片旋转状态,0-不旋转,1-左旋转90度,2-左旋转180度,3-左旋转270度,4-右旋转90度,5-右旋转180度,6-右旋转270度
    */
    public int              nPicTurn;
    /**
     * 文件记录案件编号
    */
    public byte[]           szCaseID = new byte[64];
    /**
     * 文件记录案件地点
    */
    public byte[]           szCaseAddress = new byte[128];
    /**
     * 文件记录案件备注
    */
    public byte[]           szCaseRemark = new byte[256];
    /**
     * 文件记录案件类型,1-重点案件,2-普通案件
    */
    public int              nCaseType;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[124];

    public NET_MEDIA_FILE_REMARK_INFO() {
    }
}

