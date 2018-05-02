package br.com.teste.vimeo.vimeo.exceptions;

import br.com.teste.vimeo.vimeo.error.HttpServiceError;
import br.com.teste.vimeo.vimeo.error.ServiceError;

public class FileUploadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2999954532202500126L;
	
	private final HttpServiceError httpServiceError;

	public FileUploadException(ServiceError serviceError) {
		this.httpServiceError = createServiceError(serviceError);
	}

	public FileUploadException(ServiceError serviceError, String message) {
		super(message);

		this.httpServiceError = createServiceError(serviceError);
	}

	public FileUploadException(ServiceError serviceError, String message, Throwable cause) {
		super(message, cause);

		this.httpServiceError = createServiceError(serviceError);
	}

	public HttpServiceError getHttpServiceError() {
		return httpServiceError;
	}

	private static HttpServiceError createServiceError(ServiceError serviceError) {
		return new HttpServiceError(400, serviceError);
	}

}
