package com.ruoyi.haikang.isup.xml;

import lombok.Data;
import jakarta.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NetPortStatus {
    private Integer id;
    private String netPortDescription; // ctrl / data1 / data2
    private String linkStatus;         // connected / disconnected
}
