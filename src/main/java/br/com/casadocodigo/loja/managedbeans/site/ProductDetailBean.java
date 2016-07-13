package br.com.casadocodigo.loja.managedbeans.site;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.models.Book;

@ManagedBean
@ViewScoped
public class ProductDetailBean {
	
	@Inject
	private BookDAO bookDAO;
	private Book book;
	private Integer id;	
	
	public void loadBook(){
		this.book = bookDAO.findById(id);		
	}
	
	public Book getBook() {		
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

}
