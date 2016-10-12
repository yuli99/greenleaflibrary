package com.wei.greenleaflibrary.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;

@Entity
@Table(name = "roles")
@AttributeOverride(name = "id", column = @Column(name = "roleId"))
public class Role extends BaseEntity {
	private static final long serialVersionUID = -7706797628318490704L;
	
	private String roleName;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
	
	public Role() {}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
 
	// getters and setters
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return roleName;
	}

}
