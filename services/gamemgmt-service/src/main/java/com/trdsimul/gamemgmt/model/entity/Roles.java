package com.trdsimul.gamemgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Roles {

	@Id
	private Integer roleId;
	private String roleName;
	private Date lastUpdate;

	public Roles() {
		super();
	}

	public Roles(Integer roleId, String roleName, Date lastUpdate) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.lastUpdate = lastUpdate;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
