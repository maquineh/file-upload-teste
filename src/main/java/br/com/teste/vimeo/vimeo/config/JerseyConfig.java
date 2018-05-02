package br.com.teste.vimeo.vimeo.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.com.teste.vimeo.vimeo.Rest.FileUploadRest;
import br.com.teste.vimeo.vimeo.exceptions.FileUploadExceptionMapper;

@Component
public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig() {
		register(FileUploadRest.class);
		register(MultiPartFeature.class);
		register(FileUploadExceptionMapper.class);
	}
	
}
