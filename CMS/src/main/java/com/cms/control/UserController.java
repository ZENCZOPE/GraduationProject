package com.cms.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.ConstData;
import com.cms.pojo.Page;
import com.cms.pojo.User;
import com.cms.pojo.UserBO;
import com.cms.service.UserService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.FileUtil;
import com.cms.util.POIUtil;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/uploadUser")
	public void uploadUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					// String path="E:/springUpload"+file.getOriginalFilename();
					// 上传
					// file.transferTo(new File(path));
					List<String[]> list = POIUtil.readExcel(file);

					String filePath = request.getSession().getServletContext().getRealPath("/") + file.getOriginalFilename();
					file.transferTo(new File(filePath));
				}

			}

		}

	}
	
	//获取‘添加用户’页面
	@RequestMapping(value = "/getUserAdd.do")
	public ModelAndView getUserAdd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelMap map = new ModelMap();
		try{
			return new ModelAndView("userAdd", null);
		}catch (Exception e) {
			e.printStackTrace();
			if(e.getClass()==CMSException.class){
				map.addAttribute("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
		}
		return new ModelAndView("error", null);
	}
	
	//添加用户
	@RequestMapping(value = "/ajaxUserAdd.do")
	public void ajaxUserAdd(HttpServletRequest request, HttpServletResponse response)throws IOException {
		ModelMap map = new ModelMap();
		try {
			User user = new User();
			user.setUserName(request.getParameter("userName"));
			user.setUserAge(request.getParameter("userAge"));
			user.setUserMail(request.getParameter("userMail"));
			user.setUserPhone(request.getParameter("userPhone"));
			user.setUserAddr(request.getParameter("userAddr"));
			String role = request.getParameter("userRole");
			user.setUserRole(role);
			user.setUserStatus(request.getParameter("userStatus"));
			user.setUserIdcard(request.getParameter("userIdcard"));
			userService.addUser(user);
			map.addAttribute("result", "Y");
			map.addAttribute("msg", "添加成功");
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
	
	//获取‘员工列表’页面
	@RequestMapping(value = "/getWorkerListPage.do")
	public ModelAndView getWorkerListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			return new ModelAndView("workerList", map);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}		
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);
		}
	}
	
	//显示员工列表
	@RequestMapping(value = "/ajaxGetWorkerList.do")
	public void ajaxgetWorkerList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginName = CookieUtil.getUserName(cookies);
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String userRole = ConstData.WORKER_ROLE_DEFAULT;
				String userName = request.getParameter("userName");
				String userPhone = request.getParameter("userPhone");
				String userStatus = request.getParameter("userStatus");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(userName)) {
					map.put("userName",  userName );
				}
				if (StringUtil.isNotEmptyOrNull(userPhone)) {
					map.put("userPhone",  userPhone );
				}
				if (StringUtil.isNotEmptyOrNull(userStatus)) {
					map.put("userStatus", userStatus);
				}
				if (StringUtil.isNotEmptyOrNull(currPage)) {
					map.put("currPage", Integer.parseInt(currPage));
				} else {
					map.put("currPage", 1);
				}
				map.put("userRole", userRole);
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
	
	//获取‘评委列表’页面
	@RequestMapping(value = "/getJudgeListPage.do")
	public ModelAndView getJudgeListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			String role = ConstData.JUDGE_ROLE_DEFAULT;
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("userRole", role);
			map2.put("currPage", 1);
			map2.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewUser(map2);
			map.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}		
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);
		}
		return new ModelAndView("judgeList", map);
	}

	//显示评委列表
	@RequestMapping(value = "/ajaxGetJudgeList.do")
	public void ajaxgetJudgeList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String userPhone = request.getParameter("userPhone");
			String userStatus = request.getParameter("userStatus");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(userName)) {
				map.put("userName",  userName );
			}
			if (StringUtil.isNotEmptyOrNull(userPhone)) {
				map.put("userPhone",  userPhone );
			}
			if (StringUtil.isNotEmptyOrNull(userStatus)) {
				map.put("userStatus", userStatus);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			} else {
				map.put("currPage", 1);
			}
			map.put("userRole", ConstData.JUDGE_ROLE_DEFAULT);
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewUser(map);
			ResponseUtil.printAjax(response, page);
			}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//获得‘选手列表’页面
	@RequestMapping(value = "/getPlayerListPage.do")
	public ModelAndView getPlayerListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			String role = ConstData.PLAYER_ROLE_DEFAULT;
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("userRole", role);
			map2.put("currPage", 1);
			map2.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewUser(map2);
			map.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}	
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);
		}
		return new ModelAndView("playerList", map);
	}
	
	//显示选手列表
	@RequestMapping(value = "/ajaxGetPlayerList.do")
	public void ajaxGetPlayerList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String userPhone = request.getParameter("userPhone");
			String userStatus = request.getParameter("userStatus");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(userName)) {
				map.put("userName",  userName );
			}
			if (StringUtil.isNotEmptyOrNull(userPhone)) {
				map.put("userPhone",  userPhone );
			}
			if (StringUtil.isNotEmptyOrNull(userStatus)) {
				map.put("userStatus", userStatus);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			} else {
				map.put("currPage", 1);
			}
			map.put("userRole", ConstData.PLAYER_ROLE_DEFAULT);
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewUser(map);
			ResponseUtil.printAjax(response, page);
			}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//显示已分配员工列表
	@RequestMapping(value = "/ajaxGetWorkerDistributedList.do")
	public void ajaxGetWorkerDistributedList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String userRole = ConstData.WORKER_ROLE_DEFAULT;
				String userStatus = ConstData.USER_STATUS_AUTH;
				String userName = request.getParameter("userName");
				String userAddr = request.getParameter("userAddr");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(userName)) {
					map.put("userName",  userName );
				}
				if (StringUtil.isNotEmptyOrNull(userAddr)) {
					map.put("userAddr",  userAddr );
				}
				if (StringUtil.isNotEmptyOrNull(currPage)) {
					map.put("currPage", Integer.parseInt(currPage));
				} else {
					map.put("currPage", 1);
				}
				map.put("userStatus", userStatus);
				map.put("userRole", userRole);
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

	//获取用户认证页面
	@RequestMapping(value = "/getCertifyListPage.do")
	public ModelAndView getCertifyListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			String userStatus =ConstData.USER_STATUS_DEFAULT;
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("userStatus", userStatus);
			map2.put("currPage", 1);
			map2.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewCertifyList(map2);
			map.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}			
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);
		}
		return new ModelAndView("certifyList", map);
	}
	
	//显示未认证用户列表
	@RequestMapping(value = "/ajaxGetCertifyList.do")
	public void ajaxGetCertifyList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userStatus =ConstData.USER_STATUS_DEFAULT;
			String userName = request.getParameter("userName");
			String userAddr = request.getParameter("userAddr");
			String userRole = request.getParameter("userRole");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(userName)) {
				map.put("userName",  userName );
			}
			if (StringUtil.isNotEmptyOrNull(userAddr)) {
				map.put("userAddr",  userAddr );
			}
			if (StringUtil.isNotEmptyOrNull(userRole)) {
				map.put("userRole", userRole);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			} else {
				map.put("currPage", 1);
			}
			map.put("userStatus", userStatus);
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = userService.viewCertifyList(map);
			List<UserBO> list=page.getList();
			List<UserBO> newList=new ArrayList<UserBO>();
			for (UserBO userBO : list) {
				String idString=userBO.getUserId().toString();
				boolean flag=FileUtil.checkExist(request.getSession().getServletContext().getRealPath("/")+"certify/"
	            		+ idString + ".zip");
				if (flag) {
					userBO.setIsFile("1");
				}else {
					userBO.setIsFile("0");
				}
				newList.add(userBO);
			}
			page.setList(newList);
			ResponseUtil.printAjax(response, page);
			}catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//获取修改用户界面
	@RequestMapping(value = "/getUserChangePage.do")
	public ModelAndView getUserChangePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelMap map = new ModelMap();
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			User user = userService.selectById(userId);
			map.addAttribute("user", user);
			return new ModelAndView("userChange", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}

	//修改用户
	@RequestMapping(value = "/ajaxUserChange.do")
	public void ajaxUserChange(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			User user = new User();
			String userId = request.getParameter("userId");
			user.setUserId(Integer.parseInt(userId));
			user.setUserName(request.getParameter("userName"));
			user.setUserAge(request.getParameter("userAge"));
			user.setUserMail(request.getParameter("userMail"));
			user.setUserPhone(request.getParameter("userPhone"));
			user.setUserAddr(request.getParameter("userAddr"));
			user.setUserRole(request.getParameter("userRole"));
			user.setUserStatus(request.getParameter("userStatus"));
			user.setUserIdcard(request.getParameter("userIdcard"));
			userService.updateUserMsg(user);
			map.addAttribute("result", "Y");
			map.addAttribute("msg", "修改成功");
			ResponseUtil.printAjax(response, map);
			} catch (Exception e) {
				if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
				}
			e.printStackTrace();
		}
	}

	//删除用户
	@RequestMapping(value = "/ajaxUserDelete.do")
	public void ajaxUserDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelMap map = new ModelMap();
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			userService.logoutUser(userId);
			map.addAttribute("msg","删除成功!");
			ResponseUtil.printAjax(response, map);
		}catch (Exception e) {
				if (e.getClass() == CMSException.class) {
					map.put("msg", e.getMessage());
					ResponseUtil.printAjax(response, map);
				}
			e.printStackTrace();
		}	
	}

	//获取用户权限
	@RequestMapping(value = "/getUserAuth.do")
	public void getUserAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		String userName = CookieUtil.getUserName(cookies);
		String userRole = CookieUtil.getUserRole(cookies);
		map.put("Role", userRole);
		ResponseUtil.printAjax(response, map);
	}
	
	//验证用户
	@RequestMapping(value = "/ajaxCertify.do")
	public void ajaxCertify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelMap map = new ModelMap();
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			userService.userAuth(userId);
			map.addAttribute("msg","验证成功!");
			ResponseUtil.printAjax(response, map);
		}catch (Exception e) {
				if (e.getClass() == CMSException.class) {
					map.put("msg", e.getMessage());
					ResponseUtil.printAjax(response, map);
				}
			e.printStackTrace();
		}	
	}

}
