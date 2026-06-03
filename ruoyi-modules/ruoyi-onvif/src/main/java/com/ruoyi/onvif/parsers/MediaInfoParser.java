package com.ruoyi.onvif.parsers;

import com.ruoyi.onvif.models.MediaInfo;
import com.ruoyi.onvif.responses.OnvifResponse;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * 媒体信息解析器
 */
public class MediaInfoParser extends OnvifParser<MediaInfo> {
    public static final String TAG = MediaInfoParser.class.getSimpleName();

    @Override
    public MediaInfo parse(OnvifResponse response) {
        MediaInfo mediaInfo = new MediaInfo();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();

            MediaInfo.VideoSourceConfig currentVideoSource = null;
            MediaInfo.VideoEncoderConfig currentVideoEncoder = null;
            MediaInfo.AudioSourceConfig currentAudioSource = null;
            MediaInfo.AudioEncoderConfig currentAudioEncoder = null;
            MediaInfo.VideoOutputConfig currentVideoOutput = null;

            // 首先检测我们在处理哪种响应类型
            String responseType = null;
            if (response.getXml().contains("GetVideoSourceConfigurationsResponse")) {
                responseType = "videoSource";
            } else if (response.getXml().contains("GetVideoEncoderConfigurationsResponse")) {
                responseType = "videoEncoder";
            } else if (response.getXml().contains("GetAudioSourceConfigurationsResponse")) {
                responseType = "audioSource";
            } else if (response.getXml().contains("GetAudioEncoderConfigurationsResponse")) {
                responseType = "audioEncoder";
            } else if (response.getXml().contains("GetVideoOutputConfigurationsResponse")) {
                responseType = "videoOutput";
            }

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String fullTagName = getXpp().getName();
                String tagName = (fullTagName != null && fullTagName.contains(":")) ? fullTagName.substring(fullTagName.indexOf(":") + 1) : fullTagName;

                // 检测 SOAP Fault
                if (eventType == XmlPullParser.START_TAG && "Fault".equals(tagName)) {
                    mediaInfo.setHasError(true);
                    String faultText = parseFault(getXpp());
                    mediaInfo.setErrorMessage(faultText);
                } else if (eventType == XmlPullParser.START_TAG) {
                    // 处理 Configurations 标签（根据响应类型创建相应的配置对象）
                    if ("Configurations".equals(tagName)) {
                        if ("videoSource".equals(responseType)) {
                            currentVideoSource = new MediaInfo.VideoSourceConfig();
                        } else if ("videoEncoder".equals(responseType)) {
                            currentVideoEncoder = new MediaInfo.VideoEncoderConfig();
                        } else if ("audioSource".equals(responseType)) {
                            currentAudioSource = new MediaInfo.AudioSourceConfig();
                        } else if ("audioEncoder".equals(responseType)) {
                            currentAudioEncoder = new MediaInfo.AudioEncoderConfig();
                        } else if ("videoOutput".equals(responseType)) {
                            currentVideoOutput = new MediaInfo.VideoOutputConfig();
                        }
                        
                        // 提取属性
                        if ((currentVideoSource != null || currentVideoEncoder != null || 
                             currentAudioSource != null || currentAudioEncoder != null || 
                             currentVideoOutput != null)) {
                            for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                                String attrName = getXpp().getAttributeName(i);
                                String attrValue = getXpp().getAttributeValue(i);
                                if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                    if (currentVideoSource != null) currentVideoSource.setToken(attrValue);
                                    if (currentVideoEncoder != null) currentVideoEncoder.setToken(attrValue);
                                    if (currentAudioSource != null) currentAudioSource.setToken(attrValue);
                                    if (currentAudioEncoder != null) currentAudioEncoder.setToken(attrValue);
                                    if (currentVideoOutput != null) currentVideoOutput.setToken(attrValue);
                                } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                    if (currentVideoSource != null) currentVideoSource.setName(attrValue);
                                    if (currentVideoEncoder != null) currentVideoEncoder.setName(attrValue);
                                    if (currentAudioSource != null) currentAudioSource.setName(attrValue);
                                    if (currentAudioEncoder != null) currentAudioEncoder.setName(attrValue);
                                    if (currentVideoOutput != null) currentVideoOutput.setName(attrValue);
                                }
                            }
                        }
                    }
                    // 处理 VideoSourceConfiguration 标签（备选）
                    else if ("VideoSourceConfiguration".equals(tagName)) {
                        currentVideoSource = new MediaInfo.VideoSourceConfig();
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentVideoSource.setToken(getXpp().getAttributeValue(i));
                            } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                currentVideoSource.setName(getXpp().getAttributeValue(i));
                            }
                        }
                    } 
                    // 处理 VideoEncoderConfiguration 标签（备选）
                    else if ("VideoEncoderConfiguration".equals(tagName)) {
                        currentVideoEncoder = new MediaInfo.VideoEncoderConfig();
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentVideoEncoder.setToken(getXpp().getAttributeValue(i));
                            } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                currentVideoEncoder.setName(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理 AudioSourceConfiguration 标签（备选）
                    else if ("AudioSourceConfiguration".equals(tagName)) {
                        currentAudioSource = new MediaInfo.AudioSourceConfig();
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentAudioSource.setToken(getXpp().getAttributeValue(i));
                            } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                currentAudioSource.setName(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理 AudioEncoderConfiguration 标签（备选）
                    else if ("AudioEncoderConfiguration".equals(tagName)) {
                        currentAudioEncoder = new MediaInfo.AudioEncoderConfig();
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentAudioEncoder.setToken(getXpp().getAttributeValue(i));
                            } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                currentAudioEncoder.setName(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理 VideoOutputConfiguration 标签（备选）
                    else if ("VideoOutputConfiguration".equals(tagName)) {
                        currentVideoOutput = new MediaInfo.VideoOutputConfig();
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentVideoOutput.setToken(getXpp().getAttributeValue(i));
                            } else if ("name".equals(attrName) || attrName.endsWith(":name")) {
                                currentVideoOutput.setName(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理子标签
                    else if (currentVideoSource != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoSource.setName(getXpp().getText());
                                break;
                            case "SourceToken":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoSource.setSourceToken(getXpp().getText());
                                break;
                            case "Width":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoSource.setWidth(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Height":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoSource.setHeight(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Framerate":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoSource.setFramerate(getXpp().getText());
                                break;
                            case "Type":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoSource.setType(getXpp().getText());
                                break;
                        }
                    } else if (currentVideoEncoder != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoEncoder.setName(getXpp().getText());
                                break;
                            case "SourceToken":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoEncoder.setSourceToken(getXpp().getText());
                                break;
                            case "Encoding":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoEncoder.setEncoding(getXpp().getText());
                                break;
                            case "Width":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoEncoder.setWidth(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Height":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoEncoder.setHeight(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "FrameRate":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoEncoder.setFramerate(Float.parseFloat(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Bitrate":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoEncoder.setBitrate(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Quality":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentVideoEncoder.setQuality(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "GovLength":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoEncoder.setGovLength(getXpp().getText());
                                break;
                            case "Profile":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoEncoder.setProfile(getXpp().getText());
                                break;
                        }
                    } else if (currentAudioSource != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioSource.setName(getXpp().getText());
                                break;
                            case "SourceToken":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioSource.setSourceToken(getXpp().getText());
                                break;
                            case "Type":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioSource.setType(getXpp().getText());
                                break;
                            case "Channels":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioSource.setChannels(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "SampleRate":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioSource.setSampleRate(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "BitDepth":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioSource.setBitDepth(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                        }
                    } else if (currentAudioEncoder != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioEncoder.setName(getXpp().getText());
                                break;
                            case "SourceToken":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioEncoder.setSourceToken(getXpp().getText());
                                break;
                            case "Encoding":
                                getXpp().next();
                                if (getXpp().getText() != null) currentAudioEncoder.setEncoding(getXpp().getText());
                                break;
                            case "Bitrate":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioEncoder.setBitrate(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "SampleRate":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioEncoder.setSampleRate(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Channels":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioEncoder.setChannels(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "Quality":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentAudioEncoder.setQuality(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                        }
                    } else if (currentVideoOutput != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoOutput.setName(getXpp().getText());
                                break;
                            case "Layout":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoOutput.setLayout(getXpp().getText());
                                break;
                            case "Resolution":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoOutput.setResolution(getXpp().getText());
                                break;
                            case "RefreshRate":
                                getXpp().next();
                                if (getXpp().getText() != null) currentVideoOutput.setRefreshRate(getXpp().getText());
                                break;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (("VideoSourceConfiguration".equals(tagName) || "Configurations".equals(tagName)) && currentVideoSource != null) {
                        mediaInfo.getVideoSourceConfigs().add(currentVideoSource);
                        currentVideoSource = null;
                    } else if (("VideoEncoderConfiguration".equals(tagName) || "Configurations".equals(tagName)) && currentVideoEncoder != null) {
                        mediaInfo.getVideoEncoderConfigs().add(currentVideoEncoder);
                        currentVideoEncoder = null;
                    } else if (("AudioSourceConfiguration".equals(tagName) || "Configurations".equals(tagName)) && currentAudioSource != null) {
                        mediaInfo.getAudioSourceConfigs().add(currentAudioSource);
                        currentAudioSource = null;
                    } else if (("AudioEncoderConfiguration".equals(tagName) || "Configurations".equals(tagName)) && currentAudioEncoder != null) {
                        mediaInfo.getAudioEncoderConfigs().add(currentAudioEncoder);
                        currentAudioEncoder = null;
                    } else if (("VideoOutputConfiguration".equals(tagName) || "Configurations".equals(tagName)) && currentVideoOutput != null) {
                        mediaInfo.getVideoOutputConfigs().add(currentVideoOutput);
                        currentVideoOutput = null;
                    }
                }

                eventType = getXpp().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            mediaInfo.setHasError(true);
            mediaInfo.setErrorMessage("解析响应时出错: " + e.getMessage());
        }

        return mediaInfo;
    }
    
    /**
     * 解析 SOAP Fault
     */
    private String parseFault(XmlPullParser xpp) throws XmlPullParserException, IOException {
        StringBuilder faultText = new StringBuilder();
        int depth = 1;
        while (depth > 0 && eventType != XmlPullParser.END_DOCUMENT) {
            String fullTagName = xpp.getName();
            String tagName = (fullTagName != null && fullTagName.contains(":")) ? fullTagName.substring(fullTagName.indexOf(":") + 1) : fullTagName;

            if (eventType == XmlPullParser.START_TAG) {
                if ("Text".equals(tagName) || "Reason".equals(tagName)) {
                    xpp.next();
                    String text = xpp.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        faultText.append(text.trim()).append(" ");
                    }
                } else if ("Value".equals(tagName)) {
                    xpp.next();
                    String text = xpp.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        if (text.contains("NotImplemented") || text.contains("ActionNotSupported")) {
                            return "设备不支持此功能";
                        }
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG && "Fault".equals(tagName)) {
                depth--;
            }
            eventType = xpp.next();
        }
        String result = faultText.toString().trim();
        if (result.isEmpty()) {
            return "设备返回错误";
        }
        return result;
    }
}

