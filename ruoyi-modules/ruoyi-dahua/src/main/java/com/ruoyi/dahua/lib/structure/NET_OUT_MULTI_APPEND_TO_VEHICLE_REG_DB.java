package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_MultiAppendToVehicleRegisterDB 接口输出参数
 * @date 2021/8/17 15:15
 */
public class NET_OUT_MULTI_APPEND_TO_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public  int             dwSize;                               // 结构体大小
    public int              nErrCodeNum;                          // 错误码个数
    public int              nRetErrCodeNum;                       // 返回的错误码数量
    /** {@link com.ruoyi.dahua.lib.enumeration.EM_VEHICLE_REG_DB_OPERATOR_ERRCODE} */
    public Pointer          emErrCode = new Memory(1000*4);       // 错误码信息  要求：实现VehicleRegisterDB.multiAppend +vehicle object[1000] 数组定1000
    // public int[]  		emErrCode=new int[1000];

        public NET_OUT_MULTI_APPEND_TO_VEHICLE_REG_DB(){
            this.dwSize=this.size();
        }
}

