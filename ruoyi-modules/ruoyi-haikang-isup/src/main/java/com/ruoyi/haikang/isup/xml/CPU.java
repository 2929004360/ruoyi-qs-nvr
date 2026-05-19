package com.ruoyi.haikang.isup.xml;

import lombok.Data;
import jakarta.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CPU {
    private Integer cpuUtilization; // CPU使用率 (0~100)
}
