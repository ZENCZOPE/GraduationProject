package com.cms.service;



import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cms.dao.LoginMapper;
import com.cms.dao.SqlMapper;
import com.cms.pojo.Login;
import com.cms.pojo.User;
import com.cms.util.CMSException;
import com.cms.util.MD5Util;

@Service
public class LoginService {

	@Resource
	private LoginMapper loginMapper;
	@Resource
	private UserService userService;
	@Resource 
	private SqlMapper sqlMapper;
	/**
	 * 验证用户名密码
	 * @param login
	 * @return 1表示正确 0表示错误
	 * @throws CMSException 
	 */
	public boolean getLoginStatus(Map<String, Object> map) throws Exception {
		Map<String, Object> Nmap=sqlMapper.checkLogin(map);
		if(Nmap == null) {
			throw new CMSException("用户密码错误");
		}
		else {
			return true;
		}
	}
	
	/**
	 * 创建login数据，并且返回id
	 * @param login
	 * @return
	 */
	public int createLogin(Login login){
		loginMapper.insert(login);
		return loginMapper.queryByLogin(login).get(0).getIdNo();
	}
	/**
	 * 查询用户名是否存在
	 * @param login
	 * @return
	 */
	public boolean  queryByName(Login login) {
		if(loginMapper.queryByName(login)==null){
			return false;
		}
		return true;
	}
	
	public void updateLoginMsg(Login login) {
		login=this.updateLoginOpTime(login);
		loginMapper.updateByPrimaryKeySelective(login);
	}
	
	public Login updateLoginOpTime(Login login) {
		login.setOpTime(new Date());
		return login;
	}
	/**
	 * 重置用户密码
	 * @param loginId
	 * @throws Exception 
	 */
	public void resetLoginPassword(int loginId) throws Exception {
		//获取用户身份证6位
		User user=new User();
		user.setUserId(loginId);
		user=userService.getUserMsg(user);
		String password=userService.getLast6(user);
		//加密后插入数据库
		Login login=new Login();
		login.setIdNo(loginId);
		login.setLoginPw(MD5Util.getMD5(password));
		this.updateLoginMsg(login);
	}
}
