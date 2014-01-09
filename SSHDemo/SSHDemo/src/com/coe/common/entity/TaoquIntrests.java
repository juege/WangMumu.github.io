package com.coe.common.entity;

import java.sql.Timestamp;

/**
 * TaoquIntrests entity. @author MyEclipse Persistence Tools
 */

public class TaoquIntrests implements java.io.Serializable {

	// Fields

	private Long intrestId;
	private Timestamp intrestStart;
	private String intrestMessage;
	private Timestamp intrestEnd;

	// Constructors

	/** default constructor */
	public TaoquIntrests() {
	}

	/** full constructor */
	public TaoquIntrests(Timestamp intrestStart, String intrestMessage,
			Timestamp intrestEnd) {
		this.intrestStart = intrestStart;
		this.intrestMessage = intrestMessage;
		this.intrestEnd = intrestEnd;
	}

	// Property accessors

	public Long getIntrestId() {
		return this.intrestId;
	}

	public void setIntrestId(Long intrestId) {
		this.intrestId = intrestId;
	}

	public Timestamp getIntrestStart() {
		return this.intrestStart;
	}

	public void setIntrestStart(Timestamp intrestStart) {
		this.intrestStart = intrestStart;
	}

	public String getIntrestMessage() {
		return this.intrestMessage;
	}

	public void setIntrestMessage(String intrestMessage) {
		this.intrestMessage = intrestMessage;
	}

	public Timestamp getIntrestEnd() {
		return this.intrestEnd;
	}

	public void setIntrestEnd(Timestamp intrestEnd) {
		this.intrestEnd = intrestEnd;
	}

}