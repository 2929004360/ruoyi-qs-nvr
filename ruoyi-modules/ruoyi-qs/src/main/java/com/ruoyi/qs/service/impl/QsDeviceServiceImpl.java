package com.ruoyi.qs.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupPresetInfo;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.domain.*;
import com.ruoyi.qs.mapper.QsDeviceMapper;
import com.ruoyi.qs.mapper.QsRegionMapper;
import com.ruoyi.qs.service.IQsDeviceService;
import com.ruoyi.qs.utils.StreamDetector;
import com.ruoyi.zlm.api.RemoteZlmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频监控设备Service业务层处理
 *
 * @author fengcheng
 * @date 2026-03-27
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QsDeviceServiceImpl implements IQsDeviceService {
    @Autowired
    private QsDeviceMapper qsDeviceMapper;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private StreamDetector streamDetector;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private QsRegionMapper qsRegionMapper;

    /**
     * 查询视频监控设备
     *
     * @param id 视频监控设备主键
     * @return 视频监控设备
     */
    @Override
    public QsDevice selectQsDeviceById(Long id) {
        return qsDeviceMapper.selectQsDeviceById(id);
    }

    /**
     * 查询视频监控设备列表
     *
     * @param qsDevice 视频监控设备
     * @return 视频监控设备
     */
    @Override
    public List<QsDevice> selectQsDeviceList(QsDevice qsDevice) {
        return qsDeviceMapper.selectQsDeviceList(qsDevice);
    }

    /**
     * 新增视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return 结果
     */
    @Override
    public int insertQsDevice(QsDevice qsDevice) {
        qsDevice.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        qsDevice.setCreateTime(DateUtils.getNowDate());
        qsDevice.setDeviceStatus("ON");
        // RTSP协议
        if (LiveStreamType.RTSP.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            if (!isValidRtspFormat(qsDevice.getLiveAddress())) {
                throw new RuntimeException("RTSP地址格式不正确");
            }
        }

        // RTMP协议
        if (LiveStreamType.RTMP.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            if (!isValidRtmpFormat(qsDevice.getLiveAddress())) {
                throw new RuntimeException("RTMP地址格式不正确");
            }
        }

        // FLV协议
        if (LiveStreamType.FLV.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            String flvType = getProtocolTypeSimple(qsDevice.getLiveAddress());
            qsDevice.setFlvType(flvType);

            if (!isValidFlvAddress(qsDevice.getLiveAddress())) {
                throw new RuntimeException("FLV地址格式不正确");
            }
        }

        // HLS协议
        if (LiveStreamType.HLS.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            if (!isValidHlsAddress(qsDevice.getLiveAddress())) {
                throw new RuntimeException("HLS地址格式不正确");
            }
        }

        // ONVIF协议
        if (LiveStreamType.ONVIF.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
        }

        // 视频文件
        if (LiveStreamType.VIDEO_FILE.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            if (!isValidMp4Address(qsDevice.getLiveAddress())) {
                throw new RuntimeException("视频文件格式不正确");
            }
        }

        // 海康SDK
        if (LiveStreamType.HIK_SDK.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            LoginDevice loginDevice = new LoginDevice();
            loginDevice.setIpAddress(qsDevice.getIpAddress());
            loginDevice.setPort(Short.parseShort(String.valueOf(qsDevice.getPort())));
            loginDevice.setUserName(qsDevice.getUserName());
            loginDevice.setPassword(qsDevice.getPassword());
            R<Integer> r = remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                throw new RuntimeException(r.getMsg());
            }

        }

        // 大华sdk
        if (LiveStreamType.DAHUA_SDK.getCode().equals(qsDevice.getType())) {
            com.ruoyi.dahua.api.domain.LoginDevice loginDevice = new com.ruoyi.dahua.api.domain.LoginDevice();

            // 1=主动添加
            if ("1".equals(qsDevice.getOnlineType())) {
                qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
                loginDevice.setIpAddress(qsDevice.getIpAddress());
                loginDevice.setPort(qsDevice.getPort());
                loginDevice.setUserName(qsDevice.getUserName());
                loginDevice.setPassword(qsDevice.getPassword());
                loginDevice.setOnlineType(qsDevice.getOnlineType());
            }

            // 2=主动注册
            if ("2".equals(qsDevice.getOnlineType())) {
                R<DahuaDevice> dahuaDevicer = remoteDaHuaService.getDahuaDevice(qsDevice.getIpAddress(), SecurityConstants.INNER);
                if (dahuaDevicer.getCode() != Constants.SUCCESS) {
                    throw new RuntimeException(dahuaDevicer.getMsg());
                }
                if (dahuaDevicer.getData() == null) {
                    throw new RuntimeException("未找到设备");
                }
                loginDevice.setIpAddress(qsDevice.getIpAddress());
                loginDevice.setPort(Integer.valueOf(dahuaDevicer.getData().getPort()));
                loginDevice.setDeviceId(dahuaDevicer.getData().getDeviceId());
                loginDevice.setUserName(qsDevice.getUserName());
                loginDevice.setPassword(qsDevice.getPassword());
                loginDevice.setOnlineType(qsDevice.getOnlineType());
            }

            R<Void> r = remoteDaHuaService.loginDevice(loginDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                throw new RuntimeException(r.getMsg());
            }
        }

        // 推流模式
        if (LiveStreamType.PUSH.getCode().equals(qsDevice.getType())){
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
            qsDevice.setDeviceStatus("OFFLINE");
        }

        // gb28181协议
        if (LiveStreamType.GB28181.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
        }

        // JT1078协议
        if (LiveStreamType.JT1078.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("device_" + IdUtil.getSnowflakeNextId());
        }
        return qsDeviceMapper.insertQsDevice(qsDevice);
    }

    /**
     * 修改视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return 结果
     */
    @Override
    public int updateQsDevice(QsDevice qsDevice) {
        qsDevice.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        qsDevice.setUpdateTime(DateUtils.getNowDate());

        // RTSP协议
        if (LiveStreamType.RTSP.getCode().equals(qsDevice.getType())) {
            if (!isValidRtspFormat(qsDevice.getLiveAddress())) {
                throw new RuntimeException("RTSP地址格式不正确");
            }
        }

        // RTMP协议
        if (LiveStreamType.RTMP.getCode().equals(qsDevice.getType())) {
            if (!isValidRtmpFormat(qsDevice.getLiveAddress())) {
                throw new RuntimeException("RTMP地址格式不正确");
            }
        }

        // FLV协议
        if (LiveStreamType.FLV.getCode().equals(qsDevice.getType())) {
            String flvType = getProtocolTypeSimple(qsDevice.getLiveAddress());
            qsDevice.setFlvType(flvType);
            if (!isValidFlvAddress(qsDevice.getLiveAddress())) {
                throw new RuntimeException("FLV地址格式不正确");
            }
        }

        // HLS协议
        if (LiveStreamType.HLS.getCode().equals(qsDevice.getType())) {
            if (!isValidHlsAddress(qsDevice.getLiveAddress())) {
                throw new RuntimeException("HLS地址格式不正确");
            }
        }

        // 视频文件
        if (LiveStreamType.VIDEO_FILE.getCode().equals(qsDevice.getType())) {
            if (!isValidMp4Address(qsDevice.getLiveAddress())) {
                throw new RuntimeException("视频文件格式不正确");
            }
        }

//        // 海康SDK
//        if (LiveStreamType.HIK_SDK.getCode().equals(qsDevice.getType())) {
//
//            LoginDevice loginDevice = new LoginDevice();
//            loginDevice.setIpAddress(qsDevice.getIpAddress());
//            loginDevice.setPort(Short.parseShort(String.valueOf(qsDevice.getPort())));
//            loginDevice.setUserName(qsDevice.getUserName());
//            loginDevice.setPassword(qsDevice.getPassword());
//            R<Integer> r = remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
//            if (r.getCode() != Constants.SUCCESS) {
//                throw new RuntimeException(r.getMsg());
//            }
//        }

//        // 大华sdk
//        if (LiveStreamType.DAHUA_SDK.getCode().equals(qsDevice.getType())) {
//
//            com.ruoyi.dahua.api.domain.LoginDevice loginDevice = new com.ruoyi.dahua.api.domain.LoginDevice();
//
//            // 1=主动添加
//            if("1".equals(qsDevice.getOnlineType())){
//                qsDevice.setDeviceCode("dahua_" + IdUtil.getSnowflakeNextId());
//                loginDevice.setIpAddress(qsDevice.getIpAddress());
//                loginDevice.setPort(qsDevice.getPort());
//                loginDevice.setUserName(qsDevice.getUserName());
//                loginDevice.setPassword(qsDevice.getPassword());
//            }
//
//            // 2=主动注册
//            if("2".equals(qsDevice.getOnlineType())){
//                R<DahuaDevice> dahuaDevicer = remoteDaHuaService.getDahuaDevice(qsDevice.getIpAddress(), SecurityConstants.INNER);
//                if(dahuaDevicer.getCode() != Constants.SUCCESS){
//                    throw new RuntimeException(dahuaDevicer.getMsg());
//                }
//                if(dahuaDevicer.getData() == null){
//                    throw new RuntimeException("未找到设备");
//                }
//                loginDevice.setIpAddress(qsDevice.getIpAddress());
//                loginDevice.setPort(Integer.valueOf(dahuaDevicer.getData().getPort()));
//                loginDevice.setDeviceId(dahuaDevicer.getData().getDeviceId());
//                loginDevice.setUserName(qsDevice.getUserName());
//                loginDevice.setPassword(qsDevice.getPassword());
//                loginDevice.setOnlineType(qsDevice.getOnlineType());
//            }
//
//            R<Void> r = remoteDaHuaService.loginDevice(loginDevice, SecurityConstants.INNER);
//            if (r.getCode() != Constants.SUCCESS) {
//                throw new RuntimeException(r.getMsg());
//            }
//        }
        return qsDeviceMapper.updateQsDevice(qsDevice);
    }

    /**
     * 批量删除视频监控设备
     *
     * @param ids 需要删除的视频监控设备主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceByIds(Long[] ids) {
        return qsDeviceMapper.deleteQsDeviceByIds(ids);
    }

    /**
     * 删除视频监控设备信息
     *
     * @param id 视频监控设备主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceById(Long id) {
        return qsDeviceMapper.deleteQsDeviceById(id);
    }

    /**
     * 状态修改
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    @Override
    public int updateQsDeviceStatus(QsDevice qsDevice) {
        return qsDeviceMapper.updateQsDeviceStatus(qsDevice.getId(), qsDevice.getStatus());
    }

    /**
     * 更新设备在线状态
     *
     * @param onlineDeviceSet 在线设备集合
     * @param deviceStatus    设备状态
     * @return
     */
    @Override
    public Boolean updateQsDeviceStatusList(Set<Long> onlineDeviceSet, String deviceStatus) {
        return qsDeviceMapper.updateQsDeviceStatusList(onlineDeviceSet, deviceStatus);
    }

    /**
     * 修改视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    @Override
    public int editQsDevice(QsDevice qsDevice) {
        return qsDeviceMapper.updateQsDevice(qsDevice);
    }

    /**
     * 更具流id获取视频监控设备
     *
     * @param stream 流id
     * @return
     */
    @Override
    public QsDevice getQsDeviceStream(String stream) {
        return qsDeviceMapper.getQsDeviceStream(stream);
    }

    /**
     * 修改所有设备播状态离线和设备状态离线
     */

    @Override
    public void updateAllQsDevicesToOffline() {
        qsDeviceMapper.updateAllQsDevicesToOffline();
    }

    /**
     * 获取所有视频监控设备流地址
     *
     * @return
     */
    @Override
    public List<QsDevice> fetchAllQsDeviceStreamUrls() {
        return qsDeviceMapper.fetchAllQsDeviceStreamUrls();
    }

    /**
     * 更新所有视频监控设备流地址
     *
     * @param newQsDeviceList
     */
    @Override
    public void updateAllQsDeviceStreamUrls(List<QsDevice> newQsDeviceList) {
        qsDeviceMapper.updateAllQsDeviceStreamUrls(newQsDeviceList);
    }

    @Async("taskExecutor")
    @Override
    public void task() {
        List<QsDevice> qsDeviceList = fetchAllQsDeviceStreamUrls();
        if (qsDeviceList.isEmpty()) {
            return;
        }

        // 处理所有设备，替换 ws/wss 为 http/https，先替换 wss 防止被误判
        qsDeviceList.forEach(device -> {
            String originalUrl = device.getLiveAddress();
            if (originalUrl != null && !originalUrl.isEmpty()) {
                // 注意替换顺序，先替换 wss -> https，再替换 ws -> http
                String newUrl = originalUrl.replace("wss://", "https://")
                        .replace("ws://", "http://");
                device.setLiveAddress(newUrl);
            }
        });

        List<StreamDetector.StreamResult> streamResults = streamDetector.batchDetect(qsDeviceList, taskExecutor);

        List<QsDevice> newQsDeviceList = new ArrayList<>();
        for (StreamDetector.StreamResult streamResult : streamResults) {
            QsDevice device = new QsDevice();
            device.setId(streamResult.getId());
            device.setDeviceStatus(streamResult.getStatus());
            newQsDeviceList.add(device);
        }

        if (!newQsDeviceList.isEmpty()) {
            updateAllQsDeviceStreamUrls(newQsDeviceList);
        }
    }

    /**
     * 获取计划记录对应的视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    @Override
    public List<QsDevice> listPlanRecordQsDevice(QsDevice qsDevice) {
        return qsDeviceMapper.listPlanRecordQsDevice(qsDevice);
    }

    /**
     * 设备关联录制计划
     *
     * @param deviceIds
     * @param planId
     */
    @Override
    public void link(List<Long> deviceIds, Long planId) {
        qsDeviceMapper.link(deviceIds, planId);
    }

    /**
     * 清理设备计划id
     *
     * @param planId 设备id
     */
    @Override
    public void cleanRecordPlanId(Long planId) {
        qsDeviceMapper.cleanRecordPlanId(planId);
    }

    /**
     * 根据设备id集合查询设备信息
     *
     * @param startDeviceIdList 设备id集合
     * @return
     */
    @Override
    public List<QsDevice> queryByIds(List<Long> startDeviceIdList) {
        return qsDeviceMapper.queryByIds(startDeviceIdList);
    }

    /**
     * 根据计划id查询设备数量
     *
     * @param planId 设备id
     * @return
     */
    @Override
    public Integer countRecordPlanDevice(Long planId) {
        return qsDeviceMapper.countRecordPlanDevice(planId);
    }

    /**
     * 根据行政区划编码更新设备行政区划编码
     *
     * @param oldCivilCode 旧的行政区划编码
     * @param newCivilCode 新的行政区划编码
     */
    @Override
    public void updateCivilCode(String oldCivilCode, String newCivilCode) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByCivilCode(oldCivilCode);
        if (deviceList.isEmpty()) {
            return;
        }
        int result = qsDeviceMapper.updateCivilCodeByDeviceList(newCivilCode, deviceList);
    }

    /**
     * 根据行政区划编码删除设备
     *
     * @param allChildren 所有子节点
     */
    @Override
    public void removeCivilCode(List<QsRegion> allChildren) {
        qsDeviceMapper.removeCivilCode(allChildren);
    }

    /**
     * 根据设备id查询设备关联的行政区划树
     *
     * @param deviceId 区域国标编号
     * @return
     */
    @Override
    public List<QsRegionTree> queryForRegionTreeByCivilCode(String deviceId) {
        return qsDeviceMapper.queryForRegionTreeByCivilCode(deviceId);
    }

    /**
     * 根据业务分组更新设备业务分组
     *
     * @param oldBusinessGroup
     * @param newBusinessGroup
     */
    @Override
    public void updateBusinessGroup(String oldBusinessGroup, String newBusinessGroup) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByBusinessGroup(oldBusinessGroup);
        if (deviceList.isEmpty()) {
            log.info("[更新业务分组] 发现未关联任何设备： {}", oldBusinessGroup);
            return;
        }
        int result = qsDeviceMapper.updateBusinessGroupBydeviceList(newBusinessGroup, deviceList);
    }

    /**
     * 根据业务分组更新设备
     *
     * @param oldParentId
     * @param newParentId
     */
    @Override
    public void updateParentIdGroup(String oldParentId, String newParentId) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByParentId(oldParentId);
        if (deviceList.isEmpty()) {
            return;
        }
        int result = qsDeviceMapper.updateParentIdByDeviceList(newParentId, deviceList);
    }

    /**
     * 根据业务分组删除设备
     *
     * @param businessGroup
     */
    @Override
    public void removeParentIdByBusinessGroup(String businessGroup) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByBusinessGroup(businessGroup);
        if (deviceList.isEmpty()) {
            return;
        }
        int result = qsDeviceMapper.removeParentIdByDevices(deviceList);
    }

    /**
     * 根据业务分组删除设备
     *
     * @param groupList
     */
    @Override
    public void removeParentIdByGroupList(List<QsGroup> groupList) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByGroupList(groupList);
        if (deviceList.isEmpty()) {
            return;
        }
        qsDeviceMapper.removeParentIdByDevices(deviceList);
    }

    /**
     * 根据业务分组查询设备关联的业务分组树
     *
     * @param query
     * @param parent
     * @return
     */
    @Override
    public List<QsGroupTree> queryForGroupTreeByParentId(String query, String parent) {
        return qsDeviceMapper.queryForGroupTreeByParentId(query, parent);
    }

    /**
     * 根据行政区域获取视频监控设备列表
     *
     * @param qsDevice
     * @return
     */
    @Override
    public List<QsDevice> queryListByCivilCode(QsDevice qsDevice) {
        return qsDeviceMapper.queryListByCivilCode(qsDevice);
    }

    /**
     * 根据行政区划编码添加设备
     *
     * @param civilCode
     * @param deviceIds
     */
    @Override
    public void addDeviceToRegion(String civilCode, List<Long> deviceIds) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByIds(deviceIds);
        if (deviceList.isEmpty()) {
            throw new RuntimeException("所有设备Id不存在");
        }
        for (QsDevice device : deviceList) {
            device.setGbCivilCode(civilCode);
        }
        int result = qsDeviceMapper.updateRegion(civilCode, deviceList);
    }

    /**
     * 设备删除行政区划
     *
     * @param civilCode
     * @param deviceIds
     */
    @Override
    public void deleteDeviceToRegion(String civilCode, List<Long> deviceIds) {
        if (!ObjectUtils.isEmpty(civilCode)) {
            deleteToRegionByCivilCode(civilCode);
        }
        if (!ObjectUtils.isEmpty(deviceIds)) {
            deleteToRegionByChannelIds(deviceIds);
        }
    }

    /**
     * 存在行政区划但无法挂载的通道列表
     *
     * @param qsDevice
     * @return
     */
    @Override
    public List<QsDevice> queryListByCivilCodeForUnusual(QsDevice qsDevice) {
        return qsDeviceMapper.queryListByCivilCodeForUnusual(qsDevice);
    }

    /**
     * 清除存在行政区划但无法挂载的设备列表
     *
     * @param all
     * @param deviceIds
     */
    @Override
    public void clearDeviceCivilCode(Boolean all, List<Long> deviceIds) {
        List<Long> deviceIdsForClear;
        if (all != null && all) {
            deviceIdsForClear = qsDeviceMapper.queryAllForUnusualCivilCode();
        } else {
            deviceIdsForClear = deviceIds;
        }
        qsDeviceMapper.removeCivilCodeByDeviceIds(deviceIdsForClear);
    }

    /**
     * 获取编码列表
     *
     * @return
     */
    @Override
    public List<NetworkIdentificationType> getNetworkIdentificationTypeList() {
        NetworkIdentificationTypeEnum[] values = NetworkIdentificationTypeEnum.values();
        List<NetworkIdentificationType> result = new ArrayList<>(values.length);
        for (NetworkIdentificationTypeEnum value : values) {
            result.add(NetworkIdentificationType.getInstance(value));
        }
        Collections.sort(result);
        return result;
    }

    /**
     * 获取编码列表
     *
     * @return
     */
    @Override
    public List<DeviceType> getDeviceTypeList() {
        DeviceTypeEnum[] values = DeviceTypeEnum.values();
        List<DeviceType> result = new ArrayList<>(values.length);
        for (DeviceTypeEnum value : values) {
            result.add(DeviceType.getInstance(value));
        }
        Collections.sort(result);
        return result;
    }

    /**
     * 获取行业编码列表
     *
     * @return
     */
    @Override
    public List<IndustryCodeType> getIndustryCodeList() {
        IndustryCodeTypeEnum[] values = IndustryCodeTypeEnum.values();
        List<IndustryCodeType> result = new ArrayList<>(values.length);
        for (IndustryCodeTypeEnum value : values) {
            result.add(IndustryCodeType.getInstance(value));
        }
        Collections.sort(result);
        return result;
    }

    /**
     * 获取关联业务分组通道列表
     *
     * @param qsDevice
     * @return
     */
    @Override
    public List<QsDevice> queryListByParentId(QsDevice qsDevice) {
        return qsDeviceMapper.queryListByParentId(qsDevice);
    }

    /**
     * 设备设置业务分组
     *
     * @param parentId
     * @param businessGroup
     * @param deviceIds
     */
    @Override
    public void addChannelToGroup(String parentId, String businessGroup, List<Long> deviceIds) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByIds(deviceIds);
        if (deviceList.isEmpty()) {
            throw new RuntimeException("所有设备Id不存在");
        }
        int result = qsDeviceMapper.updateGroup(parentId, businessGroup, deviceList);
    }

    /**
     * 删除业务分组设备
     *
     * @param parentId
     * @param businessGroup
     * @param deviceIds
     */
    @Override
    public void deleteDeviceToGroup(String parentId, String businessGroup, List<Long> deviceIds) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByIds(deviceIds);
        if (deviceList.isEmpty()) {
            throw new RuntimeException("所有通道Id不存在");
        }
        qsDeviceMapper.removeParentIdByDevices(deviceList);
    }

    /**
     * 存在父节点编号但无法挂载的设备列表
     *
     * @param qsDevice
     * @return
     */
    @Override
    public List<QsDevice> queryListByParentForUnusual(QsDevice qsDevice) {
        return qsDeviceMapper.queryListByParentForUnusual(qsDevice);
    }

    /**
     * 清除存在分组节点但无法挂载的设备列表
     *
     * @param all
     * @param deviceIds
     */
    @Override
    public void clearDeviceParent(Boolean all, List<Long> deviceIds) {
        List<Long> deviceIdsForClear;
        if (all != null && all) {
            deviceIdsForClear = qsDeviceMapper.queryAllForUnusualParent();
        } else {
            deviceIdsForClear = deviceIds;
        }
        qsDeviceMapper.removeParentIdByDeviceIds(deviceIdsForClear);
    }

    /**
     * 获取设备统计信息
     *
     * @return
     */
    @Override
    public DeviceStats getDeviceStatistics() {
        return qsDeviceMapper.getDeviceStatistics();
    }

    /**
     * 新增视频监控设备
     *
     * @param qsDevice
     * @return
     */
    @Override
    public int addQsDevice(QsDevice qsDevice) {
        return qsDeviceMapper.insertQsDevice(qsDevice);
    }

    /**
     * 根据 gbDeviceId 更新设备在线状态
     *
     * @param gbDeviceId   国标设备编号
     * @param deviceStatus 设备状态
     * @return
     */
    @Override
    public Boolean updateDeviceStatusByGbDeviceId(String gbDeviceId, String deviceStatus) {
        // 先查询是否有对应的设备
        QsDevice queryDevice = new QsDevice();
        queryDevice.setGbDeviceId(gbDeviceId);
        List<QsDevice> deviceList = qsDeviceMapper.selectQsDeviceList(queryDevice);
        if (deviceList.isEmpty()) {
            log.warn("[更新设备状态] 未找到 gbDeviceId 对应的设备：{}", gbDeviceId);
            return false;
        }
        // 更新设备状态
        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(deviceList.get(0).getId());
        qsDevice.setDeviceStatus(deviceStatus);
        int result = qsDeviceMapper.updateQsDevice(qsDevice);
        return result > 0;
    }

    /**
     * 根据 jtMobileNo 更新设备在线状态
     *
     * @param jtMobileNo   设备手机号
     * @param deviceStatus 设备状态
     * @return
     */
    @Override
    public Boolean updateDeviceStatusByJtMobileNo(String jtMobileNo, String deviceStatus) {
        // 先查询是否有对应的设备
        QsDevice queryDevice = new QsDevice();
        queryDevice.setJtMobileNo(jtMobileNo);
        List<QsDevice> deviceList = qsDeviceMapper.selectQsDeviceList(queryDevice);
        if (deviceList.isEmpty()) {
            log.warn("[更新设备状态] 未找到 jtMobileNo 对应的设备：{}", jtMobileNo);
            return false;
        }
        // 更新设备状态
        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(deviceList.get(0).getId());
        qsDevice.setDeviceStatus(deviceStatus);
        int result = qsDeviceMapper.updateQsDevice(qsDevice);
        return result > 0;
    }

    private void deleteToRegionByChannelIds(List<Long> deviceIds) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByIds(deviceIds);
        if (deviceList.isEmpty()) {
            throw new RuntimeException("所有通道Id不存在");
        }
        int result = qsDeviceMapper.removeCivilCodeByDeletes(deviceList);
    }

    private void deleteToRegionByCivilCode(String civilCode) {
        List<QsDevice> deviceList = qsDeviceMapper.queryByCivilCode(civilCode);
        if (deviceList.isEmpty()) {
            throw new RuntimeException("所有设备Id不存在");
        }
        int result = qsDeviceMapper.removeCivilCodeByDeletes(deviceList);
    }

    /**
     * 录制计划关联所有设备
     *
     * @param planId
     */
    @Override
    public void linkAll(Long planId) {
        qsDeviceMapper.linkAll(planId);
    }

    /**
     * 录制计划取消关联所有设备
     *
     * @param planId
     */
    @Override
    public void cleanAll(Long planId) {
        qsDeviceMapper.cleanAll(planId);
    }

    /**
     * 判断是否为合法的 RTSP 地址格式
     *
     * @param url 直播地址
     * @return true 表示格式正确且为 RTSP 协议
     */
    public static boolean isValidRtspFormat(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        try {
            URI uri = new URI(url);
            // 1. 检查协议头是否为 rtsp
            if (!"rtsp".equalsIgnoreCase(uri.getScheme())) {
                return false;
            }
            // 2. 检查是否有主机地址（防止 rtsp:// 这种空地址）
            return uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * 使用正则表达式判断是否为合法的 RTMP 地址
     * 匹配规则：rtmp:// + 域名/IP + 可选端口 + 路径
     */
    public static boolean isValidRtmpFormat(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // 正则解释：
        // ^rtmp://              : 必须以 rtmp:// 开头
        // [a-zA-Z0-9-.]+        : 域名或IP
        // (:[\\d]{1,5})?        : 可选的端口号 (如 :1935)
        // /.*                   : 后面必须跟斜杠和路径（应用名/流ID）
        String regex = "^rtmp://[a-zA-Z0-9-.]+(:[\\d]{1,5})?/.*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    /**
     * 判断是否为合法的 FLV 地址
     * 规则：
     * 1. 协议头支持：http://, https://, ws://, wss://
     * 2. 必须以 .flv 结尾（忽略大小写）
     * 3. 允许后面跟随查询参数（如 ?token=xxx）
     */
    public static boolean isValidFlvAddress(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // 正则解释：
        // ^https?://       : 匹配 http:// 或 https://
        // |                : 或者
        // wss?://          : 匹配 ws:// 或 wss://
        // .+               : 匹配中间的域名和路径
        // \.flv            : 必须以 .flv 结尾
        // (\\?.*)?$        : 允许后面跟随 ? 开头的参数（可选）
        String regex = "^(https?|wss?)://.+\\.flv(\\?.*)?$";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    /**
     * 判断是否为合法的 HLS 地址
     * 规则：必须以 http:// 或 https:// 开头，且以 .m3u8 结尾（忽略大小写，允许带参数）
     */
    public static boolean isValidHlsAddress(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // 正则解释：
        // ^https?://       : 匹配 http:// 或 https://
        // .+               : 匹配中间的域名和路径
        // \.m3u8           : 必须以 .m3u8 结尾
        // (\\?.*)?$        : 允许后面跟随 ? 开头的参数（如 token=xxx），且参数是可选的
        String regex = "^https?://.+\\.m3u8(\\?.*)?$";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    /**
     * 判断是否为合法的 MP4 地址
     * 规则：必须以 http:// 或 https:// 开头，且以 .mp4 结尾（忽略大小写，允许带参数）
     */
    public static boolean isValidMp4Address(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // 正则解释：
        // ^https?://       : 匹配 http:// 或 https://
        // .+               : 匹配中间的域名和路径
        // \.mp4            : 必须以 .mp4 结尾
        // (\\?.*)?$        : 允许后面跟随 ? 开头的参数（如 token=xxx），且参数是可选的
        String regex = "^https?://.+\\.mp4(\\?.*)?$";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }

    /**
     * 判断 URL 协议类型 (无前缀版本)
     */
    public static String getProtocolTypeSimple(String url) {
        if (url == null) return null;

        // 转小写以防万一
        String lowerUrl = url.toLowerCase();

        if (lowerUrl.startsWith("http://") || lowerUrl.startsWith("https://")) {
            return "flv";
        }

        if (lowerUrl.startsWith("ws://") || lowerUrl.startsWith("wss://")) {
            return "ws";
        }

        return null;
    }

    /**
     * 将方向字符串转换为海康 ISUP PTZ 命令码
     */
    private int convertDirectionToHikIsupPtz(String direction) {
        if (direction == null || direction.isEmpty()) {
            throw new RuntimeException("云台控制方向不能为空");
        }
        String lowerDir = direction.toLowerCase();
        switch (lowerDir) {
            case "up":
            case "tilt_up":
                return 0; // PTZ_UP
            case "down":
            case "tilt_down":
                return 1; // PTZ_DOWN
            case "left":
            case "pan_left":
                return 2; // PTZ_LEFT
            case "right":
            case "pan_right":
                return 3; // PTZ_RIGHT
            case "upleft":
            case "up_left":
                return 4; // PTZ_UPLEFT
            case "downleft":
            case "down_left":
                return 5; // PTZ_DOWNLEFT
            case "upright":
            case "up_right":
                return 6; // PTZ_UPRIGHT
            case "downright":
            case "down_right":
                return 7; // PTZ_DOWNRIGHT
            case "zoomin":
            case "zoom_in":
                return 8; // PTZ_ZOOMIN
            case "zoomout":
            case "zoom_out":
                return 9; // PTZ_ZOOMOUT
            case "near":
            case "focus_near":
                return 10; // PTZ_FOCUSNEAR
            case "far":
            case "focus_far":
                return 11; // PTZ_FOCUSFAR
            case "in":
            case "iris_open":
                return 12; // PTZ_IRISSTARTUP
            case "out":
            case "iris_close":
                return 13; // PTZ_IRISSTOPDOWN
            default:
                throw new RuntimeException("不支持的云台控制方向: " + direction);
        }
    }

    @Override
    public void startPtz(Long id, String direction, Integer controlSpeed) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        Integer channel = device.getChannel() != null ? device.getChannel() : 0;
        String deviceType = device.getType();

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            // 海康SDK
            remoteHaiKangService.startPlayControl(id, channel, direction, SecurityConstants.INNER);
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            // 海康ISUP
            int ptzCmd = convertDirectionToHikIsupPtz(direction);
            remoteHaiKangIsupService.startPtz(id, channel, ptzCmd, controlSpeed != null ? controlSpeed : 5, SecurityConstants.INNER);
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            // 大华SDK
            remoteDaHuaService.ptzControlUpStart(id, channel, direction, controlSpeed, SecurityConstants.INNER);
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            // ONVIF协议
            remoteOnvifService.startPtzControl(device.getIpAddress(), device.getUserName(), device.getPassword(), direction, controlSpeed, SecurityConstants.INNER);
        } else if (LiveStreamType.JT1078.getCode().equals(deviceType)) {
            // JT1078协议
            startJt1078Ptz(device, channel, direction, controlSpeed);
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            // GB28181协议
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            // 根据控制类型调用不同的 GB28181 接口
            String controlType = getPtzControlType(direction);
            Integer speed = (controlSpeed != null) ? controlSpeed : 50;
            
            switch (controlType) {
                case "rotate":
                    Integer horizonSpeed = speed;
                    Integer verticalSpeed = speed;
                    Integer zoomSpeed = (speed / 10);
                    remoteGb28181Service.ptz(device.getGbDeviceId(), device.getGbChannelId(), direction, horizonSpeed, verticalSpeed, zoomSpeed, SecurityConstants.INNER);
                    break;
                case "focus":
                    remoteGb28181Service.focus(device.getGbDeviceId(), device.getGbChannelId(), direction, speed, SecurityConstants.INNER);
                    break;
                case "iris":
                    remoteGb28181Service.iris(device.getGbDeviceId(), device.getGbChannelId(), direction, speed, SecurityConstants.INNER);
                    break;
                case "zoom":
                    // GB28181 的缩放通过 ptz 接口处理
                    Integer horizonSpeedForZoom = 0;
                    Integer verticalSpeedForZoom = 0;
                    Integer zoomSpeedForZoom = speed;
                    remoteGb28181Service.ptz(device.getGbDeviceId(), device.getGbChannelId(), direction, horizonSpeedForZoom, verticalSpeedForZoom, zoomSpeedForZoom, SecurityConstants.INNER);
                    break;
                default:
                    throw new RuntimeException("不支持的云台控制类型: " + controlType);
            }

        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }

    private void startJt1078Ptz(QsDevice device, int channel, String direction, Integer speed) {
        String mobileNo = device.getJtMobileNo();
        if (mobileNo == null || mobileNo.isEmpty()) {
            throw new RuntimeException("设备未配置 JT1078 手机号");
        }

        int jtDirection = convertDirectionToJt1078(direction, true);
        speed = speed != null ? speed : 50;

        switch (getPtzControlType(direction)) {
            case "rotate":
                remoteJt1078Service.ptzRotate(mobileNo, channel, jtDirection, speed, SecurityConstants.INNER);
                break;
            case "zoom":
                remoteJt1078Service.ptzZoom(mobileNo, channel, jtDirection, speed, SecurityConstants.INNER);
                break;
            case "focus":
                remoteJt1078Service.ptzFocus(mobileNo, channel, jtDirection, speed, SecurityConstants.INNER);
                break;
            case "iris":
                remoteJt1078Service.ptzIris(mobileNo, channel, jtDirection, speed, SecurityConstants.INNER);
                break;
            default:
                throw new RuntimeException("不支持的云台控制类型: " + direction);
        }
    }

    private int convertDirectionToJt1078(String direction, boolean isStart) {
        if (!isStart) {
            return 0; // 停止
        }
        switch (direction) {
            case "up":
                return 1;
            case "down":
                return 2;
            case "left":
                return 3;
            case "right":
                return 4;
            case "zoomin":
                return 1;
            case "zoomout":
                return 2;
            case "near":
                return 1;
            case "far":
                return 2;
            case "in":
                return 1;
            case "out":
                return 2;
            default:
                return 0;
        }
    }

    private String getPtzControlType(String direction) {
        switch (direction) {
            case "up":
            case "down":
            case "left":
            case "right":
                return "rotate";
            case "zoomin":
            case "zoomout":
                return "zoom";
            case "near":
            case "far":
                return "focus";
            case "in":
            case "out":
                return "iris";
            default:
                return "rotate";
        }
    }

    @Override
    public void endPtz(Long id, String direction, Integer controlSpeed) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        Integer channel = device.getChannel() != null ? device.getChannel() : 0;
        String deviceType = device.getType();

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            // 海康SDK
            remoteHaiKangService.endPlayControl(id, channel, direction, SecurityConstants.INNER);
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            // 海康ISUP
            if (direction == null || direction.isEmpty() || "stop".equals(direction)) {
                // 如果方向为空或无效，我们尝试所有方向停止
                int[] allPtzCmds = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
                for (int cmd : allPtzCmds) {
                    remoteHaiKangIsupService.endPtz(id, channel, cmd, controlSpeed != null ? controlSpeed : 5, SecurityConstants.INNER);
                }
            } else {
                int ptzCmd = convertDirectionToHikIsupPtz(direction);
                remoteHaiKangIsupService.endPtz(id, channel, ptzCmd, controlSpeed != null ? controlSpeed : 5, SecurityConstants.INNER);
            }
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            // 大华SDK
            remoteDaHuaService.ptzControlUpEnd(id, channel, direction, SecurityConstants.INNER);
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            // ONVIF协议
            remoteOnvifService.stopPtzControl(device.getIpAddress(), device.getUserName(), device.getPassword(), SecurityConstants.INNER);
        } else if (LiveStreamType.JT1078.getCode().equals(deviceType)) {
            // JT1078协议
            endJt1078Ptz(device, channel, direction);
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            // GB28181协议
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            // 根据控制类型调用不同的 GB28181 停止接口
            String controlType = getPtzControlType(direction);
            String stopCommand = "stop";
            Integer stopSpeed = 0;
            
            switch (controlType) {
                case "rotate":
                    remoteGb28181Service.ptz(device.getGbDeviceId(), device.getGbChannelId(), stopCommand, 0, 0, 0, SecurityConstants.INNER);
                    break;
                case "focus":
                    remoteGb28181Service.focus(device.getGbDeviceId(), device.getGbChannelId(), stopCommand, stopSpeed, SecurityConstants.INNER);
                    break;
                case "iris":
                    remoteGb28181Service.iris(device.getGbDeviceId(), device.getGbChannelId(), stopCommand, stopSpeed, SecurityConstants.INNER);
                    break;
                case "zoom":
                    remoteGb28181Service.ptz(device.getGbDeviceId(), device.getGbChannelId(), stopCommand, 0, 0, 0, SecurityConstants.INNER);
                    break;
                default:
                    // 默认调用 ptz 停止
                    remoteGb28181Service.ptz(device.getGbDeviceId(), device.getGbChannelId(), stopCommand, 0, 0, 0, SecurityConstants.INNER);
                    break;
            }

        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }

    private void endJt1078Ptz(QsDevice device, int channel, String direction) {
        String mobileNo = device.getJtMobileNo();
        if (mobileNo == null || mobileNo.isEmpty()) {
            throw new RuntimeException("设备未配置 JT1078 手机号");
        }

        int speed = 50;

        // 对于停止操作，我们需要停止所有相关的云台控制类型
        // 或者根据 direction 判断要停止的类型
        boolean stopAll = (direction == null || direction.isEmpty() || "stop".equals(direction));
        String[] controlTypes = stopAll ?
            new String[]{"rotate", "zoom", "focus", "iris"} :
            new String[]{getPtzControlType(direction)};

        for (String controlType : controlTypes) {
            switch (controlType) {
                case "rotate":
                    remoteJt1078Service.ptzRotate(mobileNo, channel, 0, speed, SecurityConstants.INNER);
                    break;
                case "zoom":
                    remoteJt1078Service.ptzZoom(mobileNo, channel, 0, speed, SecurityConstants.INNER);
                    break;
                case "focus":
                    remoteJt1078Service.ptzFocus(mobileNo, channel, 0, speed, SecurityConstants.INNER);
                    break;
                case "iris":
                    remoteJt1078Service.ptzIris(mobileNo, channel, 0, speed, SecurityConstants.INNER);
                    break;
            }
        }
    }

    @Override
    public List<Preset> getPresetList(Long id, Integer channelId) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        String deviceType = device.getType();
        Integer channel = channelId != null ? channelId : (device.getChannel() != null ? device.getChannel() : 0);

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            R<List<com.ruoyi.haikang.api.domain.PresetInfo>> result = remoteHaiKangService.getPresets(id, channel, SecurityConstants.INNER);
            List<com.ruoyi.haikang.api.domain.PresetInfo> presetInfoList = result.getData();
            List<Preset> presetList = new ArrayList<>();
            if (presetInfoList != null) {
                for (com.ruoyi.haikang.api.domain.PresetInfo presetInfo : presetInfoList) {
                    Preset preset = new Preset();
                    preset.setIndex(presetInfo.getIndex());
                    preset.setName(presetInfo.getName());
                    presetList.add(preset);
                }
            }
            return presetList;
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            R<List<HaiKangIsupPresetInfo>> result = remoteHaiKangIsupService.getPresetList(id, channel, SecurityConstants.INNER);
            List<HaiKangIsupPresetInfo> presetInfoList = result.getData();
            List<Preset> presetList = new ArrayList<>();
            if (presetInfoList != null) {
                for (HaiKangIsupPresetInfo presetInfo : presetInfoList) {
                    Preset preset = new Preset();
                    preset.setIndex(presetInfo.index);
                    preset.setName(presetInfo.name);
                    presetList.add(preset);
                }
            }
            return presetList;
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            R<ArrayList<HashMap<String, Object>>> result = remoteDaHuaService.getPresetList(id, channel, SecurityConstants.INNER);
            ArrayList<HashMap<String, Object>> presetMapList = result.getData();
            List<Preset> presetList = new ArrayList<>();
            if (presetMapList != null) {
                for (HashMap<String, Object> map : presetMapList) {
                    Preset preset = new Preset();
                    preset.setIndex((Integer) map.get("index"));
                    preset.setName((String) map.get("name"));
                    presetList.add(preset);
                }
            }
            return presetList;
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            R<List<Map<String, Object>>> result = remoteOnvifService.getPresets(device.getIpAddress(), device.getUserName(), device.getPassword(), SecurityConstants.INNER);
            List<Map<String, Object>> presetMapList = result.getData();
            List<Preset> presetList = new ArrayList<>();
            if (presetMapList != null) {
                for (Map<String, Object> map : presetMapList) {
                    Preset preset = new Preset();
                    Object indexObj = map.get("index");
                    Object nameObj = map.get("name");
                    preset.setIndex(indexObj != null ? Integer.parseInt(indexObj.toString()) : null);
                    preset.setName(nameObj != null ? nameObj.toString() : null);
                    presetList.add(preset);
                }
            }
            return presetList;
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            R<Object> result = remoteGb28181Service.queryPreset(device.getGbDeviceId(), device.getGbChannelId(), SecurityConstants.INNER);
            Object data = result.getData();
            List<Preset> presetList = new ArrayList<>();
            if (data instanceof List) {
                List<?> list = (List<?>) data;
                for (Object item : list) {
                    if (item instanceof Map) {
                        Map<?, ?> map = (Map<?, ?>) item;
                        Preset preset = new Preset();
                        Object indexObj = map.get("index");
                        Object nameObj = map.get("name");
                        preset.setIndex(indexObj != null ? Integer.parseInt(indexObj.toString()) : null);
                        preset.setName(nameObj != null ? nameObj.toString() : null);
                        presetList.add(preset);
                    }
                }
            }
            return presetList;
        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }

    @Override
    public void setPreset(Long id, Integer channelId, Integer presetIndex, String presetName) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        String deviceType = device.getType();
        Integer channel = channelId != null ? channelId : (device.getChannel() != null ? device.getChannel() : 0);

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            remoteHaiKangService.setPresets(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            remoteHaiKangIsupService.setPreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            remoteDaHuaService.setPreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            remoteOnvifService.setPreset(device.getIpAddress(), device.getUserName(), device.getPassword(), presetIndex, presetName, SecurityConstants.INNER);
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            remoteGb28181Service.addPreset(device.getGbDeviceId(), device.getGbChannelId(), presetIndex, SecurityConstants.INNER);
        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }

    @Override
    public void gotoPreset(Long id, Integer channelId, Integer presetIndex, Integer speed) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        String deviceType = device.getType();
        Integer channel = channelId != null ? channelId : (device.getChannel() != null ? device.getChannel() : 0);
        Integer useSpeed = speed != null ? speed : 50;

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            remoteHaiKangService.invokePresets(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            remoteHaiKangIsupService.gotoPreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            remoteDaHuaService.invokePreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            remoteOnvifService.gotoPreset(device.getIpAddress(), device.getUserName(), device.getPassword(), presetIndex, useSpeed, SecurityConstants.INNER);
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            remoteGb28181Service.callPreset(device.getGbDeviceId(), device.getGbChannelId(), presetIndex, SecurityConstants.INNER);
        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }

    @Override
    public void deletePreset(Long id, Integer channelId, Integer presetIndex) {
        QsDevice device = selectQsDeviceById(id);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        String deviceType = device.getType();
        Integer channel = channelId != null ? channelId : (device.getChannel() != null ? device.getChannel() : 0);

        if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
            remoteHaiKangService.delPresets(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
            remoteHaiKangIsupService.clearPreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
            remoteDaHuaService.delPreset(id, channel, presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
            remoteOnvifService.removePreset(device.getIpAddress(), device.getUserName(), device.getPassword(), presetIndex, SecurityConstants.INNER);
        } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
            if (device.getGbDeviceId() == null || device.getGbChannelId() == null) {
                throw new RuntimeException("设备未配置 GB28181 设备ID或通道ID");
            }
            remoteGb28181Service.deletePreset(device.getGbDeviceId(), device.getGbChannelId(), presetIndex, SecurityConstants.INNER);
        } else {
            throw new RuntimeException("不支持的设备类型: " + deviceType);
        }
    }
}
