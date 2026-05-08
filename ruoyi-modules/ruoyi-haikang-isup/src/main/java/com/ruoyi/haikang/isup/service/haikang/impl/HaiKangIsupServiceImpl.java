package com.ruoyi.haikang.isup.service.haikang.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupPresetInfo;
import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.enums.HCIsupCameraAuxEnum;
import com.ruoyi.haikang.isup.enums.HCIsupCruiseControlEnum;
import com.ruoyi.haikang.isup.enums.HCIsupPresetControlEnum;
import com.ruoyi.haikang.isup.service.haikang.IHaiKangIsupService;
import com.ruoyi.haikang.isup.service.haikang.IHaikangIsupMediaStreamService;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.utils.CommonUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName HaiKangIsupServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Slf4j
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

    /**
     * 开始云台控制
     *
     * @param deviceId
     * @param channelId
     * @param ptzCmd
     * @param speed
     */
    @Override
    public void startPtz(Long deviceId, Integer channelId, int ptzCmd, int speed) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            throw new ServiceException("未找到用户信息");
        }

        //云台控制
        HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM net_ehome_remote_ctrl_param = new HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM();
        HCISUPCMS.NET_EHOME_PTZ_PARAM net_ehome_ptz_param = new HCISUPCMS.NET_EHOME_PTZ_PARAM();
        net_ehome_ptz_param.read();
        net_ehome_ptz_param.dwSize = net_ehome_ptz_param.size();
        net_ehome_ptz_param.byPTZCmd = (byte) ptzCmd; //0-向上,1-向下,2-向左,3-向右，更多取值参考接口文档
        net_ehome_ptz_param.byAction = 0; //云台动作：0- 开始云台动作，1- 停止云台动作
        net_ehome_ptz_param.bySpeed = (byte) speed; //云台速度，取值范围：0~7，数值越大速度越快
        net_ehome_ptz_param.write();
        net_ehome_remote_ctrl_param.read();
        net_ehome_remote_ctrl_param.dwSize = net_ehome_remote_ctrl_param.size();
        net_ehome_remote_ctrl_param.lpInbuffer = net_ehome_ptz_param.getPointer();//输入控制参数
        net_ehome_remote_ctrl_param.dwInBufferSize = net_ehome_ptz_param.size();

        //条件参数输入通道号
        int iChannel = channelId; //视频通道号
        IntByReference channle = new IntByReference(iChannel);
        net_ehome_remote_ctrl_param.lpCondBuffer = channle.getPointer();
        net_ehome_remote_ctrl_param.dwCondBufferSize = 4;

        net_ehome_remote_ctrl_param.write();

        boolean b_ptz = CmsService.hCEhomeCMS.NET_ECMS_RemoteControl(lUserID, HCISUPCMS.NET_EHOME_PTZ_CTRL, net_ehome_remote_ctrl_param);
        if (!b_ptz) {
            int iErr = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            throw new ServiceException("NET_ECMS_XMLConfig失败，错误：" + iErr);
        }
    }

    /**
     * 结束云台控制
     *
     * @param deviceId
     * @param channelId
     * @param ptzCmd
     * @param speed
     */
    @Override
    public void endPtz(Long deviceId, Integer channelId, int ptzCmd, int speed) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            throw new ServiceException("未找到用户信息");
        }

        //云台控制
        HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM net_ehome_remote_ctrl_param = new HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM();
        HCISUPCMS.NET_EHOME_PTZ_PARAM net_ehome_ptz_param = new HCISUPCMS.NET_EHOME_PTZ_PARAM();
        net_ehome_ptz_param.read();
        net_ehome_ptz_param.dwSize = net_ehome_ptz_param.size();
        net_ehome_ptz_param.byPTZCmd = (byte) ptzCmd; //0-向上,1-向下,2-向左,3-向右，更多取值参考接口文档
        net_ehome_ptz_param.byAction = 1; //云台动作：0- 开始云台动作，1- 停止云台动作
        net_ehome_ptz_param.bySpeed = (byte) speed; //云台速度，取值范围：0~7，数值越大速度越快
        net_ehome_ptz_param.write();
        net_ehome_remote_ctrl_param.read();
        net_ehome_remote_ctrl_param.dwSize = net_ehome_remote_ctrl_param.size();
        net_ehome_remote_ctrl_param.lpInbuffer = net_ehome_ptz_param.getPointer();//输入控制参数
        net_ehome_remote_ctrl_param.dwInBufferSize = net_ehome_ptz_param.size();

        //条件参数输入通道号
        int iChannel = channelId; //视频通道号
        IntByReference channle = new IntByReference(iChannel);
        net_ehome_remote_ctrl_param.lpCondBuffer = channle.getPointer();
        net_ehome_remote_ctrl_param.dwCondBufferSize = 4;

        net_ehome_remote_ctrl_param.write();

        boolean b_ptz = CmsService.hCEhomeCMS.NET_ECMS_RemoteControl(lUserID, HCISUPCMS.NET_EHOME_PTZ_CTRL, net_ehome_remote_ctrl_param);
        if (!b_ptz) {
            int iErr = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            log.error("NET_ECMS_XMLConfig failed,error：" + iErr);
            return;
        }
    }

    /**
     * 执行云台控制 (PTZ控制)
     *
     * @param lUserID
     * @param channelId
     * @param ptzCmd
     * @param action
     * @param speed
     * @param param
     */
    private void executePtzControl(Integer lUserID, Integer channelId, int ptzCmd, int action, int speed, Integer param) {
        HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM net_ehome_remote_ctrl_param = new HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM();
        HCISUPCMS.NET_EHOME_PTZ_PARAM net_ehome_ptz_param = new HCISUPCMS.NET_EHOME_PTZ_PARAM();
        net_ehome_ptz_param.read();
        net_ehome_ptz_param.dwSize = net_ehome_ptz_param.size();
        net_ehome_ptz_param.byPTZCmd = (byte) ptzCmd;
        net_ehome_ptz_param.byAction = (byte) action; //0-开始，1-停止
        net_ehome_ptz_param.bySpeed = (byte) speed;
        // 如果有参数，使用 byRes 字段传递
        if (param != null) {
            net_ehome_ptz_param.byRes[0] = (byte) (param & 0xFF);
            net_ehome_ptz_param.byRes[1] = (byte) ((param >> 8) & 0xFF);
        }
        net_ehome_ptz_param.write();
        net_ehome_remote_ctrl_param.read();
        net_ehome_remote_ctrl_param.dwSize = net_ehome_remote_ctrl_param.size();
        net_ehome_remote_ctrl_param.lpInbuffer = net_ehome_ptz_param.getPointer();
        net_ehome_remote_ctrl_param.dwInBufferSize = net_ehome_ptz_param.size();

        // 条件参数输入通道号
        int iChannel = channelId;
        IntByReference channle = new IntByReference(iChannel);
        net_ehome_remote_ctrl_param.lpCondBuffer = channle.getPointer();
        net_ehome_remote_ctrl_param.dwCondBufferSize = 4;

        net_ehome_remote_ctrl_param.write();

        boolean b_ptz = CmsService.hCEhomeCMS.NET_ECMS_RemoteControl(lUserID, HCISUPCMS.NET_EHOME_PTZ_CTRL, net_ehome_remote_ctrl_param);
        if (!b_ptz) {
            int iErr = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            log.error("NET_ECMS_RemoteControl failed, error：" + iErr);
            throw new ServiceException("云台控制失败，错误：" + iErr);
        }
    }
    
    /**
     * 专门用于预置点控制的方法 - 使用正确的SDK结构
     */
    private void executePresetControl(Integer lUserID, Integer channelId, int byPresetCmd, int presetIndex) {
        log.info("开始执行预置点控制, lUserID={}, channelId={}, byPresetCmd={}, dwPresetIndex={}", 
            lUserID, channelId, byPresetCmd, presetIndex);
        
        HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM net_ehome_remote_ctrl_param = new HCISUPCMS.NET_EHOME_REMOTE_CTRL_PARAM();
        HCISUPCMS.NET_EHOME_PRESET_PARAM net_ehome_preset_param = new HCISUPCMS.NET_EHOME_PRESET_PARAM();
        
        // 填充NET_EHOME_PRESET_PARAM结构
        net_ehome_preset_param.read();
        net_ehome_preset_param.dwSize = net_ehome_preset_param.size();
        net_ehome_preset_param.byPresetCmd = (byte) byPresetCmd;  // 1-设置，2-删除，3-调用
        net_ehome_preset_param.byRes1 = new byte[3];         // 保留，设为0
        net_ehome_preset_param.dwPresetIndex = presetIndex;  // 预置点编号
        net_ehome_preset_param.byRes2 = new byte[32];        // 保留，设为0
        net_ehome_preset_param.write();
        log.info("NET_EHOME_PRESET_PARAM填充完成: dwSize={}, byPresetCmd={}, dwPresetIndex={}", 
            net_ehome_preset_param.dwSize, net_ehome_preset_param.byPresetCmd, net_ehome_preset_param.dwPresetIndex);
        
        // 填充NET_EHOME_REMOTE_CTRL_PARAM结构
        net_ehome_remote_ctrl_param.read();
        net_ehome_remote_ctrl_param.dwSize = net_ehome_remote_ctrl_param.size();
        net_ehome_remote_ctrl_param.lpInbuffer = net_ehome_preset_param.getPointer();
        net_ehome_remote_ctrl_param.dwInBufferSize = net_ehome_preset_param.size();

        // 条件参数输入通道号
        int iChannel = channelId;
        IntByReference channle = new IntByReference(iChannel);
        net_ehome_remote_ctrl_param.lpCondBuffer = channle.getPointer();
        net_ehome_remote_ctrl_param.dwCondBufferSize = 4;

        net_ehome_remote_ctrl_param.write();
        log.info("准备调用NET_ECMS_RemoteControl, dwCommand=NET_EHOME_PRESET_CTRL(1001)");

        boolean b_ptz = CmsService.hCEhomeCMS.NET_ECMS_RemoteControl(lUserID, HCISUPCMS.NET_EHOME_PRESET_CTRL, net_ehome_remote_ctrl_param);
        if (!b_ptz) {
            int iErr = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            log.error("NET_ECMS_RemoteControl failed, error={}", iErr);
            throw new ServiceException("云台控制失败，错误：" + iErr);
        } else {
            log.info("NET_ECMS_RemoteControl调用成功！");
        }
    }

    @Override
    public void setPreset(Long deviceId, Integer channelId, int presetIndex) {
        log.info("开始设置预置点，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        // 设置预置点 - 使用专门的预置点控制方法
        executePresetControl(lUserID, channelId, HCIsupPresetControlEnum.SET_PRESET.getCode(), presetIndex);

        log.info("设置预置点成功，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    @Override
    public void clearPreset(Long deviceId, Integer channelId, int presetIndex) {
        log.info("开始删除预置点，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        // 删除预置点 - 使用专门的预置点控制方法
        executePresetControl(lUserID, channelId, HCIsupPresetControlEnum.CLEAR_PRESET.getCode(), presetIndex);

        log.info("删除预置点成功，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    @Override
    public void gotoPreset(Long deviceId, Integer channelId, int presetIndex) {
        log.info("开始调用预置点，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        // 调用预置点 - 使用专门的预置点控制方法
        executePresetControl(lUserID, channelId, HCIsupPresetControlEnum.GOTO_PRESET.getCode(), presetIndex);

        log.info("调用预置点成功，deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    @Override
    public void cameraAuxControl(Long deviceId, Integer channelId, String operation, boolean isStart) {
        log.info("开始辅助设备控制，deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);

        if (StringUtils.isEmpty(operation)) {
            log.error("辅助设备控制失败，操作类型不能为空，deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCIsupCameraAuxEnum auxEnum = HCIsupCameraAuxEnum.fromValue(operation);
        if (auxEnum == null) {
            log.error("辅助设备控制失败，无效的操作类型，deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型：" + operation);
        }
        log.debug("操作类型验证成功，deviceId:{}, operation:{}, desc:{}", deviceId, operation, auxEnum.getDesc());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        int action = isStart ? 0 : 1; //0-开始，1-停止
        executePtzControl(lUserID, channelId, auxEnum.getCode(), action, 0, null);

        log.info("辅助设备控制成功，deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);
    }

    @Override
    public void cruiseControl(Long deviceId, Integer channelId, String operation, Integer param) {
        log.info("开始巡航控制，deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);

        if (StringUtils.isEmpty(operation)) {
            log.error("巡航控制失败，操作类型不能为空，deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCIsupCruiseControlEnum cruiseEnum = HCIsupCruiseControlEnum.fromValue(operation);
        if (cruiseEnum == null) {
            log.error("巡航控制失败，无效的操作类型，deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型：" + operation);
        }
        log.debug("操作类型验证成功，deviceId:{}, operation:{}, desc:{}", deviceId, operation, cruiseEnum.getDesc());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        executePtzControl(lUserID, channelId, cruiseEnum.getCode(), 0, 0, param);

        log.info("巡航控制成功，deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);
    }

    @Override
    public List<HaiKangIsupPresetInfo> getPresetList(Long deviceId, Integer channelId) {
        log.info("开始获取预置点列表，deviceId:{}, channelId:{}", deviceId, channelId);

        List<HaiKangIsupPresetInfo> presetList = new ArrayList<>();
        // 海康ISUP预置点编号范围是1到255
        for (int i = 1; i <= 255; i++) {
            presetList.add(new HaiKangIsupPresetInfo(i, "预置点" + i));
        }

        log.info("获取预置点列表成功，deviceId:{}, channelId:{}, count:{}", deviceId, channelId, presetList.size());
        return presetList;
    }

    @Override
    public ArrayList<HashMap<String, Object>> queryRecord(Long deviceId, Integer channelId, String startTime, String endTime) {
        log.info("开始查询海康设备录像，deviceId:{}, channelId:{}, startTime:{}, endTime:{}", deviceId, channelId, startTime, endTime);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败，deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功，deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = FRegisterCallBack.lUserIDMap.get(device.getIpAddress());
        if (lUserID == null) {
            log.error("海康设备未登录，deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录，IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效，deviceId:{}, userId:{}", deviceId, lUserID);

        // 解码URL编码的时间参数
        try {
            startTime = java.net.URLDecoder.decode(startTime, "UTF-8");
            endTime = java.net.URLDecoder.decode(endTime, "UTF-8");
            log.debug("URL解码后的时间，startTime:{}, endTime:{}", startTime, endTime);
        } catch (Exception e) {
            log.warn("URL解码失败，使用原始时间，error:{}", e.getMessage());
        }

        // 查询录像
        ArrayList<HashMap<String, Object>> recordList = tryFindFile(lUserID, channelId, startTime, endTime, deviceId);

        if (recordList.isEmpty()) {
            log.warn("未查询到录像文件，deviceId:{}, channelId:{}", deviceId, channelId);
            throw new ServiceException("未查询到录像文件");
        }

        log.info("查询海康设备录像完成，deviceId:{}, channelId:{}, 共查询到{}条录像记录", deviceId, channelId, recordList.size());
        return recordList;
    }

    /**
     * 尝试使用ISUP API查询录像
     */
    private ArrayList<HashMap<String, Object>> tryFindFile(int lUserID, Integer channelId, String startTime, String endTime, Long deviceId) {
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();

        HCISUPCMS.NET_EHOME_REC_FILE_COND fileCondition = new HCISUPCMS.NET_EHOME_REC_FILE_COND();
        fileCondition.read();

        // 解析时间
        try {
            String[] dateStartByFile = startTime.split(" ");
            String[] dateStart1 = dateStartByFile[0].split("-");
            String[] dateStart2 = dateStartByFile[1].split(":");

            fileCondition.struStartTime.wYear = Short.parseShort(dateStart1[0]);
            fileCondition.struStartTime.byMonth = Byte.parseByte(dateStart1[1]);
            fileCondition.struStartTime.byDay = Byte.parseByte(dateStart1[2]);
            fileCondition.struStartTime.byHour = Byte.parseByte(dateStart2[0]);
            fileCondition.struStartTime.byMinute = Byte.parseByte(dateStart2[1]);
            fileCondition.struStartTime.bySecond = Byte.parseByte(dateStart2[2]);

            String[] dateEndByFile = endTime.split(" ");
            String[] dateEnd1 = dateEndByFile[0].split("-");
            String[] dateEnd2 = dateEndByFile[1].split(":");

            fileCondition.struStopTime.wYear = Short.parseShort(dateEnd1[0]);
            fileCondition.struStopTime.byMonth = Byte.parseByte(dateEnd1[1]);
            fileCondition.struStopTime.byDay = Byte.parseByte(dateEnd1[2]);
            fileCondition.struStopTime.byHour = Byte.parseByte(dateEnd2[0]);
            fileCondition.struStopTime.byMinute = Byte.parseByte(dateEnd2[1]);
            fileCondition.struStopTime.bySecond = Byte.parseByte(dateEnd2[2]);
        } catch (Exception e) {
            log.error("时间参数解析失败，startTime:{}, endTime:{}, error:{}", startTime, endTime, e.getMessage(), e);
            return recordList;
        }

        // 设置其他查询条件
        fileCondition.dwChannel = channelId;
        fileCondition.dwRecType = 0xff; // 全部类型
        fileCondition.dwStartIndex = 0;
        fileCondition.dwMaxFileCountPer = 100; // 增加每次查询的数量，获取更多记录
        fileCondition.byLocalOrUTC = 0; // 设备本地时间
        fileCondition.write();

        log.debug("开始查询，通道号:{}, 时间范围:{}-{}", channelId, startTime, endTime);

        int findHandle = CmsService.hCEhomeCMS.NET_ECMS_StartFindFile_V11(lUserID, HCISUPCMS.ENUM_SEARCH_RECORD_FILE, fileCondition.getPointer(), fileCondition.size());

        if (findHandle == -1) {
            int errorCode = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
            log.warn("NET_ECMS_StartFindFile_V11失败，通道号:{}, 错误码:{}", channelId, errorCode);
            return recordList;
        }

        try {
            HCISUPCMS.NET_EHOME_REC_FILE findData = new HCISUPCMS.NET_EHOME_REC_FILE();
            int findNextResult;

            int retryCount = 0;
            int maxRetryCount = 50; // 最多重试50次
            long waitTimeMs = 100; // 每次等待100毫秒
            
            while (true) {
                findNextResult = CmsService.hCEhomeCMS.NET_ECMS_FindNextFile_V11(findHandle, findData.getPointer(), findData.size());
                if (findNextResult == 1000) { // 找到文件
                    findData.read();

                    String fileName = CommonUtil.parseHikvisionString(findData.sFileName);
                    String start = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStartTime.wYear, findData.struStartTime.byMonth, findData.struStartTime.byDay,
                            findData.struStartTime.byHour, findData.struStartTime.byMinute, findData.struStartTime.bySecond);
                    String stop = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStopTime.wYear, findData.struStopTime.byMonth, findData.struStopTime.byDay,
                            findData.struStopTime.byHour, findData.struStopTime.byMinute, findData.struStopTime.bySecond);

                    HashMap<String, Object> record = new HashMap<>(16);
                    record.put("channel", String.valueOf(channelId));
                    record.put("type", getRecordTypeString(findData.dwFileSubType));
                    record.put("start", start);
                    record.put("end", stop);
                    record.put("fileName", fileName);
                    record.put("fileSize", findData.dwFileSize);
                    recordList.add(record);

                    log.debug("找到录像: channel={}, fileName={}, start={}, end={}", channelId, fileName, start, stop);
                    retryCount = 0; // 重置重试计数
                } else if (findNextResult == 1003) { // 没有更多文件
                    log.debug("查询结束，共找到{}条记录", recordList.size());
                    break;
                } else if (findNextResult == 1002) { // 正在查找，请等待
                    retryCount++;
                    if (retryCount > maxRetryCount) {
                        log.warn("查找超时，已重试{}次，放弃继续查询", maxRetryCount);
                        break;
                    }
                    log.debug("正在查找，请等待，重试次数:{}/{}", retryCount, maxRetryCount);
                    try {
                        Thread.sleep(waitTimeMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.warn("等待被中断", e);
                        break;
                    }
                } else if (findNextResult == 1001 || findNextResult == 1004 || findNextResult == 1005) { // 其他结束状态
                    if (findNextResult == 1001) {
                        log.warn("未查找到文件，返回值:{}", findNextResult);
                    } else if (findNextResult == 1004) {
                        log.warn("查找文件时异常，返回值:{}", findNextResult);
                    } else if (findNextResult == 1005) {
                        log.warn("查找文件超时，返回值:{}", findNextResult);
                    }
                    break;
                } else { // 查找出错
                    int errorCode = CmsService.hCEhomeCMS.NET_ECMS_GetLastError();
                    log.warn("查找下一个文件失败，返回值:{}, 错误码:{}", findNextResult, errorCode);
                    break;
                }
            }
        } finally {
            CmsService.hCEhomeCMS.NET_ECMS_StopFindFile(findHandle);
        }

        return recordList;
    }

    /**
     * 将海康设备的录像类型转换为可读字符串
     *
     * @param fileType 海康设备返回的文件类型
     * @return 可读的录像类型字符串
     */
    private String getRecordTypeString(int fileType) {
        switch (fileType) {
            case 0:
                return "定时录像";
            case 1:
                return "移动侦测";
            case 2:
                return "报警触发";
            case 3:
                return "报警|移动侦测";
            case 4:
                return "报警&移动侦测";
            case 5:
                return "命令触发";
            case 6:
                return "手动录像";
            case 7:
                return "震动报警";
            case 8:
                return "环境报警";
            case 9:
                return "智能报警";
            case 10:
                return "PIR报警";
            case 11:
                return "无线报警";
            case 12:
                return "呼救报警";
            case 13:
                return "移动侦测/PIR/无线/呼救等报警";
            case 14:
                return "智能交通事件";
            case 15:
                return "越界侦测";
            case 16:
                return "区域入侵侦测";
            case 17:
                return "音频异常侦测";
            case 18:
                return "场景变更侦测";
            case 19:
                return "智能侦测";
            case 20:
                return "人脸侦测";
            case 21:
                return "信号量/POS录像";
            case 22:
                return "回传";
            case 23:
                return "回迁录像";
            case 24:
                return "遮挡";
            case 26:
                return "进入区域侦测";
            case 27:
                return "离开区域侦测";
            case 28:
                return "徘徊侦测";
            case 29:
                return "人员聚集侦测";
            case 30:
                return "快速运动侦测";
            case 31:
                return "停车侦测";
            case 32:
                return "物品遗留侦测";
            case 33:
                return "物品拿取侦测";
            case 34:
                return "火点检测";
            case 36:
                return "船只检测";
            case 37:
                return "测温预警";
            case 38:
                return "测温报警";
            case 42:
                return "温差报警";
            case 43:
                return "离线测温报警";
            case 44:
                return "防区报警";
            case 45:
                return "紧急求助";
            case 46:
                return "业务咨询";
            case 47:
                return "起身检测";
            case 48:
                return "折线攀高";
            case 49:
                return "目标区域滞留超时";
            default:
                return "未知类型(" + fileType + ")";
        }
    }
}
