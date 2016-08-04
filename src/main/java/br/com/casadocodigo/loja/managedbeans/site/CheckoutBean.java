package br.com.casadocodigo.loja.managedbeans.site;

import java.io.IOException;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.CheckoutDAO;
import br.com.casadocodigo.loja.daos.SystemUserDAO;
import br.com.casadocodigo.loja.models.Checkout;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.SystemUser;
import br.com.casadocodigo.loja.services.PaymentGateway;
import br.com.casadocodigo.loja.web.util.MensagemUtil;

@Model
public class CheckoutBean {	
	
	@Inject	
	private SystemUserDAO systemUserDAO;
	
	@Inject	
	private CheckoutDAO checkoutDAO;
	
	@Inject
	private ShoppingCart cart;
	
	@Inject
	private MensagemUtil mensagemUtil;
	
	@Inject
	private PaymentGateway paymentGateway;
	
	@Inject
	private FacesContext facesContext;	
	
	private SystemUser systemUser = new SystemUser();	
	
	public SystemUser getSystemUser() {
		return systemUser;
	}
	
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
	@Transactional
	public void checkout() throws IOException {
		if(systemUserDAO.otherUserHasSocialId(systemUser.getSocialId(), systemUser.getEmail())){
			mensagemUtil.adicionaMensagem("O CPF digitado já foi informado por outro usuario. É esse mesmo?");
			return;
		}
		
		if(!systemUserDAO.hasEmail(systemUser.getEmail())){
			systemUserDAO.save(systemUser);
		} else {
			systemUser = systemUserDAO.getByEmail(systemUser.getEmail());
		}		
		
		Checkout checkout = new Checkout(systemUser, cart);
		checkoutDAO.save(checkout);
		
		String contextName = facesContext.getExternalContext().getContextName();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setStatus(307);
		response.setHeader("Location", "/" + contextName + "/services/payment?uuid=" + checkout.getUuid());
		
		paymentGateway.pay(checkout.getValue());
	
	}

}
