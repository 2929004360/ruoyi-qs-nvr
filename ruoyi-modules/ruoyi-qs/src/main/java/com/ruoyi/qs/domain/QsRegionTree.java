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
}
