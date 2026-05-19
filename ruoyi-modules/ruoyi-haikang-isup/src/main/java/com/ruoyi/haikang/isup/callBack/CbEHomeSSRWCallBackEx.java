package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.haikang.isup.service.haikang.ss.HCISUPSS;

public class CbEHomeSSRWCallBackEx implements HCISUPSS.EHomeSSRWCallBackEx {
    @Override
    public boolean invoke(int iHandle, HCISUPSS.NET_EHOME_SS_RW_PARAM pRwParam, HCISUPSS.NET_EHOME_SS_EX_PARAM pExStruct) {
        System.out.println("注意：回调的处理逻辑超过5s设备会重新上报，需要保证您的读写逻辑在5s内处理完成");
        return false;
    }
}
