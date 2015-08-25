package core;

import includes.SiteWorker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import configs.Constantes;
import exceptions.CaminhoNaoEspecificadoException;

// Exemplo de URL:
// http://mangareader.com.br/Online/pokemonadventures-capitulo-1/13259-m4all-scans/#1

// Momento do código-fonte que tem as paginas:
// var mPages = new Array("","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/01.jpg","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/02.jpg","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/03.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/04.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/05.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/06.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/07.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/08.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/09.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/10.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/11.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/12.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/13.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/14.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/15.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/16.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/17.png","http://leitor1.mangasproject.com/2d1007980f49215311f7f1012e84f99b801eb5daeca04dedea3ada41cc45353b/804bf2d53913eadc57a63703b2046677b78dbc05a1594c8c895405d866dcb626/18.jpg");

public class MangaDownloader {
	private StringBuilder site;
	private List<String> paginas;
	private File pasta;
	private double capitulo; // Por mais absurdo que pareça há capítulos fracionários '-'
	public static String nomeManga = "TTGL";

	public MangaDownloader(String url) {
		paginas = new ArrayList<String>();
		site = SiteWorker.lerSite(url);
		setCapitulo(url);
		try {
			geraPasta();
			
			if(pegaArray()){
				baixaPaginas();
			}else{
				System.err.println("Erro ao tentar encontrar os arquivos on the line");
			}
		} catch (CaminhoNaoEspecificadoException e) {
			e.printStackTrace();
		}
	
	}
	
	private String getNomeArquivo(String path){
		String[] pedacos = path.split("/");
		return pedacos[(pedacos.length)-1];
	}

	private void baixaPaginas() {
		URL url;
		
		for (String link : paginas) {
			try {
				url = new URL(link);
				String fileName = getNomeArquivo(url.getPath());
				InputStream is = url.openStream();
				FileOutputStream fos = new FileOutputStream(pasta.getAbsolutePath() + "\\" + fileName);

				int umByte = 0;
				while ((umByte = is.read()) != -1) {
					fos.write(umByte);
				}

				is.close();
				fos.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

	private void setCapitulo(String url) {
		String er = "capitulo-(.*?)/";
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			capitulo = Double.parseDouble(matcher.group(1));
		}
	}

	private boolean geraPasta() throws CaminhoNaoEspecificadoException{
		String caminho = Constantes.PATH + "\\" + nomeManga + "\\" + capitulo;
		File f = new File(caminho);
		System.out.println(f.getAbsolutePath());
		
		if (!f.exists()){
			if(!f.mkdirs()){
				throw new CaminhoNaoEspecificadoException(caminho);
			}
		}
		
		this.pasta = f;
		return true;
	}

	private boolean pegaArray() {
		String er = "var mPages = new Array\\((.*)\\)";
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(site.toString());

		String vetor = null;

		if (matcher.find())
			vetor = matcher.group(1);
		else
			return false;

		er = "\"(.*?)\"";
		pattern = Pattern.compile(er);
		matcher = pattern.matcher(vetor);

		while (matcher.find()) {
			if (matcher.group(1).length() > 0) {
				//System.out.println(matcher.group(1));
				paginas.add(matcher.group(1));
			}
		}

		return true;
	}
}
