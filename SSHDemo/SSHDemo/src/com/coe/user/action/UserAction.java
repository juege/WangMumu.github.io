package com.coe.user.action;

import java.util.List;

import com.coe.common.entity.CoeUserInfo;
import com.coe.framework.action.BaseAction;
import com.coe.user.service.UserService;

public class UserAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6534061320130256549L;

	private CoeUserInfo  coeUserInfo;
	
	private UserService userService;
	
	private List<CoeUserInfo>  userInfos; 



	public List<CoeUserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<CoeUserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CoeUserInfo getCoeUserInfo() {
		return coeUserInfo;
	}

	public void setCoeUserInfo(CoeUserInfo coeUserInfo) {
		this.coeUserInfo = coeUserInfo;
	}
	
	
	public String addUser(){
		
		return "success";
		
	}
	public String saveUser(){
		System.out.print(coeUserInfo.getSex());
		userService.saveUserInfo(coeUserInfo);
		List<CoeUserInfo> userInfos = userService.findUserInfo();
		
		this.getRequest().setAttribute("userInfos", userInfos);
		return "success";
	}
	
	public String listUser(){
		
		System.out.print("222222");
		
		List<CoeUserInfo> userInfos = userService.findUserInfo();
		
		this.getRequest().setAttribute("userInfos", userInfos);
		return "success";
	}
	

}
