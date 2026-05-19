package com.ruoyi.haikang.isup.utils;

import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class CmsUtil {

    public String passThrough(int loginID, String reqUrl, String reqContent) {
        if (reqUrl == null) {
            throw new RuntimeException("透传的请求地址为null");
        }

        HCISUPCMS.NET_EHOME_PTXML_PARAM m_struParam = new HCISUPCMS.NET_EHOME_PTXML_PARAM();
        m_struParam.read();
        HCISUPCMS.BYTE_ARRAY ptrurlInBuffer = new HCISUPCMS.BYTE_ARRAY(reqUrl.length() + 1);
        System.arraycopy(reqUrl.getBytes(), 0, ptrurlInBuffer.byValue, 0, reqUrl.length());
        ptrurlInBuffer.write();
        m_struParam.pRequestUrl = ptrurlInBuffer.getPointer();
        m_struParam.dwRequestUrlLen = reqUrl.length();

        if (reqContent != null && !reqContent.trim().isEmpty()) {
            byte[] byInbuffer;
            byInbuffer = reqContent.getBytes(StandardCharsets.UTF_8);
            int iInBufLen = byInbuffer.length;
            HCISUPCMS.BYTE_ARRAY ptrInBuffer = new HCISUPCMS.BYTE_ARRAY(iInBufLen);
            ptrInBuffer.read();
            System.arraycopy(byInbuffer, 0, ptrInBuffer.byValue, 0, iInBufLen);
            ptrInBuffer.write();
            m_struParam.dwInSize = iInBufLen;
            m_struParam.pInBuffer = ptrInBuffer.getPointer();
        } else {
            m_struParam.dwInSize = 0;
            m_struParam.pInBuffer = null;
        }

        int iOutSize2 = 2 * 1024 * 1024;
        HCISUPCMS.BYTE_ARRAY ptrOutByte2 = new HCISUPCMS.BYTE_ARRAY(iOutSize2);
        m_struParam.pOutBuffer = ptrOutByte2.getPointer();
        m_struParam.dwOutSize = iOutSize2;
        m_struParam.dwRecvTimeOut = 5000;
        m_struParam.write();
        if (!CmsService.hCEhomeCMS.NET_ECMS_ISAPIPassThrough(loginID, m_struParam)) {
            log.error("NET_ECMS_ISAPIPassThrough failed, error：{}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
        } else {
            m_struParam.read();
            ptrOutByte2.read();
        }
        return new String(ptrOutByte2.byValue).trim();
    }

    public byte[] passThroughBytes(int loginID, String reqUrl, String reqContent) {
        if (reqUrl == null) {
            throw new RuntimeException("透传的请求地址为null");
        }

        HCISUPCMS.NET_EHOME_PTXML_PARAM m_struParam = new HCISUPCMS.NET_EHOME_PTXML_PARAM();
        m_struParam.read();
        HCISUPCMS.BYTE_ARRAY ptrurlInBuffer = new HCISUPCMS.BYTE_ARRAY(reqUrl.length() + 1);
        System.arraycopy(reqUrl.getBytes(), 0, ptrurlInBuffer.byValue, 0, reqUrl.length());
        ptrurlInBuffer.write();
        m_struParam.pRequestUrl = ptrurlInBuffer.getPointer();
        m_struParam.dwRequestUrlLen = reqUrl.length();

        if (reqContent != null && !reqContent.trim().isEmpty()) {
            byte[] byInbuffer;
            byInbuffer = reqContent.getBytes(StandardCharsets.UTF_8);
            int iInBufLen = byInbuffer.length;
            HCISUPCMS.BYTE_ARRAY ptrInBuffer = new HCISUPCMS.BYTE_ARRAY(iInBufLen);
            ptrInBuffer.read();
            System.arraycopy(byInbuffer, 0, ptrInBuffer.byValue, 0, iInBufLen);
            ptrInBuffer.write();
            m_struParam.dwInSize = iInBufLen;
            m_struParam.pInBuffer = ptrInBuffer.getPointer();
        } else {
            m_struParam.dwInSize = 0;
            m_struParam.pInBuffer = null;
        }

        int iOutSize2 = 5 * 1024 * 1024;
        HCISUPCMS.BYTE_ARRAY ptrOutByte2 = new HCISUPCMS.BYTE_ARRAY(iOutSize2);
        m_struParam.pOutBuffer = ptrOutByte2.getPointer();
        m_struParam.dwOutSize = iOutSize2;
        m_struParam.dwRecvTimeOut = 10000;
        m_struParam.write();
        if (!CmsService.hCEhomeCMS.NET_ECMS_ISAPIPassThrough(loginID, m_struParam)) {
            log.error("NET_ECMS_ISAPIPassThrough failed, error：{}", CmsService.hCEhomeCMS.NET_ECMS_GetLastError());
            return null;
        } else {
            m_struParam.read();
            ptrOutByte2.read();
        }
        
        int actualLength = m_struParam.dwOutSize;
        byte[] result = new byte[actualLength];
        System.arraycopy(ptrOutByte2.byValue, 0, result, 0, actualLength);
        return result;
    }
}
