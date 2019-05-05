package com.cms.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface SqlMapper {
	public Map<String, Object> checkLogin(Map<String, Object> map) ;
}
