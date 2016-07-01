package br.com.casadocodigo.loja.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;
import br.com.casadocodigo.loja.web.util.MensagemUtil;

@Model
public class AdminBooksBean {	
	
	@Inject
	private BookDAO bookDAO;
	
	@Inject
	private AuthorDAO authorDAO;
	
	@Inject
	private MensagemUtil mensagemUtil;
	
	@Inject
	private FileSaver fileSaver;
	
	private Book product = new Book();	
	private Part summary;	
	private Part cover;	
	private List<Book> books;
	private List<Author> authors;
	private List<Integer> selectedAuthorsIds = new ArrayList<Integer>();
	
	@PostConstruct
	public void loadObjects(){
		this.authors = authorDAO.list();		
	}
	
	@Transactional
	public String save(){	
		if(summary != null){
			String summaryFileName = fileSaver.writeOnExternal("sumarios", summary);
			product.setSummaryPath(summaryFileName);
		}
		if(cover != null){
			String coverFileName = fileSaver.writeOnExternal("capas", cover);
			product.setCoverPath(coverFileName);
		}		
		if(product.getId() == null || product.getId() == 0){
			bookDAO.save(product);
		} else {
			bookDAO.update(product);
		}		
		this.clearObjects();		
		mensagemUtil.adicionaMensagem("Livro gravado com sucesso.");
		return "/livros/lista?faces-redirect=true";
	}	
	
	@Transactional
	public void remove(){	
		bookDAO.remove(product);			
		mensagemUtil.adicionaMensagem("Livro " + product.getTitle() + " removido com sucesso.");
		this.clearObjects();	
	}
	
	private void clearObjects(){
		product = new Book();	
		this.selectedAuthorsIds.clear();
		this.books = null;
	}

	public Book getProduct() {
		return product;
	}

	public void setProduct(Book product) {
		this.product = product;
	}

	public List<Integer> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Book> getBooks() {
		if(books == null){
			books = this.bookDAO.list();
		}
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Part getSummary() {
		return summary;
	}

	public void setSummary(Part summary) {
		this.summary = summary;
	}

	public Part getCover() {
		return cover;
	}

	public void setCover(Part cover) {
		this.cover = cover;
	}

}
