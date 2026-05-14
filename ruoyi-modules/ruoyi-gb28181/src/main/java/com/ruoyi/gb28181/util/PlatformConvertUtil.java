package com.ruoyi.gb28181.util;

import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.domain.Gb28181PlatformChannel;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;

/**
 * 平台实体转换工具类
 *
 * @author ruoyi
 */
public class PlatformConvertUtil {

    /**
     * QsGb28181Platform -> Gb28181Platform
     */
    public static Gb28181Platform convertToGbPlatform(QsGb28181Platform qsPlatform) {
        if (qsPlatform == null) {
            return null;
        }
        Gb28181Platform platform = new Gb28181Platform();
        platform.setId(qsPlatform.getId());
        platform.setName(qsPlatform.getName());
        platform.setServerIp(qsPlatform.getServerIp());
        platform.setServerPort(qsPlatform.getServerPort());
        platform.setServerGbId(qsPlatform.getServerGbId());
        platform.setServerGbDomain(qsPlatform.getServerGbDomain());
        platform.setDeviceGbId(qsPlatform.getDeviceGbId());
        platform.setDeviceIp(qsPlatform.getDeviceIp());
        platform.setDevicePort(qsPlatform.getDevicePort());
        platform.setTransport(qsPlatform.getTransport());
        platform.setExpires(qsPlatform.getExpires());
        platform.setKeepTimeout(qsPlatform.getKeepTimeout());
        platform.setAutoPushChannel(qsPlatform.getAutoPushChannel());
        platform.setEnable(qsPlatform.getEnable());
        platform.setStatus(qsPlatform.getStatus());
        platform.setCatalogGroup(qsPlatform.getCatalogGroup());
        platform.setManufacturer(qsPlatform.getManufacturer());
        platform.setModel(qsPlatform.getModel());
        platform.setAddress(qsPlatform.getAddress());
        platform.setUsername(qsPlatform.getUsername());
        platform.setPassword(qsPlatform.getPassword());
        platform.setRtcp(qsPlatform.getRtcp());
        platform.setCivilCode(qsPlatform.getCivilCode());
        platform.setRegisterWay(qsPlatform.getRegisterWay());
        platform.setSecrecy(qsPlatform.getSecrecy());
        platform.setPtz(qsPlatform.getPtz());
        platform.setCharacterSet(qsPlatform.getCharacterSet());
        platform.setAsMessageChannel(qsPlatform.getAsMessageChannel());
        platform.setCatalogWithPlatform(qsPlatform.getCatalogWithPlatform());
        platform.setCatalogWithGroup(qsPlatform.getCatalogWithGroup());
        platform.setCatalogWithRegion(qsPlatform.getCatalogWithRegion());
        platform.setSendStreamIp(qsPlatform.getSendStreamIp());
        platform.setServerId(qsPlatform.getServerId());
        platform.setCreateBy(qsPlatform.getCreateBy());
        platform.setCreateTime(qsPlatform.getCreateTime());
        platform.setUpdateBy(qsPlatform.getUpdateBy());
        platform.setUpdateTime(qsPlatform.getUpdateTime());
        return platform;
    }

    /**
     * QsGb28181PlatformChannel -> Gb28181PlatformChannel
     */
    public static Gb28181PlatformChannel convertToGbChannel(QsGb28181PlatformChannel qsChannel) {
        if (qsChannel == null) {
            return null;
        }
        Gb28181PlatformChannel channel = new Gb28181PlatformChannel();
        channel.setId(qsChannel.getId());
        channel.setPlatformId(qsChannel.getPlatformId());
        channel.setDeviceId(qsChannel.getDeviceId());
        channel.setCreateBy(qsChannel.getCreateBy());
        channel.setCreateTime(qsChannel.getCreateTime());
        channel.setUpdateBy(qsChannel.getUpdateBy());
        channel.setUpdateTime(qsChannel.getUpdateTime());
        return channel;
    }
}
