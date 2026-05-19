package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
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
        if (map == null) {
            log.warn("未找到对应的抓图任务，handleKey: {}", handleKey);
            return true;
        }
        log.info("获取到抓图任务信息: {}", map);

        Long deviceId = (Long) map.get("deviceId");
        Integer channelId = (Integer) map.get("channelId");
        String snapshotType = (String) map.get("snapshotType");

        // 获取设备信息
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败，deviceId: {}, code: {}, msg: {}", deviceId, r.getCode(), r.getMsg());
            return false;
        }
        QsDevice device = r.getData();
        log.info("获取设备信息成功，deviceId: {}, deviceName: {}", deviceId, device.getDeviceName());

        // 准备文件路径
        String snapshotDir = filePath + File.separator + "haikang_isup_snapshot";
        String fileName = extractFilename(deviceId, channelId);
        String absPath = getAbsoluteFile(snapshotDir, fileName).getAbsolutePath();
        log.info("准备保存图片，路径: {}", absPath);

        // 保存图片数据
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
        } else {
            log.warn("图片数据为空，dwFileLen: {}, pFileBuf: {}", dwFileLen, pFileBuf);
        }

        // 返回文件路径给SDK
        byte[] pathBytes = absPath.getBytes();
        int writeLen = Math.min(pathBytes.length, 255);
        pFilePath.write(0, pathBytes, 0, writeLen);
        log.info("已将文件路径返回给SDK，路径: {}", absPath);

        // 构造访问URL
        File savedFile = new File(absPath);
        String fileUrl = fileDomain + filePrefix + "/haikang_isup_snapshot/" + fileName.replace("/", "");
        log.info("图片访问URL: {}", fileUrl);

        // 保存到数据库
        QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
        snapshot.setDeviceId(device.getId());
        snapshot.setDeviceCode(device.getDeviceCode());
        snapshot.setDeviceName(device.getDeviceName());
        snapshot.setFileUrl(fileUrl);
        snapshot.setFilePath(absPath);
        snapshot.setFileSize(savedFile.length());
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
}
