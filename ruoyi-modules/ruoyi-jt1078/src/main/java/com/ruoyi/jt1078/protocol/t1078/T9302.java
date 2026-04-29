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
@Message(JT1078.云台调整焦距控制)
public class T9302 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "方向：0.停止 1.焦距调大 2.焦距调小")
    private int direction;
    @Field(length = 1, desc = "速度（1-255）")
    private int speed;

}

