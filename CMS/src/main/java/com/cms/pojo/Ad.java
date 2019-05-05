package com.cms.pojo;

import java.util.Date;

public class Ad {
    private Integer adId;

    private String adTitle;

    private String adNote;

    private String adAddr;

    private String adPrice;

    private String adOwner;

    private Date adEffectTime;

    private Date adEndTime;

    private String adStatus;

    private Date opTime;

    private Integer opId;

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle == null ? null : adTitle.trim();
    }

    public String getAdNote() {
        return adNote;
    }

    public void setAdNote(String adNote) {
        this.adNote = adNote == null ? null : adNote.trim();
    }

    public String getAdAddr() {
        return adAddr;
    }

    public void setAdAddr(String adAddr) {
        this.adAddr = adAddr == null ? null : adAddr.trim();
    }

    public String getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(String adPrice) {
        this.adPrice = adPrice == null ? null : adPrice.trim();
    }

    public String getAdOwner() {
        return adOwner;
    }

    public void setAdOwner(String adOwner) {
        this.adOwner = adOwner == null ? null : adOwner.trim();
    }

    public Date getAdEffectTime() {
        return adEffectTime;
    }

    public void setAdEffectTime(Date adEffectTime) {
        this.adEffectTime = adEffectTime;
    }

    public Date getAdEndTime() {
        return adEndTime;
    }

    public void setAdEndTime(Date adEndTime) {
        this.adEndTime = adEndTime;
    }

    public String getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(String adStatus) {
        this.adStatus = adStatus == null ? null : adStatus.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }
}