package br.com.casadocodigo.loja.security;

import org.jboss.security.Base64Encoder;

public class PassGenerator {
	
	public static void main(String[] args) throws Exception {
		String pass[] = new String[]{"123456","SHA-256"};		
		Base64Encoder.main(pass);		
	}

}
