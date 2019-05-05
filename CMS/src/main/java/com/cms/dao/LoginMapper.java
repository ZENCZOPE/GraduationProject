package com.cms.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.pojo.Login;
@Service
public interface LoginMapper {
    int deleteByPrimaryKey(Integer idNo);
    /**
     * 插入登陆数据
     * @param record
     * @return
     */
    int insert(Login record);

    int insertSelective(Login record);

    Login selectByPrimaryKey(Integer idNo);
    
    Login queryByName(Login login);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);
    /**
     * 根据登陆名密码查询
     * @param login
     * @return
     */
    List<Login> queryByLogin(Login login);
}