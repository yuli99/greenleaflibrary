package com.wei.greenleaflibrary.dao;

import com.wei.greenleaflibrary.commons.dao.GenericDao;
import com.wei.greenleaflibrary.domain.Role;


public interface RoleDao extends GenericDao<Role, Long>{
	
	Role findRoleByName(String roleName);
	
}
