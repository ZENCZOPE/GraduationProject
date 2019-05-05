package com.cms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.AdMapper;
import com.cms.pojo.Account;
import com.cms.pojo.Ad;
import com.cms.pojo.ConstData;
import com.cms.pojo.Page;
import com.cms.util.CMSException;
import com.cms.util.StringUtil;

@Service
public class AdService {

	@Resource
	private AdMapper adMapper;
	@Resource
	private AccountService accountService;
	/**
	 * 获取ad信息
	 * @param id
	 * @return
	 */
	public Ad getAdMsg(int id){
		return adMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 分页查询广告
	 * @param map
	 * @return
	 */
	public Page queryPageByCondition(Map<String, Object> map){
		int currPage = (Integer) map.get("currPage");
		int pageSize = (Integer) map.get("pageSize");
		int count=adMapper.queryCountByCondition(map);
		Page page=new Page(count, pageSize, currPage);
		if (currPage <= 0) {
			currPage = 1;
		}
		map.put("index", page.getIndex());
		List<Ad> list=adMapper.queryPageByCondition(map);
		page.setList(list);
		return page;
	}
	/**
	 * 添加广告
	 * @param ad
	 */
	private void createAd(Ad ad) {
		ad.setAdStatus(ConstData.AD_STATUS_NEW);
		adMapper.insert(ad);
	}
	
	/**
	 * 修改广告信息
	 * @param ad
	 * @throws Exception 
	 */
	public void updateAdMsg(Ad ad) throws Exception {
		ad.setOpTime(new Date());
		Account newAccount=new Account();
		if(StringUtil.isNotEmptyOrNull(ad.getAdPrice())){
			newAccount.setPrice(Double.valueOf(ad.getAdPrice()));
		}
		if(StringUtil.isNotEmptyOrNull(ad.getAdTitle())){
			newAccount.setBillNote(ad.getAdTitle());
		}
		if(StringUtil.isNotEmptyOrNull(ad.getAdStatus())){
		if (ad.getAdStatus().equals(ConstData.AD_STATUS_DIED)) {
			newAccount.setStatus(ConstData.ACC_STATUS_DIED);
		}}
		if (StringUtil.isNotEmptyOrNull(ad.getAdEffectTime())) {
			newAccount.setEffectTime(ad.getAdEffectTime());
		}
		if(StringUtil.isNotEmptyOrNull(newAccount.getEffectTime())||StringUtil.isNotEmptyOrNull(newAccount.getStatus())||StringUtil.isNotEmptyOrNull(newAccount.getBillNote())||StringUtil.isNotEmptyOrNull(newAccount.getPrice())){
			newAccount.setBillId(ad.getAdId()*1000);
			accountService.updateAccountMsg(newAccount);
		}
		adMapper.updateByPrimaryKeySelective(ad);
	}
	
	/**
	 * 停用广告
	 * @param adId
	 * @throws Exception 
	 */
	public void stopAd(Ad ad) throws Exception {
		if (StringUtil.isEmptyOrNull(ad.getAdId())) {
			throw new CMSException("广告ID不能为空！");
		}
		if (StringUtil.isEmptyOrNull(ad.getOpId())) {
			throw new CMSException("操作工号不能为空！");
		}
		ad=adMapper.selectByPrimaryKey(ad.getAdId());
		if (!ad.getAdStatus().equals(ConstData.AD_STATUS_NEW)) {
			throw new CMSException("只有未生效的广告才能停用");
		}
		ad.setAdStatus(ConstData.AD_STATUS_DIED);
		this.updateAdMsg(ad);
	}
	/**
	 * 修改广告基本信息
	 * @param ad
	 * @throws Exception 
	 */
	public void updateAdBaseMsg(Ad ad) throws Exception{
		Ad ad_=new Ad();
		ad_.setAdId(ad.getAdId());
		if (StringUtil.isEmptyOrNull(ad.getOpId())) {
			throw new CMSException("操作工号不能为空！");
		}
		Ad ad2=adMapper.selectByPrimaryKey(ad.getAdId());
		if (!ad2.getAdStatus().equals(ConstData.AD_STATUS_NEW)) {
			throw new CMSException("只有未生效的广告才能被修改");
		}
		ad_.setOpId(ad.getOpId());
		if (StringUtil.isNotEmptyOrNull(ad.getAdNote())) {
			ad_.setAdNote(ad.getAdNote());
		}
		if (StringUtil.isNotEmptyOrNull(ad.getAdTitle())) {
			ad_.setAdTitle(ad.getAdTitle());
		}
		this.updateAdMsg(ad_);
	}
	/**
	 * 修改广告所有信息
	 * @param ad
	 * @throws Exception
	 */
	public void updateAdAllMsg(Ad ad) throws Exception {
		if (StringUtil.isEmptyOrNull(ad.getOpId())) {
			throw new CMSException("操作工号不能为空！");
		}
		Ad ad2=adMapper.selectByPrimaryKey(ad.getAdId());
		if (!ad2.getAdStatus().equals(ConstData.AD_STATUS_NEW)) {
			throw new CMSException("只有未生效的广告才能被修改");
		}
		this.updateAdMsg(ad);
	}
	
	/**
	 * 新建广告
	 * @param newAd
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void addAd(Ad newAd) throws Exception {
		//根据广告位置查询所有数据
		Ad ad=new Ad();
		ad.setAdAddr(newAd.getAdAddr());
		List<Ad> list=	adMapper.queryByCondition(ad);
		for (Ad oldAd : list) {
			if(newAd.getAdEndTime().getTime()>=oldAd.getAdEffectTime().getTime()&&newAd.getAdEndTime().getTime()<=oldAd.getAdEndTime().getTime()){
				throw new CMSException("该广告与广告id="+oldAd.getAdId()+"冲突，请修改数据后重新提交！");
			}else if(newAd.getAdEffectTime().getTime()>=oldAd.getAdEffectTime().getTime()&&newAd.getAdEffectTime().getTime()<=oldAd.getAdEndTime().getTime()){
				throw new CMSException("该广告与广告id="+oldAd.getAdId()+"冲突，请修改数据后重新提交！");
			}
		}
		List<Ad> ads=this.queryByCondition(newAd);
		if (ads.size()!=0) {
			throw new CMSException("广告信息相同！");
		}
		this.createAd(newAd);
		ads=this.queryByCondition(newAd);
		if (ads.size()!=1) {
			throw new CMSException("广告信息相同！");
		}
		Account account=new Account();
		account.setOpId(ConstData.SYSTEM_ID);
		account.setBillType("广告");
		account.setEffectTime(newAd.getAdEffectTime());
		account.setBillNote(newAd.getAdTitle());
		account.setPrice(Double.valueOf(newAd.getAdPrice()));
		account.setBillId(1000*ads.get(0).getAdId());
		accountService.addAccount(account);
	}
	
	public List<Ad> queryByCondition(Ad ad) {
		return adMapper.queryByCondition(ad);
	}
}
