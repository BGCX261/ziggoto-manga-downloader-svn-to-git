package core;

import includes.SiteWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import configs.Constantes;
import exceptions.MangaPageException;

public class MangaPage {
	private int maxPage;
	private String url;
	private StringBuilder site;
	private List<MangaDownloader> lista;
	private String tituloManga;
	
	public MangaPage(String url) throws MangaPageException{
		this.url = url;
		
		if(!isMangaPage()){
			throw new MangaPageException(MangaPageException.NAO_EH_MANGAPAGE);
		}
		lista = new ArrayList<MangaDownloader>();
		site = SiteWorker.lerSite(url);
		ultimaPagina();
		preencheTitulo();
		geraPasta();
		preencheLista(1);
	}
	
	private void geraPasta(){
		File f = new File(Constantes.PATH+"\\"+tituloManga);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	private void preencheLista(int aux){
		String novaURL = url+"/page/"+aux;
		site = SiteWorker.lerSite(novaURL);
		
		String er = "<div id=\"menuOpcoes\">\\n.*<a href=\"(.*?)\">";
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(site.toString());
		while(matcher.find()){
			System.out.println(matcher.group(1));
			lista.add(new MangaDownloader(matcher.group(1)));
		}
		
		if(aux < maxPage){
			preencheLista(aux+1);
		}
	}
	
	private void preencheTitulo(){
		String er = "<div id=\"mInfos_Serie\">\\n.*<h2>(.*?)</h2>";
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(site.toString());
		
		if(matcher.find()){
			tituloManga = matcher.group(1);
			MangaDownloader.nomeManga = tituloManga;
		}
	}
	
	private void ultimaPagina() throws MangaPageException{
		//	/16">Última Página
		String er = "/([0-9]+)\">Última Página";
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(site.toString());
		
		if(!matcher.find()){
			throw new MangaPageException(MangaPageException.ULTIMA_PAGINA_NAO_ENCONTRADA);
		}else{
			maxPage = Integer.parseInt(matcher.group(1));
		}
		
	}
	
	private boolean isMangaPage(){
		//http://mangareader.com.br/Lista/mangas/429-pokemon-adventures
		String er = "http://mangareader\\.com\\.br/Lista/mangas/.+";
		
		Pattern pattern = Pattern.compile(er);
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.matches()) return true;
		return false;
	}
}
