package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.domain.ZlmMediaServer;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaServerChangeEvent extends ApplicationEvent {

    public MediaServerChangeEvent(Object source) {
        super(source);
    }

    private List<ZlmMediaServer> mediaServerItemList;

    public List<ZlmMediaServer> getMediaServerItemList() {
        return mediaServerItemList;
    }

    public void setMediaServerItemList(List<ZlmMediaServer> mediaServerItemList) {
        this.mediaServerItemList = mediaServerItemList;
    }

    public void setMediaServerItemList(ZlmMediaServer... mediaServerItemArray) {
        this.mediaServerItemList = new ArrayList<>();
        this.mediaServerItemList.addAll(Arrays.asList(mediaServerItemArray));
    }

    public void setMediaServerItem(List<ZlmMediaServer> mediaServerItemList) {
        this.mediaServerItemList = mediaServerItemList;
    }
}
