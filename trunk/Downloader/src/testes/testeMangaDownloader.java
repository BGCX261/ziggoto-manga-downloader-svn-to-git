package testes;

import core.MangaDownloader;

public class testeMangaDownloader {
	public static void main(String[] args) {
		//new Manga("http://mangareader.com.br/Online/pokemonadventures-capitulo-1/13259-m4all-scans/#1");
		//new Manga("http://www.mangareader.com.br/Online/pokemonadventures-capitulo-10/13268-pbn#1");
		//new Manga("http://www.mangareader.com.br/Online/onepiece-capitulo-732/49009-scansproject/#1");
		MangaDownloader.nomeManga = "One Piece";
		new MangaDownloader("http://www.mangareader.com.br/Online/onepiece-capitulo-733/49288-scansproject/#1");
	}
}
