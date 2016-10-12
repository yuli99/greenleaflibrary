package com.wei.greenleaflibrary.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.wei.greenleaflibrary.commons.dao.GenericJpaDao;
import com.wei.greenleaflibrary.domain.Role;


public class RoleJpaDao extends GenericJpaDao<Role, Long> implements RoleDao {

	public RoleJpaDao() {
		super(Role.class);
	}

	@Override
	public Role findRoleByName(String roleName) {
		Assert.notNull(roleName);

		Role role = null;

		Query query = getEntityManager().createQuery(
				"select r from " + getPersistentClass().getSimpleName()
						+ " r where r.roleName = :roleName").setParameter(
				"roleName", roleName);
		try {
			role = (Role) query.getSingleResult();
		} catch (NoResultException exc) {
			exc.printStackTrace();
		}

		return role;
	}
	
}
