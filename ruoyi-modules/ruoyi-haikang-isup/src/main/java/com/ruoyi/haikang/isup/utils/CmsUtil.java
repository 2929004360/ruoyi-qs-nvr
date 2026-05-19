package com.ruoyi.haikang.isup.utils;

import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
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

    /**
     * XML远程控制（用于远程升级等）
     * @param loginID 设备登录ID
     * @param xmlContent XML内容
     * @param charset 字符集，例如 GB2312
     * @return 响应结果
     */
    public String xmlRemoteControl(int loginID, String xmlContent, Charset charset) {
        if (xmlContent == null || xmlContent.isEmpty()) {
            throw new RuntimeException("XML内容为空");
        }

        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }

        HCISUPCMS.NET_EHOME_XML_REMOTE_CTRL_PARAM m_struParam = new HCISUPCMS.NET_EHOME_XML_REMOTE_CTRL_PARAM();
        m_struParam.read();

        // 设置输入数据
        byte[] byInbuffer = xmlContent.getBytes(charset);
        int iInBufLen = byInbuffer.length;
        HCISUPCMS.BYTE_ARRAY ptrInBuffer = new HCISUPCMS.BYTE_ARRAY(iInBufLen);
        ptrInBuffer.read();
        System.arraycopy(byInbuffer, 0, ptrInBuffer.byValue, 0, iInBufLen);
        ptrInBuffer.write();
        m_struParam.lpInbuffer = ptrInBuffer.getPointer();
        m_struParam.dwInBufferSize = iInBufLen;

        // 设置输出缓冲区
        int iOutSize = 1024 * 1024;
        HCISUPCMS.BYTE_ARRAY ptrOutBuffer = new HCISUPCMS.BYTE_ARRAY(iOutSize);
        m_struParam.lpOutBuffer = ptrOutBuffer.getPointer();
        m_struParam.dwOutBufferSize = iOutSize;

        // 设置状态缓冲区
        int iStatusSize = 4096;
        HCISUPCMS.BYTE_ARRAY ptrStatusBuffer = new HCISUPCMS.BYTE_ARRAY(iStatusSize);
        m_struParam.lpStatusBuffer = ptrStatusBuffer.getPointer();
        m_struParam.dwStatusBufferSize = iStatusSize;

        // 设置超时时间（升级需要较长时间）
        m_struParam.dwSendTimeOut = 60000;  // 60秒
        m_struParam.dwRecvTimeOut = 300000; // 5分钟
        m_struParam.dwSize = m_struParam.size();
        m_struParam.write();

        log.info("调用 NET_ECMS_XMLRemoteControl...");
        if (!CmsService.hCEhomeCMS.NET_ECMS_XMLRemoteControl(loginID, m_struParam, m_struParam.size())) {
            int errorCode = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            log.error("NET_ECMS_XMLRemoteControl failed, error：{}", errorCode);
            throw new RuntimeException("远程控制失败，错误码：" + errorCode);
        } else {
            m_struParam.read();
            ptrOutBuffer.read();
            ptrStatusBuffer.read();
        }

        String outResult = new String(ptrOutBuffer.byValue, charset).trim();
        String statusResult = new String(ptrStatusBuffer.byValue, charset).trim();

        log.info("输出结果：{}", outResult);
        log.info("状态结果：{}", statusResult);

        return statusResult.isEmpty() ? outResult : statusResult;
    }
}
