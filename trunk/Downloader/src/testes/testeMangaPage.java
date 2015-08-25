package testes;

import core.MangaPage;
import exceptions.MangaPageException;

public class testeMangaPage {
	public static void main(String[] args) {
		try {
			MangaPage mp = new MangaPage("http://mangareader.com.br/Lista/mangas/429-pokemon-adventures");
		} catch (MangaPageException e) {
			e.printStackTrace();
		}
	}
}
