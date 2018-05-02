package br.com.teste.vimeo.vimeo.provider;

public class RootPathProviderImpl implements RootPathProvider {
	
	private final String path;
	
	public RootPathProviderImpl(String path) {
        this.path = path;
    }

	@Override
	public String getRootPath() {
		return path;
	}
		
}
