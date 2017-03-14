package br.com.cta.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public ClassLoader classLoader = this.getClass().getClassLoader();
	public String strCtaProperties = classLoader.getResource("cta.properties").getPath();
	public String strTagJson = classLoader.getResource("tags.json").getPath();
	
	// Propriedades do arquivo cta.properties
	public static String strTagRepositorioUrl = "tags.repositorio.url";
	public static String strTagRepositorioRevision = "tags.repositorio.revision";
	public static String strTagRepositorioDiretorio = "tags.repositorio.diretorio";
	public static String strVersaoRepositorio = "versao.repositorio";
	public static String strPropriedadeRepositorioDiretorio = "propriedades.repositorio.diretorio";

	public static String strLocalDiretorioRoot = "local.repositorio.root";
	public static String strLocalDiretorioPropriedades = "local.repositorio.propriedades";
	public static String strLocalDiretorioScripts = "local.repositorio.scripts";

	
	
}
