package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.casadocodigo.loja.models.Checkout;

public class CheckoutDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Checkout checkout) {
		entityManager.persist(checkout);
	}

	public Checkout findByUuid(String uuid) {
		String hql = "select c from Checkout c where c.uuid = :uuid";
		Query consulta = entityManager.createQuery(hql);
		consulta.setParameter("uuid", uuid );
		return (Checkout) consulta.getSingleResult();
	}

}
