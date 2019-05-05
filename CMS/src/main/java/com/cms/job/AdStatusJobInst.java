package com.cms.job;



import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cms.pojo.Ad;
import com.cms.pojo.ConstData;
import com.cms.service.AdService;

@Service
public class AdStatusJobInst {
		Logger logger=Logger.getLogger(AdStatusJobInst.class);
		
		@Resource
		private AdService adService;
		
		@Scheduled(cron = "1  *  *  *  *  ?")
		public void run() {
			logger.info("开始定时任务：Ad状态自动更新定时任务");
			try {
				checkAdEffectTime();
				checkAdEndTime();
				logger.info("结束定时任务：Ad状态自动更新定时任务");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		
		private void checkAdEffectTime() throws Exception {
			//获取所有新建状态的广告
			Ad ad=new Ad();
			ad.setAdStatus(ConstData.AD_STATUS_NEW);
			List<Ad> list=adService.queryByCondition(ad);
			
			//遍历所有广告 判断广告是否已生效
			for (Ad ad2 : list) {
				if(System.currentTimeMillis()>ad2.getAdEffectTime().getTime()){
					Ad ad3=new Ad();
					ad3.setAdId(ad2.getAdId());
					ad3.setAdStatus(ConstData.AD_STATUS_KEEP);
					logger.info("广告id="+ad2.getAdId()+"已生效");
					adService.updateAdMsg(ad3);
				}
			}
		}
		
		private void checkAdEndTime() throws Exception {
			//获取所有生效状态的广告
			Ad ad=new Ad();
			ad.setAdStatus(ConstData.AD_STATUS_KEEP);
			List<Ad> list=adService.queryByCondition(ad);
			
			//遍历所有广告 判断广告是否已失效
			for (Ad ad2 : list) {
				if(System.currentTimeMillis()>ad2.getAdEndTime().getTime()){
					Ad ad3=new Ad();
					ad3.setAdId(ad2.getAdId());
					ad3.setAdStatus(ConstData.AD_STATUS_END);
					logger.info("广告id="+ad2.getAdId()+"已失效");
					adService.updateAdMsg(ad3);
				}
			}
		}
}
