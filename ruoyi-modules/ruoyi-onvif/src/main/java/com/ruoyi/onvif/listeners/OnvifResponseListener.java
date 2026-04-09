package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.responses.OnvifResponse;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifResponseListener {

    void onResponse(OnvifDevice onvifDevice, OnvifResponse response);

    void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage);
}
