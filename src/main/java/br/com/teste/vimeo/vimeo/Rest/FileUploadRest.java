package br.com.teste.vimeo.vimeo.Rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import br.com.teste.vimeo.vimeo.handler.FileUploadHandler;
import br.com.teste.vimeo.vimeo.model.FileUploadRequest;
import br.com.teste.vimeo.vimeo.model.FileUploadResponse;
import br.com.teste.vimeo.vimeo.model.HttpFile;

@Component
@Path("/arquivos")
public class FileUploadRest {
	
	private final FileUploadHandler fileUploadHandler;

	public FileUploadRest(FileUploadHandler fileUploadHandler) {
		super();
		this.fileUploadHandler = fileUploadHandler;
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA+";charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
	//@Async
	public Response upload(@FormDataParam("titulo") String titulo, @FormDataParam("descricao") String descricao,
			@FormDataParam("arquivo") InputStream stream, @FormDataParam("arquivo") FormDataContentDisposition arquivo) {

		// cria o HttpFile
		HttpFile httpFile = new HttpFile(arquivo.getName(), arquivo.getFileName(), arquivo.getSize(), arquivo.getParameters(),
				stream);

		// Cria o FileUploadRequest
		FileUploadRequest fileUploadRequest = new FileUploadRequest(titulo, descricao, httpFile);

		// Recebe o File Upload no response
		FileUploadResponse fileUploadResponse = fileUploadHandler.handle(fileUploadRequest);

		return Response.status(200).entity(fileUploadResponse).build();
	}

}
