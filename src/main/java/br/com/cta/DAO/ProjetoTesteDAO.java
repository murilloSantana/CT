package br.com.cta.DAO;

import java.util.List;

import br.com.cta.model.ProjetoTeste;

public interface ProjetoTesteDAO {

	Boolean salvarProjetoTeste(ProjetoTeste projetoTeste);

	Boolean salvarProjetoTeste(List<ProjetoTeste> projetoTestes);

	Boolean editarProjetoTeste(ProjetoTeste projetoTeste);

	Boolean excluirProjetoTeste(ProjetoTeste projetoTeste);

	Boolean exluirProjetoTestePorChave(Long chave);

	List<ProjetoTeste> listarProjetoTestes();

	List<ProjetoTeste> pesquisarProjetoTestePorNome(String nomeAtributo, String valor);

	ProjetoTeste pesquisarProjetoTestePorChave(Long chave);

}
