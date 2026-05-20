package com.ruoyi.haikang.isup.task;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * 海康ISUP报警图片匹配定时任务
 * 定时检查Redis中的报警记录和图片队列，进行匹配关联
 */
@Slf4j
@Component
public class AlarmPictureMatchTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RemoteQsDeviceAlarmService remoteQsDeviceAlarmService;

    @Autowired
    private HaikangIsupConfig haikangIsupConfig;

    /**
     * 每500毫秒执行一次，检查Redis中的报警记录和图片队列
     */
    @Scheduled(fixedRate = 500)
    public void matchAlarmPicture() {
        try {
            Boolean alarmStorage = haikangIsupConfig.getAlarmServer().getAlarmStorage();
            if (alarmStorage == null || !alarmStorage) {
                return;
            }

            // 获取所有isup_alarm_pic_list开头的key（报警ID列表）
            Set<String> alarmKeys = redisTemplate.keys("isup_alarm_pic_list:*");
            if (alarmKeys == null || alarmKeys.isEmpty()) {
                return;
            }

            // 先尝试按设备匹配
            for (String alarmKey : alarmKeys) {
                // 提取设备ID
                String deviceId = alarmKey.replace("isup_alarm_pic_list:", "");
                // 先尝试用该设备的图片队列匹配
                matchByDevice(alarmKey, deviceId);
            }

            // 再用全局图片队列匹配剩余的报警
            matchWithGlobalQueue(alarmKeys);
        } catch (Exception e) {
            log.error("报警图片匹配定时任务异常", e);
        }
    }

    /**
     * 按设备ID匹配报警和图片
     */
    private void matchByDevice(String alarmKey, String deviceId) {
        try {
            // 检查该设备是否有图片队列
            String picKey = "isup_alarm_pic_queue:" + deviceId;
            Long picSize = redisTemplate.opsForList().size(picKey);
            if (picSize == null || picSize == 0) {
                return;
            }

            // 检查是否有未匹配的报警
            Long alarmSize = redisTemplate.opsForList().size(alarmKey);
            if (alarmSize == null || alarmSize == 0) {
                return;
            }

            // 尝试匹配
            matchOne(alarmKey, picKey);
        } catch (Exception e) {
            log.error("按设备匹配异常, alarmKey:{}, deviceId:{}", alarmKey, deviceId, e);
        }
    }

    /**
     * 使用全局图片队列匹配剩余的报警
     */
    private void matchWithGlobalQueue(Set<String> alarmKeys) {
        try {
            String globalPicKey = "isup_alarm_pic_queue:global";
            Long picSize = redisTemplate.opsForList().size(globalPicKey);
            if (picSize == null || picSize == 0) {
                return;
            }

            // 遍历所有报警队列，尝试用全局图片匹配
            for (String alarmKey : alarmKeys) {
                Long alarmSize = redisTemplate.opsForList().size(alarmKey);
                if (alarmSize != null && alarmSize > 0) {
                    matchOne(alarmKey, globalPicKey);
                }
            }
        } catch (Exception e) {
            log.error("全局队列匹配异常", e);
        }
    }

    /**
     * 匹配一个报警和一张图片
     */
    private void matchOne(String alarmKey, String picKey) {
        try {
            // 从右边取出最新的一个报警ID
            Long alarmId = (Long) redisTemplate.opsForList().rightPop(alarmKey);
            if (alarmId == null) {
                return;
            }

            // 检查这个报警是否已经有图片了
            boolean hasPicture = checkAlarmHasPicture(alarmId);
            if (hasPicture) {
                log.info("报警已关联图片，无需匹配, alarmKey:{}, alarmId:{}", alarmKey, alarmId);
                return;
            }

            // 从右边取出最新的一张图片
            HashMap<String, Object> picInfo = (HashMap<String, Object>) redisTemplate.opsForList().rightPop(picKey);
            if (picInfo == null) {
                // 没有图片了，把报警ID放回去
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                return;
            }

            String fileUrl = (String) picInfo.get("fileUrl");
            if (fileUrl == null || fileUrl.isEmpty()) {
                // 图片URL为空，把报警ID和图片信息都放回去
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                redisTemplate.opsForList().rightPush(picKey, picInfo);
                return;
            }

            // 更新报警图片
            boolean success = updateAlarmWithImage(alarmId, fileUrl);
            if (!success) {
                // 更新失败，把报警ID和图片信息都放回去
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                redisTemplate.opsForList().rightPush(picKey, picInfo);
                log.warn("更新报警图片失败，已放回, alarmId:{}, fileUrl:{}", alarmId, fileUrl);
            } else {
                log.info("报警图片匹配成功, alarmId:{}, fileUrl:{}", alarmId, fileUrl);
            }
        } catch (Exception e) {
            log.error("匹配异常, alarmKey:{}, picKey:{}", alarmKey, picKey, e);
        }
    }

    /**
     * 检查报警是否已经有图片
     */
    private boolean checkAlarmHasPicture(Long alarmId) {
        try {
            R<QsDeviceAlarm> result = remoteQsDeviceAlarmService.getInfo(alarmId, SecurityConstants.INNER);
            if (R.isSuccess(result) && result.getData() != null) {
                QsDeviceAlarm alarm = result.getData();
                String imageUrl = alarm.getImageUrl();
                return imageUrl != null && !imageUrl.isEmpty();
            }
        } catch (Exception e) {
            log.error("检查报警图片异常, alarmId:{}", alarmId, e);
        }
        return false;
    }

    /**
     * 更新报警记录的图片URL
     */
    private boolean updateAlarmWithImage(Long alarmId, String fileUrl) {
        try {
            QsDeviceAlarm alarm = new QsDeviceAlarm();
            alarm.setId(alarmId);
            alarm.setImageUrl(fileUrl);
            R<Boolean> result = remoteQsDeviceAlarmService.edit(alarm, SecurityConstants.INNER);
            if (result.getCode() == 200 && result.getData()) {
                log.info("报警图片URL更新成功！alarmId: {}, imageUrl: {}", alarmId, fileUrl);
                return true;
            } else {
                log.error("报警图片URL更新失败: {}", result.getMsg());
                return false;
            }
        } catch (Exception e) {
            log.error("更新报警图片URL时出错", e);
            return false;
        }
    }
}
