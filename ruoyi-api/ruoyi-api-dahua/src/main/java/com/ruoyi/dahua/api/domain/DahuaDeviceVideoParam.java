package com.ruoyi.dahua.api.domain;

import java.io.Serializable;

/**
 * 大华设备视频输入参数
 *
 * @author ruoyi
 */
public class DahuaDeviceVideoParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 亮度
     */
    private Integer brightness;

    /**
     * 对比度
     */
    private Integer contrast;

    /**
     * 饱和度
     */
    private Integer saturation;

    /**
     * 色度
     */
    private Integer chroma;

    /**
     * 锐度
     */
    private Integer sharpness;

    /**
     * 色调
     */
    private Integer hue;

    /**
     * 增益
     */
    private Integer gain;

    /**
     * 黑白模式
     * 0-彩色
     * 1-黑白
     */
    private Integer blackWhiteMode;

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getContrast() {
        return contrast;
    }

    public void setContrast(Integer contrast) {
        this.contrast = contrast;
    }

    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    public Integer getChroma() {
        return chroma;
    }

    public void setChroma(Integer chroma) {
        this.chroma = chroma;
    }

    public Integer getSharpness() {
        return sharpness;
    }

    public void setSharpness(Integer sharpness) {
        this.sharpness = sharpness;
    }

    public Integer getHue() {
        return hue;
    }

    public void setHue(Integer hue) {
        this.hue = hue;
    }

    public Integer getGain() {
        return gain;
    }

    public void setGain(Integer gain) {
        this.gain = gain;
    }

    public Integer getBlackWhiteMode() {
        return blackWhiteMode;
    }

    public void setBlackWhiteMode(Integer blackWhiteMode) {
        this.blackWhiteMode = blackWhiteMode;
    }
}
