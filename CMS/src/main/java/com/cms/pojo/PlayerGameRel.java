package com.cms.pojo;

public class PlayerGameRel extends PlayerGameRelKey {
    @Override
	public String toString() {
		return "PlayerGameRel [userGrade=" + userGrade + ", userStatus="
				+ userStatus + "]";
	}

	private Float userGrade;

    private String userStatus;

    public Float getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Float userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }
    
}