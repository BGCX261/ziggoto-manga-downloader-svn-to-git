package beans;

import java.util.List;

public class Volume {
	private int id;
	private String titulo;
	private String URL;
	private List<Manga> mangas;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	@Override
	public String toString() {
		return titulo;
	}
	
}
