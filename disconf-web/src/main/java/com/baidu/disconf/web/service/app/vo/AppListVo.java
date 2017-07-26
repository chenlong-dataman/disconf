package com.baidu.disconf.web.service.app.vo;

/**
 * @author liaoqiqi
 * @version 2014-6-22
 */
public class AppListVo {

    private long id;
    private String name;

    //updated 20161228
    private String desc;

    private String createTime;

    private String updateTime;

    private String emails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //updated 20161228
    public String getDesc() {
        return desc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getEmails() {
        return emails;
    }

}
