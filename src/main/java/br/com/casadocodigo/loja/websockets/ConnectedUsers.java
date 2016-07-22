package br.com.casadocodigo.loja.websockets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ConnectedUsers {
	
	private Set<Session> remoteUsers;	
	
	private Logger logger = LoggerFactory.getLogger(SalesEndpoint.class);
	
	@PostConstruct
	public void init(){
		remoteUsers = new HashSet<>();		
	}
	
	public void send(String message) {
		logger.info("enviando mensagem para um usuario...");
		for (Session user : remoteUsers) {
			if (user.isOpen()) {
				try {
					user.getBasicRemote().sendText(message);
					logger.info("enviando mensagem para um usuario. ok!");
				} catch (IOException e) {
					logger.error("Não foi possivel enviar mensagem para um cliente, {}", e);
				}
			} else {
				logger.warn("conexão não está aberta com o usuario.");
			}
		}
	}
	
	public void add(Session remoteUser) {
		this.remoteUsers.add(remoteUser);
		logger.info("Adicionado conexão com usuario/cliente de webasocket.");
	}
	
	public void remove(Session remoteUser) {
		this.remoteUsers.remove(remoteUser);
		logger.info("Removida conexão com usuario/cliente de webasocket.");
	}
	
	public Set<Session> getRemoteUsers() {
		return remoteUsers;
	}
	public void setRemoteUsers(Set<Session> remoteUsers) {
		this.remoteUsers = remoteUsers;
	}

}
