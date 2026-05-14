package com.ruoyi.zlm.api.domain;

import com.ruoyi.qs.api.domain.QsDevice;
import lombok.Data;

import java.io.Serializable;

@Data
public class Gb28181PlatformPlay implements Serializable {
    private static final long serialVersionUID = 1L;

    private QsDevice qsDevice;
    private String platformDeviceGbId;
    private String dstUrl;
    private int dstPort;
    private String ssrc;
    private String streamId;
    /**
     * 是否使用UDP，1=UDP，0=TCP
     */
    private Integer isUdp;
}
