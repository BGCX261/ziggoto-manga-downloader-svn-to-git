package beans;

import java.text.DecimalFormat;

public class Manga {
	private int idManga, idVolume;
	private double edicao;
	private String URL, edicaoFinal;
	
	public Manga() {

	}
	
	public Manga(int edicao){
		this.edicao = edicao;
	}
	
	public int getIdManga() {
		return idManga;
	}
	public void setIdManga(int idManga) {
		this.idManga = idManga;
	}
	public int getIdVolume() {
		return idVolume;
	}
	public void setIdVolume(int idVolume) {
		this.idVolume = idVolume;
	}
	public double getEdicao() {
		return edicao;
	}
	
	public String getEdicaoFinal(){
		return edicaoFinal;
	}
	
	public void setEdicao(double edicao) {
		DecimalFormat df = new DecimalFormat("###.##");
		
		this.edicao = edicao;
		this.edicaoFinal = df.format(edicao);
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(edicao);
	}
	
}
