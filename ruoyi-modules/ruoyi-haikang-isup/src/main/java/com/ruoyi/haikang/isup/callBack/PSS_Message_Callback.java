package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.haikang.isup.service.haikang.ss.HCISUPSS;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PSS_Message_Callback implements HCISUPSS.EHomeSSMsgCallBack {
    @Override
    public boolean invoke(int iHandle, int enumType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer, int dwInLen, Pointer pUser) {
        log.info("========== 收到SS信息上报回调 ==========");
        log.info("iHandle: {}, enumType: {}, dwOutLen: {}, dwInLen: {}", iHandle, enumType, dwOutLen, dwInLen);
        
        if (1 == enumType) {
            log.info("收到图片上报消息，开始处理...");
            try {
                HCISUPSS.NET_EHOME_SS_TOMCAT_MSG pTomcatMsg = new HCISUPSS.NET_EHOME_SS_TOMCAT_MSG();
                pTomcatMsg.write();
                pTomcatMsg.getPointer().write(0, pOutBuffer.getByteArray(0, pTomcatMsg.size()), 0, pTomcatMsg.size());
                pTomcatMsg.read();
                
                String szDevUri = new String(pTomcatMsg.szDevUri).trim();
                int dwPicNum = pTomcatMsg.dwPicNum;
                String pPicURLs = pTomcatMsg.pPicURLs;
                
                log.info("设备URI: {}", szDevUri);
                log.info("图片数量: {}", dwPicNum);
                log.info("图片URLs: {}", pPicURLs);
                
                log.info("图片上报消息处理完成");
            } catch (Exception e) {
                log.error("处理图片上报消息异常", e);
            }
        } else {
            log.info("收到未知类型的SS信息上报，enumType: {}", enumType);
        }
        
        log.info("========== SS信息上报回调处理完成 ==========");
        return true;
    }
}
