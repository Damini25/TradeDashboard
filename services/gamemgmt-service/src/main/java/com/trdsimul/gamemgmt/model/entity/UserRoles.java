package com.trdsimul.gamemgmt.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserRoles {

	@Id
	private Integer userId;
	private Integer roleId;
	private Integer userTypeId;

	public UserRoles() {
		super();
	}

	public UserRoles(Integer userId, Integer roleId, Integer userTypeId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.userTypeId = userTypeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

}
