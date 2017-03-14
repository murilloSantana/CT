package br.com.cta.controller;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cta.DAO.ProjetoTesteDAO;
import br.com.cta.model.ProjetoTeste;

@Controller
@RequestMapping(value = { "/testesAutomatizados" })
@SuppressWarnings("static-access")
public class CtrlProjetoTeste {
	
	@Autowired
	private ProjetoTesteDAO projetoTesteDAO;
	
	@RequestMapping(value = { "/projetoDeTeste" })
	public String pageProjetoTeste(Model model) {
		model.addAttribute("projetoTeste", new ProjetoTeste());
		model.addAttribute("projetosTestes", projetoTesteDAO.listarProjetoTestes());
		return "projeto-teste";
	}

	@RequestMapping(value = { "/salvarProjetoTeste" }, method = RequestMethod.POST)
	public String salvarProjetoTeste(@ModelAttribute("projetoTeste") ProjetoTeste projetoTeste) {
		
		projetoTeste.setDataCongelamentoProjeto(stringToLocalDateTime(projetoTeste.getDataCongelamentoNonFormat()));
		LocalDateTime ldl = new LocalDateTime();	
		projetoTeste.setDataInicioProjeto(ldl.now());
		projetoTeste.setDataPrazoProjeto(stringToLocalDateTime(projetoTeste.getDataPrazoProjetoNonFormat()));
		
		projetoTesteDAO.salvarProjetoTeste(projetoTeste);
		
		return "redirect:projetoDeTeste";
	}

	public LocalDateTime stringToLocalDateTime(String data) {
		// formata recebida como string e setta como data do tipo localDatetime
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDateTime(data);
	}
}
