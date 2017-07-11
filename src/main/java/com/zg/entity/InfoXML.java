package com.zg.entity;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class InfoXML {
    private String titleName;
    private String type;
    private boolean parseSuccess;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isParseSuccess() {
        return parseSuccess;
    }

    public void setParseSuccess(boolean parseSuccess) {
        this.parseSuccess = parseSuccess;
    }
}
