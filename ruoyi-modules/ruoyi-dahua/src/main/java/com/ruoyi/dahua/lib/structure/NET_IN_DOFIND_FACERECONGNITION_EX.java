package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_DoFindFaceRecognitionEx 接口输入参数
 * @date 2022/11/21 17:53:19
 */
public class NET_IN_DOFIND_FACERECONGNITION_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 查询句柄
     */
    public NetSDKLib.LLong  lFindHandle;
    /**
     * 查询起始序号
     */
    public int              nBeginNum;
    /**
     * 当前想查询的记录条数
     */
    public int              nCount;
    /**
     * 指定查询结果返回图片的格式 {@link com.ruoyi.dahua.lib.enumeration.EM_NEEDED_PIC_RETURN_TYPE}
     */
    public int              emDataType;
    /**
     * 图片的编号是否有效
     * TRUE : nPictureID字段有效且下发该字段, 表示只查单张图片的结果
     * FALSE : nPictureID字段无效且不下发该字段, 表示查所有图片的结果
     */
    public int              bPictureIDEnable;
    /**
     * null
     */
    public int              nPictureID;

    public NET_IN_DOFIND_FACERECONGNITION_EX() {
        this.dwSize = this.size();
    }
}

