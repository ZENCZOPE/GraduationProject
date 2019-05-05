package com.cms.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.ConstData;
import com.cms.pojo.Login;
import com.cms.pojo.Page;
import com.cms.pojo.User;
import com.cms.service.LoginService;
import com.cms.service.UserService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.MD5Util;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Resource
	private LoginService loginService;
	@Resource
	private UserService userService;

	/*
	 * @RequestMapping(value="/ajaxLogin.do") public ResponseEntity<String>
	 * Login(HttpServletRequest request,HttpServletResponse response,HttpSession
	 * session) throws JsonProcessingException { Login login=new Login();
	 * ModelMap map=new ModelMap(); ObjectMapper mapper=new ObjectMapper();
	 * login.setLoginName(request.getParameter("name"));
	 * login.setLoginPw(request.getParameter("password"));
	 * 
	 * if(loginService.getLoginStatus(login)){ map.addAttribute("msg", "Y");
	 * 
	 * return new
	 * ResponseEntity<String>(mapper.writeValueAsString(map),HttpStatus.OK);
	 * }else { map.addAttribute("msg","N"); return new
	 * ResponseEntity<String>(mapper.writeValueAsString(map),HttpStatus.OK); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/ajaxLogin.do")
	public void ajaxLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		ModelMap map = new ModelMap();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("userIdcard", request.getParameter("userIdcard"));
		map2.put("loginPw", request.getParameter("password"));
		try {
			if (loginService.getLoginStatus(map2)) {
				map.addAttribute("result", "Y");
				ResponseUtil.printAjax(response, map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/initLogin.do")
	public ModelAndView initLogin(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		return new ModelAndView("login", null);
	}

	@RequestMapping(value = "/login.do")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			User user = new User();
			user.setUserIdcard(request.getParameter("userIdcard"));
			user = userService.getUserMsg(user);
			Cookie cookie1 = CookieUtil.makeCookie("loginName",user.getUserName(), request);
			Cookie cookie3 = CookieUtil.makeCookie("loginRole",user.getUserRole(), request);
			Cookie cookie2 = CookieUtil.makeCookie("loginId",user.getUserId().toString(), request);
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			response.addCookie(cookie3);
			userService.updateUserOpTime(user);
			map.addAttribute("loginUserMsg", user);
			String userRole = user.getUserRole();
			if(userRole.equals("admin")) {
				return new ModelAndView("homeAdmin", map);
			}else if(userRole.equals("worker")) {
				return new ModelAndView("homeWorker", map);
			}else if(userRole.equals("judge")) {
				return new ModelAndView("homeJudge", map);
			}else {
				return new ModelAndView("homePlayer", map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
		}
		map.addAttribute("msg", "程序异常，请联系管理员");
		return new ModelAndView("error", map);
	}
	
	@RequestMapping(value = "/returnToLoginPage.do")
	public ModelAndView returnToLoginPage(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Cookie[] cookies = request.getCookies();
		ModelMap map = new ModelMap();
		try {
			if(cookies!=null) {
				for(int i=0;i<cookies.length;i++) {
					Cookie temp = cookies[i];
					temp.setMaxAge(0);
					response.addCookie(temp);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			map.addAttribute("msg", "程序异常，请联系管理员");
			return new ModelAndView("error", map);
		}
		return new ModelAndView("login", null);
	}
	
	//注册用户
	@RequestMapping(value = "/ajaxRegister.do")
	public void ajaxAddUser(HttpServletRequest request, HttpServletResponse response)throws IOException {
		ModelMap map = new ModelMap();
		try {
			User user = new User();
			user.setUserRole(ConstData.PLAYER_ROLE_DEFAULT);
			user.setUserName(request.getParameter("userName"));
			user.setUserAge(request.getParameter("userAge"));
			user.setUserMail(request.getParameter("userMail"));
			user.setUserPhone(request.getParameter("userPhone"));
			user.setUserAddr(request.getParameter("userAddr"));
			user.setUserIdcard(request.getParameter("userCard"));
			map.put("user", user);
			map.put("password", MD5Util.getMD5(request.getParameter("newPassword")));
			userService.addPlayer(map);
			map.addAttribute("result", "Y");
			map.addAttribute("msg", "注册成功！");
			ResponseUtil.printAjax(response, map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				e.printStackTrace();
				map.addAttribute("result","N");
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
			map.addAttribute("result","N");
			map.put("msg", "添加失败，请联系管理员！");
			ResponseUtil.printAjax(response, map);
		}
	}
	
	//获得显示个人信息页面
	@RequestMapping(value = "/getMyInfoPage.do")
	public ModelAndView getMyInfoPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			User user = new User();
			user.setUserIdcard(request.getParameter("userIdcard"));
			user = userService.getUserMsg(user);
			map.addAttribute("user", user);
			return new ModelAndView("myInfo", map);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
		}
		map.addAttribute("msg", "程序异常，请联系管理员");
		return new ModelAndView("error", map);
	}
	
	//获得修改密码页面
	@RequestMapping(value = "/getChangePasswordPage.do")
	public ModelAndView getChangePasswordPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			Cookie[] cookies = request.getCookies();
			int  userId = CookieUtil.getUserId(cookies);
			User user = userService.selectById(userId);
			String userIdcard = user.getUserIdcard();
			map.addAttribute("userIdcard", userIdcard);
			return new ModelAndView("changePassword", map);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}
			return new ModelAndView("error", map);
		}
	}
	
	//判断旧密码是否正确
	@RequestMapping(value = "/ajaxPasswordMatch.do")
	public void ajaxPasswordMatch(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {		
		ModelMap map = new ModelMap();
		try {
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.put("userIdcard", request.getParameter("userIdcard"));
			map2.put("loginPw", request.getParameter("oldPassword"));
			if (loginService.getLoginStatus(map2)) {
				map.addAttribute("result", "Y");
				ResponseUtil.printAjax(response, map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e.getClass() == CMSException.class) {
				map.addAttribute("result", "N");
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//修改密码
	@RequestMapping(value = "/ajaxChangePassword.do")
	public void ajaxChangePassword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			Cookie[] cookies = request.getCookies();
			int userId = CookieUtil.getUserId(cookies);
			Login login = new Login();
			String newPassword =request.getParameter("newPassword");
			login.setLoginPw(MD5Util.getMD5(newPassword));
			login.setIdNo(userId);
			loginService.updateLoginMsg(login);
			map.addAttribute("result", "Y");
			map.addAttribute("msg", "修改成功！");
			ResponseUtil.printAjax(response, map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
			}
			e.printStackTrace();
		}
	}
	
	//获取‘重置密码’页面
	@RequestMapping(value = "/getResetPasswordPage.do")
	public ModelAndView getResetPasswordPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("currPage", 1);
				map2.put("pageSize", ConstData.PAGE_SIZE);
				Page page = userService.viewUser(map2);
				map.addAttribute("page", page);
			}else {
				throw new CMSException("你没有权限使用此功能");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}		
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);
		}
		return new ModelAndView("resetPassword", map);
	}
	
	//显示‘重置密码’界面
	@RequestMapping(value = "/ajaxResetPasswordPage.do")
	public void ajaxResetPasswordPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String userName = request.getParameter("userName");
				String userPhone = request.getParameter("userPhone");
				String userRole = request.getParameter("userRole");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(userName)) {
					map.put("userName",  userName );
				}
				if (StringUtil.isNotEmptyOrNull(userPhone)) {
					map.put("userPhone",  userPhone );
				}
				if (StringUtil.isNotEmptyOrNull(userRole)) {
					map.put("userRole", userRole);
				}
				if (StringUtil.isNotEmptyOrNull(currPage)) {
					map.put("currPage", Integer.parseInt(currPage));
				} else {
					map.put("currPage", 1);
				}
				map.put("pageSize", ConstData.PAGE_SIZE);
				Page page = userService.viewUser(map);
				ResponseUtil.printAjax(response, page);	
				} 
			}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//重置密码
	@RequestMapping(value = "/ajaxResetPassword.do")
	public void ajaxResetPassword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String loginRole = CookieUtil.getUserRole(cookies);
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				int userId = Integer.parseInt(request.getParameter("userId"));
				loginService.resetLoginPassword(userId);
				map.put("msg", "密码重置成功！");
				ResponseUtil.printAjax(response, map);	
				} 
			}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
}