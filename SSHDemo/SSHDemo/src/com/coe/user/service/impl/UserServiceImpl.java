package com.coe.user.service.impl;

import java.util.List;

import com.coe.common.entity.CoeUserInfo;
import com.coe.user.dao.UserDao;
import com.coe.user.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void saveUserInfo(CoeUserInfo  coeUserInfo){
		userDao.saveUserInfo(coeUserInfo);
	}
	
	public List<CoeUserInfo> findUserInfo(){
		return userDao.findUserInfo();
	}

}
