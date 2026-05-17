package com.ruoyi.qs.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.ColumnType;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QsDeviceSnapshot extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "编号", cellType = ColumnType.NUMERIC)
    private Long id;

    private Long deviceId;

    @Excel(name = "设备编号")
    private String deviceCode;

    @Excel(name = "设备名称")
    private String deviceName;

    @Excel(name = "文件地址")
    private String fileUrl;

    private String filePath;

    @Excel(name = "文件大小", cellType = ColumnType.NUMERIC)
    private Long fileSize;

    @Excel(name = "文件名称")
    private String fileName;

    @Excel(name = "文件类型")
    private String fileType;

    @Excel(name = "抓图类型", readConverterExp = "manual=手动,alarm=报警,schedule=定时,preview=预览")
    private String snapshotType;

    @Excel(name = "SDK类型", readConverterExp = "hik=海康,hik_isup=海康ISUP,dahua=大华,uniview=宇视,tiandy=天地伟业,gb28181=国标28181")
    private String sdkType;

    @Excel(name = "通道号", cellType = ColumnType.NUMERIC)
    private Integer channel;

    @Excel(name = "抓图时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date captureTime;

    @Excel(name = "宽度", cellType = ColumnType.NUMERIC)
    private Integer width;

    @Excel(name = "高度", cellType = ColumnType.NUMERIC)
    private Integer height;

    @Excel(name = "纬度")
    private BigDecimal latitude;

    @Excel(name = "经度")
    private BigDecimal longitude;
}
