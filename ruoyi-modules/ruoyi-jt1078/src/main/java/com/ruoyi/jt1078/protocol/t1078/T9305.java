package com.ruoyi.jt1078.protocol.t1078;

import com.ruoyi.jt1078.protocol.basics.JTMessage;
import com.ruoyi.jt1078.protocol.commons.JT1078;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Data
@Accessors(chain = true)
@Message(JT1078.红外补光控制)
public class T9305 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "红外补光控制：0.停止 1.开始")
    private int control;

}

