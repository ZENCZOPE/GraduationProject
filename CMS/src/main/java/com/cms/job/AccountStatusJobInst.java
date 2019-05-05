package com.cms.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cms.pojo.Account;
import com.cms.pojo.ConstData;
import com.cms.service.AccountService;

@Service
public class AccountStatusJobInst {

	Logger logger=Logger.getLogger(AccountStatusJobInst.class);
	
	@Resource
	private AccountService accountService;
	
	@Scheduled(cron = "1  *  *  *  *  ?")
	public void run() {
		logger.info("开始定时任务：Ad状态自动更新定时任务");
		try {
			checkAccountEffectTime();
			logger.info("结束定时任务：Ad状态自动更新定时任务");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void checkAccountEffectTime() throws Exception {
		//获取所有新建状态的账单
		Account account=new Account();
		account.setStatus(ConstData.ACC_STATUS_NEW);
		List<Account> list=accountService.queryByCondition(account);
		
		//遍历所有账单 判断账单是否已生效
		for (Account account2 : list) {
			if(System.currentTimeMillis()>account2.getEffectTime().getTime()){
				account2.setStatus(ConstData.ACC_STATUS_KEEP);
				logger.info("账单id="+account2.getBillId()+"已生效");
				accountService.updateAccountMsg(account2);
			}
		}
	}
	
	
}
