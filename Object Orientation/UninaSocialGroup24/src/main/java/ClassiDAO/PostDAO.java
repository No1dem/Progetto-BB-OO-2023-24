package ClassiDAO;

import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
	            java.sql.Date sqlDate = res.getDate("dataPubblicazione");
	            LocalDate dataPubblicazione = sqlDate.toLocalDate();
	            java.sql.Time sqlTime = res.getTime("oraPubblicazione");
	            LocalTime oraPubblicazione = sqlTime.toLocalTime();
				listaPost.add(new Post(res.getInt("idPost"),res.getString("testo"),res.getString("urlImmagine"),
						dataPubblicazione,oraPubblicazione,res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),
						new UtenteDAO().getUtenteFromArrayListById(res.getInt("idUtente")),new GruppoDAO().getGruppoFromArrayListById(res.getInt("idGruppo"))));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNuovoPost(Post p) {
		String query="INSERT INTO Post (testo,urlImmagine,dataPubblicazione,oraPubblicazione,idUtente,idGruppo) VALUES (?,?,?,?,?,?)";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,p.getTesto());
			pstmt.setString(2,p.getUrlImmagine()); 
	        Date dataPubblicazione = Date.valueOf(p.getDataPubblicazione());
	        pstmt.setDate(3, dataPubblicazione);
	        Time oraPubblicazione = Time.valueOf(p.getOraPubblicazione());
	        pstmt.setTime(4, oraPubblicazione);
			pstmt.setInt(5,p.getIdUtente());
			pstmt.setInt(6,p.getIdGruppo());		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNuovoPost(Post p,Gruppo g,Utente u) {
		String query="INSERT INTO Post (testo,urlImmagine,dataPubblicazione,oraPubblicazione,idUtente,idGruppo) VALUES (?,?,?,?,?,?)";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,p.getTesto());
			pstmt.setString(2,p.getUrlImmagine()); 
	        Date dataPubblicazione = Date.valueOf(p.getDataPubblicazione());
	        pstmt.setDate(3, dataPubblicazione);
	        Time oraPubblicazione = Time.valueOf(p.getOraPubblicazione());
	        pstmt.setTime(4, oraPubblicazione);
			pstmt.setInt(5,g.getIdGruppo());
			pstmt.setInt(6,u.getIdUtente());		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
