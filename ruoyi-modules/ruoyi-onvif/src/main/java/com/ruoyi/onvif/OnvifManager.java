package com.ruoyi.onvif;

import com.ruoyi.onvif.listeners.*;
import com.ruoyi.onvif.listeners.StorageInfoListener;
import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.OnvifMediaProfile;
import com.ruoyi.onvif.requests.*;
import com.ruoyi.onvif.responses.OnvifResponse;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifManager implements OnvifResponseListener {

    //Constants
    public final static String TAG = OnvifManager.class.getSimpleName();

    //Attributes
    private final OnvifExecutor executor;
    private volatile OnvifResponseListener onvifResponseListener;
    private final AtomicBoolean destroyed = new AtomicBoolean(false);

    //Constructors
    public OnvifManager() {
        this(null);
    }

    private OnvifManager(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        executor = new OnvifExecutor(this);
    }

    //Methods
    public void getServices(OnvifDevice device, OnvifServicesListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetServicesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getDeviceInformation(OnvifDevice device, OnvifDeviceInformationListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetDeviceInformationRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaProfiles(OnvifDevice device, OnvifMediaProfilesListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetMediaProfilesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaStreamURI(OnvifDevice device, OnvifMediaProfile profile, OnvifMediaStreamURIListener listener) {
        if (destroyed.get() || device == null || profile == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetMediaStreamRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void sendOnvifRequest(OnvifDevice device, OnvifRequest request) {
        if (destroyed.get() || device == null || request == null) {
            return;
        }
        executor.sendRequest(device, request);
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    /**
     * 云台连续移动
     */
    public void continuousMove(OnvifDevice device, String profileToken, float pan, float tilt, float zoom, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null) {
            return;
        }
        OnvifRequest request = new ContinuousMoveRequest(profileToken, pan, tilt, zoom, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 停止云台移动
     */
    public void stop(OnvifDevice device, String profileToken, boolean panTilt, boolean zoom, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null) {
            return;
        }
        OnvifRequest request = new StopRequest(profileToken, panTilt, zoom, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取预置点列表
     */
    public void getPresets(OnvifDevice device, String profileToken, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null) {
            return;
        }
        OnvifRequest request = new GetPresetsRequest(profileToken, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 设置预置点
     */
    public void setPreset(OnvifDevice device, String profileToken, String presetToken, String presetName, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null) {
            return;
        }
        OnvifRequest request = new SetPresetRequest(profileToken, presetToken, presetName, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 调用预置点
     */
    public void gotoPreset(OnvifDevice device, String profileToken, String presetToken, float speed, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null || presetToken == null) {
            return;
        }
        OnvifRequest request = new GotoPresetRequest(profileToken, presetToken, speed, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 删除预置点
     */
    public void removePreset(OnvifDevice device, String profileToken, String presetToken, OnvifResponseListener listener) {
        if (destroyed.get() || device == null || profileToken == null || presetToken == null) {
            return;
        }
        OnvifRequest request = new RemovePresetRequest(profileToken, presetToken, listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取存储配置
     */
    public void getStorageConfigurations(OnvifDevice device, StorageInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetStorageConfigurationsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取存储能力
     */
    public void getStorageCapabilities(OnvifDevice device, StorageInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetStorageCapabilitiesRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取存储状态
     */
    public void getStorageState(OnvifDevice device, StorageInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetStorageStateRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取网络接口配置
     */
    public void getNetworkInterfaces(OnvifDevice device, NetworkInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetNetworkInterfacesRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取网络协议配置
     */
    public void getNetworkProtocols(OnvifDevice device, NetworkInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetNetworkProtocolsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取视频源配置
     */
    public void getVideoSourceConfigs(OnvifDevice device, MediaInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetVideoSourceConfigsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取视频编码器配置
     */
    public void getVideoEncoderConfigs(OnvifDevice device, MediaInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetVideoEncoderConfigsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取音频源配置
     */
    public void getAudioSourceConfigs(OnvifDevice device, MediaInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetAudioSourceConfigsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取音频编码器配置
     */
    public void getAudioEncoderConfigs(OnvifDevice device, MediaInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetAudioEncoderConfigsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * 获取视频输出配置
     */
    public void getVideoOutputConfigs(OnvifDevice device, MediaInfoListener listener) {
        if (destroyed.get() || device == null || listener == null) {
            return;
        }
        OnvifRequest request = new GetVideoOutputConfigsRequest(listener);
        executor.sendRequest(device, request);
    }

    /**
     * Clear up the resources.
     */
    public void destroy() {
        if (destroyed.compareAndSet(false, true)) {
            onvifResponseListener = null;
            executor.clear();
        }
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {
        if (!destroyed.get() && onvifResponseListener != null) {
            onvifResponseListener.onResponse(onvifDevice, response);
        }
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        if (!destroyed.get() && onvifResponseListener != null) {
            onvifResponseListener.onError(onvifDevice, errorCode, errorMessage);
        }
    }

}
