package com.coe.common.entity;

/**
 * CoeUserInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CoeUserInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String UName;
	private String sex;
	private Integer age;

	// Constructors

	/** default constructor */
	public CoeUserInfo() {
	}

	/** full constructor */
	public CoeUserInfo(String UName, String sex, Integer age) {
		this.UName = UName;
		this.sex = sex;
		this.age = age;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUName() {
		return this.UName;
	}

	public void setUName(String UName) {
		this.UName = UName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}