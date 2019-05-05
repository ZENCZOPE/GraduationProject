package com.cms.pojo;

public class PlayerGradeRelKey {
    @Override
	public String toString() {
		return "PlayerGradeRelKey [gameId=" + gameId + ", playerId=" + playerId
				+ ", judgeId=" + judgeId + "]";
	}

	private Integer gameId;

    private Integer playerId;

    private Integer judgeId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Integer judgeId) {
        this.judgeId = judgeId;
    }
}