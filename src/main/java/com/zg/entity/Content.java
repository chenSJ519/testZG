package com.zg.entity;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class Content {
    private String taskid;
    private String plan;
    private Program program;
    private String name;
    private String url;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
