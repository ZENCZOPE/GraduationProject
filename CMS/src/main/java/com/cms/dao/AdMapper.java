package com.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cms.pojo.Ad;
@Service
public interface AdMapper {
    int deleteByPrimaryKey(Integer adId);

    int insert(Ad record);

    int insertSelective(Ad record);

    Ad selectByPrimaryKey(Integer adId);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);

    int queryCountByCondition(Map<String , Object> map);
    
    List<Ad> queryPageByCondition(Map<String , Object> map);
    
    List<Ad> queryByCondition(Ad ad);
}