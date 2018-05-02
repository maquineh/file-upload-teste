package br.com.teste.vimeo.vimeo.handler;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.vimeo.vimeo.error.ServiceError;
import br.com.teste.vimeo.vimeo.exceptions.FileUploadException;
import br.com.teste.vimeo.vimeo.model.FileUploadRequest;
import br.com.teste.vimeo.vimeo.model.FileUploadResponse;
import br.com.teste.vimeo.vimeo.model.HttpFile;
import br.com.teste.vimeo.vimeo.provider.RootPathProvider;

public class LocalStorageFileUpload implements FileUploadHandler {

	private final RootPathProvider rootPathProvider;

	@Autowired
	public LocalStorageFileUpload(RootPathProvider rootPathProvider) {
		super();
		this.rootPathProvider = rootPathProvider;
	}

	@Override
	public FileUploadResponse handle(FileUploadRequest request) {

		// Sai logo, se não houve arquivo na requisicao:
		if (request == null) {
			throw new FileUploadException(new ServiceError("missingFile", "Nao ha arquivo"),
					String.format("Missing Parameter: request"));
		}

		// Get the HttpFile:
		HttpFile httpFile = request.getHttpFile();

		// Sai logo se o dado nao estiver assinado.
		if (httpFile == null) {
			throw new FileUploadException(new ServiceError("missingFile", "Nao ha arquivo."),
					String.format("Falta o parametro: request.httpFile"));
		}

		// Não sobrescreve arquivo no disco sempre será gerado um novo id.
		String targetFileName = UUID.randomUUID().toString();

		// Salvando em disco:
		internalWriteFile(httpFile.getStream(), targetFileName);

		return new FileUploadResponse(targetFileName);
	}

	private void internalWriteFile(InputStream stream, String fileName) {
		try {
			Files.copy(stream, Paths.get(rootPathProvider.getRootPath(), fileName));
		} catch (Exception e) {
			throw new FileUploadException(new ServiceError("storingFileError", "Erro ao adicionar arquivo"),
					String.format("Adicionado o arquivo '%s' falhou", fileName), e);
		}
	}

}
