package br.com.teste.vimeo.vimeo.exceptions;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.teste.vimeo.vimeo.error.HttpServiceError;

public class FileUploadExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<FileUploadException> {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadExceptionMapper.class);

	@Override
	public Response toResponse(FileUploadException fileUploadException) {
		if (logger.isErrorEnabled()) {
			logger.error("Ocorreu um erro inesperado.", fileUploadException);
		}

		HttpServiceError httpServiceError = fileUploadException.getHttpServiceError();

		return Response.status(httpServiceError.getHttpStatusCode()).entity(httpServiceError.getServiceError()).build();
	}

}
