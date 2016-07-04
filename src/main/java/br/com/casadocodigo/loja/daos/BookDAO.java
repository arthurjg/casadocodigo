package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.casadocodigo.loja.models.Book;

@Stateful
public class BookDAO {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;	

	public void save(Book product) {
		manager.persist(product);
	}
	
	public void update(Book product) {
		manager.merge(product);
	}
	
	public void remove(Book product) {
		Book productAttached = manager.find(Book.class, product.getId());		
		if(productAttached != null){
			manager.remove(product);
		}		
	}
	
	public Book findById(Integer id) {
		return manager.find(Book.class, id);
	}
	
	public List<Book> list() {
		return manager.createQuery(
				"select distinct(b) from Book b join fetch b.authors",Book.class)
		.getResultList();
	}
	
	public List<Book> lastReleases() {
		return manager.createQuery(
			"select b from Book b where b.releaseDate <= now() order by	b.id desc", Book.class)
		.setMaxResults(3).getResultList();
	}

	public List<Book> olderBooks() {
		return manager.createQuery("select b from Book b", Book.class)
				.setMaxResults(20).getResultList();
	}

	

}
