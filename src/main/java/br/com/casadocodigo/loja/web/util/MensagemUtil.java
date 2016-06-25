package br.com.casadocodigo.loja.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class MensagemUtil {
	
	@Inject
	private FacesContext faceContext;
	
	public void adicionaMensagem(String menssagem){		
		faceContext.getExternalContext().getFlash().setKeepMessages(true);
		FacesMessage facesMessage = new FacesMessage(menssagem);
		faceContext.addMessage( null, facesMessage);
	}

}
