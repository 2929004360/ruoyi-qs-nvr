package com.ruoyi.gb28181.transmit.event;

import com.ruoyi.gb28181.common.ErrorCode;
import com.ruoyi.gb28181.transmit.event.sip.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * @author lin
 */
@Slf4j
@Component
public class MessageSubscribe {

    private final Map<String, MessageEvent<?>> subscribes = new ConcurrentHashMap<>();

    private final DelayQueue<MessageEvent<?>> delayQueue = new DelayQueue<>();

    @Scheduled(fixedDelay = 200)
    public void execute() {
        // 1. 使用 poll() 替代 take()
        // poll() 会立即返回：如果有已超期的任务则返回，否则返回 null
        MessageEvent<?> take = delayQueue.poll();

        // 2. 循环处理所有已超期的任务
        while (take != null) {
            try {
                // 执行回调（超时处理）
                if (take.getCallback() != null) {
                    take.getCallback().run(ErrorCode.ERROR486.getCode(), "消息超时未回复", null);
                }
                subscribes.remove(take.getKey());
            } catch (Exception e) {
                // 建议捕获 Exception 而不是 RuntimeException，防止业务异常导致循环中断
                log.error("[超时检查] 处理超时任务异常", e);
            }
            // 3. 继续尝试获取下一个已超期的任务
            take = delayQueue.poll();
        }
        // 4. 如果 poll() 返回 null，说明当前没有超时的任务，方法直接结束
        // 线程释放，等待下一次 200ms 调度
    }


    public void addSubscribe(MessageEvent<?> event) {
        MessageEvent<?> messageEvent = subscribes.get(event.getKey());
        if (messageEvent != null) {
            subscribes.remove(event.getKey());
            delayQueue.remove(messageEvent);
        }
        subscribes.put(event.getKey(), event);
        delayQueue.offer(event);
    }

    public MessageEvent<?> getSubscribe(String key) {
        return subscribes.get(key);
    }

    public void removeSubscribe(String key) {
        if(key == null){
            return;
        }
        MessageEvent<?> messageEvent = subscribes.get(key);
        if (messageEvent != null) {
            subscribes.remove(key);
            delayQueue.remove(messageEvent);
        }
    }

    public boolean isEmpty(){
        return subscribes.isEmpty();
    }

    public Integer size() {
        return subscribes.size();
    }
}
