package br.com.cta.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.cta.model.Tag;

@Repository("tagDAO")
@Transactional
@SuppressWarnings("unchecked")
public class TagDAOImpl extends AbstractDAO<Long, Tag> implements TagDAO{
	
	public Boolean salvarTag(Tag tag) {
		return super.salvar(tag);
	}
	public Boolean salvarTag(List<Tag> tags) {
		return super.salvar(tags);
	}
	public Boolean editarTag(Tag tag) {
		return super.editar(tag);
	}

	public Boolean excluirTag(Tag tag) {
		return super.excluir(tag);
	}

	public Boolean exluirTagPorChave(Long chave) {
		return super.excluirPorChave(chave);
	}

	public List<Tag> listarTags() {
		return (List<Tag>) super.listarTodos();
	}

	public List<Tag> pesquisarTagPorNome(String nomeAtributo, String valor) {
		return (List<Tag>) super.pesquisarPorNome(nomeAtributo, valor);
	}

	public Tag pesquisarTagPorChave(Long chave) {
		return super.pesquisarPorChave(chave);
	}


}