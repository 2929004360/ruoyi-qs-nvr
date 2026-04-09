package com.ruoyi.onvif.listeners;

import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.OnvifMediaProfile;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifMediaStreamURIListener {

    void onMediaStreamURIReceived(OnvifDevice device, OnvifMediaProfile profile, String uri);

}
