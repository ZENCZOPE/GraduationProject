package com.cms.pojo;

public class PlayerGradeRel extends PlayerGradeRelKey {
    @Override
	public String toString() {
		return "PlayerGradeRel [grade=" + grade + ", status=" + status + "]";
	}

	private Float grade;

    private String status;

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}