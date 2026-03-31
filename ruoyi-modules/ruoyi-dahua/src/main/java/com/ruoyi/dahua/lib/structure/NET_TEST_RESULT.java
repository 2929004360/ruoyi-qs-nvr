package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description ESD阻值测试结果
 * @date 2021/8/20 11:56
 */
public class NET_TEST_RESULT extends NetSDKLib.SdkStructure {
    public  int             nHandValue;                           // k欧姆（阻值单位）
    public  int             nLeftFootValue;                       // k欧姆（阻值单位）
    public  int             nRightFootValue;                      // k欧姆（阻值单位）
    /**  EM_ESD_RESULT */
    public  int             emEsdResult;                          // 测试结果
    public byte[]           bReserved = new byte[128];            // 预留字节
}

