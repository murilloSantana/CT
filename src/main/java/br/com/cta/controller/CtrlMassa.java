package br.com.cta.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cta.DAO.MassaDAO;
import br.com.cta.model.Massa;

@Controller
@RequestMapping(value = { "/testesAutomatizados" })
public class CtrlMassa {
	
	@Autowired
	private MassaDAO massaDAO;
	
	@RequestMapping(value = { "/massas" }, method = RequestMethod.GET)
	public String pageMassa() throws URISyntaxException, IOException {

//		JenkinsServer jenkins = new JenkinsServer(new URI("http://localhost:8000/jenkins"), "atsoc", "031994");
//		Map<String, Job> jobs = jenkins.getJobs();
//		for (String job : jobs.keySet()) {
//			System.out.println(job);
//		}
		
		return "/massa/massa";
	}
	@RequestMapping(value = { "/massas/massaForm" }, method = RequestMethod.GET)
	public String pageMassaForm(Model model) {
		model.addAttribute("massa", new Massa());
		model.addAttribute("modelosBD", TipoBD.values());
		return "/massa/massa-form";
	}
	@RequestMapping(value = { "/massas/salvarMassa" }, method = RequestMethod.POST)
	public String salvarMassa(@ModelAttribute("massa") Massa massa) {
		massaDAO.salvarMassa(massa);
		return "redirect:/testesAutomatizados/massas/massaForm";
	}
}
