package br.com.teste.vimeo.vimeo.handler;

import br.com.teste.vimeo.vimeo.model.FileUploadRequest;
import br.com.teste.vimeo.vimeo.model.FileUploadResponse;

public interface FileUploadHandler {
	FileUploadResponse handle(FileUploadRequest request);
}
