package br.com.cta.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cta.DAO.TagDAO;
import br.com.cta.model.Propriedade;
import br.com.cta.model.Tag;
import br.com.cta.util.Constants;
import br.com.cta.util.UtilGenerica;
import br.com.cta.util.UtilSVN;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = { "/testesAutomatizados" })
@PropertySource(value = { "classpath:cta.properties" })
public class CtrlTag {

	@Autowired
	private Environment environment;
	private String strLinha = null;
	private Gson gson = null;
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

	@RequestMapping(value = { "/tags" }, method = RequestMethod.GET)
	public String pageTags() throws IOException {
		return "tag";
	}

	@RequestMapping(value = { "/atualizaTags" }, method = RequestMethod.GET)
	public @ResponseBody
	String atualizaTags() throws IOException {
		File file = new File(utilGenerica.getPropriedade(
				constants.strCtaProperties,
				Constants.strTagRepositorioDiretorio));
		for (Tag t : formataTags(file)) {
			List<Tag> resultTag = tagDAO.pesquisarTagPorNome("id", t.getId());
			List<String> caminhos = resultTag.get(0).getCaminhos();
			List<String> categorias = resultTag.get(0).getCategorias();

			List<Tag> tagsComparator = tagDAO.pesquisarTagPorNome("id",
					t.getId());
			if (resultTag.size() > 0) {

				if (!caminhos.contains(t.getCaminhos().get(0))) {
					caminhos.add(t.getCaminhos().get(0));
				}

				for (String categoria : t.getCategorias()) {
					if (!resultTag.get(0).getCategorias().contains(categoria)) {
						categorias.add(categoria);
					}

				}

				resultTag.get(0).setCaminhos(caminhos);
				resultTag.get(0).setCategorias(categorias);
				boolean editaTag = false;

				for (String c : resultTag.get(0).getCaminhos()) {
					if (!tagsComparator.get(0).getCaminhos().contains(c)) {
						editaTag = true;
						break;
					}
				}

				for (String c : resultTag.get(0).getCategorias()) {
					if (!tagsComparator.get(0).getCategorias().contains(c)) {
						editaTag = true;
						break;

					}
				}

				if (editaTag) {
					tagDAO.editarTag(resultTag.get(0));
				}
			} else {

				tagDAO.salvarTag(t);
			}
		}

		gson = new Gson();
		List<Tag> tags = tagDAO.listarTags();

		 utilGenerica.escreverArquivo(constants.strTagJson,
		 gson.toJson(tags));

		return gson.toJson(tags);
	}

	@RequestMapping(value = { "/getTags" }, method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	String tags(ModelMap model) throws Exception {

		// List<Tag> listaTags = null;
		//
		// String urlRepositorioTags =
		// environment.getRequiredProperty(Constants.strTagRepositorioUrl);
		//
		//
		//
		// if (utilGenerica
		// .getPropriedade(utilGenerica.formataCaminho(constants.strCtaProperties),
		// Constants.strTagRepositorioRevision)
		// .equals(utilSVN.coletaRevisionHEAD(URLDecoder.decode(urlRepositorioTags,
		// "utf-8")).toString())) {
		//

		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(new File(
				utilGenerica.formataCaminho(constants.strTagJson))));

		while ((strLinha = br.readLine()) != null) {
			buffer.append(strLinha);
		}
		br.close();
		return buffer.toString();
		//
		// }else{
		//
		// // edita o arquivo de propriedades do cta passando a nova revision
		// utilGenerica.salvaPropriedade(constants.strCtaProperties,
		// "tags.repositorio.revision",
		// utilSVN.coletaRevisionHEAD(URLDecoder.decode(urlRepositorioTags,
		// "utf-8")).toString());
		//
		// listaTags = formataTags();
		// model.addAttribute("tags", gson.toJson(listaTags));
		// model.addAttribute("tagClass", listaTags);
		//
		// utilGenerica.escreverArquivo(constants.strTagJson,
		// gson.toJson(listaTags));
		//
		// return gson.toJson(listaTags);
		// }

	}

	public List<Tag> formataTags(File file) throws IOException {
		BufferedReader br = null;
		String tagRemovida = null;
		String id = null;
		String descricao = null;
		String categorias[] = null;
		List<String> listaCategorias = null;
		List<String> listaCaminhos = null;
		String autor;
		String data;

		String regexCategorias = "(\\[(\\w+.*)\\])+";
		List<Tag> listaTags = new ArrayList<Tag>();

		for (File arquivo : file.listFiles()) {

			if (arquivo.isFile() && arquivo.getName().endsWith(".sj")) {
				InputStreamReader isr = new InputStreamReader(
						new FileInputStream(arquivo), "Unicode");
				br = new BufferedReader(isr);
				// br = new BufferedReader(new FileReader(arquivo));
				while ((strLinha = br.readLine()) != null) {
					if (strLinha.contains("Tag:")) {

						listaCategorias = new ArrayList<String>();
						listaCaminhos = new ArrayList<String>();
						listaCaminhos.add(arquivo.getName());

						tagRemovida = strLinha.replace("Tag:", "").trim();
						String[] valores = tagRemovida.split("-");
						id = valores[0].trim();

						descricao = valores[1].trim();
						autor = valores[3].trim();
						data = valores[4].trim();

						Pattern p = Pattern.compile(regexCategorias);
						Matcher m = p.matcher(valores[2]);
						while (m.find()) {
							categorias = m.group(2).split(",");
						}
						for (int i = 0; i < categorias.length; i++) {
							listaCategorias.add(categorias[i].trim());
						}

						tag = new Tag(id, descricao, listaCaminhos,
								listaCategorias, autor, data);
						listaTags.add(tag);

					}
				}

				br.close();
			} else if (arquivo.isDirectory()) {
				formataTags(arquivo);
			}

		}
		return listaTags;

	}

}
