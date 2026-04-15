package com.ruoyi.qs.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域树
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QsRegionTree extends QsRegion {

    /**
     * 树节点ID
     */
    private String treeId;

    /**
     * 是否有子节点
     */
    private boolean isLeaf;

    /**
     * 类型, 行政区划:0 摄像头: 1
     */
    private int type;

    /** 直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078) */
    private int streamType;

    /**
     * 设备状态(OFFLINE=离线,ON=在线)
     */
    private String status;

    /**
     * 经度 WGS-84坐标系
     */
    private Double longitude;

    /**
     * 纬度 WGS-84坐标系
     */
    private Double latitude;

    /**
     * 设备厂商
     */
    private String manufacturer;

    /**
     * 安装地址
     */
    private String address;

    /**
     * 摄像机结构类型,标识摄像机类型: 1-球机; 2-半球; 3-固定枪机; 4-遥控枪机;5-遥控半球;6-多目设备的全景/拼接通道;7-多目设备的分割通道
     */
    private Integer ptzType;

    /**
     * ip地址
     */
    private String ipAddress;
}
