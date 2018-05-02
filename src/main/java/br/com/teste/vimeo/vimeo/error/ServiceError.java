package br.com.teste.vimeo.vimeo.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceError {

	@JsonProperty("codigo")
	private final String codigo;
	
	@JsonProperty("mensagem")
	private final String mensagem;
	
	public ServiceError(String codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMensagem() {
		return mensagem;
	}	
}
