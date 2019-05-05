package com.cms.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.PlayerGradeRel;
import com.cms.service.GameService;
import com.cms.service.PlayerGameRelService;
import com.cms.service.PlayerGradeRelService;
import com.cms.service.UserService;
import com.cms.util.CMSException;
import com.cms.util.StringUtil;

@Service
public class MarkGradeJobInst {
				@Resource
				private GameService gameService;
				@Resource
				private UserService userService;
				@Resource
				private PlayerGameRelService playerGameRelService;
				@Resource
				private PlayerGradeRelService playerGradeRelService;
				
				Logger logger=Logger.getLogger(MarkGradeJobInst.class);
				@Scheduled(cron = "0  *  *  *  *  ?" )
				public void run() {
					try {
						logger.info("开始定时任务：MarkGradeJobInst");
						//1、获取正在进行中的比赛列表
						List<Game> games=gameService.queryGamesByStatus(ConstData.GAME_STATUS_DOING);
						
						logger.info("正在进行中的比赛有"+games.size()+"场");
						//2、遍历每场比赛
						for (Game game : games) {
							logger.debug("遍历game："+game);
							//3、获取比赛人员信息
							List<PlayerGameRel> list=playerGameRelService.getPlayersByGame(game);
							logger.debug("该比赛有"+list.size()+"位选手");
							//4、遍历每场选手
							for (PlayerGameRel playerGameRel : list) {
								int userId=playerGameRel.getPlayerId();
								int gameId=playerGameRel.getGameId();
								//float grade=playerGameRel.getUserGrade();
								//如果用户该场比赛成绩为空，则开始计算分数
								if (StringUtil.isEmptyOrNull(playerGameRel.getUserGrade())) {
									
									//5、获取选手成绩列表
									List<PlayerGradeRel> grades=this.scan(userId, gameId);
									
									//6、如果列表与裁判数量一致，就开始计算
									if (grades.size()==ConstData.JUDGE_NUM) {
										float newGrade=this.doSum(grades);
										playerGameRel.setUserGrade(newGrade);
										//7、更新选手成绩
										playerGameRelService.updatePlayerMsg(playerGameRel);
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("结束定时任务MarkGradeJobInst");
				}
				/**
				 * 扫描该场比赛该名选手的所有得分
				 * @param userId
				 * @param gameId
				 */
				private List<PlayerGradeRel> scan(int playerId,int gameId) {
					PlayerGradeRel playerGradeRel=new PlayerGradeRel();
					playerGradeRel.setGameId(gameId);
					playerGradeRel.setPlayerId(playerId);
					return playerGradeRelService.getPlayerAllGrades(playerGradeRel);
				}
				
				private float doSum(List<PlayerGradeRel> list) throws Exception {
					if(list.size()!=ConstData.JUDGE_NUM){
						throw new CMSException("得分数量不正确");
					}
					
					float grade=(float) 0.0;
					float sum=0;
					float[] grades=new float[ConstData.JUDGE_NUM];
					for (int i = 0; i < ConstData.JUDGE_NUM; i++) {
						grades[i]=list.get(i).getGrade();
					}
					for (PlayerGradeRel playerGradeRel : list) {
						sum+=playerGradeRel.getGrade();
					}
					grade=sum/ConstData.JUDGE_NUM;
					return grade;
				}
}
