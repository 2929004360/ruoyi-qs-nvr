package com.ruoyi.haikang.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.haikang.api.factory.RemoteHaiKangFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 海康sdk 服务
 *
 * @FileName RemoteHaiKangService
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@FeignClient(contextId = "remoteHaiKangService", value = ServiceNameConstants.HAIKANG_SERVICE, fallbackFactory = RemoteHaiKangFallbackFactory.class)
public interface RemoteHaiKangService {

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param loginDevice 海康设备登录信息
     * @param source 请求来源
     * @return 登录成功返回用户ID，失败返回-1
     */
    @PostMapping(value = "/api/haikang/loginDevice")
    public R<Integer> loginDevice(@RequestBody LoginDevice loginDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 设备注销
     *
     * @param ip 设备ip
     * @param source 请求来源
     */
    @PostMapping(value = "/api/haikang/logoutDevice/{ip}")
    public R<Void> logoutDevice(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/getUserId/{ip}")
    public R<Integer> getUserId(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
