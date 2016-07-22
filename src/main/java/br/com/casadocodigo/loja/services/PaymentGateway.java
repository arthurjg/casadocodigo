package br.com.casadocodigo.loja.services;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import br.com.casadocodigo.loja.models.PaymentData;

public class PaymentGateway {
	
	public PaymentGateway() {
	}
	
	public void pay(BigDecimal total) {
		Client client = ClientBuilder.newClient();
		PaymentData paymentData = new PaymentData(total);
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		Entity<PaymentData> json = Entity.json(paymentData);
		client.target(uriToPay).request().post(json, String.class);
	}

}
