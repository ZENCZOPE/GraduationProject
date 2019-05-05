package com.cms.pojo;

public class PlayerGameRelKey {
    @Override
	public String toString() {
		return "PlayerGameRelKey [gameId=" + gameId + ", playerId=" + playerId
				+ "]";
	}

	private Integer gameId;

    private Integer playerId;

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
}