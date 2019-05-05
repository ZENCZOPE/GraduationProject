package com.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.GameMapper;
import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.Page;
import com.cms.pojo.PlayerGameBo;
import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.User;
import com.cms.util.CMSException;

@Service
public class GameService {
	@Resource
	private GameMapper gameMapper;
	@Resource
	private PlayerGameRelService playerGameRelService;
	@Resource
	private UserService userService;

	Logger logger = Logger.getLogger(GameService.class);

	/**
	 * 添加赛程
	 * 
	 * @param game
	 */
	public void addGame(Game game) throws Exception {
		gameMapper.insert(game);
	}

	/**
	 * 分配赛程
	 * 
	 * @param game
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void makeGame(Game game) throws Exception {
		/*
		 * 1、获取赛程的状态 只有创建状态和准备状态的赛程才可以生成赛程
		 */
		game = this.queryByGameId(game.getGameId());
		logger.debug("生成可用赛程开始：game=" + game);
		if (!(game.getGameStatus().equals(ConstData.GAME_STATUS_NEW) || game
				.getGameStatus().equals(ConstData.GAME_STATUS_READY))) {
			throw new CMSException("只有创建状态和准备状态的赛程才可以生成赛程！");
		}
		/*
		 * 2、获取赛程选手数量 如果存在数量小于需要数量则添加选手进入赛程 如果存在数量大于需要数量则清除选手重新添加选手
		 */
		List<PlayerGameRel> list = playerGameRelService.getPlayersByGame(game);
		logger.debug("当前选手数量：" + list.size());
		List<PlayerGameRel> list2 = playerGameRelService.getPlayersByGame(game);
		logger.debug("当前裁判数量：" + list2.size());
		if (list.size() < game.getGameJoinCount()) {
			logger.debug("开始增加选手");
			this.addPlayers(game);
			// this.addJudges(game);
		} else if (list.size() >= game.getGameJoinCount()) {
			logger.debug("开始清空选手");
			clearPlayerAndJudge(game);
			logger.debug("开始增加选手");
			this.addPlayers(game);
			// this.addJudges(game);
		}
		/*
		 * 3、获取赛程裁判数量 如果存在数量小于需要数量则添加选手进入赛程 如果存在数量大于需要数量则清除选手重新添加选手
		 */

		if (list2.size() <= ConstData.JUDGE_NUM) {

			this.addJudges(game);
		} else if (list2.size() > game.getGameJoinCount()) {
			clearPlayerAndJudge(game);
			this.addJudges(game);
		}
	}

	/**
	 * 清除比赛选手和裁判
	 * 
	 * @param game
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void clearPlayerAndJudge(Game game) throws Exception {
		List<PlayerGameRel> list = playerGameRelService.getPlayersByGame((game));
		for (PlayerGameRel playerGameRel : list) {
			int id = playerGameRel.getPlayerId();
			userService.statusReduction(id);
		}
		playerGameRelService.deleteByGameId(game);
	}

	/**
	 * 添加比赛选手
	 * 
	 * @param game
	 * @throws Exception
	 */
	private void addPlayers(Game game) throws Exception {
		List<PlayerGameRel> list = playerGameRelService.getPlayersByGame(game);
		int currPlayer = list.size();
		int needPlayer = game.getGameJoinCount();
		if (currPlayer > needPlayer) {
			throw new CMSException("人数过多！");
		}
		for (; currPlayer < needPlayer; currPlayer++) {
			playerGameRelService.addPlayerToGame(game);
		}
	}

	/**
	 * 添加裁判
	 * 
	 * @param game
	 * @throws Exception
	 */
	private void addJudges(Game game) throws Exception {
		List<PlayerGameRel> list = playerGameRelService.getJudgesByGame(game);
		int currJudge = list.size();
		if (currJudge > ConstData.JUDGE_NUM) {
			throw new CMSException("人数过多！");
		}
		for (; currJudge < ConstData.JUDGE_NUM; currJudge++) {
			playerGameRelService.addJudgeToGame(game);
		}
	}

	/**
	 * 查看用户参与的比赛
	 * 
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public List<PlayerGameBo> getPlayerGame(User user) throws Exception {
		List<PlayerGameBo> bos = new ArrayList<PlayerGameBo>();
		// 1、获取用户ID
		int id = user.getUserId();
		user = userService.getUserMsg(user);
		// 2、获取相关联的比赛id
		List<PlayerGameRel> list1 = playerGameRelService.getGamesByUserId(id);
		for (PlayerGameRel playerGameRel : list1) {
			int gameId = playerGameRel.getGameId();
			Game game = gameMapper.selectByPrimaryKey(gameId);
			PlayerGameBo playerGameBo = new PlayerGameBo();
			BeanUtils.copyProperties(game, playerGameBo);
			playerGameBo.setUserStatus(user.getUserStatus());
			playerGameBo.setUserGrade(playerGameRel.getUserGrade());
			bos.add(playerGameBo);
		}

		return bos;
	}

	public List<Game> queryGamesByStatus(String status) {
		return gameMapper.queryByStatus(status);
	}

	public Game queryByGameId(int gameId) {
		return gameMapper.selectByPrimaryKey(gameId);
	}

	public void changGameStatus(int gameId, String status) throws Exception {
		Game game = new Game();
		game.setGameStatus(status);
		game.setGameId(gameId);
		this.updateGameMsg(game);
	}

	public void updateGameMsg(Game game) throws Exception {
		gameMapper.updateByPrimaryKeySelective(game);
	}

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	public Page viewGame(Map<String, Object> map) throws Exception {
		int currPage = (Integer) map.get("currPage");
		int pageSize = (Integer) map.get("pageSize");
		int totalrecord = gameMapper.queryCountByCondition(map);
		Page page = new Page(totalrecord, pageSize, currPage);
		if (currPage <= 0) {
			currPage = 1;
		}
		map.put("index", page.getIndex());
		List<Game> list = gameMapper.queryPageByCondition(map);
		page.setList(list);
		return page;
	}

	/**
	 * 根据id定位赛程
	 * 
	 * @param id
	 * @return
	 */
	public Game selectById(int id) {
		return gameMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 注销赛程
	 * @param game
	 */
	public void logoutGame(int gameId) throws Exception {
		Game game=this.selectById(gameId);
		if(game.getGameStatus().equals(ConstData.GAME_STATUS_NEW)){
			game.setGameStatus(ConstData.GAME_STATUS_LOGOUT);
			this.updateGameMsg(game);
		}else if (game.getGameStatus().equals(ConstData.GAME_STATUS_READY)) {
			this.clearPlayerAndJudge(game);
			game.setGameStatus(ConstData.GAME_STATUS_LOGOUT);
			this.updateGameMsg(game);
		}else {
			throw new CMSException("该比赛状态不允许注销");
		}
	}
}
