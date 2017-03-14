package br.com.cta.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cta.DAO.TagDAO;
import br.com.cta.model.Propriedade;
import br.com.cta.model.Tag;
import br.com.cta.util.Constants;
import br.com.cta.util.UtilGenerica;
import br.com.cta.util.UtilSVN;

@Controller
@RequestMapping(value = { "/testesAutomatizados" })
@PropertySource(value = { "classpath:cta.properties" })
public class CtrlPropriedade {

	private String strSeparador = " - ";;

	@Autowired
	private Environment environment;
	@Autowired
	private Tag tag;
	@Autowired
	private UtilSVN utilSVN;
	@Autowired
	private UtilGenerica utilGenerica;
	@Autowired
	private Constants constants;
	@Autowired
	private TagDAO tagDAO;
	@Autowired
	private Propriedade propriedade;

	@RequestMapping(value = { "/propriedade" }, method = RequestMethod.GET)
	public String pagePropriedades(Model model) throws Exception {

		model.addAttribute("propriedade", new Propriedade());
		return "propriedade";
	}

	@RequestMapping(value = { "/salvarPropriedade" }, method = RequestMethod.POST)
	public String salvarPropriedade(
			@ModelAttribute("propriedade") Propriedade propriedade)
			throws IOException {
		File diretorioPropriedades = new File(
				environment
						.getRequiredProperty(Constants.strPropriedadeRepositorioDiretorio));

		List<File> arquivos = new ArrayList<File>();

		arquivos = validaPadraoArquivos(diretorioPropriedades, arquivos,
				propriedade);

		for (File arquivoPropriedade : arquivos) {

			StringBuffer bufferHistoricoMudancas = new StringBuffer();
			StringBuffer bufferNovaPropriedade = new StringBuffer();

			bufferHistoricoMudancas.append(propriedade.getAutor());
			bufferHistoricoMudancas.append(strSeparador);
			bufferHistoricoMudancas.append(propriedade.getData());
			bufferHistoricoMudancas.append(strSeparador);
			bufferHistoricoMudancas.append("Cenário "
					+ propriedade.getCenario());
			bufferHistoricoMudancas.append(strSeparador);
			bufferHistoricoMudancas.append("DriveAMnet "
					+ propriedade.getVersaoDriveAMnet());
			bufferHistoricoMudancas.append(strSeparador);
			bufferHistoricoMudancas.append("Mantis "
					+ propriedade.getNumeroMantis());
			bufferHistoricoMudancas.append(strSeparador);
			bufferHistoricoMudancas.append(propriedade.getDescricaoHistorico());

			editaHistoricoMudancas(diretorioPropriedades,
					bufferHistoricoMudancas.toString(),false);
			//
			// bufferNovaPropriedade.append(formataComentario(propriedade.getDescricaoPropriedade()));
			// bufferNovaPropriedade.append(propriedade.getNovoValor());
			//
			// utilGenerica.salvarPropriedadeFimArquivo(bufferNovaPropriedade.toString(),
			// arquivoLocal);
		}
		return "redirect:propriedade";
	}

	public List<File> validaPadraoArquivos(File arquivoLocal,
			List<File> arquivos, Propriedade propriedade) {

		for (File arquivo : arquivoLocal.listFiles()) {
			if (arquivo.isDirectory()) {
				validaPadraoArquivos(arquivo, arquivos, propriedade);
			} else {
				if (arquivo.getName().startsWith(
						propriedade.getNumeroProcesso())) {
					arquivos.add(arquivo);
				}
			}

		}

		return arquivos;
	}

	public String formataComentario(String descricao) {
		String[] formatDescricao = descricao.split("\n");
		StringBuffer buffer = new StringBuffer();
		for (String desc : formatDescricao) {
			buffer.append("#" + desc);
		}

		return buffer.toString();
	}

	public void editaHistoricoMudancas(File file, String strHistorico,boolean isDirectory)
			throws IOException {
		boolean padraoEncontrado = false;
		if (isDirectory) {
			for (File arquivo : file.listFiles()) {

				if (arquivo.isDirectory()) {
					editaHistoricoMudancas(arquivo, strHistorico,isDirectory);

				} else if (arquivo.getName().endsWith(".properties")) {

					InputStreamReader isr = new InputStreamReader(
							new FileInputStream(arquivo));
					BufferedReader br = new BufferedReader(isr);
					String strLinha = br.readLine();
					String novo = null;

					while (strLinha != null) {

						if (validaPadraoHistoricoMudancas(strLinha)) {
							novo = strLinha;
							strLinha = br.readLine();
							padraoEncontrado = true;
						
						} else {
							if(padraoEncontrado){
								utilGenerica.salvarPropriedade(
										"#"
										+ geraIdentificadorHistoricoMudancas(novo)
										+ strSeparador
										+ strHistorico, novo,
										arquivo.getAbsoluteFile(),
								true, false);
								strLinha = null;
							}else{
								strLinha = br.readLine();
							}
						}

					}
					br.close();

				}

			}
		}else{

			if (file.getName().endsWith(".properties")) {

				InputStreamReader isr = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(isr);
				String strLinha = br.readLine();
				String novo = null;

				while (strLinha != null) {
					System.out.println("aquiiiiiii");

					if (validaPadraoHistoricoMudancas(strLinha)) {
						novo = strLinha;
						strLinha = br.readLine();
						padraoEncontrado = true;
					
					} else {
						if(padraoEncontrado){
							utilGenerica.salvarPropriedade(
									"#"
									+ geraIdentificadorHistoricoMudancas(novo)
									+ strSeparador
									+ strHistorico, novo,
							file.getAbsoluteFile(),
							true, false);
							strLinha = null;

						}else{
							strLinha = br.readLine();
						}
					}

				}
				br.close();

			}
		}

	}

	public String geraIdentificadorHistoricoMudancas(String strLinha) {

		String[] tracos = strLinha.split("-");

		tracos[0] = tracos[0].replace("#", "").trim();
		Integer novoIdentificador = Character.getNumericValue(tracos[0]
				.charAt(0));
		novoIdentificador += 1;
		String strVersaoFinalId = novoIdentificador + ".00.00";
		return strVersaoFinalId;
	}

	public boolean validaPadraoHistoricoMudancas(String strLinha) {
		if (strLinha.contains("#")) {
			String[] tracos = strLinha.split("-");
			if (tracos.length > 3) {
				tracos[0] = tracos[0].replace("#", "").trim();
				if (tracos[2].trim().matches("\\d{2}/\\d{2}/\\d{4}")) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		} else {
			return false;
		}

	}
}
