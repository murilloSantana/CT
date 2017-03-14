package br.com.cta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="massa")
public class Massa {
	@Id
	@GeneratedValue
	@Column(name = "massa_id")
	private Long massaId;
	@Column(name = "nome_massa")
	private String nomeMassa;
	@Column(name="versao_massa")
	private String versao;
	@Column(name = "tipo_bd")
	private String tipoBd;
	@Column(name = "local_bkp")
	private String localBkp;
	@Column(name = "numero_schema")
	private Integer numeroSchema;
	@Column(name = "info_massa")
	private String infoMassa;

	public Long getMassaId() {
		return massaId;
	}

	public void setMassaId(Long massaId) {
		this.massaId = massaId;
	}

	public String getNomeMassa() {
		return nomeMassa;
	}

	public void setNomeMassa(String nomeMassa) {
		this.nomeMassa = nomeMassa;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getTipoBd() {
		return tipoBd;
	}

	public void setTipoBd(String tipoBd) {
		this.tipoBd = tipoBd;
	}

	public String getLocalBkp() {
		return localBkp;
	}

	public void setLocalBkp(String localBkp) {
		this.localBkp = localBkp;
	}

	public Integer getNumeroSchema() {
		return numeroSchema;
	}

	public void setNumeroSchema(Integer numeroSchema) {
		this.numeroSchema = numeroSchema;
	}

	public String getInfoMassa() {
		return infoMassa;
	}

	public void setInfoMassa(String infoMassa) {
		this.infoMassa = infoMassa;
	}

	// private CasoTeste casoTeste;

}
