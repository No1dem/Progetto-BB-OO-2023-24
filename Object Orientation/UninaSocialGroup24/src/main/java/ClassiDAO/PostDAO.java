package ClassiDAO;

import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PostDAO {
	private Connection connessioneDB; 
	private LinkedList<Post> listaPost;
	
	public void listaPostDao(Connection conn) {
		String query="SELECT * FROM Post";
		listaPost = new LinkedList<Post>();
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				// Conversione java.sql.Date in LocalDate
	            java.sql.Date sqlDate = res.getDate("dataPubblicazione");
	            LocalDate dataPubblicazione = sqlDate.toLocalDate();
	            // Conversione java.sql.Time in LocalTime
	            java.sql.Time sqlTime = res.getTime("oraPubblicazione");
	            LocalTime oraPubblicazione = sqlTime.toLocalTime();
				listaPost.add(new Post(res.getInt("idPost"),res.getString("testo"),res.getString("urlImmagine"),
						dataPubblicazione,oraPubblicazione,res.getInt("NumeroLike"),res.getInt("NumeroCommenti")));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
