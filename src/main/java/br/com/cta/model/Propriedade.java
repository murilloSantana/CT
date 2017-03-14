package br.com.cta.model;

import org.springframework.stereotype.Component;

@Component
public class Propriedade {
	// Historico de mudancas
	private String autor;
	
	private String data;
	
	private String versaoDriveAMnet;
	
	private String cenario;
	
	private String numeroMantis;
	
	private String descricaoHistorico;
	
	// propriedade
	private String numeroProcesso;
	
	private String descricaoPropriedade;
	
	private String novoValor;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getVersaoDriveAMnet() {
		return versaoDriveAMnet;
	}

	public void setVersaoDriveAMnet(String versaoDriveAMnet) {
		this.versaoDriveAMnet = versaoDriveAMnet;
	}

	public String getCenario() {
		return cenario;
	}

	public void setCenario(String cenario) {
		this.cenario = cenario;
	}

	public String getNumeroMantis() {
		return numeroMantis;
	}

	public void setNumeroMantis(String numeroMantis) {
		this.numeroMantis = numeroMantis;
	}

	public String getDescricaoHistorico() {
		return descricaoHistorico;
	}

	public void setDescricaoHistorico(String descricaoHistorico) {
		this.descricaoHistorico = descricaoHistorico;
	}

	public String getDescricaoPropriedade() {
		return descricaoPropriedade;
	}

	public void setDescricaoPropriedade(String descricaoPropriedade) {
		this.descricaoPropriedade = descricaoPropriedade;
	}

	public String getNovoValor() {
		return novoValor;
	}

	public void setNovoValor(String novoValor) {
		this.novoValor = novoValor;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
	
	
	
}
