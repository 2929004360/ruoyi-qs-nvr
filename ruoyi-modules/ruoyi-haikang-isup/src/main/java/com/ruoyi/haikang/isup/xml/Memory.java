package com.ruoyi.haikang.isup.xml;

import lombok.Data;
import jakarta.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Memory {
    private String memoryDescription;
    private Float memoryUsage;      // 已用内存 (MB)
    private Float memoryAvailable;  // 可用内存 (MB)
}
