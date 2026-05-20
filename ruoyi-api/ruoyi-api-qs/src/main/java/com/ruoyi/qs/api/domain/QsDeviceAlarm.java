package com.ruoyi.qs.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.ColumnType;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QsDeviceAlarm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "编号", cellType = ColumnType.NUMERIC)
    private Long id;

    private Long deviceId;

    @Excel(name = "设备编号")
    private String deviceCode;

    @Excel(name = "设备名称")
    private String deviceName;

    @Excel(name = "告警类型", readConverterExp = "motion=移动侦测,video_loss=视频丢失,cover=视频遮挡,cross_line=越界侦测,enter_area=区域入侵,leave_area=区域离开,object_remove=物品移除,object_leave=物品遗留,face_detection=人脸检测,other=其他")
    private String alarmType;

    @Excel(name = "告警级别", readConverterExp = "low=低,medium=中,high=高,critical=紧急")
    private String alarmLevel;

    @Excel(name = "SDK类型", readConverterExp = "hik=海康,hik_isup=海康ISUP,dahua=大华,uniview=宇视,tiandy=天地伟业,gb28181=国标28181")
    private String sdkType;

    @Excel(name = "通道号", cellType = ColumnType.NUMERIC)
    private Integer channel;

    @Excel(name = "告警时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    @Excel(name = "告警内容")
    private String alarmContent;

    @Excel(name = "告警图片地址")
    private String imageUrl;

    @Excel(name = "是否已处理", readConverterExp = "0=未处理,1=已处理")
    private Integer handled;

    @Excel(name = "处理人")
    private String handler;

    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    @Excel(name = "备注")
    private String remark;

    /**
     * 批量处理用的id数组
     */
    private Long[] ids;
}
