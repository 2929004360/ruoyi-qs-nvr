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
@Message(JT1078.云台变倍控制)
public class T9306 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "方向：0.停止 1.变倍大 2.变倍小")
    private int direction;
    @Field(length = 1, desc = "速度（1-255）")
    private int speed;

}

