package classiDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.sql.Statement;

public class LikeDAO {

	private Connection connessioneDB; 
	private LinkedList<Like> listaLikes;
	
	
	
	public void listaLikeDAO(Connection conn) {
		String query = "SELECT * FROM Like_";
		listaLikes = new LinkedList<Like>();
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				listaLikes.add(new Like (res.getInt("IdLike")));
		    }
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
		
	
	// dato in input un idLike restituisce l'oggetto Like se presente 
	public Like getLikeFromArrayListById(int idLike) {
	    for (Like like : listaLikes) {
	        if (like.getIdLike() == idLike) {
	            return like;
	        }
	    }
	    
	    return null;
	}


	
}
