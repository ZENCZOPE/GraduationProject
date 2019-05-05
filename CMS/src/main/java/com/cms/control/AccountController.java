package com.cms.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.Account;
import com.cms.pojo.ConstData;
import com.cms.pojo.Page;
import com.cms.service.AccountService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	@Resource
	private AccountService accountService;
	
	// 获得添加账单页面
	@RequestMapping(value = "/getAddAccountPage.do")
	public ModelAndView getAddAccountPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
			return new ModelAndView("addAccount", null);
	}
	
	@RequestMapping(value="/addAcc.do")
	public void addAcc(HttpServletRequest request,HttpServletResponse response) {
		Map< String, Object> map=new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		
		try {
			String effectTimeStr = request.getParameter("effectTime");
			String loginName = CookieUtil.getUserName(cookies);
			String loginRole = CookieUtil.getUserRole(cookies);
			int loginId=CookieUtil.getUserId(cookies);
			Account account=new Account();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date effectTime=sim.parse(effectTimeStr);
			account.setBillNote(request.getParameter("billNote"));
			account.setBillType(request.getParameter("billType"));
			account.setEffectTime(effectTime);
			account.setPrice(Double.parseDouble(request.getParameter("price")));
			account.setOpId(loginId);
			accountService.addAccount(account);
			map.put("result", "Y");
			map.put("msg", "添加成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}		
		ResponseUtil.printAjax(response, map);
	}
	
	// 获得‘修改作废’页面
	@RequestMapping(value = "/getAccListPage.do")
	public ModelAndView getAccListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return new ModelAndView("accList", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	//显示‘修改作废’页面
	@RequestMapping(value="/queryAccList.do")
	public void queryAccList(HttpServletRequest request,HttpServletResponse response) {
		Map< String, Object> map=new HashMap<String, Object>();
		try {
			String billType = request.getParameter("billType");
			String billNote = request.getParameter("billNote");
			String status = request.getParameter("status");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(billType)) {
				map.put("billType", billType);
			}
			if (StringUtil.isNotEmptyOrNull(billNote)) {
				map.put("billNote", billNote);
			}
			if (StringUtil.isNotEmptyOrNull(status)) {
				map.put("status", status);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			map.put("status", ConstData.ACC_STATUS_NEW);
			Page page=accountService.queryPageByCondition(map);
			map.put("page", page);
			map.put("result", "Y");
			map.put("msg", "添加成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}
		ResponseUtil.printAjax(response, map);
	}
	
	// 获得‘财务统计’页面
	@RequestMapping(value = "/getFinancePage.do")
	public ModelAndView getFinancePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			String userName = CookieUtil.getUserName(cookies);
			return new ModelAndView("finance", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	@RequestMapping(value="/financeList.do")
	public void financeList(HttpServletRequest request,HttpServletResponse response) {
		Map< String, Object> map=new HashMap<String, Object>();
		try {
			String billType = request.getParameter("billType");
			String billNote = request.getParameter("billNote");
			String opId = request.getParameter("opId");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(billType)) {
				map.put("billType", billType);
			}
			if (StringUtil.isNotEmptyOrNull(billNote)) {
				map.put("billNote", billNote);
			}
			if (StringUtil.isNotEmptyOrNull(opId)) {
				map.put("opId", opId);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			/*map.put("status", ConstData.ACC_STATUS_END);*/
			Map<String, Object> map2 = accountService.statistics(map);
			Object page = map2.get("page");
			Object pay = map2.get("pay");
			Object income = map2.get("income");
			Object sum = map2.get("sum");
			map.put("page", page);
			map.put("pay", pay);
			map.put("income", income);
			map.put("sum", sum);
			map.put("result", "Y");
			map.put("msg", "添加成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}
		ResponseUtil.printAjax(response, map);
	}
	
	// 获得‘账单修改’界面
	@RequestMapping(value = "/getAccChangePage.do")
	public ModelAndView getAccChangePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			int billId = Integer.parseInt(request.getParameter("billId"));
			Account account = accountService.getAccountMsg(billId); 
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String effectTime =sim.format(account.getEffectTime());
			String opTime =sim.format(account.getOpTime());
			map.put("account", account);
			map.put("effectTime", effectTime);
			map.put("opTime", opTime);
			return new ModelAndView("accChange", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	@RequestMapping(value="/updateAcc.do")
	public void updateAcc(HttpServletRequest request,HttpServletResponse response) {
		Map< String, Object> map=new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		
		try {
			String loginName = CookieUtil.getUserName(cookies);
			String loginRole = CookieUtil.getUserRole(cookies);
			int loginId=CookieUtil.getUserId(cookies);
			String billType = request.getParameter("billType");
			Double price = Double.parseDouble(request.getParameter("price"));
			String billNote = request.getParameter("billNote");
			int opId = Integer.parseInt(request.getParameter("opId"));
			String effectTimeStr = request.getParameter("effectTime");
			String status = request.getParameter("status");
			int billId = Integer.parseInt(request.getParameter("billId"));
			Account account=new Account();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date effectTime=sim.parse(effectTimeStr);
			account.setBillId(billId);
			account.setBillNote(billNote);
			account.setBillType(billType);
			account.setEffectTime(effectTime);
			account.setPrice(price);
			account.setOpId(billId);
			account.setOpId(opId);
			account.setStatus(status);
			accountService.updateAccountMsg(account);
			map.put("result", "Y");
			map.put("msg", "修改成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}
		ResponseUtil.printAjax(response, map);
	}
	
	@RequestMapping(value="/stopAcc.do")
	public void stopAcc(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int billId) {
		Map< String, Object> map=new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		
		try {
			String loginName = CookieUtil.getUserName(cookies);
			String loginRole = CookieUtil.getUserRole(cookies);
			int loginId=CookieUtil.getUserId(cookies);
			Account account=new Account();
			account.setBillId(billId);
			account.setOpId(loginId);
			accountService.stopAccount(account);
			map.put("result", "Y");
			map.put("msg", "操作成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}
		ResponseUtil.printAjax(response, map);
	}
	
	@RequestMapping(value="/sumAcc.do")
	public void sum(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int currPage,
			@RequestParam int billId,
			@RequestParam String billNote,
			@RequestParam double price,
			@RequestParam String billType,
			@RequestParam String status,
			@RequestParam int opId,
			@RequestParam String endTime,
			@RequestParam String startTime) {
		Map< String, Object> map=new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		
		try {
			map.put("currPage", currPage);
			map.put("pageSize", ConstData.PAGE_SIZE);
			map.put("billId",billId );
			map.put("billNote", billNote);
			map.put("price",price );
			map.put("billType", billType);
			map.put("status", status);
			map.put("opId", opId);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate=sim.parse(startTime.replace("T", " "));
			Date endDate=sim.parse(endTime.replace("T", " "));
			map.put("startTime",startDate);
			map.put("endTime",endDate );
			map=accountService.statistics(map);
			map.put("result", "Y");
			map.put("msg", "添加成功");
		} catch (Exception e) {
			if (e.getClass().equals(CMSException.class)) {
				map.put("result", "N");
				map.put("msg", e.getMessage());
			}else{
				map.put("result", "N");
				map.put("msg", "后台调用失败，请联系管理员");
			}
			e.printStackTrace();
		}		
	}
}
