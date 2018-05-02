package br.com.teste.vimeo.vimeo.model;

public class FileUploadRequest {

	private final String titulo;
	private final String descricao;
	private final HttpFile httpFile;

	public FileUploadRequest(String titulo, String descricao, HttpFile httpFile) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.httpFile = httpFile;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public HttpFile getHttpFile() {
		return httpFile;
	}

}
