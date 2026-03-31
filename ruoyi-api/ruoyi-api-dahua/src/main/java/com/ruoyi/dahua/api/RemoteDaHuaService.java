package com.ruoyi.dahua.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.LoginDevice;
import com.ruoyi.dahua.api.factory.RemoteDaHuaFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 大华sdk 服务
 *
 * @FileName RemoteDaHuaService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@FeignClient(contextId = "remoteDaHuaService", value = ServiceNameConstants.DAHUA_SERVICE, fallbackFactory = RemoteDaHuaFallbackFactory.class)
public interface RemoteDaHuaService {

    /**
     * 登录设备
     *
     * @param loginDevice 大华设备登录信息
     * @param source      请求来源
     */
    @PostMapping(value = "/api/dahua/loginDevice")
    public R<Void> loginDevice(@RequestBody LoginDevice loginDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 查询是否登录
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping(value = "/api/dahua/isUserId/{ip}")
    R<Boolean> isUserId(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 大华设备获取时间
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping(value = "/api/dahua/getTime/{ip}")
    R<String> getTime(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取大华主动上线设备
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping(value = "/api/dahua/getDahuaDevice/{ip}")
    R<DahuaDevice> getDahuaDevice(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 退出登录
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping(value = "/api/dahua/logoutDevice/{ip}")
    R<Boolean> logoutDevice(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
