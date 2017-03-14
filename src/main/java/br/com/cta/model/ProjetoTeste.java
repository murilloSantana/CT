package br.com.cta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "projeto_teste")
public class ProjetoTeste {
	@Id
	@GeneratedValue
	@Column(name = "projeto_teste_id")
	private Long projetoTesteId;
	@Column(name = "nome_projeto_teste")
	private String nomeProjetoTeste;
	@Column(name = "data_inicio_projeto")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dataInicioProjeto;
	@Column(name = "data_prazo_projeto")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dataPrazoProjeto;
	@Column(name = "data_congelamento_projeto")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dataCongelamentoProjeto;
	@Column(name = "is_projeto_ativo")
	private Boolean isProjetoAtivo;
	@Transient
	private String dataCongelamentoNonFormat;
	@Transient
	private String dataPrazoProjetoNonFormat;

	public Long getProjetoTesteId() {
		return projetoTesteId;
	}

	public void setProjetoTesteId(Long projetoTesteId) {
		this.projetoTesteId = projetoTesteId;
	}

	public String getNomeProjetoTeste() {
		return nomeProjetoTeste;
	}

	public void setNomeProjetoTeste(String nomeProjetoTeste) {
		this.nomeProjetoTeste = nomeProjetoTeste;
	}

	public LocalDateTime getDataInicioProjeto() {
		return dataInicioProjeto;
	}

	public void setDataInicioProjeto(LocalDateTime dataInicioProjeto) {
		this.dataInicioProjeto = dataInicioProjeto;
	}

	public LocalDateTime getDataPrazoProjeto() {
		return dataPrazoProjeto;
	}

	public void setDataPrazoProjeto(LocalDateTime dataPrazoProjeto) {
		this.dataPrazoProjeto = dataPrazoProjeto;
	}

	public LocalDateTime getDataCongelamentoProjeto() {
		return dataCongelamentoProjeto;
	}

	public void setDataCongelamentoProjeto(LocalDateTime dataCongelamentoProjeto) {
		this.dataCongelamentoProjeto = dataCongelamentoProjeto;
	}

	public Boolean getIsProjetoAtivo() {
		return isProjetoAtivo;
	}

	public void setIsProjetoAtivo(Boolean isProjetoAtivo) {
		this.isProjetoAtivo = isProjetoAtivo;
	}

	public String getDataCongelamentoNonFormat() {
		return dataCongelamentoNonFormat;
	}

	public void setDataCongelamentoNonFormat(String dataCongelamentoNonFormat) {
		this.dataCongelamentoNonFormat = dataCongelamentoNonFormat;
	}

	public String getDataPrazoProjetoNonFormat() {
		return dataPrazoProjetoNonFormat;
	}

	public void setDataPrazoProjetoNonFormat(String dataPrazoProjetoNonFormat) {
		this.dataPrazoProjetoNonFormat = dataPrazoProjetoNonFormat;
	}

}
