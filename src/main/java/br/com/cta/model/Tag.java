package br.com.cta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "tag")
@Component
public class Tag  {

	@Id
	@GeneratedValue
	@Column(name = "chave_tag")
	private Long chaveTag;
	@Column(name = "nome")
	private String id;
	private String descricao;
	@ElementCollection
	@CollectionTable(name = "caminhos", joinColumns = @JoinColumn(name = "chaveTag"))
	@Column(name = "caminho")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> caminhos = new ArrayList<String>();
	@ElementCollection
	@CollectionTable(name = "categorias", joinColumns = @JoinColumn(name = "chaveTag"))
	@Column(name = "categoria")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> categorias = new ArrayList<String>();

	private String autor;

	private String data;

	public Tag() {
		super();
	}

	public Tag(String id, String descricao, List<String> caminhos, List<String> categorias, String autor, String data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.caminhos = caminhos;
		this.categorias = categorias;
		this.autor = autor;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public List<String> getCaminhos() {
		return caminhos;
	}

	public void setCaminhos(List<String> caminhos) {
		this.caminhos = caminhos;
	}

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

	public Long getChaveTag() {
		return chaveTag;
	}

	public void setChaveTag(Long chaveTag) {
		this.chaveTag = chaveTag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((caminhos == null) ? 0 : caminhos.hashCode());
		result = prime * result + ((categorias == null) ? 0 : categorias.hashCode());
		result = prime * result + ((chaveTag == null) ? 0 : chaveTag.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (caminhos == null) {
			if (other.caminhos != null)
				return false;
		} else if (!caminhos.equals(other.caminhos))
			return false;
		if (categorias == null) {
			if (other.categorias != null)
				return false;
		} else if (!categorias.equals(other.categorias))
			return false;
		if (chaveTag == null) {
			if (other.chaveTag != null)
				return false;
		} else if (!chaveTag.equals(other.chaveTag))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
