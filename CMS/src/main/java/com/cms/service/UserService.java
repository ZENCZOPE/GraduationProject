package com.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cms.dao.UserMapper;
import com.cms.pojo.ConstData;
import com.cms.pojo.Login;
import com.cms.pojo.Page;
import com.cms.pojo.User;
import com.cms.pojo.UserBO;
import com.cms.util.CMSException;
import com.cms.util.MD5Util;
import com.cms.util.StringUtil;

@Service
public class UserService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private LoginService loginService;

	/**
	 * 批量生成用户pojo
	 * 
	 * @return
	 * @throws Exception
	 */
	public void addUsersByList(List<String[]> list) throws Exception {
		List<User> list2 = new ArrayList<User>();
		for (String[] strings : list) {
			/**
			 * 批量新增用户模板 登录名、年龄、电话、地址、邮箱、身份证、角色
			 */
			if (StringUtil.isEmptyOrNull(strings[0])) {
				throw new CMSException("登录名为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[1])) {
				throw new CMSException("年龄为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[2])) {
				throw new CMSException("电话为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[3])) {
				throw new CMSException("地址为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[4])) {
				throw new CMSException("邮箱为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[5])) {
				throw new CMSException("身份证为必填项！");
			}
			if (StringUtil.isEmptyOrNull(strings[6])) {
				throw new CMSException("角色为必填项！");
			}
			User user = new User();
			user.setUserName(strings[0]);
			user.setUserAge(strings[1]);
			user.setUserPhone(strings[2]);
			user.setUserAddr(strings[3]);
			user.setUserMail(strings[4]);
			user.setUserIdcard(strings[5]);
			user.setUserRole(strings[6]);
			user.setUserStatus(ConstData.USER_STATUS_DEFAULT);
			list2.add(user);
		}
		this.addUsers(list2);
	}

	/**
	 * 批量新增用户
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void addUsers(List<User> list) throws Exception {
		for (User user : list) {
			this.addUser(user);
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void addUser(User user) throws Exception {
		Login login = new Login();
		login.setLoginName(user.getUserName());
		login.setLoginPw(MD5Util.getMD5(this.getLast6(user)));
		login.setCreatTime(new Date(System.currentTimeMillis()));
		login.setOpTime(new Date(System.currentTimeMillis()));
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("userIdcard", user.getUserIdcard());
		List<User> list=this.queryByCondition(map);
		// 校验身份证是否有重复
		if (list.size()!=0) {
			throw new CMSException("你已经注册过了！");
		}
		int idNo = loginService.createLogin(login);
		user.setUserId(idNo);
		user.setUserStatus(ConstData.USER_STATUS_DEFAULT);
		this.createUser(user);
	}
	
	/**
	 * 新增选手
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void addPlayer(Map<String, Object> map) throws Exception {
		User user=(User) map.get("user");
		if (user==null) {
			throw new CMSException("user对象为空！ 请联系管理员！");
		}
		String password=(String) map.get("password");
		if(StringUtil.isEmptyOrNull(password)){
			throw new CMSException("密码不能为空！");
		}
		Login login = new Login();
		login.setLoginName(user.getUserName());
		login.setCreatTime(new Date(System.currentTimeMillis()));
		login.setOpTime(new Date(System.currentTimeMillis()));
		login.setLoginPw(password);
		//Map<String , Object> map=new HashMap<String, Object>();
		map.put("userIdcard", user.getUserIdcard());
		List<User> list=this.queryByCondition(map);
		// 校验身份证是否有重复
		if (list.size()!=0) {
			throw new CMSException("你已经注册过了！");
		}
		int idNo = loginService.createLogin(login);
		user.setUserId(idNo);
		user.setUserStatus(ConstData.USER_STATUS_DEFAULT);
		this.createUser(user);
	}

	/**
	 * 插入用户数据
	 * 
	 * @param user
	 */
	private void createUser(User user) {
		userMapper.insert(user);
	}

	/**
	 * 获取用户身份证后6位数
	 * 
	 * @param user
	 * @return
	 */
	public String getLast6(User user) {
		String a = user.getUserIdcard().substring(
				user.getUserIdcard().length() - 6,
				user.getUserIdcard().length());
		return a;
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public User getUserMsg(User user) throws Exception {
		User user2 = new User();
		if (StringUtil.isNotEmptyOrNull(user.getUserId())) {
			user2 = userMapper.selectByPrimaryKey(user.getUserId());

		} else if (StringUtil.isNotEmptyOrNull(user.getUserIdcard())) {
			List<User> users = userMapper.queryByIdcard(user);
			if(users.size()>1){
				throw new CMSException("该用户数据异常，请联系管理员！");
			}
			user2=users.get(0);
		} else {
			throw new CMSException("查询条件不足");
		}

		return user2;
	}

	/**
	 * 分页查询用户
	 * 
	 * @param map
	 * @return
	 */
	public Page viewUser(Map<String, Object> map) throws Exception {
		int currPage = (Integer) map.get("currPage");
		int pageSize = (Integer) map.get("pageSize");
		int totalrecord =userMapper.queryUserCountByCondition(map);
		Page page = new Page(totalrecord, pageSize, currPage);
		map.put("index", page.getIndex());
		List<User> list = userMapper.queryUserListByCondition(map);
		page.setList(list);
		return page;
	}
	
	/**
	 * 分页查询用户
	 * 
	 * @param map
	 * @return
	 */
	public Page viewCertifyList(Map<String, Object> map) throws Exception {
		int currPage = (Integer) map.get("currPage");
		int pageSize = (Integer) map.get("pageSize");
		int totalrecord =userMapper.queryUserCountByCondition(map);
		Page page = new Page(totalrecord, pageSize, currPage);
		map.put("index", page.getIndex());
		List<User> list = userMapper.queryUserListByCondition(map);
		List<UserBO> newList=new ArrayList<UserBO>();
		for (User user : list) {
			UserBO userBO=new UserBO();
			BeanUtils.copyProperties(user, userBO);
			newList.add(userBO);
		}
		page.setList(newList);
		return page;
	}
	
	/**
	 * 注销用户
	 * 
	 * @param user
	 */
	public void logoutUser(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void updateUserMsg(User user) throws Exception {
		// 如果用户名被修改了，这login表也会被修改
		if (StringUtil.isNotEmptyOrNull(user.getUserName())) {
			userMapper.updateByPrimaryKeySelective(user);
			User newUser = this.getUserMsg(user);
			Login login = new Login();
			login.setLoginName(newUser.getUserName());
			login.setIdNo(newUser.getUserId());
			loginService.updateLoginMsg(login);
		} else {
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	/**
	 * 根据level查询用户
	 * 
	 * @param level
	 * @return
	 */
	public List<User> selectByLevel(int level, String role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userStatus", String.valueOf(level));
		map.put("userRole", role);
		return userMapper.queryByCondition(map);
	}
	
	/**
	 * 条件查询
	 * @param map
	 * @return
	 */
	public List<User> queryByCondition(Map< String, Object> map) {
	 return	userMapper.queryByCondition(map);
	}

	/**
	 * 根据id定位用户
	 * 
	 * @param id
	 * @return
	 */
	public User selectById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	public void updateUserOpTime(User user) {
		Login login=new Login();
		login.setIdNo(user.getUserId());
		login.setOpTime(new Date());
		loginService.updateLoginMsg(login);
	}

	/**
	 * 晋级比赛后修改用户status
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void statusPromotion(int userId) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		String currLevel = user.getUserStatus();
		if (currLevel.substring(1).equals(".5")) {
			float curr = Float.parseFloat(currLevel);
			int newLevel = (int) (curr + 0.5);
			user.setUserStatus(String.valueOf(newLevel));
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			throw new CMSException("用户状态错误");
		}

	}

	/**
	 * 参加比赛后修改用户status
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void statusJoin(int userId) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		String currLevel = user.getUserStatus();
		if (!currLevel.substring(1).equals(".5")) {
			int curr = Integer.parseInt(currLevel);
			float newLevel = (float) (curr + 0.5);
			user.setUserStatus(String.valueOf(newLevel));
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			throw new CMSException("用户状态错误");
		}

	}

	/**
	 * 被淘汰后修改用户status
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void statusEliminate(int userId) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		String currLevel = user.getUserStatus();
		if (currLevel.substring(1).equals(".5")) {
			float curr = Float.parseFloat(currLevel);
			float newLevel = (float) -(curr + 0.5);
			user.setUserStatus(String.valueOf(newLevel));
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			throw new CMSException("用户状态错误");
		}

	}

	/**
	 * 还原用户status
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void statusReduction(int userId) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		String currLevel = user.getUserStatus();
		if (currLevel.substring(1).equals(".5")) {
			float curr = Float.parseFloat(currLevel);
			if (curr < 0) {
				throw new CMSException("被淘汰用户不能还原状态");
			}

			int newLevel = (int) (curr - 0.5);
			user.setUserStatus(String.valueOf(newLevel));
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			throw new CMSException("用户状态错误");
		}

	}
	/**
	 * 用户认证服务
	 * @param userId
	 * @throws Exception
	 */
	public void userAuth(int userId) throws Exception {
		User user=userMapper.selectByPrimaryKey(userId);
		if(!user.getUserStatus().equals("0")){
			throw new CMSException("该用户已通过认证!");
		}
		user.setUserStatus(ConstData.USER_STATUS_AUTH);
		this.updateUserMsg(user);
	}
}
