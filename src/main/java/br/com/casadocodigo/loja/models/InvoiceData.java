package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

public class InvoiceData {
	
	private BigDecimal value;
	private String buyerEmail;
	
	public InvoiceData(Checkout checkout) {
		this.setValue(checkout.getValue());
		this.setBuyerEmail(checkout.getBuyer().getEmail());
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

}
