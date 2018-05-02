package br.com.teste.vimeo.vimeo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUploadResponse {

	@JsonProperty("identificador")
	private final String identificador;

	public FileUploadResponse(String identificador) {
		super();
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}
		
}
