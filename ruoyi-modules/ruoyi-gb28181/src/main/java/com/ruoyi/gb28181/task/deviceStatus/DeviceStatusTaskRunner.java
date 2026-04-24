package com.ruoyi.gb28181.task.deviceStatus;

import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DeviceStatusTaskRunner {

    private final Map<String, DeviceStatusTask> subscribes = new ConcurrentHashMap<>();

    private final DelayQueue<DeviceStatusTask> delayQueue = new DelayQueue<>();

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UserSetting userSetting;

    private final String prefix = "QS_DEVICE_STATUS";

    @Scheduled(fixedDelay = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void expirationCheck() {
        // 使用 poll() 而不是 take()
        // poll() 会立即返回：如果有到期任务则返回，如果没有（或没到期）则返回 null
        DeviceStatusTask task = delayQueue.poll();

        // 只要取到了任务（说明有任务到期了），就处理
        while (task != null) {
            try {
                removeTask(task.getDeviceId());
                task.expired();
            } catch (Exception e) {
                log.error("[设备状态到期] 到期处理时出现异常，设备编号: {} ", task.getDeviceId(), e);
            }
            // 继续尝试取下一个已到期任务
            task = delayQueue.poll();
        }
        // 如果 poll() 返回 null，说明当前没有到期任务，方法直接结束
        // 线程释放，Spring 调度器可以在 500ms 后再次调用此方法
    }

    public void addTask(DeviceStatusTask task) {
        Duration duration = Duration.ofSeconds((task.getDelayTime() - System.currentTimeMillis()) / 1000);
        if (duration.getSeconds() < 0) {
            return;
        }
        subscribes.put(task.getDeviceId(), task);
        String key = String.format("%s_%s_%s", prefix, userSetting.getServerId(), task.getDeviceId());
        redisTemplate.opsForValue().set(key, task.getInfo(), duration);
        delayQueue.offer(task);
    }

    public boolean removeTask(String key) {
        DeviceStatusTask task = subscribes.get(key);
        if (task == null) {
            return false;
        }
        String redisKey = String.format("%s_%s_%s", prefix, userSetting.getServerId(), task.getDeviceId());
        redisTemplate.delete(redisKey);
        subscribes.remove(key);
        if (delayQueue.contains(task)) {
            boolean remove = delayQueue.remove(task);
            if (!remove) {
                log.info("[移除状态任务] 从延时队列内移除失败： {}", key);
            }
        }
        return true;
    }

    public SipTransactionInfo getTransactionInfo(String key) {
        DeviceStatusTask task = subscribes.get(key);
        if (task == null) {
            return null;
        }
        return task.getTransactionInfo();
    }

    public boolean updateDelay(String key, long expirationTime) {
        DeviceStatusTask task = subscribes.get(key);
        if (task == null) {
            return false;
        }
        log.debug("[更新状态任务时间] 编号： {}", key);
        // 如果值更改时间，如果队列中有多个元素时 超时无法出发。目前采用移除再加入的方法
        delayQueue.remove(task);
        task.setDelayTime(expirationTime);
        delayQueue.offer(task);
        String redisKey = String.format("%s_%s_%s", prefix, userSetting.getServerId(), task.getDeviceId());
        Duration duration = Duration.ofSeconds((expirationTime - System.currentTimeMillis()) / 1000);
        redisTemplate.expire(redisKey, duration);
        return true;
    }

    public boolean containsKey(String key) {
        return subscribes.containsKey(key);
    }

    public List<DeviceStatusTaskInfo> getAllTaskInfo() {
        String scanKey = String.format("%s_%s_*", prefix, userSetting.getServerId());
        List<Object> values = RedisUtil.scan(redisTemplate, scanKey);
        if (values.isEmpty()) {
            return new ArrayList<>();
        }
        List<DeviceStatusTaskInfo> result = new ArrayList<>();
        for (Object value : values) {
            String redisKey = (String) value;
            DeviceStatusTaskInfo taskInfo = (DeviceStatusTaskInfo) redisTemplate.opsForValue().get(redisKey);
            if (taskInfo == null) {
                continue;
            }
            Long expire = redisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            taskInfo.setExpireTime(expire);
            result.add(taskInfo);
        }
        return result;

    }
}
