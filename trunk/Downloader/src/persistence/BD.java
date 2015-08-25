package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import configs.Constantes;

public class BD {
	protected static Connection con;
	
	public static Connection getConnection(){
		if(con == null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(Constantes.HOST_URI, Constantes.USER, Constantes.PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return con;
	}
}
