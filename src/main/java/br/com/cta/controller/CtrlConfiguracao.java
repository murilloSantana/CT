package br.com.cta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cta.model.ConfiguracaoGeral;
import br.com.cta.util.Constants;

@Controller
@RequestMapping(value = { "/testesAutomatizados" })
@PropertySource(value={"classpath:cta.properties"})
public class CtrlConfiguracao {
	
	@Autowired
	private ConfiguracaoGeral configuracaoGeral;
	@Autowired
	private Environment environment;
	@Autowired
	private Constants constants;
	
	@RequestMapping(value={"/configuracoes"},method = RequestMethod.GET)
	public String pageConfiguracao(Model model){
		configuracaoGeral.setNumeroVersao(environment.getRequiredProperty(Constants.strVersaoRepositorio));
		configuracaoGeral.setRepositorioPropriedadesLocal(environment.getRequiredProperty(Constants.strPropriedadeRepositorioDiretorio));
		configuracaoGeral.setRepositorioTagsLocal(environment.getRequiredProperty(Constants.strTagRepositorioDiretorio));

		model.addAttribute("configuracaoGeral", configuracaoGeral);
		return "configuracao";
	}
	
	@RequestMapping(value={"/salvarConfiguracoesGerais"},method = RequestMethod.POST)
	public String salvarConfiguracoesGerais(@ModelAttribute("configuracaoGeral") ConfiguracaoGeral configuracaoGeral){
		System.setProperty(Constants.strVersaoRepositorio, configuracaoGeral.getNumeroVersao());
		String novaUrl = "https://svnprodrj02.seniorsolution.com.br/svn/TC_"+ configuracaoGeral.getNumeroVersao()+"/Scripts/trunk/Projetos_Automacao/Cenarios/Lib";
		System.setProperty(Constants.strTagRepositorioUrl, novaUrl);
		System.setProperty(Constants.strTagRepositorioDiretorio, configuracaoGeral.getRepositorioTagsLocal());
		System.setProperty(Constants.strPropriedadeRepositorioDiretorio, configuracaoGeral.getRepositorioPropriedadesLocal());

		return "redirect:configuracoes";
	}
}
