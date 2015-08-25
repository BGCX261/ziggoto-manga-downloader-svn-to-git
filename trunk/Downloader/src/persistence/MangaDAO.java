package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.Manga;
import beans.Volume;

public class MangaDAO extends BD {
	
	public static List<Manga> getTodosMangasByIdVolume(int idVolume){
		List<Manga> mangas = new LinkedList<Manga>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM manga WHERE id_volume = ? ORDER BY num DESC");
			pstmt.setInt(1, idVolume);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Manga m = new Manga();
				
				m.setIdManga(rs.getInt(1));
				m.setIdVolume(rs.getInt(2));
				m.setEdicao(rs.getDouble(3));
				m.setURL(rs.getString(4));
				
				mangas.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mangas;
	}
}
