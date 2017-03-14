package br.com.cta.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.cta.model.ProjetoTeste;

@Repository("projetoTesteDAO")
@Transactional
@SuppressWarnings("unchecked")
public class ProjetoTesteDAOImpl extends AbstractDAO<Long, ProjetoTeste> implements ProjetoTesteDAO{

	public Boolean salvarProjetoTeste(ProjetoTeste projetoTeste) {
		return super.salvar(projetoTeste);
	}
	public Boolean salvarProjetoTeste(List<ProjetoTeste> projetoTestes) {
		return super.salvar(projetoTestes);
	}
	public Boolean editarProjetoTeste(ProjetoTeste projetoTeste) {
		return super.editar(projetoTeste);
	}

	public Boolean excluirProjetoTeste(ProjetoTeste projetoTeste) {
		return super.excluir(projetoTeste);
	}

	public Boolean exluirProjetoTestePorChave(Long chave) {
		return super.excluirPorChave(chave);
	}

	public List<ProjetoTeste> listarProjetoTestes() {
		return (List<ProjetoTeste>) super.listarTodos();
	}

	public List<ProjetoTeste> pesquisarProjetoTestePorNome(String nomeAtributo, String valor) {
		return (List<ProjetoTeste>) super.pesquisarPorNome(nomeAtributo, valor);
	}

	public ProjetoTeste pesquisarProjetoTestePorChave(Long chave) {
		return super.pesquisarPorChave(chave);
	}

}
