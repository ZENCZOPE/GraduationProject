package com.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cms.pojo.Device;
@Service
public interface DeviceMapper {
    int deleteByPrimaryKey(String iemi);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(String iemi);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
    
    int stopDevice (Device record);
    
    int queryCountByCondition(Map<String, Object> map);
    
    List<Device> queryPageByCondition(Map<String, Object> map);
}