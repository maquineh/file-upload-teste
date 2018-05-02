package br.com.teste.vimeo.vimeo.model;

import java.io.InputStream;
import java.util.Map;

public class HttpFile {

	private final String nome;
	private final String nomeArquivoSubmetido;
	private final long tamanho;
	private final Map<String, String> parametros;
	private final InputStream stream;

	public HttpFile(String nome, String nomeArquivoSubmetido, long tamanho, Map<String, String> parametros,
			InputStream stream) {
		this.nome = nome;
		this.nomeArquivoSubmetido = nomeArquivoSubmetido;
		this.tamanho = tamanho;
		this.parametros = parametros;
		this.stream = stream;
	}

	public String getNome() {
		return nome;
	}

	public String getNomeArquivoSubmetido() {
		return nomeArquivoSubmetido;
	}

	public long getTamanho() {
		return tamanho;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public InputStream getStream() {
		return stream;
	}

}
