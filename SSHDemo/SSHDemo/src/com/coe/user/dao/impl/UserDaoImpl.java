package com.coe.user.dao.impl;


import java.util.List;

import com.coe.common.entity.CoeUserInfo;
import com.coe.framework.dao.BaseDao;
import com.coe.user.dao.UserDao;

public class UserDaoImpl implements UserDao {
	
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
    //添加操作
	public void saveUserInfo(CoeUserInfo  coeUserInfo){
		baseDao.save(coeUserInfo);
	}
	//列表查找
	public List<CoeUserInfo> findUserInfo(){
		List<CoeUserInfo> userInfos = baseDao.findAll(CoeUserInfo.class);
		
		//List<CoeUserInfo> userInfos = baseDao..findAll_order(CoeUserInfo.class, "age");

		return userInfos;
	}
	//通过主键查找修改对象
	public CoeUserInfo findUserInfo(Long id){
		CoeUserInfo userInfo = (CoeUserInfo) baseDao.findEnityById(CoeUserInfo.class, id);
		return userInfo;
	}
    //修改操作
	@SuppressWarnings("unchecked")
	public void updateUserInfo(CoeUserInfo  coeUserInfo){
		baseDao.update(coeUserInfo);
	}
	//删除
	public void delUser(Long id){
		baseDao.deleteById(CoeUserInfo.class, id);
	}
	
}
