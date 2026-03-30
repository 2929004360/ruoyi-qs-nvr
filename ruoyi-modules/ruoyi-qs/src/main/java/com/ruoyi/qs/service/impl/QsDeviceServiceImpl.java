package com.ruoyi.qs.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.mapper.QsDeviceMapper;
import com.ruoyi.qs.service.IQsDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频监控设备Service业务层处理
 *
 * @author fengcheng
 * @date 2026-03-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QsDeviceServiceImpl implements IQsDeviceService {
    @Autowired
    private QsDeviceMapper qsDeviceMapper;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

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

        // RTSP协议
        if (LiveStreamType.RTSP.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("rtsp_" + IdUtil.getSnowflakeNextId());
            if (!isValidRtspFormat(qsDevice.getLiveAddress())) {
                throw new SecurityException("RTSP地址格式不正确");
            }
        }

        // RTMP协议
        if (LiveStreamType.RTMP.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("rtmp_" + IdUtil.getSnowflakeNextId());
            if (!isValidRtmpFormat(qsDevice.getLiveAddress())) {
                throw new SecurityException("RTMP地址格式不正确");
            }
        }

        // FLV协议
        if (LiveStreamType.FLV.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("flv_" + IdUtil.getSnowflakeNextId());
            if (!isValidFlvAddress(qsDevice.getLiveAddress())) {
                throw new SecurityException("FLV地址格式不正确");
            }
        }

        // HLS协议
        if (LiveStreamType.HLS.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("hls_" + IdUtil.getSnowflakeNextId());
            if (!isValidHlsAddress(qsDevice.getLiveAddress())) {
                throw new SecurityException("HLS地址格式不正确");
            }
        }

        // 视频文件
        if (LiveStreamType.VIDEO_FILE.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("video_file_" + IdUtil.getSnowflakeNextId());
            if (!isValidMp4Address(qsDevice.getLiveAddress())) {
                throw new SecurityException("视频文件格式不正确");
            }
        }

        // 海康SDK
        if (LiveStreamType.HIK_SDK.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("haikang_" + IdUtil.getSnowflakeNextId());
            LoginDevice loginDevice = new LoginDevice();
            loginDevice.setIpAddress(qsDevice.getIpAddress());
            loginDevice.setPort(qsDevice.getPort());
            loginDevice.setUserName(qsDevice.getUserName());
            loginDevice.setPassword(qsDevice.getPassword());
            R<Integer> r = remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                throw new SecurityException(r.getMsg());
            }
        }

        // 海康ISUP
        if (LiveStreamType.HIK_ISUP.getCode().equals(qsDevice.getType())) {
            qsDevice.setDeviceCode("haikang_isup_" + qsDevice.getDeviceCode());
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
                throw new SecurityException("RTSP地址格式不正确");
            }
        }

        // RTMP协议
        if (LiveStreamType.RTMP.getCode().equals(qsDevice.getType())) {
            if (!isValidRtmpFormat(qsDevice.getLiveAddress())) {
                throw new SecurityException("RTMP地址格式不正确");
            }
        }

        // FLV协议
        if (LiveStreamType.FLV.getCode().equals(qsDevice.getType())) {
            if (!isValidFlvAddress(qsDevice.getLiveAddress())) {
                throw new SecurityException("FLV地址格式不正确");
            }
        }

        // HLS协议
        if (LiveStreamType.HLS.getCode().equals(qsDevice.getType())) {
            if (!isValidHlsAddress(qsDevice.getLiveAddress())) {
                throw new SecurityException("HLS地址格式不正确");
            }
        }

        // 视频文件
        if (LiveStreamType.VIDEO_FILE.getCode().equals(qsDevice.getType())) {
            if (!isValidMp4Address(qsDevice.getLiveAddress())) {
                throw new SecurityException("视频文件格式不正确");
            }
        }

        // 海康SDK
        if (LiveStreamType.HIK_SDK.getCode().equals(qsDevice.getType())) {
            R<Void> logoutDevicer = remoteHaiKangService.logoutDevice(qsDevice.getIpAddress(), SecurityConstants.INNER);
            if (logoutDevicer.getCode() != Constants.SUCCESS) {
                throw new SecurityException(logoutDevicer.getMsg());
            }

            LoginDevice loginDevice = new LoginDevice();
            loginDevice.setIpAddress(qsDevice.getIpAddress());
            loginDevice.setPort(qsDevice.getPort());
            loginDevice.setUserName(qsDevice.getUserName());
            loginDevice.setPassword(qsDevice.getPassword());
            R<Integer> r = remoteHaiKangService.loginDevice(loginDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                throw new SecurityException(r.getMsg());
            }
        }
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
     * 判断是否为合法的 HTTP-FLV 地址
     * 规则：必须以 http:// 或 https:// 开头，且以 .flv 结尾（忽略大小写，允许带参数）
     */
    public static boolean isValidFlvAddress(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // 正则解释：
        // ^https?://       : 匹配 http:// 或 https://
        // .+               : 匹配中间的域名和路径
        // \.flv            : 必须以 .flv 结尾
        // (\\?.*)?$        : 允许后面跟随 ? 开头的参数（如 token=xxx），且参数是可选的
        String regex = "^https?://.+\\.flv(\\?.*)?$";

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
}
