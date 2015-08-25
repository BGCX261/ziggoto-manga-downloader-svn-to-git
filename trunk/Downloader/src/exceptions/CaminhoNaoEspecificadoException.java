package exceptions;

public class CaminhoNaoEspecificadoException extends Exception {
	public CaminhoNaoEspecificadoException(String caminho) {
		super("Erro ao gerar o caminho: "+caminho);
	}
}
