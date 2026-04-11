package com.ruoyi.zlm.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 接收redis发送的推流设备上线下线通知
 *
 * @author lin
 * 发送 PUBLISH VM_MSG_PUSH_STREAM_STATUS_CHANGE '{"setAllOffline":false,"offlineStreams":[{"app":"1000","stream":"10000022","timeStamp":1726729716551}]}'
 * 订阅 SUBSCRIBE VM_MSG_PUSH_STREAM_STATUS_CHANGE
 */
@Slf4j
@Component
public class RedisPushStreamStatusMsgListener implements MessageListener, ApplicationRunner {

    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[REDIS: 推流设备状态变化]： {}", new String(message.getBody()));
        taskQueue.offer(message);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
