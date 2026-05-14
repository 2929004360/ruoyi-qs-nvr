package com.ruoyi.gb28181.transmit.cmd;

import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.utils.GitUtil;
import com.ruoyi.gb28181.api.utils.SipUtils;
import com.ruoyi.gb28181.config.SipConfig;
import com.ruoyi.gb28181.runner.SipLayer;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import com.ruoyi.gb28181.utils.IpPortUtil;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.header.*;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * 平台级联SIP请求头提供者
 *
 * @author ruoyi
 */
@Component
public class PlatformSIPRequestHeaderProvider {

    @Autowired
    private SipConfig sipConfig;

    @Autowired
    private SipLayer sipLayer;

    @Autowired
    private GitUtil gitUtil;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    /**
     * 创建REGISTER请求（注册到上级平台）
     */
    public Request createRegisterRequest(Gb28181Platform platform, String viaTag, String fromTag, String toTag, CallIdHeader callIdHeader, Long cseq, AuthorizationHeader authHeader) throws ParseException, InvalidArgumentException, PeerUnavailableException {
        Request request = null;
        // sipuri
        SipURI requestURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getServerGbId(), platform.getServerIp() + ":" + platform.getServerPort());
        // via
        ArrayList<ViaHeader> viaHeaders = new ArrayList<>();
        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        ViaHeader viaHeader = SipFactory.getInstance().createHeaderFactory().createViaHeader(localIp, sipConfig.getPort(), platform.getTransport(), viaTag);
        viaHeader.setRPort();
        viaHeaders.add(viaHeader);

        // from - 本地设备作为发送方
        SipURI fromSipURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getDeviceGbId(), platform.getServerGbDomain());
        Address fromAddress = SipFactory.getInstance().createAddressFactory().createAddress(fromSipURI);
        FromHeader fromHeader = SipFactory.getInstance().createHeaderFactory().createFromHeader(fromAddress, fromTag);

        // to - 上级平台作为接收方
        SipURI toSipURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getServerGbId(), platform.getServerGbDomain());
        Address toAddress = SipFactory.getInstance().createAddressFactory().createAddress(toSipURI);
        ToHeader toHeader = SipFactory.getInstance().createHeaderFactory().createToHeader(toAddress, toTag);

        // Forwards
        MaxForwardsHeader maxForwards = SipFactory.getInstance().createHeaderFactory().createMaxForwardsHeader(70);

        // ceq
        CSeqHeader cSeqHeader = SipFactory.getInstance().createHeaderFactory().createCSeqHeader(cseq != null ? cseq : redisCatchStorage.getCSEQ(), Request.REGISTER);

        request = SipFactory.getInstance().createMessageFactory().createRequest(requestURI, Request.REGISTER, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaders, maxForwards);

        request.addHeader(SipUtils.createUserAgentHeader(gitUtil));

        // Contact
        String contactIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        String contactPort = ObjectUtils.isEmpty(platform.getDevicePort()) ? String.valueOf(sipConfig.getPort()) : platform.getDevicePort();
        Address concatAddress = SipFactory.getInstance().createAddressFactory().createAddress(SipFactory.getInstance().createAddressFactory().createSipURI(platform.getDeviceGbId(), IpPortUtil.concatenateIpAndPort(contactIp, contactPort)));
        request.addHeader(SipFactory.getInstance().createHeaderFactory().createContactHeader(concatAddress));

        // Expires
        ExpiresHeader expiresHeader = SipFactory.getInstance().createHeaderFactory().createExpiresHeader(Integer.parseInt(platform.getExpires()));
        request.addHeader(expiresHeader);

        // Authorization
        if (authHeader != null) {
            request.addHeader(authHeader);
        }

        return request;
    }

    /**
     * 创建REGISTER请求（不带认证）
     */
    public Request createRegisterRequest(Gb28181Platform platform, String viaTag, String fromTag, String toTag, CallIdHeader callIdHeader, Long cseq) throws ParseException, InvalidArgumentException, PeerUnavailableException {
        return createRegisterRequest(platform, viaTag, fromTag, toTag, callIdHeader, cseq, null);
    }

    /**
     * 创建MESSAGE请求（发送设备信息、目录等）
     */
    public Request createMessageRequest(Gb28181Platform platform, String content, String viaTag, String fromTag, String toTag, CallIdHeader callIdHeader) throws ParseException, InvalidArgumentException, PeerUnavailableException {
        Request request = null;
        // sipuri
        SipURI requestURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getServerGbId(), platform.getServerIp() + ":" + platform.getServerPort());
        // via
        ArrayList<ViaHeader> viaHeaders = new ArrayList<>();
        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        ViaHeader viaHeader = SipFactory.getInstance().createHeaderFactory().createViaHeader(localIp, sipConfig.getPort(), platform.getTransport(), viaTag);
        viaHeader.setRPort();
        viaHeaders.add(viaHeader);

        // from - 本地设备
        SipURI fromSipURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getDeviceGbId(), platform.getServerGbDomain());
        Address fromAddress = SipFactory.getInstance().createAddressFactory().createAddress(fromSipURI);
        FromHeader fromHeader = SipFactory.getInstance().createHeaderFactory().createFromHeader(fromAddress, fromTag);

        // to - 上级平台
        SipURI toSipURI = SipFactory.getInstance().createAddressFactory().createSipURI(platform.getServerGbId(), platform.getServerGbDomain());
        Address toAddress = SipFactory.getInstance().createAddressFactory().createAddress(toSipURI);
        ToHeader toHeader = SipFactory.getInstance().createHeaderFactory().createToHeader(toAddress, toTag);

        // Forwards
        MaxForwardsHeader maxForwards = SipFactory.getInstance().createHeaderFactory().createMaxForwardsHeader(70);

        // ceq
        CSeqHeader cSeqHeader = SipFactory.getInstance().createHeaderFactory().createCSeqHeader(redisCatchStorage.getCSEQ(), Request.MESSAGE);

        request = SipFactory.getInstance().createMessageFactory().createRequest(requestURI, Request.MESSAGE, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaders, maxForwards);

        request.addHeader(SipUtils.createUserAgentHeader(gitUtil));

        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        ContentTypeHeader contentTypeHeader = SipFactory.getInstance().createHeaderFactory().createContentTypeHeader("Application", "MANSCDP+xml");
        contentTypeHeader.setParameter("charset", charset);
        request.setContent(content, contentTypeHeader);
        return request;
    }

    /**
     * 创建MESSAGE请求（发送心跳）
     */
    public Request createKeepAliveRequest(Gb28181Platform platform, String viaTag, String fromTag, String toTag, CallIdHeader callIdHeader) throws ParseException, InvalidArgumentException, PeerUnavailableException {
        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        StringBuffer content = new StringBuffer();
        content.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        content.append("<Notify>\r\n");
        content.append("<CmdType>Keepalive</CmdType>\r\n");
        content.append("<SN>" + (int) ((Math.random() * 9 + 1) * 100000) + "</SN>\r\n");
        content.append("<DeviceID>" + platform.getDeviceGbId() + "</DeviceID>\r\n");
        content.append("</Notify>\r\n");

        return createMessageRequest(platform, content.toString(), viaTag, fromTag, toTag, callIdHeader);
    }

    /**
     * 创建ACK请求
     */
    public Request createAckRequest(String localIp, SipURI sipURI, SIPResponse sipResponse) throws ParseException, InvalidArgumentException, PeerUnavailableException {
        // via
        ArrayList<ViaHeader> viaHeaders = new ArrayList<ViaHeader>();
        ViaHeader viaHeader = SipFactory.getInstance().createHeaderFactory().createViaHeader(localIp, sipConfig.getPort(), sipResponse.getTopmostViaHeader().getTransport(), SipUtils.getNewViaTag());
        viaHeaders.add(viaHeader);

        //Forwards
        MaxForwardsHeader maxForwards = SipFactory.getInstance().createHeaderFactory().createMaxForwardsHeader(70);

        //ceq
        CSeqHeader cSeqHeader = SipFactory.getInstance().createHeaderFactory().createCSeqHeader(sipResponse.getCSeqHeader().getSeqNumber(), Request.ACK);

        Request request = SipFactory.getInstance().createMessageFactory().createRequest(sipURI, Request.ACK, sipResponse.getCallIdHeader(), cSeqHeader, sipResponse.getFromHeader(), sipResponse.getToHeader(), viaHeaders, maxForwards);

        request.addHeader(SipUtils.createUserAgentHeader(gitUtil));

        return request;
    }
}
