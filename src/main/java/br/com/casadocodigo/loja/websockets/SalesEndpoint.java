package br.com.casadocodigo.loja.websockets;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/channel/sales")
public class SalesEndpoint {	
	
	@Inject
	private ConnectedUsers connectedUsers;	
	
	private Logger logger = LoggerFactory.getLogger(SalesEndpoint.class);
	
	@OnOpen
	public void onOpen(Session session){		
		connectedUsers.add(session);
	}	
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason){
		connectedUsers.remove(session);
		logger.info(closeReason.getCloseCode().toString());		
	}	

}
