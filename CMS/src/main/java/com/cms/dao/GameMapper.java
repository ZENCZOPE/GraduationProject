package com.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.cms.pojo.Game;

@Service
public interface GameMapper {
	int deleteByPrimaryKey(Integer idNo);

	/**
	 * 插入赛程
	 * 
	 * @param record
	 * @return
	 */
	int insert(Game record);

	int insertSelective(Game record);

	Game selectByPrimaryKey(Integer idNo);

	int updateByPrimaryKeySelective(Game record);

	int updateByPrimaryKey(Game record);

	List<Game> queryByStatus(@Param("gameStatus") String status);

	int queryCountByCondition(Map<String, Object> map);

	List<Game> queryByCondition(Map<String, Object> map);

	List<Game> queryPageByCondition(Map<String, Object> map);
}