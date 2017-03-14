package br.com.cta.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.cta.model.Massa;
@Repository("massaDAO")
@Transactional
@SuppressWarnings("unchecked")
public class MassaDAOImpl extends AbstractDAO<Long, Massa> implements MassaDAO{

	public Boolean salvarMassa(Massa massa) {
		return super.salvar(massa);
	}
	public Boolean salvarMassa(List<Massa> massas) {
		return super.salvar(massas);
	}
	public Boolean editarMassa(Massa massa) {
		return super.editar(massa);
	}

	public Boolean excluirMassa(Massa massa) {
		return super.excluir(massa);
	}

	public Boolean exluirMassaPorChave(Long chave) {
		return super.excluirPorChave(chave);
	}

	public List<Massa> listarMassas() {
		return (List<Massa>) super.listarTodos();
	}

	public List<Massa> pesquisarMassaPorNome(String nomeAtributo, String valor) {
		return (List<Massa>) super.pesquisarPorNome(nomeAtributo, valor);
	}

	public Massa pesquisarMassaPorChave(Long chave) {
		return super.pesquisarPorChave(chave);
	}

}
