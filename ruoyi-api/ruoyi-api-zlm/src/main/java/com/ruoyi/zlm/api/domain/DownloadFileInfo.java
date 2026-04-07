package com.ruoyi.zlm.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class DownloadFileInfo implements Serializable {

    private String httpPath;
    private String httpsPath;
    private String httpDomainPath;
    private String httpsDomainPath;

}
