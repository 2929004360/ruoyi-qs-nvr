package com.ruoyi.zlm.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 接收来自redis的关闭流更新通知
 * 消息举例： PUBLISH VM_MSG_STREAM_PUSH_CLOSE "{'app': 'live', 'stream': 'stream'}"
 *
 * @author lin
 */
@Slf4j
@Component
public class RedisCloseStreamMsgListener implements MessageListener {


    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(@NotNull Message message, byte[] bytes) {
        taskQueue.offer(message);
    }

//    @Scheduled(fixedDelay = 100)
//    public void executeTaskQueue() {
//
//    }
}
