package com.coe.user.dao;

import java.util.List;

import com.coe.common.entity.CoeUserInfo;

public interface UserDao {
	
	public void saveUserInfo(CoeUserInfo  coeUserInfo);
	
	public List<CoeUserInfo> findUserInfo();

}
