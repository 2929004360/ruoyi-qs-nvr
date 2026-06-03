package com.ruoyi.onvif.models;

import java.util.ArrayList;
import java.util.List;

/**
 * ONVIF 存储信息
 */
public class StorageInfo {
    
    private List<StorageConfiguration> configurations = new ArrayList<>();
    private List<StorageCapability> capabilities = new ArrayList<>();
    private List<StorageState> states = new ArrayList<>();
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

    public List<StorageConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<StorageConfiguration> configurations) {
        this.configurations = configurations;
    }

    public List<StorageCapability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<StorageCapability> capabilities) {
        this.capabilities = capabilities;
    }

    public List<StorageState> getStates() {
        return states;
    }

    public void setStates(List<StorageState> states) {
        this.states = states;
    }

    /**
     * 存储配置
     */
    public static class StorageConfiguration {
        private String token;
        private String type;
        private String name;
        private String storageUri;
        private Boolean enabled;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStorageUri() {
            return storageUri;
        }

        public void setStorageUri(String storageUri) {
            this.storageUri = storageUri;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "StorageConfiguration{" +
                    "token='" + token + '\'' +
                    ", type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", storageUri='" + storageUri + '\'' +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 存储能力
     */
    public static class StorageCapability {
        private String token;
        private String type;
        private Boolean recording;
        private Boolean search;
        private Boolean replay;
        private Boolean export;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getRecording() {
            return recording;
        }

        public void setRecording(Boolean recording) {
            this.recording = recording;
        }

        public Boolean getSearch() {
            return search;
        }

        public void setSearch(Boolean search) {
            this.search = search;
        }

        public Boolean getReplay() {
            return replay;
        }

        public void setReplay(Boolean replay) {
            this.replay = replay;
        }

        public Boolean getExport() {
            return export;
        }

        public void setExport(Boolean export) {
            this.export = export;
        }

        @Override
        public String toString() {
            return "StorageCapability{" +
                    "token='" + token + '\'' +
                    ", type='" + type + '\'' +
                    ", recording=" + recording +
                    ", search=" + search +
                    ", replay=" + replay +
                    ", export=" + export +
                    '}';
        }
    }

    /**
     * 存储状态
     */
    public static class StorageState {
        private String token;
        private String state;
        private Long totalCapacity;
        private Long freeCapacity;
        private Long usedCapacity;
        private String lastUpdated;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Long getTotalCapacity() {
            return totalCapacity;
        }

        public void setTotalCapacity(Long totalCapacity) {
            this.totalCapacity = totalCapacity;
        }

        public Long getFreeCapacity() {
            return freeCapacity;
        }

        public void setFreeCapacity(Long freeCapacity) {
            this.freeCapacity = freeCapacity;
        }

        public Long getUsedCapacity() {
            return usedCapacity;
        }

        public void setUsedCapacity(Long usedCapacity) {
            this.usedCapacity = usedCapacity;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        @Override
        public String toString() {
            return "StorageState{" +
                    "token='" + token + '\'' +
                    ", state='" + state + '\'' +
                    ", totalCapacity=" + totalCapacity +
                    ", freeCapacity=" + freeCapacity +
                    ", usedCapacity=" + usedCapacity +
                    ", lastUpdated='" + lastUpdated + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StorageInfo{" +
                "configurations=" + configurations +
                ", capabilities=" + capabilities +
                ", states=" + states +
                '}';
    }
}
