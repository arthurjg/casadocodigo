package br.com.casadocodigo.loja.web.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.casadocodigo.loja.websockets.ConnectedUsers;

public class ConectedUsersSingleton {
	
	private static ConnectedUsers instance;
	
	public static ConnectedUsers getInstance(){
		if(instance == null){
			instance = new ConnectedUsers();
			instance.init();
		}
		return instance;
	}

}
