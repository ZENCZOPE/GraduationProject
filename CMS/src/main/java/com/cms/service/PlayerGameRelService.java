package com.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.PlayerGameRelMapper;
import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.PlayerGameRelKey;
import com.cms.pojo.User;
import com.cms.util.CMSException;

@Service
public class PlayerGameRelService {
		@Resource
		private PlayerGameRelMapper playerGameRelMapper;
		@Resource
		private UserService userService;
		/**
		 * 获取比赛id的选手信息
		 * @param game
		 * @return
		 * @throws Exception 
		 */
		public List<PlayerGameRel> getPlayersByGame(Game game) throws Exception{
			List<PlayerGameRel> list=playerGameRelMapper.queryByGameId(game.getGameId());
			List<PlayerGameRel> list2=new ArrayList<PlayerGameRel>();
			
			for (PlayerGameRel playerGameRel : list) {
				int id=playerGameRel.getPlayerId();
				User user=new User();
				user.setUserId(id);
				String role=userService.getUserMsg(user).getUserRole();
				
				if(role.equals(ConstData.PLAYER_ROLE_DEFAULT)){
					list2.add(playerGameRel);
				}
			}
			return list2;
		}
		/**
		 * 获取该比赛的裁判数据
		 * @param game
		 * @return
		 * @throws Exception
		 */
		public List<PlayerGameRel> getJudgesByGame(Game game) throws Exception {
			List<PlayerGameRel> list=playerGameRelMapper.queryByGameId(game.getGameId());
			List<PlayerGameRel> list2=new ArrayList<PlayerGameRel>();
			
			for (PlayerGameRel playerGameRel : list) {
				int id=playerGameRel.getPlayerId();
				User user=new User();
				user.setUserId(id);
				String role=userService.getUserMsg(user).getUserRole();
				
				if(role.equals(ConstData.JUDGE_ROLE_DEFAULT)){
					list2.add(playerGameRel);
				}
			}
			return list2;
		}
		
		/**
		 * 获取该比赛的裁判数据
		 * @param game
		 * @return
		 * @throws Exception
		 */
		public List<PlayerGameRel> getJudgesAndPlayerByGame(Game game) throws Exception {
			List<PlayerGameRel> list=playerGameRelMapper.queryByGameId(game.getGameId());
			List<PlayerGameRel> list2=new ArrayList<PlayerGameRel>();
			
			for (PlayerGameRel playerGameRel : list) {
				int id=playerGameRel.getPlayerId();
				///User user=new User();
				//user.setUserId(id);
				//String role=userService.getUserMsg(user).getUserRole();
				
				
					list2.add(playerGameRel);
				
			}
			return list2;
		}
		/**
		 * 获取该比赛的用户数据
		 * @param game
		 * @return
		 * @throws Exception
		 */
		public List<User> getUsersByGame(Game game) throws Exception {
			List<PlayerGameRel> list=playerGameRelMapper.queryByGameId(game.getGameId());
			List<User> list2=new ArrayList<User>();
			for (PlayerGameRel playerGameRel : list) {
				int userId=playerGameRel.getPlayerId();
				User user=userService.selectById(userId);
				list2.add(user);
			}
		
			return list2;
		}
		
		/**
		 * 移除该场比赛的全部选手
		 * @param game
		 */
		public void deleteByGameId(Game game) {
			playerGameRelMapper.deleteByGameId(game.getGameId());
		}
		/**
		 * 参赛
		 * @param game
		 * @throws Exception 
		 */
		public void addPlayerToGame(Game game) throws Exception {
			int level=Integer.parseInt(game.getGameLevel());
			List<User> list=userService.selectByLevel(level,ConstData.PLAYER_ROLE_DEFAULT);
			if(list.size()==0){
				throw new CMSException("没有符合要求的选手");
			}
			Random random=new Random();
			int get=random.nextInt(list.size());
			User user=list.get(get);
			PlayerGameRel playerGameRel=new PlayerGameRel();
			playerGameRel.setGameId(game.getGameId());
			playerGameRel.setPlayerId(user.getUserId());
			playerGameRel.setUserStatus(ConstData.PLAYER_STATUS_DEFAULT);
			playerGameRelMapper.insertSelective(playerGameRel);
			userService.statusJoin(user.getUserId());
		}
		/**
		 * 参赛
		 * @param game
		 * @throws Exception 
		 */
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
		public void addJudgeToGame(Game game) throws Exception {
			int level=Integer.parseInt(game.getGameLevel());
			List<User> list=userService.selectByLevel(Integer.parseInt(ConstData.JUDGE_STATUS_DEFAULT),ConstData.JUDGE_ROLE_DEFAULT);
			if(list.size()==0){
				throw new CMSException("没有符合要求的裁判");
			}
			Random random=new Random();
			int get=random.nextInt(list.size());
			User user=list.get(get);
			PlayerGameRel playerGameRel=new PlayerGameRel();
			playerGameRel.setGameId(game.getGameId());
			playerGameRel.setPlayerId(user.getUserId());
			playerGameRel.setUserStatus(ConstData.JUDGE_STATUS_DEFAULT);
			playerGameRel.setUserGrade(ConstData.JUDGE_GRADE_DEFAULT);
			playerGameRelMapper.insertSelective(playerGameRel);
			//userService.statusJoin(user.getUserId());
		}
		/**
		 * 根据用户id查询相关的games
		 * @param id
		 * @return
		 */
		public List<PlayerGameRel> getGamesByUserId(int id) {
		return playerGameRelMapper.queryByPlayerId(id);
		}
		
		public void updatePlayerMsg(PlayerGameRel playerGameRel) {
			playerGameRelMapper.updateByPrimaryKeySelective(playerGameRel);
		}
		
		public PlayerGameRel queryByKey(int gameId,int playerId) {
			PlayerGameRelKey key=new PlayerGameRelKey();
			key.setGameId(gameId);
			key.setPlayerId(playerId);
			return playerGameRelMapper.selectByPrimaryKey(key);
		}
		
		public List<PlayerGameRel> getPlayerAllGrades(PlayerGameRel playerGameRel) {
			return playerGameRelMapper.queryByCondition(playerGameRel);
		}
}
