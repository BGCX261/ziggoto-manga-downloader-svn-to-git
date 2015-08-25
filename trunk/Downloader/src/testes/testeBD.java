package testes;

import java.sql.Connection;
import java.util.List;

import persistence.BD;
import persistence.MangaDAO;
import persistence.VolumeDAO;
import beans.Manga;
import beans.Volume;

public class testeBD {
	public static void main(String[] args) {
		Connection con = BD.getConnection();
		
		//List<Volume> lista = VolumeDAO.getTodosVolumes();
		List<Manga> lista2 = MangaDAO.getTodosMangasByIdVolume(1);
		
		for (Manga m:lista2) {
			System.out.println(m);
		}
	}
}
