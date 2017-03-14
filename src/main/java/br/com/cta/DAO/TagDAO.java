package br.com.cta.DAO;

import java.util.List;

import br.com.cta.model.Tag;

public interface TagDAO {

	Boolean salvarTag(Tag tag);
	Boolean salvarTag(List<Tag> tags);
	Boolean editarTag(Tag tag);
	Boolean excluirTag(Tag tag);
	Boolean exluirTagPorChave(Long chave);
	List<Tag> listarTags();
	List<Tag> pesquisarTagPorNome(String nomeAtributo, String valor);
	Tag pesquisarTagPorChave(Long chave);
}
