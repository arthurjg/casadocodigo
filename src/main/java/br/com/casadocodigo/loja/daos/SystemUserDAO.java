package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.casadocodigo.loja.models.SystemUser;

public class SystemUserDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(SystemUser systemUser) {
		entityManager.persist(systemUser);
	}
	
	public boolean hasEmail(String email) {				
		return getByEmailQuery(email).getResultList().size() > 0;
	}
	
	public boolean otherUserHasSocialId(String socialId, String email) {				
		return getBySocialIdQuery(socialId, email).getResultList().size() > 0;
	}
	
	public SystemUser getByEmail(String email) {		
		return (SystemUser) getByEmailQuery(email).getSingleResult();
	}	
	
	private Query getByEmailQuery(String email){
		String hql = "select u from SystemUser u where u.email = :email";
		Query consulta = entityManager.createQuery(hql);
		consulta.setParameter("email", email );
		return consulta;
	}	
	
	private Query getBySocialIdQuery(String socialId, String email){
		String hql = "select u from SystemUser u where u.socialId = :socialId and u.email != :email";
		Query consulta = entityManager.createQuery(hql);
		consulta.setParameter("socialId", socialId );
		consulta.setParameter("email", email );
		return consulta;
	}	

}