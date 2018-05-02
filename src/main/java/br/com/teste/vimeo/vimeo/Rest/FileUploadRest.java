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
import org.springframework.beans.factory.annotation.Autowired;
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response upload(@FormDataParam("titulo") String title, @FormDataParam("descricao") String description,
			@FormDataParam("arquivo") InputStream stream, @FormDataParam("arquivo") FormDataContentDisposition file) {

		// cria o HttpFile
		HttpFile httpFile = new HttpFile(file.getName(), file.getFileName(), file.getSize(), file.getParameters(),
				stream);

		// Cria o FileUploadRequest
		FileUploadRequest fileUploadRequest = new FileUploadRequest(title, description, httpFile);

		// Recebe o File Upload no response
		FileUploadResponse fileUploadResponse = fileUploadHandler.handle(fileUploadRequest);

		return Response.status(200).entity(fileUploadResponse).build();
	}

}
