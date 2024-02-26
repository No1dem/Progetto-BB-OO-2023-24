package dataBaseConnection;
import java.sql.*;

public class ConnectDB{	
	
	private static Connection myConn;
	
	static {
		try {     //Inizializzazione delle connessione
			String url = "jdbc:postgresql://localhost:5432/DBUninaSG";
		    String username = "postgres";
		    String password = "postgres";
			Class.forName("org.postgresql.Driver");
			myConn=DriverManager.getConnection(url,username,password);
		}
		catch(ClassNotFoundException | SQLException e){ 
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return myConn;
	}
	
}