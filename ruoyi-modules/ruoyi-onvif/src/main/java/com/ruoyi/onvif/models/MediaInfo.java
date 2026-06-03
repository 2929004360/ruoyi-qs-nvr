package com.ruoyi.onvif.models;

import java.util.ArrayList;
import java.util.List;

/**
 * ONVIF 媒体配置信息
 */
public class MediaInfo {

    private List<VideoSourceConfig> videoSourceConfigs = new ArrayList<>();
    private List<VideoEncoderConfig> videoEncoderConfigs = new ArrayList<>();
    private List<AudioSourceConfig> audioSourceConfigs = new ArrayList<>();
    private List<AudioEncoderConfig> audioEncoderConfigs = new ArrayList<>();
    private List<VideoOutputConfig> videoOutputConfigs = new ArrayList<>();
    private boolean hasError = false;
    private String errorMessage = "";

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<VideoSourceConfig> getVideoSourceConfigs() {
        return videoSourceConfigs;
    }

    public void setVideoSourceConfigs(List<VideoSourceConfig> videoSourceConfigs) {
        this.videoSourceConfigs = videoSourceConfigs;
    }

    public List<VideoEncoderConfig> getVideoEncoderConfigs() {
        return videoEncoderConfigs;
    }

    public void setVideoEncoderConfigs(List<VideoEncoderConfig> videoEncoderConfigs) {
        this.videoEncoderConfigs = videoEncoderConfigs;
    }

    public List<AudioSourceConfig> getAudioSourceConfigs() {
        return audioSourceConfigs;
    }

    public void setAudioSourceConfigs(List<AudioSourceConfig> audioSourceConfigs) {
        this.audioSourceConfigs = audioSourceConfigs;
    }

    public List<AudioEncoderConfig> getAudioEncoderConfigs() {
        return audioEncoderConfigs;
    }

    public void setAudioEncoderConfigs(List<AudioEncoderConfig> audioEncoderConfigs) {
        this.audioEncoderConfigs = audioEncoderConfigs;
    }

    public List<VideoOutputConfig> getVideoOutputConfigs() {
        return videoOutputConfigs;
    }

    public void setVideoOutputConfigs(List<VideoOutputConfig> videoOutputConfigs) {
        this.videoOutputConfigs = videoOutputConfigs;
    }

    /**
     * 视频源配置
     */
    public static class VideoSourceConfig {
        private String token;
        private String name;
        private String sourceToken;
        private Integer width;
        private Integer height;
        private String framerate;
        private String type;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceToken() {
            return sourceToken;
        }

        public void setSourceToken(String sourceToken) {
            this.sourceToken = sourceToken;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getFramerate() {
            return framerate;
        }

        public void setFramerate(String framerate) {
            this.framerate = framerate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "VideoSourceConfig{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", sourceToken='" + sourceToken + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", framerate='" + framerate + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    /**
     * 视频编码器配置
     */
    public static class VideoEncoderConfig {
        private String token;
        private String name;
        private String sourceToken;
        private String encoding;
        private Integer width;
        private Integer height;
        private Float framerate;
        private Integer bitrate;
        private Integer quality;
        private String govLength;
        private String profile;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceToken() {
            return sourceToken;
        }

        public void setSourceToken(String sourceToken) {
            this.sourceToken = sourceToken;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Float getFramerate() {
            return framerate;
        }

        public void setFramerate(Float framerate) {
            this.framerate = framerate;
        }

        public Integer getBitrate() {
            return bitrate;
        }

        public void setBitrate(Integer bitrate) {
            this.bitrate = bitrate;
        }

        public Integer getQuality() {
            return quality;
        }

        public void setQuality(Integer quality) {
            this.quality = quality;
        }

        public String getGovLength() {
            return govLength;
        }

        public void setGovLength(String govLength) {
            this.govLength = govLength;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        @Override
        public String toString() {
            return "VideoEncoderConfig{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", sourceToken='" + sourceToken + '\'' +
                    ", encoding='" + encoding + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", framerate=" + framerate +
                    ", bitrate=" + bitrate +
                    ", quality=" + quality +
                    ", govLength='" + govLength + '\'' +
                    ", profile='" + profile + '\'' +
                    '}';
        }
    }

    /**
     * 音频源配置
     */
    public static class AudioSourceConfig {
        private String token;
        private String name;
        private String sourceToken;
        private String type;
        private Integer channels;
        private Integer sampleRate;
        private Integer bitDepth;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceToken() {
            return sourceToken;
        }

        public void setSourceToken(String sourceToken) {
            this.sourceToken = sourceToken;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getChannels() {
            return channels;
        }

        public void setChannels(Integer channels) {
            this.channels = channels;
        }

        public Integer getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(Integer sampleRate) {
            this.sampleRate = sampleRate;
        }

        public Integer getBitDepth() {
            return bitDepth;
        }

        public void setBitDepth(Integer bitDepth) {
            this.bitDepth = bitDepth;
        }

        @Override
        public String toString() {
            return "AudioSourceConfig{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", sourceToken='" + sourceToken + '\'' +
                    ", type='" + type + '\'' +
                    ", channels=" + channels +
                    ", sampleRate=" + sampleRate +
                    ", bitDepth=" + bitDepth +
                    '}';
        }
    }

    /**
     * 音频编码器配置
     */
    public static class AudioEncoderConfig {
        private String token;
        private String name;
        private String sourceToken;
        private String encoding;
        private Integer bitrate;
        private Integer sampleRate;
        private Integer channels;
        private Integer quality;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceToken() {
            return sourceToken;
        }

        public void setSourceToken(String sourceToken) {
            this.sourceToken = sourceToken;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public Integer getBitrate() {
            return bitrate;
        }

        public void setBitrate(Integer bitrate) {
            this.bitrate = bitrate;
        }

        public Integer getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(Integer sampleRate) {
            this.sampleRate = sampleRate;
        }

        public Integer getChannels() {
            return channels;
        }

        public void setChannels(Integer channels) {
            this.channels = channels;
        }

        public Integer getQuality() {
            return quality;
        }

        public void setQuality(Integer quality) {
            this.quality = quality;
        }

        @Override
        public String toString() {
            return "AudioEncoderConfig{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", sourceToken='" + sourceToken + '\'' +
                    ", encoding='" + encoding + '\'' +
                    ", bitrate=" + bitrate +
                    ", sampleRate=" + sampleRate +
                    ", channels=" + channels +
                    ", quality=" + quality +
                    '}';
        }
    }

    /**
     * 视频输出配置
     */
    public static class VideoOutputConfig {
        private String token;
        private String name;
        private String layout;
        private String resolution;
        private String refreshRate;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public String getRefreshRate() {
            return refreshRate;
        }

        public void setRefreshRate(String refreshRate) {
            this.refreshRate = refreshRate;
        }

        @Override
        public String toString() {
            return "VideoOutputConfig{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", layout='" + layout + '\'' +
                    ", resolution='" + resolution + '\'' +
                    ", refreshRate='" + refreshRate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
                "videoSourceConfigs=" + videoSourceConfigs +
                ", videoEncoderConfigs=" + videoEncoderConfigs +
                ", audioSourceConfigs=" + audioSourceConfigs +
                ", audioEncoderConfigs=" + audioEncoderConfigs +
                ", videoOutputConfigs=" + videoOutputConfigs +
                '}';
    }
}

