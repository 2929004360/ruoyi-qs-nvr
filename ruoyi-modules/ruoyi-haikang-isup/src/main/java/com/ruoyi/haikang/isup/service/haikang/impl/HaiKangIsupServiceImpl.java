package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import com.ruoyi.haikang.isup.service.haikang.IHaikangIsupMediaStreamService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.utils.CommonUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName HaiKangIsupServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Service
public class HaiKangIsupServiceImpl implements IHaiKangIsupService {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IHaikangIsupMediaStreamService mediaStreamService;

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

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    @Override
    public void startPlay(RtpServerParam rtpServerParam) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();

        String streamKey = "haikang_isup_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            throw new ServiceException("未找到用户信息");
        }

        mediaStreamService.startPlay(lUserID, device, streamKey, rtpServerParam);
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @Override
    public void stopPlay(Long id) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        String streamKey = "haikang_isup_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            throw new ServiceException("未找到用户信息");
        }
        mediaStreamService.stopPlay(lUserID, device.getId(), device.getChannel(), streamKey);
    }
}
