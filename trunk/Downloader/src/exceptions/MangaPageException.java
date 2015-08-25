package exceptions;

public class MangaPageException extends Exception {
	public static final int NAO_EH_MANGAPAGE = 0;
	public static final int ULTIMA_PAGINA_NAO_ENCONTRADA = 1;

	private int e;

	public MangaPageException(int e) {
		super(erroMessage(e));
	}

	private static String erroMessage(int e) {
		String texto = null;

		switch (e) {
		case NAO_EH_MANGAPAGE:
			texto = "Não é uma página válida. Por favor, fornecer uma página de mangá da Punch.";
			break;
		case ULTIMA_PAGINA_NAO_ENCONTRADA:
			texto = "Erro ao tentar procurar a última página da lista.";
			break;
		default:
			texto = "Erro de programação desconhecida.";
			break;
		}

		return texto;
	}
}
