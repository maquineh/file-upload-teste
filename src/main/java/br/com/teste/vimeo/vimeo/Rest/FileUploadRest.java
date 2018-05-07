package br.com.teste.vimeo.vimeo.Rest;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.clickntap.vimeo.VimeoResponse;

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
	public Response upload(@FormDataParam("titulo") String titulo, @FormDataParam("descricao") String descricao,
			@FormDataParam("arquivo") InputStream stream, @FormDataParam("arquivo") FormDataContentDisposition arquivo) throws InterruptedException{

		// cria o HttpFile
		HttpFile httpFile = new HttpFile(titulo, arquivo.getFileName(), arquivo.getSize(), arquivo.getParameters(),
				stream);

		// Cria o FileUploadRequest
		FileUploadRequest fileUploadRequest = new FileUploadRequest(titulo, descricao, httpFile);

		// Recebe o File Upload no response
		FileUploadResponse fileUploadResponse = fileUploadHandler.handleAsync(fileUploadRequest);

		return Response.status(200).entity(fileUploadResponse).build();
	}
	
	@Path("/delete/{id}")
	@DELETE
	public Response deleteVideo(@PathParam("id") String id) throws IOException {
		fileUploadHandler.deleteVideo(id);
		return Response.status(200).build();
	}

	@Path("/get/{id}")
	@GET
	@Produces("application/vnd.vimeo.video+json")
	public Response getVideo(@PathParam("id") String id) throws IOException {
		VimeoResponse res = fileUploadHandler.getVideo(id);
		return Response.status(200).entity(res.toString()).build();
	}
	
}
