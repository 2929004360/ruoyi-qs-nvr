package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯组/灯色公共信息
 * @date 2021/09/01
 */
public class LAMP_INFO extends NetSDKLib.SdkStructure {
	/**
     *  灯组类型，参考{ @link EM_LAMP_TYPE }
     */
    public int              emLampType;
    /**
     *  灯组编号 
     */
    public int              nLampNo;
    /**
     *  色步数
     */
    public int              nStepNumber;
    /**
     *  色步时间序列信息个数
     */
    public int              nStepSequenceInfoNum;
    /**
     *  色步时间序列信息 
     */
    public STEP_SEQUENCE_INFO[] stuStepSequenceInfo = (STEP_SEQUENCE_INFO[]) new STEP_SEQUENCE_INFO().toArray(10);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[256];
}

