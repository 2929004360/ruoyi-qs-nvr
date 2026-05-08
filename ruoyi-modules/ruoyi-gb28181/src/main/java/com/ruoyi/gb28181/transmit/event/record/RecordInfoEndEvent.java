package com.ruoyi.gb28181.transmit.event.record;

import com.ruoyi.gb28181.api.bean.RecordInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * @description: 录像查询结束时间
 * @author: pan
 * @data: 2022-02-23
 */
@Setter
@Getter
public class RecordInfoEndEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    public RecordInfoEndEvent(Object source) {
        super(source);
    }

    private RecordInfo recordInfo;

}
