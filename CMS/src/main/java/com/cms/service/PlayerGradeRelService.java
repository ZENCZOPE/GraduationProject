package com.cms.service;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cms.dao.PlayerGradeRelMapper;
import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.PlayerGradeRel;
import com.cms.pojo.PlayerGradeRelKey;
import com.cms.util.CMSException;
import com.cms.util.StringUtil;

@Service
public class PlayerGradeRelService {

	@Resource
	private PlayerGradeRelMapper playerGradeRelMapper;
	@Resource
	private GameService gameService;
	@Resource
	private PlayerGameRelService playerGameRelService;
	
	/**
	 * 评委打分
	 * @param playerGradeRel
	 * @throws Exception 
	 */
	public void marking(PlayerGradeRel playerGradeRel) throws Exception {
		Game game=gameService.queryByGameId(playerGradeRel.getGameId());
		if(!game.getGameStatus().equals(ConstData.GAME_STATUS_DOING)){
			throw new CMSException("game状态不为进行状态！");
		}
		PlayerGameRel playerGameRel=playerGameRelService.queryByKey(playerGradeRel.getGameId(), playerGradeRel.getPlayerId());
		if(null==playerGameRel){
			throw new CMSException("game里没有该选手");
		}
		playerGameRel=playerGameRelService.queryByKey(playerGradeRel.getGameId(), playerGradeRel.getJudgeId());
		if(null==playerGameRel){
			throw new CMSException("game里没有该裁判");
		}
		PlayerGradeRelKey key=new PlayerGradeRelKey();
		BeanUtils.copyProperties(playerGradeRel, key);
		PlayerGradeRel playerGradeRel2=playerGradeRelMapper.selectByPrimaryKey(key);
		if(playerGradeRel2==null){
			playerGradeRelMapper.insert(playerGradeRel);
		}else {
			throw new CMSException("你已经打过分了!");
		}
		
	}
	/**
	 * 获取该场比赛的所有成绩
	 * @param playerGradeRel
	 * @return
	 */
	public List<PlayerGradeRel> getPlayerAllGrades(PlayerGradeRel playerGradeRel) {
		return playerGradeRelMapper.queryByCondition(playerGradeRel);
	}
}
