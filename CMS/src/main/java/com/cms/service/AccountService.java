package com.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cms.dao.AccountMapper;
import com.cms.pojo.Account;
import com.cms.pojo.ConstData;
import com.cms.pojo.Page;
import com.cms.util.CMSException;
import com.cms.util.StringUtil;

@Service
public class AccountService {
		@Resource
		private AccountMapper accountMapper;
		
		/**
		 * 条件查询
		 * @param account
		 * @return
		 */
		public List<Account> queryByCondition(Account	account) {
			return accountMapper.queryByCondition(account);
		}
		
		/**
		 * 条件查询
		 * @param account
		 * @return
		 */
		public List<Account> queryByCondition2(Map<String, Object> map) {
			return accountMapper.queryByCondition2(map);
		}
		
		/**
		 * 获取账单数据
		 * @param accountId
		 * @return
		 */
		public Account getAccountMsg(int accountId){
			return accountMapper.selectByPrimaryKey(accountId);
		}
		
		/**
		 * 更新账单数据
		 * @param account
		 * @throws Exception 
		 */
		public void updateAccountMsg(Account account) throws Exception{
			Account oldAccount=this.getAccountMsg(account.getBillId());
			if(oldAccount==null){
				throw new CMSException("未找到原账单数据！");
			}
			if(!oldAccount.getStatus().equals(ConstData.ACC_STATUS_NEW)){
				throw new CMSException("只能修改未生效的账单！");
			}
			account.setOpTime(new Date());
			accountMapper.updateByPrimaryKeySelective(account);
		}
		/**
		 * 分页查询
		 * @param map
		 * @return
		 */
		public Page queryPageByCondition(Map<String, Object> map){
			int currPage = (Integer) map.get("currPage");
			int pageSize = (Integer) map.get("pageSize");
			int count=accountMapper.queryCountByCondition(map);
			Page page=new Page(count, pageSize, currPage);
			if (currPage <= 0) {
				currPage = 1;
			}
			map.put("index", page.getIndex());
			List<Account> list=accountMapper.queryPageByCondition(map);
			page.setList(list);
			return page;
		}
		
		/**
		 * 新增账单
		 * @param account
		 * @throws Exception
		 */
		public void addAccount(Account account) throws Exception {
			account.setOpTime(new Date());
			if(StringUtil.isEmptyOrNull(account.getOpId())){
				throw new CMSException("工号不能为空！");
			}
			account.setStatus(ConstData.ACC_STATUS_NEW);
			accountMapper.insertSelective(account);
		}
		
		/**
		 * 账单作废
		 * @throws Exception 
		 */
		public void stopAccount(Account account) throws Exception {
			if(StringUtil.isEmptyOrNull(account.getOpId())){
				throw new CMSException("操作工号不能为空！");
			}
			Account oldAccount=this.getAccountMsg(account.getBillId());
			if(!oldAccount.getStatus().equals(ConstData.ACC_STATUS_NEW)){
				throw new CMSException("只有未生效的账单才能作废！");
			}
			if(!oldAccount.getOpId().equals(account.getOpId())){
				throw new CMSException("操作工号不一致，只能操作自己创建的数据！");
			}
			account.setStatus(ConstData.ACC_STATUS_DIED);
			this.updateAccountMsg(account);
		}
		/**
		 * 计算收益
		 * @param map
		 * @return
		 */
		public Map<String, Object> statistics(Map< String, Object> map ){
			Map<String, Object> resMap=new HashMap<String, Object>();
			Page page=this.queryPageByCondition(map);
			resMap.put("page", page);
			List<Account> list=this.queryByCondition2(map);
			List<Account> newList=new ArrayList<Account>();
			for (Account account : list) {
				if(!account.getStatus().equals(ConstData.ACC_STATUS_DIED)){
					newList.add(account);
				}
			}
			resMap.put("pay", 0-this.getPay(newList));
			resMap.put("income", this.getIncome(newList));
			resMap.put("sum", this.getPay(newList)+this.getIncome(newList));
			return resMap;
		}
		
		/**
		 * 计算支出
		 * @param list
		 * @return
		 */
		private double getPay(List<Account> list){
			double sum=0.0;
			for (Account account : list) {
				if(account.getPrice()<0){
					sum+=account.getPrice();
				}
			}
			return sum;
		}
		
		/**
		 * 计算收入
		 * @param list
		 * @return
		 */
		private double getIncome(List<Account> list){
			double sum=0.0;
			for (Account account : list) {
				if(account.getPrice()>0){
					sum+=account.getPrice();
				}
			}
			return sum;
		}
		
}
