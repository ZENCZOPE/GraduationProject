package com.cms.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.ConstData;
import com.cms.pojo.Device;
import com.cms.pojo.Page;
import com.cms.service.DeviceService;
import com.cms.service.UserService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
	@Resource
	private DeviceService deviceService;
	@Resource
	private UserService userService;
	
	// 获得添加设备页面
	@RequestMapping(value = "/getAddDevicePage.do")
	public ModelAndView getAddDevicePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
			return new ModelAndView("addDevice", null);
		}
		return new ModelAndView("error", null);
	}
	
	//添加设备
	@RequestMapping(value = "/ajaxAddDevice.do")
	public void ajaxAddDevice(HttpServletRequest request, HttpServletResponse response)throws IOException {
		ModelMap map = new ModelMap();
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
			try {
				Device device = new Device();
				device.setIemi(request.getParameter("iemi"));
				device.setSocImei(request.getParameter("socImei"));	
				deviceService.addDevice(device);
				map.addAttribute("result", "Y");
				map.addAttribute("msg", "添加成功");
				ResponseUtil.printAjax(response, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
	}
	
	//获得设备列表页面
	@RequestMapping(value = "/getDeviceListPage.do")
	public ModelAndView getDeviceListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				return new ModelAndView("deviceList", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg",e.getMessage());
				return new ModelAndView("error", map);
			}	
			map.addAttribute("msg", "后台调用失败，请联系管理员");
			return new ModelAndView("error", map);		
			// TODO: handle exception
		}
		return new ModelAndView("error", null);
	}
	
	//显示设备列表
	@RequestMapping(value = "/ajaxGetDeviceList.do")
	public void ajaxGetDeviceList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				String status = request.getParameter("status");
				String socImei = request.getParameter("socImei");
				String userRole = ConstData.PLAYER_ROLE_DEFAULT;
				if (StringUtil.isNotEmptyOrNull(iemi)) {
					map.put("iemi", iemi);
				}
				if (StringUtil.isNotEmptyOrNull(status)) {
					map.put("status",  status);
				}
				if (StringUtil.isNotEmptyOrNull(socImei)) {
					map.put("socImei", socImei);
				}
				map.put("currPage", 1);
				map.put("pageSize", ConstData.PAGE_SIZE);
				Page page = deviceService.queryPageByCondition(map);
				ResponseUtil.printAjax(response, page);	
				} 
			}catch (Exception e) {
				// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//获取分配设备界面
	@RequestMapping(value = "/getDeviceUsePage.do")
	public ModelAndView getDeviceUsePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		ModelMap map = new ModelMap();
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				return new ModelAndView("deviceUse", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	//显示未分配的设备
	@RequestMapping(value = "/deviceUsePage.do")
	public void deviceUsePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				String socImei = request.getParameter("socImei");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(iemi)) {
					map.put("iemi", iemi);
				}
				if (StringUtil.isNotEmptyOrNull(socImei)) {
					map.put("socImei", socImei);
				}
				if (StringUtil.isNotEmptyOrNull(currPage)) {
					map.put("currPage", Integer.parseInt(currPage));
				} else {
					map.put("currPage", 1);
				}
				map.put("status", ConstData.DEVICE_STATUS_FREE);
				map.put("pageSize", ConstData.PAGE_SIZE);
				Page page = deviceService.queryPageByCondition(map);
				ResponseUtil.printAjax(response, page);
				}
			}catch (Exception e) {
				// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//分配设备
	@RequestMapping(value = "/ajaxDeviceDistribute.do")
	public void ajaxDeviceDistribute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		ModelMap map = new ModelMap();
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				int userId = Integer.parseInt(request.getParameter("userId"));
				deviceService.useDevice(userId, iemi);
				map.addAttribute("result", "Y");
				map.addAttribute("msg", "分配成功");
				ResponseUtil.printAjax(response, map);
				}else {
					map.put("msg", "分配失败");
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
	
	//获取‘停用报损’界面
	@RequestMapping(value = "/getDeviceTroublePage.do")
	public ModelAndView getDeviceTroublePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		ModelMap map = new ModelMap();
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				return new ModelAndView("deviceTrouble", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	//显示‘停用报损’页面
	@RequestMapping(value = "/deviceTroublePage.do")
	public void deviceTroublePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String loginRole = CookieUtil.getUserRole(cookies);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				String socImei = request.getParameter("socImei");
				int currPage = Integer.parseInt(request.getParameter("currPage"));
				if (StringUtil.isNotEmptyOrNull(iemi)) {
					map.put("iemi", iemi);
				}
				if (StringUtil.isNotEmptyOrNull(socImei)) {
					map.put("socImei", socImei);
				}
				map.put("currPage", currPage);
				map.put("pageSize", ConstData.PAGE_SIZE);
				Page page = deviceService.queryPageByCondition(map);
				ResponseUtil.printAjax(response, page);	
				} 
			}catch (Exception e) {
				// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//获取‘查询设备’界面
	@RequestMapping(value = "/getDeviceQueryPage.do")
	public ModelAndView getDeviceQueryPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelMap map = new ModelMap();
		try {
			return new ModelAndView("deviceQuery", null);
		} catch (Exception e) {
			// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.addAttribute("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	//显示‘查询设备’页面
	@RequestMapping(value = "/deviceQueryPage.do")
	public void deviceQueryPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String iemi = request.getParameter("iemi");
			String socImei = request.getParameter("socImei");
			int currPage = Integer.parseInt(request.getParameter("currPage"));
			if (StringUtil.isNotEmptyOrNull(iemi)) {
				map.put("iemi", iemi);
			}
			if (StringUtil.isNotEmptyOrNull(socImei)) {
				map.put("socImei", socImei);
			}
			map.put("currPage", currPage);
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = deviceService.queryPageByCondition(map);
			ResponseUtil.printAjax(response, page);
			}catch (Exception e) {
				// TODO: handle exception
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				ResponseUtil.printAjax(response, map);
			}
			e.printStackTrace();
		}
	}
	
	//停用设备
	@RequestMapping(value = "/ajaxDeviceOff.do")
	public void ajaxDeviceOff(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		ModelMap map = new ModelMap();
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				deviceService.stopDevice(iemi);
				map.addAttribute("result", "Y");
				map.addAttribute("msg", "停用成功");
				ResponseUtil.printAjax(response, map);
				}else {
					map.put("msg", "停用失败");
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
	
	//报损设备
	@RequestMapping(value = "/ajaxDeviceDamage.do")
	public void ajaxDeviceDamage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		String userRole = CookieUtil.getUserRole(cookies);
		ModelMap map = new ModelMap();
		try {
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String iemi = request.getParameter("iemi");
				deviceService.deleteDevice(iemi);
				map.addAttribute("result", "Y");
				map.addAttribute("msg", "停用成功");
				ResponseUtil.printAjax(response, map);
				}else {
					map.put("msg", "停用失败");
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
	
	//获取‘选择员工’页面
	@RequestMapping(value = "/getDeviceToWorker.do")
	public ModelAndView getDeviceToWorker(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				map.put("iemi", request.getParameter("iemi"));
				return new ModelAndView("deviceToWorker", map);
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
	}
	
	//显示‘选择员工’页面
	@RequestMapping(value = "/ajaxDeviceToWorker.do")
	public void ajaxDeviceToWorker(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String loginRole = CookieUtil.getUserRole(cookies);
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String userName = request.getParameter("userName");
				String userAddr = request.getParameter("userAddr");
				String userStatus = request.getParameter("userStatus");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(userName)) {
					map.put("userName",  userName );
				}
				if (StringUtil.isNotEmptyOrNull(userAddr)) {
					map.put("userAddr",  userAddr );
				}
				if (StringUtil.isNotEmptyOrNull(userStatus)) {
					map.put("userStatus", userStatus);
				}
				if (StringUtil.isNotEmptyOrNull(currPage)) {
					map.put("currPage", Integer.parseInt(currPage));
				} else {
					map.put("currPage", 1);
				}
				map.put("userRole", ConstData.WORKER_ROLE_DEFAULT);
				map.put("userStatus", ConstData.USER_STATUS_AUTH);
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
	
	//获取‘选择评委’页面
	@RequestMapping(value = "/getDeviceToJudge.do")
	public ModelAndView getDeviceToJudge(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			if (userRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				map.put("iemi", request.getParameter("iemi"));
				return new ModelAndView("deviceToJudge", map);
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
	}
	
	//显示‘选择评委’页面
	@RequestMapping(value = "/ajaxDeviceToJudge.do")
	public void ajaxDeviceToJudge(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String loginRole = CookieUtil.getUserRole(cookies);
			if (loginRole.equals(ConstData.ADMIN_ROLE_DEFAULT)) {
				String userName = request.getParameter("userName");
				String userAddr = request.getParameter("userAddr");
				String userStatus = request.getParameter("userStatus");
				String currPage = request.getParameter("currPage");
				if (StringUtil.isNotEmptyOrNull(userName)) {
					map.put("userName",  userName );
				}
				if (StringUtil.isNotEmptyOrNull(userAddr)) {
					map.put("userAddr",  userAddr );
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
				map.put("userStatus", ConstData.USER_STATUS_AUTH);
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
	
}