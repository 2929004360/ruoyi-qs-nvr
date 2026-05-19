package com.ruoyi.haikang.isup.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Time")
@XmlAccessorType(XmlAccessType.FIELD)
public class Time {
    @XmlAttribute
    private String version;
    private String timeMode;
    private String localTime;
    private String timeZone;
}
