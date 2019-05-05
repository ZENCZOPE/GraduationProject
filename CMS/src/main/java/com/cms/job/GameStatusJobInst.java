package com.cms.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.PlayerGameRel;
import com.cms.service.GameService;
import com.cms.service.PlayerGameRelService;
import com.cms.service.UserService;
import com.cms.util.CMSException;
import com.cms.util.DateUtil;
import com.cms.util.StringUtil;

/**
 * 开始比赛定时任务
 * 
 * @author ZENC
 * 
 */
@Service
public class GameStatusJobInst {
	Logger logger = Logger.getLogger(GameStatusJobInst.class);
	@Resource
	private GameService gameService;
	@Resource
	private PlayerGameRelService playerGameRelService;
	@Resource
	private UserService userService;

	@Scheduled(cron = "1  *  *  *  *  ?")
	public void run() {
		logger.debug("开始定时任务 Game状态自动更新定时任务");
		try {
			checkNewGame();
			checkReadyGame();
			checkDoingGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkNewGame() throws Exception {
		logger.debug("开始扫描新建状态的Game");
		List<Game> games = gameService.queryGamesByStatus(ConstData.GAME_STATUS_NEW);
		for (Game game : games) {
			logger.debug("获取player列表");
			List<PlayerGameRel> players = playerGameRelService	.getPlayersByGame(game);
			logger.debug("获取judge列表");
			List<PlayerGameRel> judges = playerGameRelService.getJudgesByGame(game);
			
			logger.debug("校验角色数量");
			if (players.size() > game.getGameJoinCount() ||  judges.size() > ConstData.JUDGE_NUM) {
				throw new CMSException("player或者judge数据异常，请联系管理员！");
			}
			if (players.size() == game.getGameJoinCount()	&& judges.size() == ConstData.JUDGE_NUM) {
				logger.debug("修改Game状态到ready");
				gameService.changGameStatus(game.getGameId(),ConstData.GAME_STATUS_READY);
			}

		}
	}

	private void checkReadyGame() throws Exception {
		logger.debug("开始扫描ready状态的Game");
		List<Game> games= gameService.queryGamesByStatus(ConstData.GAME_STATUS_READY);
		for (Game game : games) {
			logger.debug("获取player列表");
			List<PlayerGameRel> players = playerGameRelService	.getPlayersByGame(game);
			logger.debug("获取judge列表");
			List<PlayerGameRel> judges = playerGameRelService.getJudgesByGame(game);
			
			logger.debug("校验角色数量");
			if (players.size() > game.getGameJoinCount() ||  judges.size() > ConstData.JUDGE_NUM) {
				throw new CMSException("player或者judge数据异常，请联系管理员！");
			}
			if (players.size() != game.getGameJoinCount()	|| judges.size() != ConstData.JUDGE_NUM) {
				logger.debug("修改Game状态到ready");
				gameService.changGameStatus(game.getGameId(),ConstData.GAME_STATUS_NEW);
			}
			//检查开始时间是否大于当前时间
			if(game.getGameDate().getTime()<=DateUtil.getSysDate().getTime()){
				logger.debug("修改Game状态到doing");
				gameService.changGameStatus(game.getGameId(),ConstData.GAME_STATUS_DOING);
			}
			
		}
		

	}

	private void checkDoingGame() throws Exception {
		logger.debug("开始扫描doing状态的Game");
		List<Game> games= gameService.queryGamesByStatus(ConstData.GAME_STATUS_DOING);
		for (Game game : games) {
			logger.debug("获取player列表");
			List<PlayerGameRel> players = playerGameRelService	.getPlayersByGame(game);
			logger.debug("获取judge列表");
			List<PlayerGameRel> judges = playerGameRelService.getJudgesByGame(game);
			
			logger.debug("校验角色数量");
			if (players.size() > game.getGameJoinCount() ||  judges.size() > ConstData.JUDGE_NUM) {
				throw new CMSException("player或者judge数据异常，请联系管理员！");
			}
			if (players.size() != game.getGameJoinCount()	|| judges.size() != ConstData.JUDGE_NUM) {
				throw new CMSException("正在进行的比赛数据异常，请联系管理员！");
			}
			//检查开始时间是否大于当前时间
			logger.debug("检查比赛时间");
			Date a=DateUtil.getSysDate();
			Date b=game.getGameDate();
			if(game.getGameDate().getTime()>DateUtil.getSysDate().getTime()){
				throw new CMSException("game时间错误，请联系管理员！");
			}
			boolean flag=true;
			for (PlayerGameRel player : players) {
				if (StringUtil.isEmptyOrNull(player.getUserGrade())) {
					flag=false;
				}
			}
			
			if(flag){
				logger.debug("进行选手状态修改");
				int up=game.getGameUpCount();
				for(int i=0;i<up;i++){
					int id=players.get(i).getPlayerId();
					userService.statusPromotion(id);
				}
				for(int i=up;i<players.size();i++){
					int id=players.get(i).getPlayerId();
					userService.statusEliminate(id);
				}
				logger.debug("修改Game状态到end");
				gameService.changGameStatus(game.getGameId(),ConstData.GAME_STATUS_END);
			}
		}
		
	}
}
