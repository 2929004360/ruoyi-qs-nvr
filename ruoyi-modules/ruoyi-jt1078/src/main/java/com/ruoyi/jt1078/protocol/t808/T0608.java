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
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.查询区域或线路数据应答)
public class T0608 extends JTMessage {

    /** @see com.ruoyi.jt1078.protocol.commons.Shape */
    @Field(length = 1, desc = "查询类型")
    private int type;
    @Field(totalUnit = 4, desc = "查询返回的数据")
    private byte[] bytes;

}