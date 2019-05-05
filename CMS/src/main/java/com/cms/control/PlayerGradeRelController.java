package com.cms.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cms.pojo.ConstData;
import com.cms.pojo.PlayerGradeRel;
import com.cms.service.DeviceService;
import com.cms.service.PlayerGradeRelService;
import com.cms.util.CMSException;
import com.cms.util.ResponseUtil;

@RestController
@RequestMapping(value="/PlayerGradeRel")
public class PlayerGradeRelController {
	@Resource
	private PlayerGradeRelService playerGradeRelService;
	@Resource
	private DeviceService deviceService;
	
	@RequestMapping(value="/mark/{deviceId}/{playerId}/{gameId}/{grade}/.do",method=RequestMethod.POST)
	public void mark(@PathVariable("deviceId") String deviceId,
			@PathVariable("playerId") int playerId,
			@PathVariable("gameId") int gameId,
			@PathVariable("grade") Float grade ,HttpServletResponse response) {
			PlayerGradeRel playerGradeRel=new PlayerGradeRel();
			Map<String, String> map=new HashMap<String, String>();
		
			try {
				int judgeId=deviceService.getJudgeIdByDeviceIemi(deviceId);
				playerGradeRel.setGameId(gameId);
				playerGradeRel.setGrade(grade);
				playerGradeRel.setJudgeId(judgeId);
				playerGradeRel.setStatus(ConstData.MARK_STATUS_DEFAULT);
				playerGradeRel.setPlayerId(playerId);
				playerGradeRelService.marking(playerGradeRel);
				map.put("msg", "操作成功！");
				map.put("result", "OK");
				ResponseUtil.printAjax(response, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("result", "ERROR");
				if (e.getClass()==CMSException.class) {
					map.put("msg", e.getMessage());
					ResponseUtil.printAjax(response, map);
				}else {
					map.put("msg", "后台服务调用失败，请联系管理员！");
					ResponseUtil.printAjax(response, map);
				}
			}
	}
}
