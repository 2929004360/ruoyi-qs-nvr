package com.ruoyi.qs.domain;

public class Preset {
    private Integer index;
    private String name;

    public Preset() {
    }

    public Preset(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}