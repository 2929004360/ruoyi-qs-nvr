package com.ruoyi.zlm.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 接收来自redis的GPS更新通知
 *
 * @author lin
 * 监听：  SUBSCRIBE VM_MSG_GPS
 * 发布   PUBLISH VM_MSG_GPS '{"messageId":"1727228507555","id":"24212345671381000047","lng":116.30307666666667,"lat":40.03295833333333,"time":"2024-09-25T09:41:47","direction":"56.0","speed":0.0,"altitude":60.0,"unitNo":"100000000","memberNo":"10000047"}'
 */
@Slf4j
@Component
public class RedisGpsMsgListener implements MessageListener {

    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();


    @Override
    public void onMessage(@NotNull Message message, byte[] bytes) {
        log.debug("[REDIS: GPS]： {}", new String(message.getBody()));
        taskQueue.offer(message);
    }

//    @Scheduled(fixedDelay = 200, timeUnit = TimeUnit.MILLISECONDS)   //每400毫秒执行一次
//    public void executeTaskQueue() {
//
//    }

    /**
     * 定时将经纬度更新到数据库
     */
//    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)   //每2秒执行一次
//    public void execute() {
//
//    }
}
