package com.zg.config;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class MySpringConfig {
    private String rootPath;
    private String videoInfoXML;
    private String mediaPath;

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getVideoInfoXML() {//abc
        return videoInfoXML;
    }

    public void setVideoInfoXML(String videoInfoXML) {
        this.videoInfoXML = videoInfoXML;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
