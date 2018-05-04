package br.com.teste.vimeo.vimeo.handler;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import br.com.teste.vimeo.vimeo.model.FileUploadRequest;
import br.com.teste.vimeo.vimeo.model.FileUploadResponse;

public interface FileUploadHandler {
	
	public FileUploadResponse handle(FileUploadRequest request);
	public CompletableFuture<FileUploadResponse> handleAsync(FileUploadRequest request);
	public void deleteVideo(String id) throws IOException;
}
