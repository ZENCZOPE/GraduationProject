package com.cms.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.ConstData;
import com.cms.pojo.Game;
import com.cms.pojo.Page;
import com.cms.pojo.PlayerGameBo;
import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.PlayerGradeRel;
import com.cms.pojo.User;
import com.cms.service.GameService;
import com.cms.service.PlayerGameRelService;
import com.cms.service.PlayerGradeRelService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;

@Controller
@RequestMapping(value = "/game")
public class GameController {
	@Resource
	private GameService gameService;
	@Resource
	private PlayerGradeRelService playerGradeRelService;
	@Resource
	private PlayerGameRelService playerGameRelService;

	// 获得‘添加赛程’页面
	@RequestMapping(value = "/getAddGamePage.do")
	public ModelAndView getAddGameGame(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			return new ModelAndView("addGame", null);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}

	// 添加赛程
	@RequestMapping(value = "ajaxAddGame.do")
	public void addGame(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Game game = new Game();
			game.setGameName(request.getParameter("gameName"));
			game.setGameLevel(request.getParameter("gameLevel"));
			game.setGameAddr(request.getParameter("gameAddr"));
			// string类型转换为date类型
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String gameDate = request.getParameter("gameDate");
			game.setGameDate(sim.parse(gameDate));
			String gameEndDate = request.getParameter("gameEndDate");
			game.setGameEndDate(sim.parse(gameEndDate));
			int joincount = Integer.parseInt(request.getParameter("gameJoinCount"));
			game.setGameJoinCount(joincount);
			int upcount = Integer.parseInt(request.getParameter("gameUpCount"));
			game.setGameUpCount(upcount);
			game.setGameStatus(ConstData.GAME_STATUS_NEW);
			gameService.addGame(game);
			map.put("result", "Y");
			map.put("msg", "添加成功");
			ResponseUtil.printAjax(response, map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}

	
	// 获得‘分配赛程’页面
	@RequestMapping(value = "/getGameAllocation.do")
	public ModelAndView getGameAllocation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return new ModelAndView("gameAllocation", null);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 分配赛程列表
	@RequestMapping(value = "/gameAllocation.do")
	public void gameAllocation(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String gameName = request.getParameter("gameName");
			String gameLevel = request.getParameter("gameLevel");
			String gameAddr = request.getParameter("gameAddr");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(gameName)) {
				map.put("gameName", gameName);
			}
			if (StringUtil.isNotEmptyOrNull(gameLevel)) {
				map.put("gameLevel", gameLevel);
			}
			if (StringUtil.isNotEmptyOrNull(gameAddr)) {
				map.put("gameAddr", gameAddr);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = gameService.viewGame(map);
			ResponseUtil.printAjax(response, page);
			} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}

	//分配赛程
	@RequestMapping(value = "/ajaxMakeGame.do")
	public void ajaxMakeGame(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Game game = new Game();
			int gameId = Integer.parseInt(request.getParameter("gameId"));
			game.setGameId(gameId);
			map.put("msg", "分配成功");
			gameService.makeGame(game);
			ResponseUtil.printAjax(response, map);
		}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}
	
	// 获得‘赛程管理’页面
	@RequestMapping(value = "/getGameManagePage.do")
	public ModelAndView getGameManagePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return new ModelAndView("gameManage", null);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 显示赛程列表
	@RequestMapping(value = "/ajaxGetGameList.do")
	public void ajaxGetGameList(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String gameName = request.getParameter("gameName");
			String gameLevel = request.getParameter("gameLevel");
			String gameAddr = request.getParameter("gameAddr");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(gameName)) {
				map.put("gameName", gameName);
			}
			if (StringUtil.isNotEmptyOrNull(gameLevel)) {
				map.put("gameLevel", gameLevel);
			}
			if (StringUtil.isNotEmptyOrNull(gameAddr)) {
				map.put("gameAddr", gameAddr);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = gameService.viewGame(map);
			ResponseUtil.printAjax(response, page);
			} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}
	
	// 获得‘查看赛程’页面
	@RequestMapping(value = "/getGameQueryPage.do")
	public ModelAndView getGameQueryPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return new ModelAndView("gameQuery", null);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 获得‘我的赛程’页面
	@RequestMapping(value = "/getMyGamePage.do")
	public ModelAndView getMyGamePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			return new ModelAndView("gameOfMine", null);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 显示我的赛程
	@RequestMapping(value = "/ajaxMyGameList.do")
	public void ajaxMyGameList(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			int userId = CookieUtil.getUserId(cookies);
			User user = new User();
			user.setUserId(userId);
			List<PlayerGameBo> games = gameService.getPlayerGame(user);
			map.put("games", games);
			map.put("userRole",userRole);
			ResponseUtil.printAjax(response, map);
			} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}
	
	// 获得‘详细情况’页面
	@RequestMapping(value = "/getGameDetailPage.do")
	public ModelAndView getGameDetailPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			int gameId = Integer.parseInt(request.getParameter("gameId"));
			map.put("gameId", gameId);
			if(userRole.equals("judge")) {
				return new ModelAndView("gameDetail", map);
			}else {
				return new ModelAndView("gameDetailPlayer", map);
			}
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 显示‘详细情况’
	@RequestMapping(value = "/ajaxGameDetail.do")
	public void ajaxGameDetail(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			int gameId = Integer.parseInt(request.getParameter("gameId"));
			if (userRole.equals("judge")) {
				PlayerGradeRel playerGradeRel = new PlayerGradeRel();
				playerGradeRel.setGameId(gameId);
				List<PlayerGradeRel> games = playerGradeRelService.getPlayerAllGrades(playerGradeRel);
				map.put("games", games);
			}else {
				PlayerGameRel playerGameRel = new PlayerGameRel();
				playerGameRel.setGameId(gameId);
				playerGameRel.setUserStatus(ConstData.PLAYER_STATUS_DEFAULT);
				List<PlayerGameRel> games = playerGameRelService.getPlayerAllGrades(playerGameRel);
				map.put("games", games);
			}
			ResponseUtil.printAjax(response, map);
			} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}

	// 获得‘赛程修改’界面
	@RequestMapping(value = "/getGameChangePage.do")
	public ModelAndView getGameChangePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int gameId = Integer.parseInt(request.getParameter("gameId"));
			Game game = gameService.selectById(gameId); 
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String gameDate =sim.format(game.getGameDate());
			String gameEndDate =sim.format(game.getGameEndDate());
			map.put("game", game);
			map.put("gameDate", gameDate);
			map.put("gameEndDate", gameEndDate);
			return new ModelAndView("gameChange", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}

	// 修改赛程
	@RequestMapping(value = "/ajaxGameChange.do")
	public void ajaxGameChange(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Game game = new Game();
			game.setGameId(Integer.parseInt(request.getParameter("gameId")));
			game.setGameName(request.getParameter("gameName"));
			game.setGameLevel(request.getParameter("gameLevel"));
			game.setGameAddr(request.getParameter("gameAddr"));
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			game.setGameDate(sim.parse(request.getParameter("gameDate")));
			game.setGameEndDate(sim.parse(request.getParameter("gameEndDate")));
			String gameJoinCount = request.getParameter("gameJoinCount");
			game.setGameJoinCount(Integer.parseInt(gameJoinCount));
			String gameUpCount = request.getParameter("gameUpCount");
			game.setGameUpCount(Integer.parseInt(gameUpCount));
			game.setGameStatus(request.getParameter("gameStatus"));
			gameService.updateGameMsg(game);
			map.put("result", "Y");
			map.put("msg", "修改成功");
			ResponseUtil.printAjax(response, map);
		}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("result", "N");
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();			
		}
	}
	
	//删除赛程
	@RequestMapping(value = "/ajaxGameDelete.do")
	public void ajaxGameDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int gameId = Integer.parseInt(request.getParameter("gameId"));
			gameService.logoutGame(gameId);
			map.put("msg","删除成功!");
			ResponseUtil.printAjax(response, map);
		}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			map.put("msg", "操作异常,请联系管理员！");
			ResponseUtil.printAjax(response, map);
			e.printStackTrace();
		}
	}
	
}
