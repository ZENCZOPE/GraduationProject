package com.cms.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.pojo.Ad;
import com.cms.pojo.ConstData;
import com.cms.pojo.Page;
import com.cms.service.AdService;
import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.ResponseUtil;
import com.cms.util.StringUtil;

@Controller
@RequestMapping(value="/ad")
public class AdController {
	@Resource
	private AdService adService;
	
	// 获得添加广告页面
	@RequestMapping(value = "/getAddAccountPage.do")
	public ModelAndView getAddAccountPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
			return new ModelAndView("addAd", null);
	}
	
	//添加广告
	@RequestMapping(value="/addAd.do")
	public void addAcc(HttpServletRequest request,HttpServletResponse response) {
		Map< String, Object> map=new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		
		try {
			String adTitle = request.getParameter("adTitle");
			String adNote = request.getParameter("adNote");
			String adAddr = request.getParameter("adAddr");
			String adPrice = request.getParameter("adPrice");
			String adOwner = request.getParameter("adOwner");
			String adEffectTime = request.getParameter("adEffectTime");
			String adEndTime = request.getParameter("adEndTime");
			
			Ad ad = new Ad();
			int opId=CookieUtil.getUserId(cookies);
			ad.setAdTitle(adTitle);
			ad.setAdNote(adNote);
			ad.setAdAddr(adAddr);
			ad.setAdPrice(adPrice);
			ad.setAdOwner(adOwner);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			ad.setAdEffectTime(sim.parse(adEffectTime));
			ad.setAdEndTime(sim.parse(adEndTime));
			ad.setOpId(opId);
			adService.addAd(ad);
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
	
	// 获得‘修改停用’页面
	@RequestMapping(value = "/getAdManagePage.do")
	public ModelAndView getAdManagePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return new ModelAndView("adManage", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 显示‘修改停用’页面
	@RequestMapping(value = "/adList.do")
	public void adList(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String adTitle = request.getParameter("adTitle");
			String adNote = request.getParameter("adNote");
			String adOwner = request.getParameter("adOwner");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(adTitle)) {
				map.put("adTitle", adTitle);
			}
			if (StringUtil.isNotEmptyOrNull(adNote)) {
				map.put("adNote", adNote);
			}
			if (StringUtil.isNotEmptyOrNull(adOwner)) {
				map.put("adOwner", adOwner);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = adService.queryPageByCondition(map);
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
	
	// 获得‘查询广告’页面
	@RequestMapping(value = "/getAdQueryPage.do")
	public ModelAndView getAdQueryPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				return new ModelAndView("adQuery", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 显示‘查询广告’页面
	@RequestMapping(value = "/adQuery.do")
	public void adQuery(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String adTitle = request.getParameter("adTitle");
			String adNote = request.getParameter("adNote");
			String adOwner = request.getParameter("adOwner");
			String currPage = request.getParameter("currPage");
			if (StringUtil.isNotEmptyOrNull(adTitle)) {
				map.put("adTitle", adTitle);
			}
			if (StringUtil.isNotEmptyOrNull(adNote)) {
				map.put("adNote", adNote);
			}
			if (StringUtil.isNotEmptyOrNull(adOwner)) {
				map.put("adOwner", adOwner);
			}
			if (StringUtil.isNotEmptyOrNull(currPage)) {
				map.put("currPage", Integer.parseInt(currPage));
			}else {
				map.put("currPage", 1);
			}
			map.put("pageSize", ConstData.PAGE_SIZE);
			Page page = adService.queryPageByCondition(map);
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
	
	// 获得‘广告修改’界面
	@RequestMapping(value = "/getAdChange.do")
	public ModelAndView getAdChange(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int adId = Integer.parseInt(request.getParameter("adId"));
			Ad ad = adService.getAdMsg(adId); 
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String adEffectTime =sim.format(ad.getAdEffectTime());
			String adEndTime =sim.format(ad.getAdEndTime());
			map.put("adEffectTime", adEffectTime);
			map.put("adEndTime", adEndTime);
			map.put("ad", ad);
			return new ModelAndView("adChange", map);
		} catch (Exception e) {
			if (e.getClass() == CMSException.class) {
				map.put("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
			e.printStackTrace();
		}
		return new ModelAndView("error", null);
	}
	
	// 修改广告
	@RequestMapping(value = "/adChange.do")
	public void adChange(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			int opId = CookieUtil.getUserId(cookies);
			Ad ad = new Ad();
			ad.setOpId(opId);
			ad.setAdId(Integer.parseInt(request.getParameter("adId")));
			ad.setAdTitle(request.getParameter("adTitle"));
			ad.setAdNote(request.getParameter("adNote"));
			ad.setAdAddr(request.getParameter("adAddr"));
			ad.setAdPrice(request.getParameter("adPrice"));
			ad.setAdOwner(request.getParameter("adOwner"));
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			ad.setAdEffectTime(sim.parse(request.getParameter("adEffectTime")));
			ad.setAdEndTime(sim.parse(request.getParameter("adEndTime")));
			adService.updateAdAllMsg(ad);
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
	
	//停用广告
	@RequestMapping(value = "/adStop.do")
	public void adStop(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Cookie[] cookies = request.getCookies();
			int opId = CookieUtil.getUserId(cookies);
			Ad ad = new Ad();
			int adId = Integer.parseInt(request.getParameter("adId"));
			ad.setAdId(adId);
			ad.setOpId(opId);
			adService.stopAd(ad);
			map.put("msg","停用成功!");
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