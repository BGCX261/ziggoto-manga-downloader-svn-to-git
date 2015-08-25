package testes;

import includes.SiteWorker;


public class testeMetodoLerSite {
	public static void main(String[] args) {
		SiteWorker l = new SiteWorker();
		StringBuilder sb = l.lerSite("http://mangareader.com.br/Lista/mangas/429-pokemon-adventures");
		System.out.println(sb.toString());
	}
}
