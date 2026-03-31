package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 目标特征向量重建结果信息
 * @origin autoTool
 * @date 2023/06/28 10:00:12
 */
public class FACE_FEATURE_ABSTRACT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 计算特征向量结果 {@link com.ruoyi.dahua.lib.enumeration.EM_ABSTRACT_STATE}
     */
    public int              emAbstractState;
    /**
     * 人员组重建进度,百分比
     */
    public int              nProcess;
    /**
     * 重建完成的人员编号, IVSS支持
     */
    public byte[]           szUID = new byte[32];
    /**
     * 重建完成的人员组ID, IPC支持
     */
    public byte[]           szGroupId = new byte[64];
    /**
     * 人员组建模成功的个数，IPC支持
     */
    public int              nSucceedCnt;
    /**
     * 人员组建模失败的个数，IPC支持
     */
    public int              nFailedCnt;
    /**
     * 建模失败记录的错误原因(当FailedCnt大于0时有效)；IPC支持
     * {@link com.ruoyi.dahua.lib.enumeration.EM_FEATURE_ERROR_CODE}
     */
    public int              emFeatureError;
    /**
     * 该次建模的token值，建模时提供原样返回，IPC支持
     */
    public int              nToken;
    /**
     * 保留扩展字节
     */
    public byte[]           byReserved = new byte[496];

    public FACE_FEATURE_ABSTRACT_INFO() {
    }
}

