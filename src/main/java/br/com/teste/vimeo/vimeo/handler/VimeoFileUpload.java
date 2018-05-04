package br.com.teste.vimeo.vimeo.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoException;
import com.clickntap.vimeo.VimeoResponse;

import br.com.teste.vimeo.vimeo.error.ServiceError;
import br.com.teste.vimeo.vimeo.exceptions.FileUploadException;
import br.com.teste.vimeo.vimeo.model.FileUploadRequest;
import br.com.teste.vimeo.vimeo.model.FileUploadResponse;
import br.com.teste.vimeo.vimeo.model.HttpFile;
import br.com.teste.vimeo.vimeo.provider.RootPathProvider;

@Component
@Primary
public class VimeoFileUpload implements FileUploadHandler {

	private final RootPathProvider rootPathProvider;

	@Autowired
	public VimeoFileUpload(RootPathProvider rootPathProvider) {
		super();
		this.rootPathProvider = rootPathProvider;
	}

	@Async
	public CompletableFuture<FileUploadResponse> handleAsync(FileUploadRequest request) {
		
		String vimeoToken = "a3ac62d5dd556c1d7cb4010485d9ba23";
		Vimeo vimeo = new Vimeo(vimeoToken);

		// add a video
		boolean upgradeTo1080 = false;
		String videoEndPoint = "";
		
		// Sai logo, se não houve arquivo na requisicao:
		if (request == null) {
			throw new FileUploadException(new ServiceError("missingFile", "Nao ha arquivo."),
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
		

		try {
			long tempoInicial = System.currentTimeMillis();
			videoEndPoint = vimeo.addVideo(Paths.get(rootPathProvider.getRootPath(), targetFileName).toFile(), upgradeTo1080);
			long tempoFinal = System.currentTimeMillis();
			 System.out.printf("%.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
			// get video info
			VimeoResponse info = vimeo.getVideoInfo(videoEndPoint);
			//System.out.println(info);

			// edit video
			String name = httpFile.getNome();
			String desc = httpFile.getNomeArquivoSubmetido();
			String license = ""; // see Vimeo API Documentation
			String privacyView = "disable"; // see Vimeo API Documentation
			String privacyEmbed = "privacy"; // see Vimeo API Documentation
			boolean reviewLink = false;
			vimeo.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);

			// add video privacy domain
			vimeo.addVideoPrivacyDomain(videoEndPoint, "alltismapptest.com.br");

			// delete video
			//vimeo.removeVideo(videoEndPoint);
			Path caminhoDoArquivo = Paths.get(rootPathProvider.getRootPath(), targetFileName);
			Files.delete(caminhoDoArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (VimeoException e) {
			e.printStackTrace();
		}
		
		return CompletableFuture.completedFuture(new FileUploadResponse(videoEndPoint));
	}
	
	public void deleteVideo(String id) throws IOException {
		String vimeoToken = "a3ac62d5dd556c1d7cb4010485d9ba23";
		Vimeo vimeo = new Vimeo(vimeoToken);
		vimeo.removeVideo("/videos/"+id);
	}
	
	private void internalWriteFile(InputStream stream, String fileName) {
		try {
			Files.copy(stream, Paths.get(rootPathProvider.getRootPath(), fileName));
		} catch (Exception e) {
			throw new FileUploadException(new ServiceError("storingFileError", "Erro ao adicionar arquivo"),
					String.format("Adicionado o arquivo '%s' falhou", fileName), e);
		}
	}

	@Override
	public FileUploadResponse handle(FileUploadRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
