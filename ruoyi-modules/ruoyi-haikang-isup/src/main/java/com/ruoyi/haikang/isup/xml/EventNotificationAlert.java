
package com.ruoyi.haikang.isup.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "EventNotificationAlert")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventNotificationAlert {
    @XmlElement(name = "ipAddress")
    private String ipAddress;

    @XmlElement(name = "portNo")
    private String portNo;

    @XmlElement(name = "protocol")
    private String protocol;

    @XmlElement(name = "macAddress")
    private String macAddress;

    @XmlElement(name = "dynChannelID")
    private String dynChannelID;

    @XmlElement(name = "channelID")
    private String channelID;

    @XmlElement(name = "dateTime")
    private String dateTime;

    @XmlElement(name = "activePostCount")
    private String activePostCount;

    @XmlElement(name = "eventType")
    private String eventType;

    @XmlElement(name = "eventState")
    private String eventState;

    @XmlElement(name = "eventDescription")
    private String eventDescription;

    @XmlElement(name = "channelName")
    private String channelName;

    @XmlElement(name = "deviceID")
    private String deviceID;

    @XmlElement(name = "targetType")
    private String targetType;

    @XmlElement(name = "serialNumber")
    private String serialNumber;
}
