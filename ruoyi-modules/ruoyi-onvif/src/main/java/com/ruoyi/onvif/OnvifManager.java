package com.ruoyi.onvif;

import com.ruoyi.onvif.listeners.*;
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
