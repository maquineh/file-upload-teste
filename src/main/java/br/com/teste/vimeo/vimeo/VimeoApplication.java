package br.com.teste.vimeo.vimeo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import br.com.teste.vimeo.vimeo.provider.RootPathProvider;
import br.com.teste.vimeo.vimeo.provider.RootPathProviderImpl;
@ComponentScan("br.com.teste.vimeo.*")
@SpringBootApplication
public class VimeoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new VimeoApplication()
			.configure(new SpringApplicationBuilder(VimeoApplication.class))
			.run(args);
	}
	
	@Bean
	RootPathProvider rootPathProvider() {
		return new RootPathProviderImpl("c:/out");
	}
}
