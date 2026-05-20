package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.haikang.isup.service.haikang.ss.HCISUPSS;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class PSS_Storage_Callback implements HCISUPSS.EHomeSSStorageCallBack {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteQsDeviceSnapshotService remoteQsDeviceSnapshotService;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.prefix}")
    private String filePrefix;

    @Override
    public boolean invoke(int iHandle, String pFileName, Pointer pFileBuf, int dwFileLen, Pointer pFilePath, Pointer pUser) throws IOException {
        log.info("========== 开始处理图片上传回调 ==========");
        log.info("iHandle: {}, pFileName: {}, dwFileLen: {}", iHandle, pFileName, dwFileLen);
        
        String handleKey = "IsupApiPicByCloud";
        
        // 获取任务信息
        HashMap<String, Object> map = (HashMap<String, Object>) redisTemplate.opsForValue().get(handleKey);
        
        Long deviceId = null;
        Integer channelId = null;
        String snapshotType = null;
        boolean isManualSnapshot = false;
        
        if (map != null) {
            isManualSnapshot = true;
            deviceId = (Long) map.get("deviceId");
            channelId = (Integer) map.get("channelId");
            snapshotType = (String) map.get("snapshotType");
            log.info("获取到手动抓图任务信息: {}", map);
        } else {
            log.warn("未找到手动抓图任务，尝试识别报警图片...");
            // 这可能是报警图片，尝试从文件名中提取设备信息
            // 海康报警图片格式通常包含设备ID
            if (pFileName != null && !pFileName.isEmpty()) {
                // 尝试解析文件名，例如: EHome_XXXXX_1_0.jpg
                log.info("报警图片文件名: {}", pFileName);
            }
        }
        
        QsDevice device = null;
        if (isManualSnapshot && deviceId != null) {
            // 获取设备信息
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败，deviceId: {}, code: {}, msg: {}", deviceId, r.getCode(), r.getMsg());
                return false;
            }
            device = r.getData();
            log.info("获取设备信息成功，deviceId: {}, deviceName: {}", deviceId, device.getDeviceName());
        } else {
            // 报警图片情况，目前先保存图片，后续可以根据文件名匹配设备
            log.info("报警图片，暂无法精确匹配设备，先保存图片");
        }
        
        // 准备文件路径
        String snapshotDir = filePath + File.separator + "haikang_isup_snapshot";
        String fileName = extractFilename(deviceId != null ? deviceId : 0L, channelId != null ? channelId : 0);
        String absPath = getAbsoluteFile(snapshotDir, fileName).getAbsolutePath();
        log.info("准备保存图片，路径: {}", absPath);
        
        // 保存图片数据
        String fileUrl = null;
        if (dwFileLen > 0 && pFileBuf != null) {
            try (FileOutputStream fos = new FileOutputStream(absPath)) {
                ByteBuffer buffers = pFileBuf.getByteBuffer(0, dwFileLen);
                byte[] bytes = new byte[dwFileLen];
                buffers.rewind();
                buffers.get(bytes);
                fos.write(bytes);
                log.info("图片保存成功！路径: {}，大小: {} bytes", absPath, bytes.length);
            } catch (IOException e) {
                log.error("保存文件失败，iHandle: {}", iHandle, e);
                return false;
            }
            
            // 构造访问URL
            fileUrl = fileDomain + filePrefix + "/haikang_isup_snapshot/" + fileName.replace("/", "");
            log.info("图片访问URL: {}", fileUrl);
        } else {
            log.warn("图片数据为空，dwFileLen: {}, pFileBuf: {}", dwFileLen, pFileBuf);
        }
        
        // 返回文件路径给SDK
        byte[] pathBytes = absPath.getBytes();
        int writeLen = Math.min(pathBytes.length, 255);
        pFilePath.write(0, pathBytes, 0, writeLen);
        log.info("已将文件路径返回给SDK，路径: {}", absPath);
        
        // 如果是手动抓图，保存到抓图表
        if (isManualSnapshot && device != null && fileUrl != null) {
            // 保存到数据库
            QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
            snapshot.setDeviceId(device.getId());
            snapshot.setDeviceCode(device.getDeviceCode());
            snapshot.setDeviceName(device.getDeviceName());
            snapshot.setFileUrl(fileUrl);
            snapshot.setFilePath(absPath);
            snapshot.setFileSize(new File(absPath).length());
            snapshot.setFileName(fileName);
            snapshot.setFileType("jpg");
            snapshot.setSnapshotType(snapshotType);
            snapshot.setSdkType("hik_isup");
            snapshot.setChannel(channelId);
            snapshot.setCaptureTime(new Date());
            
            R<Long> result = remoteQsDeviceSnapshotService.add(snapshot, SecurityConstants.INNER);
            if (R.isSuccess(result)) {
                log.info("抓图记录保存成功，snapshotId: {}", result.getData());
            } else {
                log.error("保存抓图记录失败: {}", result.getMsg());
            }
            
            // 清理任务
            redisTemplate.delete(handleKey);
        } else if (!isManualSnapshot && fileUrl != null) {
            // 报警图片：直接存入Redis，由定时任务负责匹配
            log.info("报警图片，存入Redis等待定时任务匹配...");
            
            // 尝试从文件名提取设备ID信息
            String extractedDeviceId = extractDeviceIdFromFileName(pFileName);
            log.info("从文件名提取的设备ID: {}, 完整文件名: {}", extractedDeviceId, pFileName);
            
            // 将图片信息存入Redis
            savePictureToRedis(extractedDeviceId, fileUrl);
        }
        
        log.info("========== 图片上传回调处理完成 ==========");
        return true;
    }

    public static final String extractFilename(Long deviceId, int channelId) {
        String timeStr = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return StringUtils.format("haikang_{}_{}_{}.jpg", deviceId, channelId, timeStr);
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    /**
     * 尝试从文件名中提取设备ID
     */
    private String extractDeviceIdFromFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            log.warn("文件名为空，无法提取设备ID");
            return null;
        }
        
        log.info("尝试从文件名提取设备ID，完整文件名: [{}]", fileName);
        
        // 常见的海康报警图片格式:
        // EHome_XXXX_1_0.jpg
        // 或其他包含设备ID的格式
        
        // 先尝试简单的方式：用下划线分割
        String[] parts = fileName.split("_");
        
        // 尝试从分割后的部分中找
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            // 查找看起来像设备ID的部分（字母数字组合，至少4个字符）
            if (part.matches("[A-Za-z0-9]{4,}")) {
                log.info("找到可能的设备ID: {}", part);
                return part;
            }
        }
        
        // 如果没找到，尝试用点号分割取前面的部分
        if (fileName.contains(".")) {
            String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));
            
            // 再用下划线分割
            parts = nameWithoutExt.split("_");
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.matches("[A-Za-z0-9]{4,}")) {
                    log.info("找到可能的设备ID: {}", part);
                    return part;
                }
            }
        }
        
        log.warn("未能从文件名中提取到设备ID");
        return null;
    }

    /**
     * 将图片信息存入Redis
     */
    private void savePictureToRedis(String deviceId, String fileUrl) {
        try {
            // 创建一个包含图片信息的Map
            HashMap<String, Object> picInfo = new HashMap<>();
            picInfo.put("fileUrl", fileUrl);
            picInfo.put("deviceId", deviceId);
            picInfo.put("createTime", new Date().getTime());

            // 存入全局图片队列
            String picQueueKey = "isup_alarm_pic_queue:global";
            redisTemplate.opsForList().leftPush(picQueueKey, picInfo);
            redisTemplate.opsForList().trim(picQueueKey, 0, 99);
            redisTemplate.expire(picQueueKey, 30, java.util.concurrent.TimeUnit.MINUTES);
            log.info("图片信息已存入全局队列, key:{}, fileUrl:{}", picQueueKey, fileUrl);

            // 如果有设备ID，也存入设备特定队列
            if (deviceId != null && !deviceId.isEmpty()) {
                String devicePicKey = "isup_alarm_pic_queue:" + deviceId;
                redisTemplate.opsForList().leftPush(devicePicKey, picInfo);
                redisTemplate.opsForList().trim(devicePicKey, 0, 49);
                redisTemplate.expire(devicePicKey, 30, java.util.concurrent.TimeUnit.MINUTES);
                log.info("图片信息已存入设备队列, key:{}, fileUrl:{}", devicePicKey, fileUrl);
            }
        } catch (Exception e) {
            log.error("保存图片信息到Redis时出错", e);
        }
    }
}
