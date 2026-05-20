package com.ruoyi.dahua.task;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.dahua.config.DahuaConfig;
import com.ruoyi.dahua.service.impl.DaHuaServiceImpl;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Slf4j
@Component
public class AlarmPictureMatchTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RemoteQsDeviceAlarmService remoteQsDeviceAlarmService;

    @Autowired
    private DahuaConfig dahuaConfig;

    @Scheduled(fixedRate = 500)
    public void matchAlarmPicture() {
        try {
            if (!dahuaConfig.isEnableAlarmListen()) {
                return;
            }

            Set<String> alarmKeys = redisTemplate.keys("dahua_alarm_pic_list:*");
            if (alarmKeys == null || alarmKeys.isEmpty()) {
                return;
            }

            for (String alarmKey : alarmKeys) {
                processKey(alarmKey);
            }
        } catch (Exception e) {
            log.error("大华报警图片匹配定时任务异常", e);
        }
    }

    private void processKey(String alarmKey) {
        try {
            String deviceCode = alarmKey.replace("dahua_alarm_pic_list:", "");
            boolean matched = false;

            matched = tryMatchByDevice(alarmKey, deviceCode);

            if (!matched) {
                tryMatchWithGlobalQueue(alarmKey);
            }
        } catch (Exception e) {
            log.error("处理大华Redis key异常, key:{}", alarmKey, e);
        }
    }

    private boolean tryMatchByDevice(String alarmKey, String deviceCode) {
        try {
            String picKey = "dahua_alarm_pic_queue:" + deviceCode;
            Long picSize = redisTemplate.opsForList().size(picKey);
            if (picSize == null || picSize == 0) {
                return false;
            }

            return matchOne(alarmKey, picKey);
        } catch (Exception e) {
            log.error("按设备匹配异常, deviceCode:{}", deviceCode, e);
            return false;
        }
    }

    private boolean tryMatchWithGlobalQueue(String alarmKey) {
        try {
            String picKey = "dahua_alarm_pic_queue:global";
            Long picSize = redisTemplate.opsForList().size(picKey);
            if (picSize == null || picSize == 0) {
                return false;
            }

            return matchOne(alarmKey, picKey);
        } catch (Exception e) {
            log.error("全局队列匹配异常", e);
            return false;
        }
    }

    private boolean matchOne(String alarmKey, String picKey) {
        try {
            Long alarmId = (Long) redisTemplate.opsForList().rightPop(alarmKey);
            if (alarmId == null) {
                return false;
            }

            boolean hasPicture = checkAlarmHasPicture(alarmId);
            if (hasPicture) {
                log.info("大华报警已关联图片，无需匹配, key:{}, alarmId:{}", alarmKey, alarmId);
                return true;
            }

            HashMap<String, Object> picInfo = (HashMap<String, Object>) redisTemplate.opsForList().rightPop(picKey);
            if (picInfo == null) {
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                return false;
            }

            String fileUrl = (String) picInfo.get("fileUrl");
            if (fileUrl == null || fileUrl.isEmpty()) {
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                redisTemplate.opsForList().rightPush(picKey, picInfo);
                return false;
            }

            boolean success = updateAlarmWithImage(alarmId, fileUrl);
            if (!success) {
                redisTemplate.opsForList().rightPush(alarmKey, alarmId);
                redisTemplate.opsForList().rightPush(picKey, picInfo);
                log.warn("更新大华报警图片失败，已放回, alarmId:{}, fileUrl:{}", alarmId, fileUrl);
                return false;
            } else {
                log.info("大华报警图片匹配成功, alarmId:{}, fileUrl:{}", alarmId, fileUrl);
                return true;
            }
        } catch (Exception e) {
            log.error("匹配异常, alarmKey:{}, picKey:{}", alarmKey, picKey, e);
            return false;
        }
    }

    private boolean checkAlarmHasPicture(Long alarmId) {
        try {
            R<QsDeviceAlarm> result = remoteQsDeviceAlarmService.getInfo(alarmId,
                    com.ruoyi.common.core.constant.SecurityConstants.INNER);
            if (R.isSuccess(result) && result.getData() != null) {
                QsDeviceAlarm alarm = result.getData();
                String imageUrl = alarm.getImageUrl();
                return imageUrl != null && !imageUrl.isEmpty();
            }
        } catch (Exception e) {
            log.error("检查大华报警图片异常, alarmId:{}", alarmId, e);
        }
        return false;
    }

    private boolean updateAlarmWithImage(Long alarmId, String fileUrl) {
        try {
            QsDeviceAlarm alarm = new QsDeviceAlarm();
            alarm.setId(alarmId);
            alarm.setImageUrl(fileUrl);
            R<Boolean> result = remoteQsDeviceAlarmService.edit(alarm,
                    com.ruoyi.common.core.constant.SecurityConstants.INNER);
            if (result.getCode() == 200 && result.getData()) {
                log.info("大华报警图片URL更新成功！alarmId: {}, imageUrl: {}", alarmId, fileUrl);
                return true;
            } else {
                log.error("大华报警图片URL更新失败: {}", result.getMsg());
                return false;
            }
        } catch (Exception e) {
            log.error("更新大华报警图片URL时出错", e);
            return false;
        }
    }
}
