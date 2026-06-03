package com.ruoyi.onvif.parsers;

import com.ruoyi.onvif.models.StorageInfo;
import com.ruoyi.onvif.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * 存储信息解析器
 */
public class StorageInfoParser extends OnvifParser<StorageInfo> {

    //Constants
    public static final String TAG = StorageInfoParser.class.getSimpleName();
    
    // StorageConfiguration keys
    private static final String KEY_STORAGE_CONFIGURATION = "StorageConfiguration";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_NAME = "Name";
    private static final String KEY_STORAGE_URI = "StorageUri";
    private static final String KEY_ENABLED = "Enabled";
    
    // StorageCapability keys
    private static final String KEY_STORAGE_CAPABILITY = "StorageCapability";
    private static final String KEY_RECORDING = "Recording";
    private static final String KEY_SEARCH = "Search";
    private static final String KEY_REPLAY = "Replay";
    private static final String KEY_EXPORT = "Export";
    
    // StorageState keys
    private static final String KEY_STORAGE_STATE = "StorageState";
    private static final String KEY_STATE = "State";
    private static final String KEY_TOTAL_CAPACITY = "TotalCapacity";
    private static final String KEY_FREE_CAPACITY = "FreeCapacity";
    private static final String KEY_USED_CAPACITY = "UsedCapacity";
    private static final String KEY_LAST_UPDATED = "LastUpdated";

    @Override
    public StorageInfo parse(OnvifResponse response) {
        StorageInfo storageInfo = new StorageInfo();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();

            StorageInfo.StorageConfiguration currentConfig = null;
            StorageInfo.StorageCapability currentCapability = null;
            StorageInfo.StorageState currentState = null;

            // 首先检查是否有 SOAP Fault
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String fullTagName = getXpp().getName();
                String tagName = (fullTagName != null && fullTagName.contains(":")) ? fullTagName.substring(fullTagName.indexOf(":") + 1) : fullTagName;

                if (eventType == XmlPullParser.START_TAG && "Fault".equals(tagName)) {
                    // 发现 SOAP Fault
                    storageInfo.setHasError(true);
                    String faultText = parseFault(getXpp());
                    storageInfo.setErrorMessage(faultText);
                } else if (eventType == XmlPullParser.START_TAG) {
                    // 解析存储配置
                    if (KEY_STORAGE_CONFIGURATION.equals(tagName)) {
                        currentConfig = new StorageInfo.StorageConfiguration();
                        // 获取token属性
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if (KEY_TOKEN.equals(attrName) || attrName.endsWith(":token")) {
                                currentConfig.setToken(getXpp().getAttributeValue(i));
                            }
                        }
                    } else if (currentConfig != null) {
                        switch (tagName) {
                            case KEY_TYPE:
                                getXpp().next();
                                currentConfig.setType(getXpp().getText());
                                break;
                            case KEY_NAME:
                                getXpp().next();
                                currentConfig.setName(getXpp().getText());
                                break;
                            case KEY_STORAGE_URI:
                                getXpp().next();
                                currentConfig.setStorageUri(getXpp().getText());
                                break;
                            case KEY_ENABLED:
                                getXpp().next();
                                currentConfig.setEnabled(Boolean.parseBoolean(getXpp().getText()));
                                break;
                        }
                    }
                    // 解析存储能力
                    else if (KEY_STORAGE_CAPABILITY.equals(tagName)) {
                        currentCapability = new StorageInfo.StorageCapability();
                        // 获取token属性
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if (KEY_TOKEN.equals(attrName) || attrName.endsWith(":token")) {
                                currentCapability.setToken(getXpp().getAttributeValue(i));
                            }
                        }
                    } else if (currentCapability != null) {
                        switch (tagName) {
                            case KEY_TYPE:
                                getXpp().next();
                                currentCapability.setType(getXpp().getText());
                                break;
                            case KEY_RECORDING:
                                getXpp().next();
                                currentCapability.setRecording(Boolean.parseBoolean(getXpp().getText()));
                                break;
                            case KEY_SEARCH:
                                getXpp().next();
                                currentCapability.setSearch(Boolean.parseBoolean(getXpp().getText()));
                                break;
                            case KEY_REPLAY:
                                getXpp().next();
                                currentCapability.setReplay(Boolean.parseBoolean(getXpp().getText()));
                                break;
                            case KEY_EXPORT:
                                getXpp().next();
                                currentCapability.setExport(Boolean.parseBoolean(getXpp().getText()));
                                break;
                        }
                    }
                    // 解析存储状态
                    else if (KEY_STORAGE_STATE.equals(tagName)) {
                        currentState = new StorageInfo.StorageState();
                        // 获取token属性
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if (KEY_TOKEN.equals(attrName) || attrName.endsWith(":token")) {
                                currentState.setToken(getXpp().getAttributeValue(i));
                            }
                        }
                    } else if (currentState != null) {
                        switch (tagName) {
                            case KEY_STATE:
                                getXpp().next();
                                currentState.setState(getXpp().getText());
                                break;
                            case KEY_TOTAL_CAPACITY:
                                getXpp().next();
                                try {
                                    currentState.setTotalCapacity(Long.parseLong(getXpp().getText()));
                                } catch (NumberFormatException e) {
                                    currentState.setTotalCapacity(0L);
                                }
                                break;
                            case KEY_FREE_CAPACITY:
                                getXpp().next();
                                try {
                                    currentState.setFreeCapacity(Long.parseLong(getXpp().getText()));
                                } catch (NumberFormatException e) {
                                    currentState.setFreeCapacity(0L);
                                }
                                break;
                            case KEY_USED_CAPACITY:
                                getXpp().next();
                                try {
                                    currentState.setUsedCapacity(Long.parseLong(getXpp().getText()));
                                } catch (NumberFormatException e) {
                                    currentState.setUsedCapacity(0L);
                                }
                                break;
                            case KEY_LAST_UPDATED:
                                getXpp().next();
                                currentState.setLastUpdated(getXpp().getText());
                                break;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    // 结束标签处理
                    if (KEY_STORAGE_CONFIGURATION.equals(tagName) && currentConfig != null) {
                        storageInfo.getConfigurations().add(currentConfig);
                        currentConfig = null;
                    } else if (KEY_STORAGE_CAPABILITY.equals(tagName) && currentCapability != null) {
                        storageInfo.getCapabilities().add(currentCapability);
                        currentCapability = null;
                    } else if (KEY_STORAGE_STATE.equals(tagName) && currentState != null) {
                        storageInfo.getStates().add(currentState);
                        currentState = null;
                    }
                }

                eventType = getXpp().next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            storageInfo.setHasError(true);
            storageInfo.setErrorMessage("解析响应时出错: " + e.getMessage());
        }

        return storageInfo;
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
