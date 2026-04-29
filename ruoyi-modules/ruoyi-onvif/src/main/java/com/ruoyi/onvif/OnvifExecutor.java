package com.ruoyi.onvif;

import com.ruoyi.onvif.listeners.OnvifResponseListener;
import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.OnvifServices;
import com.ruoyi.onvif.parsers.GetDeviceInformationParser;
import com.ruoyi.onvif.parsers.GetMediaProfilesParser;
import com.ruoyi.onvif.parsers.GetMediaStreamParser;
import com.ruoyi.onvif.parsers.GetServicesParser;
import com.ruoyi.onvif.requests.*;
import com.ruoyi.onvif.responses.OnvifResponse;
import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifExecutor {

    //Constants
    public static final String TAG = OnvifExecutor.class.getSimpleName();

    //Attributes
    private final OkHttpClient client;
    private final MediaType reqBodyType;

    private volatile OnvifResponseListener onvifResponseListener;
    private final AtomicBoolean destroyed = new AtomicBoolean(false);
    private final Credentials credentials;

    //Constructors

    OnvifExecutor(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        credentials = new Credentials("", "");
        DigestAuthenticator authenticator = new DigestAuthenticator(credentials);
        Map<String, CachingAuthenticator> authCache = new ConcurrentHashMap<>();

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .authenticator(new CachingAuthenticatorDecorator(authenticator, authCache))
                .addInterceptor(new AuthenticationCacheInterceptor(authCache))
                .build();

        reqBodyType = MediaType.parse("application/soap+xml; charset=utf-8;");
    }

    //Methods

    /**
     * Sends a request to the Onvif-compatible device.
     *
     * @param device
     * @param request
     */
    void sendRequest(OnvifDevice device, OnvifRequest request) {
        if (destroyed.get()) {
            return;
        }
        
        if (device == null || request == null) {
            return;
        }
        
        // 注意：这里有线程安全隐患，如果同一个 OnvifExecutor 被并发用于多个设备，
        // credentials 会被覆盖。但从 OnvifServiceImpl 的使用方式看，每次都创建新的 OnvifManager，
        // 所以这个问题在当前代码中不会发生。
        credentials.setUserName(device.getUsername());
        credentials.setPassword(device.getPassword());
        RequestBody reqBody = RequestBody.create(reqBodyType, OnvifXMLBuilder.getSoapHeader() + request.getXml() + OnvifXMLBuilder.getEnvelopeEnd());
        performXmlRequest(device, request, buildOnvifRequest(device, request, reqBody));
    }

    /**
     * Clears up the resources.
     */
    void clear() {
        if (destroyed.compareAndSet(false, true)) {
            onvifResponseListener = null;
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
        }
    }

    //Properties

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    private void performXmlRequest(OnvifDevice device, OnvifRequest request, Request xmlRequest) {
        if (xmlRequest == null || destroyed.get()) {
            return;
        }

        client.newCall(xmlRequest)
                .enqueue(new Callback() {

                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        if (!destroyed.get() && onvifResponseListener != null) {
                            onvifResponseListener.onError(device, -1, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response xmlResponse) {
                        if (destroyed.get()) {
                            return;
                        }
                        
                        try (ResponseBody xmlBody = xmlResponse.body()) {
                            OnvifResponse response = new OnvifResponse(request);

                            if (xmlResponse.code() == 200 && xmlBody != null) {
                                response.setSuccess(true);
                                response.setXml(xmlBody.string());
                                parseResponse(device, response);
                                return;
                            }

                            String errorMessage = "";
                            if (xmlBody != null) {
                                errorMessage = xmlBody.string();
                            }
                            
                            if (onvifResponseListener != null) {
                                onvifResponseListener.onError(device, xmlResponse.code(), errorMessage);
                            }
                        } catch (IOException e) {
                            if (onvifResponseListener != null) {
                                onvifResponseListener.onError(device, -1, e.getMessage());
                            }
                        }
                    }

                });
    }

    private void parseResponse(OnvifDevice device, OnvifResponse response) {
        if (destroyed.get() || response == null || response.request() == null) {
            return;
        }
        
        switch (response.request().getType()) {
            case GET_SERVICES:
                OnvifServices path = new GetServicesParser().parse(response);
                device.setPath(path);
                ((GetServicesRequest) response.request()).getListener().onServicesReceived(device, path);
                break;
            case GET_DEVICE_INFORMATION:
                ((GetDeviceInformationRequest) response.request()).getListener().onDeviceInformationReceived(device,
                        new GetDeviceInformationParser().parse(response));
                break;
            case GET_MEDIA_PROFILES:
                ((GetMediaProfilesRequest) response.request()).getListener().onMediaProfilesReceived(device,
                        new GetMediaProfilesParser().parse(response));
                break;
            case GET_STREAM_URI:
                GetMediaStreamRequest streamRequest = (GetMediaStreamRequest) response.request();
                streamRequest.getListener().onMediaStreamURIReceived(device, streamRequest.getMediaProfile(),
                        new GetMediaStreamParser().parse(response));
                break;
            default:
                if (onvifResponseListener != null) {
                    onvifResponseListener.onResponse(device, response);
                }
                break;
        }
    }

    private Request buildOnvifRequest(OnvifDevice device, OnvifRequest request, RequestBody reqBody) {
        return new Request.Builder()
                .url(getUrlForRequest(device, request))
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .post(reqBody)
                .build();
    }

    private String getUrlForRequest(OnvifDevice device, OnvifRequest request) {
        return device.getHostName() + getPathForRequest(device, request);
    }

    private String getPathForRequest(OnvifDevice device, OnvifRequest request) {
        if (device.getPath() == null) {
            return "";
        }
        
        switch (request.getType()) {
            case GET_SERVICES:
                return device.getPath().getServicesPath();
            case GET_DEVICE_INFORMATION:
                return device.getPath().getDeviceInformationPath();
            case GET_MEDIA_PROFILES:
                return device.getPath().getProfilesPath();
            case GET_STREAM_URI:
                return device.getPath().getStreamURIPath();
            case CONTINUOUS_MOVE:
            case STOP:
            case GET_PRESETS:
            case SET_PRESET:
            case GOTO_PRESET:
            case REMOVE_PRESET:
                return device.getPath().getPTZPath() != null ? device.getPath().getPTZPath() : device.getPath().getServicesPath();
            default:
                return device.getPath().getServicesPath();
        }
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            if (copy.body() != null) {
                copy.body().writeTo(buffer);
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            return "";
        }
    }

}
