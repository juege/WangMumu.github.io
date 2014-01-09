package com.coe.user.service;

import java.util.List;

import com.coe.common.entity.CoeUserInfo;

public interface UserService {
	
	public void saveUserInfo(CoeUserInfo  coeUserInfo);
	
	public List<CoeUserInfo> findUserInfo();

}
