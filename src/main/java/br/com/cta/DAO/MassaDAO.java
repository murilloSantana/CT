package br.com.cta.DAO;

import java.util.List;

import br.com.cta.model.Massa;

public interface MassaDAO {

	Boolean salvarMassa(Massa massa);

	Boolean salvarMassa(List<Massa> massas);

	Boolean editarMassa(Massa massa);

	Boolean excluirMassa(Massa massa);

	Boolean exluirMassaPorChave(Long chave);

	List<Massa> listarMassas();

	List<Massa> pesquisarMassaPorNome(String nomeAtributo, String valor);

	Massa pesquisarMassaPorChave(Long chave);

}
