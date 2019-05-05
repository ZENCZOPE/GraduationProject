package com.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.cms.pojo.User;

@Service
public interface UserMapper {
	/**
	 * 注销用户
	 * 
	 * @param userId
	 * @return
	 */
	void deleteByPrimaryKey(Integer userId);

	/**
	 * 根据用户名查询数据
	 * 
	 * @param user
	 * @return
	 */
	List<User> queryByIdcard(User user);

	/**
	 * 插入用户数据
	 * 
	 * @param record
	 * @return
	 */
	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	/**
	 * 更新用户数据
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	/**
	 * 分页查询user
	 * 
	 * @param index
	 * @param pageSize
	 * @return
	 */
	List<User> viewPage(@Param("index") int index,
			@Param("pageSize") int pageSize);

	/**
	 * 按照等级查询
	 * 
	 * @param level
	 * @return
	 */
	List<User> selectByLevel(@Param("level") int level);

	/**
	 * 获取user数据总和
	 * 
	 * @return
	 */
	int viewCount();
	
	//条件查询满足条件的用户数量
	int queryUserCountByCondition(Map<String, Object> map);
	
	//条件查询满足条件的用户记录
	List<User> queryUserListByCondition(Map<String, Object> map);
	
	List<User> queryByCondition(Map<String, Object> map);
}