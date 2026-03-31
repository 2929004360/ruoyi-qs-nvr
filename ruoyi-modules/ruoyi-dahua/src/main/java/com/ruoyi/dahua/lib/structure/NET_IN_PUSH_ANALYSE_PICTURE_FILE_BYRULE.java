package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @description CLIENT_PushAnalysePictureFileByRule 接口输入参数
 * @date 2022/06/28 19:02:02
 */
public class NET_IN_PUSH_ANALYSE_PICTURE_FILE_BYRULE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 任务ID
     */
    public int              nTaskID;
    /**
     * 推送图片信息，文件列表支持url路径和二进制数据两种方式，但是每次只能选择一种方式，即URL和Offset/Length应该是两者有且只有一个
     * 用户自定义空间  NET_PUSH_PICTURE_BYRULE_INFO
     */
    public Pointer          pstuPushPicByRuleInfos;
    /**
     * 推送图片数量,用户定义
     */
    public int              nPicNum;
    /**
     * 数据缓冲区长度, 单位:字节
     */
    public int              nBinBufLen;
    /**
     * 数据缓冲区, 由用户申请和释放,选择nOffset/nLength方式，需要传送图片数据
     */
    public Pointer          pBinBuf;

    public NET_IN_PUSH_ANALYSE_PICTURE_FILE_BYRULE() {
        this.dwSize = this.size();
    }
}

