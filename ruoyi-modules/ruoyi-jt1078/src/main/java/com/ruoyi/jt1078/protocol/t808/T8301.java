package com.ruoyi.jt1078.protocol.t808;

import com.ruoyi.jt1078.protocol.basics.JTMessage;
import com.ruoyi.jt1078.protocol.commons.JT808;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.事件设置)
public class T8301 extends JTMessage {

    /** @see com.ruoyi.jt1078.protocol.commons.Action */
    @Field(length = 1, desc = "设置类型：0.清空 1.更新(先清空,后追加) 2.追加 3.修改 4.指定删除")
    private int type;
    @Field(totalUnit = 1, desc = "事件项")
    private List<Event> events;

    public void addEvent(int id, String content) {
        if (events == null)
            events = new ArrayList<>(2);
        events.add(new Event().setId(id).setContent(content));
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Event {
        @Field(length = 1, desc = "事件ID")
        private int id;
        @Field(lengthUnit = 1, desc = "内容")
        private String content;
    }
}