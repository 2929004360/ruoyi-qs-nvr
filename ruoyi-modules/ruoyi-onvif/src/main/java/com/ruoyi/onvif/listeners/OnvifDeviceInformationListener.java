package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.OnvifDeviceInformation;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifDeviceInformationListener {

    void onDeviceInformationReceived(OnvifDevice device, OnvifDeviceInformation deviceInformation);

}
