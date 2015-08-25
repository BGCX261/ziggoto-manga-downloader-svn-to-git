package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.Volume;

public class VolumeDAO extends BD {
	public static List<Volume> getTodosVolumes(){
		List<Volume> mangas = new LinkedList<Volume>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM volume");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Volume v = new Volume();
				
				v.setId(rs.getInt(1));
				v.setTitulo(rs.getString(2));
				mangas.add(v);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mangas;
	}
}
