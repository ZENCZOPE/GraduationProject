package com.cms.pojo;

import java.util.Date;

public class Device {
    private String iemi;

    private String socImei;

    private String status;

    private Date createDate;

    private Date opDate;

    private Integer userId;

    public String getIemi() {
        return iemi;
    }

    public void setIemi(String iemi) {
        this.iemi = iemi == null ? null : iemi.trim();
    }

    public String getSocImei() {
        return socImei;
    }

    public void setSocImei(String socImei) {
        this.socImei = socImei == null ? null : socImei.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}