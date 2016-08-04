package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.SystemUser;

public class SecurityDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public SystemUser loadUserByUsername(String username) throws NoResultException {
		String jpql = "select u from SystemUser u	where u.email = :login";
		SystemUser user = em.createQuery(jpql, SystemUser.class)
				.setParameter("login", username)
				.getSingleResult();
		return user;
	}

}
