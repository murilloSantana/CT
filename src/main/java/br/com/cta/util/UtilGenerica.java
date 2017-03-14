package br.com.cta.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class UtilGenerica {

	public String formataCaminho(String caminho)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(caminho, "utf-8");
	}

	public void salvaPropriedadeConfig(String strNomeArquivo,
			String strNomePropriedade, String strValorPropriedade)
			throws IOException {
		Properties props = new Properties();

		InputStream is = new FileInputStream(new File(strNomeArquivo));
		props.load(is);

		props.setProperty(strNomePropriedade, strValorPropriedade);
		FileOutputStream fo = new FileOutputStream(new File(strNomeArquivo));
		props.store(fo, "Atualizado em:");

		fo.close();

	}

	public String getPropriedade(String strNomeArquivo,
			String strNomePropriedade) throws IOException {

		strNomeArquivo = formataCaminho(strNomeArquivo);
		Properties props = new Properties();
		InputStream is = new FileInputStream(new File(strNomeArquivo));
		props.load(is);

		return props.getProperty(strNomePropriedade);

	}

	public void escreverArquivo(String strNomeArquivo, String strValor)
			throws IOException {

		strNomeArquivo = formataCaminho(strNomeArquivo);
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				strNomeArquivo)));
		bw.write(strValor);
		bw.close();

	}

	public void salvarPropriedade(String novoValor, String antigoValor,
			File arquivo, boolean isBefore, boolean isIdenta)
			throws UnsupportedEncodingException, FileNotFoundException {

		File arquivoTemporario = new File(formataCaminho("temporario.tmp"));

		String strEspaco = isIdenta ? "   " : "";
		BufferedWriter bw = null;
		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		FileWriter fw = null;
		String linha = null;
		try {

			fw = new FileWriter(arquivoTemporario);
			bw = new BufferedWriter(fw);

			linha = br.readLine();

			while (linha != null) {

				if (linha.equals(antigoValor)) {

					if (isBefore) {

						linha = linha.replace(
								antigoValor,
								antigoValor
										+ System.getProperty("line.separator")
										+ strEspaco + novoValor);

					} else {
						linha = linha.replace(
								antigoValor,
								novoValor
										+ System.getProperty("line.separator")
										+ strEspaco + antigoValor);
					}

				}
				
				bw.write(linha + System.getProperty("line.separator"));
				linha = br.readLine();
			}

			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvarPropriedadeFimArquivo(String novoValor, File arquivo)
			throws UnsupportedEncodingException, FileNotFoundException {

		File arquivoTemporario = new File(formataCaminho("temporario.tmp"));

		BufferedWriter bw = null;
		FileWriter fw = null;
		StringBuffer buffer = new StringBuffer();
		try {

			fw = new FileWriter(arquivoTemporario, true);

			bw = new BufferedWriter(fw);

			buffer.append(System.getProperty("line.separator"));
			buffer.append(novoValor);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void criaDiretorio(String arquivo) throws IOException{
		File fileArq = new File(arquivo);
		
		if(!fileArq.exists())
			fileArq.mkdirs();
	}
	
	public boolean limparPasta(File arquivo){
		for (File file : arquivo.listFiles()) {
			if(file.isDirectory()){
				limparPasta(file);
			}else{
				file.delete();
			}
		}
		return arquivo.delete();
	}
}
