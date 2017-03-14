package br.com.cta.model;

import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoGeral {

	private String numeroVersao;

	private String repositorioTagsLocal;

	private String repositorioPropriedadesLocal;

	public String getNumeroVersao() {
		return numeroVersao;
	}

	public void setNumeroVersao(String numeroVersao) {
		this.numeroVersao = numeroVersao;
	}

	public String getRepositorioTagsLocal() {
		return repositorioTagsLocal;
	}

	public void setRepositorioTagsLocal(String repositorioTagsLocal) {
		this.repositorioTagsLocal = repositorioTagsLocal;
	}

	public String getRepositorioPropriedadesLocal() {
		return repositorioPropriedadesLocal;
	}

	public void setRepositorioPropriedadesLocal(
			String repositorioPropriedadesLocal) {
		this.repositorioPropriedadesLocal = repositorioPropriedadesLocal;
	}

}
