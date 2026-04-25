package com.ruoyi.jt1078.protocol.t808;

import com.ruoyi.jt1078.protocol.basics.JTMessage;
import com.ruoyi.jt1078.protocol.commons.JT808;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.事件报告)
public class T0301 extends JTMessage {

    @Field(length = 1, desc = "事件ID")
    private int eventId;

}