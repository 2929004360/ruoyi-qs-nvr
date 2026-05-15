package com.ruoyi.zlm.api.domain;

import com.ruoyi.qs.api.domain.QsDevice;
import lombok.Data;

import java.io.Serializable;

@Data
public class Gb28181PlatformPlayback implements Serializable {
    private static final long serialVersionUID = 1L;

    private QsDevice qsDevice;
    private String platformDeviceGbId;
    private String dstUrl;
    private int dstPort;
    private String ssrc;
    private String streamId;
    private Integer isUdp;
    private Integer rtcp;
    private Long startTime;
    private Long endTime;
}
