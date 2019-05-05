package com.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cms.pojo.Account;

@Service
public interface AccountMapper {
    int deleteByPrimaryKey(Integer billId);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer billId);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
    
    int queryCountByCondition(Map<String , Object> map);
    
    List<Account> queryPageByCondition(Map<String , Object> map);
    
    List<Account> queryByCondition(Account account);
    
    List<Account> queryByCondition2(Map< String, Object> map);
}