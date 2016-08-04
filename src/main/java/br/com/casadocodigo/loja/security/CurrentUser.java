package br.com.casadocodigo.loja.security;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.casadocodigo.loja.daos.SecurityDAO;
import br.com.casadocodigo.loja.models.SystemUser;

@Named
@SessionScoped
public class CurrentUser {
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private SecurityDAO securityDAO;
	private SystemUser systemUser;

	public SystemUser get() {
		return this.systemUser;
	}
	
	public boolean hasRole(String role){
		return request.isUserInRole(role);
	}

	@PostConstruct
	private void loadSystemUser() {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			this.systemUser = securityDAO.loadUserByUsername(principal.getName());
		}
	}

}
