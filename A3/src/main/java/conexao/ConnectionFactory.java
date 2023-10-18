package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection conexao(){
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/serieList", "root", "12345678");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
		
}
