package br.com.casadocodigo.loja.managedbeans;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.models.Book;
import br.com.casadocodigo.loja.models.Sale;
import br.com.casadocodigo.loja.websockets.ConnectedUsers;

@Model
public class AdminSalesBean {
	
	@Inject
	private ConnectedUsers connectedUsers; 	
	
	private Sale sale = new Sale();	
	private Integer saleBookId;
	
	@PostConstruct
	private void configure() {
		this.sale.setBook(new Book());
	}
	
	public String save() {
		if(saleBookId != null){
			sale.getBook().setId(saleBookId);
		}		
		connectedUsers.send(sale.toJson());
		return "/admin/promocoes/form.xhtml?faces-redirect=true";
	}
	
	public Sale getSale() {
		return sale;
	}
	
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Integer getSaleBookId() {
		return saleBookId;
	}

	public void setSaleBookId(Integer saleBookId) {
		this.saleBookId = saleBookId;
	}

}
