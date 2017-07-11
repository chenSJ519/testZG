package com.zg.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class Program {
    private String taskid;
    private String name;
    private String type;
    private String xmlplan;
    private String videoplan;
    private Date scantime;
    private Integer count;
    private List<Content> listContent;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Content> getListContent() {
        return listContent;
    }

    public void setListContent(List<Content> listContent) {
        this.listContent = listContent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getScantime() {
        return scantime;
    }

    public void setScantime(Date scantime) {
        this.scantime = scantime;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setTyp(String typ) {
        this.type = typ;
    }

    public String getXmlplan() {
        return xmlplan;
    }

    public void setXmlplan(String xmlplan) {
        this.xmlplan = xmlplan;
    }

    public String getVideoplan() {
        return videoplan;
    }

    public void setVideoplan(String videoplan) {
        this.videoplan = videoplan;
    }

}
