package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.utils.CommonUtil;
import org.springframework.stereotype.Service;

/**
 * @FileName HaiKangIsupServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Service
public class HaiKangIsupServiceImpl implements IHaiKangIsupService {

    /**
     * 获取设备信息
     *
     * @param lUserID 用户id
     * @return
     */
    @Override
    public HaiKangIsupDeviceInfo getDevInfo(Integer lUserID) {

        boolean bRet;

        HCISUPCMS.NET_EHOME_DEVICE_INFO ehomeDeviceInfo = new HCISUPCMS.NET_EHOME_DEVICE_INFO();
        ehomeDeviceInfo.read();
        ehomeDeviceInfo.dwSize = ehomeDeviceInfo.size();
        ehomeDeviceInfo.write();

        HCISUPCMS.NET_EHOME_CONFIG strEhomeCfd = new HCISUPCMS.NET_EHOME_CONFIG();
        strEhomeCfd.pCondBuf = null;
        strEhomeCfd.dwCondSize = 0;
        strEhomeCfd.pOutBuf = ehomeDeviceInfo.getPointer();
        strEhomeCfd.dwOutSize = ehomeDeviceInfo.size();
        strEhomeCfd.pInBuf = null;
        strEhomeCfd.dwInSize = 0;
        strEhomeCfd.write();


        bRet = CmsService.hCEhomeCMS.NET_ECMS_GetDevConfig(lUserID, 1, strEhomeCfd.getPointer(), strEhomeCfd.size());
        if (!bRet) {
            int dwErr = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            throw new ServiceException("获取设备信息失败，Error:" + dwErr);
        } else {
            //  读取返回的数据
            ehomeDeviceInfo.read();
            HaiKangIsupDeviceInfo deviceInfo = new HaiKangIsupDeviceInfo();
            deviceInfo.setDwChannelNumber(ehomeDeviceInfo.dwChannelNumber);
            deviceInfo.setDwChannelAmount(ehomeDeviceInfo.dwChannelAmount);
            deviceInfo.setDwDevType(ehomeDeviceInfo.dwDevType);
            deviceInfo.setDwDiskNumber(ehomeDeviceInfo.dwDiskNumber);
            deviceInfo.setSSerialNumber(CommonUtil.parseHikvisionString(ehomeDeviceInfo.sSerialNumber));
            deviceInfo.setDwAlarmOutPortNum(ehomeDeviceInfo.dwAlarmInPortNum);
            deviceInfo.setDwAlarmOutAmount(ehomeDeviceInfo.dwAlarmOutAmount);
            deviceInfo.setDwStartChannel(ehomeDeviceInfo.dwStartChannel);
            deviceInfo.setDwAudioChanNum(ehomeDeviceInfo.dwAudioChanNum);
            deviceInfo.setDwMaxDigitChannelNum(ehomeDeviceInfo.dwMaxDigitChannelNum);
            deviceInfo.setDwSupportZeroChan(ehomeDeviceInfo.dwSupportZeroChan);
            deviceInfo.setDwStartZeroChan(ehomeDeviceInfo.dwStartZeroChan);
            deviceInfo.setDwSmartType(ehomeDeviceInfo.dwSmartType);
            deviceInfo.setDwAudioEncType(ehomeDeviceInfo.dwAudioEncType);
            deviceInfo.setSSIMCardSN(CommonUtil.parseHikvisionString(ehomeDeviceInfo.sSIMCardSN));
            deviceInfo.setSSIMCardPhoneNum(CommonUtil.parseHikvisionString(ehomeDeviceInfo.sSIMCardPhoneNum));

            return deviceInfo;
        }
    }
}
