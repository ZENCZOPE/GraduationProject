package com.cms.pojo;

import java.util.Date;

public class PlayerGameBo {
	

	private Integer gameId;

    private String gameName;

    private String gameLevel;
    
    private String gameAddr;

    private Date gameDate;
    
    private Date gameEndDate;

    private Integer gameJoinCount;

    private Integer gameUpCount;

    private String gameStatus;
    
    private Float userGrade;
    
    private String userStatus;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName == null ? null : gameName.trim();
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel == null ? null : gameLevel.trim();
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getGameJoinCount() {
        return gameJoinCount;
    }

    public void setGameJoinCount(Integer gameJoinCount) {
        this.gameJoinCount = gameJoinCount;
    }

    public Integer getGameUpCount() {
        return gameUpCount;
    }

    public void setGameUpCount(Integer gameUpCount) {
        this.gameUpCount = gameUpCount;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus == null ? null : gameStatus.trim();
    }
    
    @Override
	public String toString() {
		return "PlayerGameBo [gameId=" + gameId + ", gameName=" + gameName
				+ ", gameLevel=" + gameLevel + ", gameDate=" + gameDate
				+ ", gameJoinCount=" + gameJoinCount + ", gameUpCount="
				+ gameUpCount + ", gameStatus=" + gameStatus + ", userGrade="
				+ userGrade + ", userStatus=" + userStatus + "]";
	}

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
		this.userStatus = userStatus;
	}

	public String getGameAddr() {
		return gameAddr;
	}

	public void setGameAddr(String gameAddr) {
		this.gameAddr = gameAddr;
	}

	public Date getGameEndDate() {
		return gameEndDate;
	}

	public void setGameEndDate(Date gameEndDate) {
		this.gameEndDate = gameEndDate;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
}
