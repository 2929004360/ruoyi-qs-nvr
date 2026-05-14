package com.ruoyi.gb28181.transmit.event.request.impl.message.query;

import com.ruoyi.gb28181.transmit.event.request.impl.message.MessageHandlerAbstract;
import com.ruoyi.gb28181.transmit.event.request.impl.message.MessageRequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Query类型消息处理
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class QueryMessageHandler extends MessageHandlerAbstract implements InitializingBean {

    private final String messageType = "Query";

    @Autowired
    private MessageRequestProcessor messageRequestProcessor;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageRequestProcessor.addHandler(messageType, this);
    }
}
