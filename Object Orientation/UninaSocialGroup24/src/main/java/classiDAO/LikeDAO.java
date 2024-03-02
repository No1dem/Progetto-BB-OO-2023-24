package classiDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import dataBaseConnection.ConnectDB;

import java.sql.Statement;

public class LikeDAO {

	private Connection connessioneDB; 
	private LinkedList<Like> listaLikes;
	
	
	
	public void listaLikeDAO() {
		String query = "SELECT * FROM Like_";
		listaLikes = new LinkedList<Like>();
		
		try(Connection conn = ConnectDB.getConnection()){
			Statement stmt=conn.createStatement();
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
				
				int idLike = res.getInt("IdLike");
	            int idUtente = res.getInt("IdUtente");
	            int idCommento = res.getInt("IdCommento");
	            int idPost = res.getInt("IdPost");

	            UtenteDAO utenteDAO = new UtenteDAO();
	            Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
	            
	            CommentoDAO commentoDAO = new CommentoDAO();
	            Commento commento = commentoDAO.getCommentoFromArrayListById(idCommento);
	            
	            PostDAO postDAO = new PostDAO();
	            Post post = postDAO.getPostFromArrayListById(idPost); 

	            Like like = new Like(idLike, utente, commento, post);
	            listaLikes.add(like);
			
		    }
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Like getLikeFromArrayListByIdLike(int idLike) {
		
	    for (Like like : listaLikes) {
	        if (like.getIdLike() == idLike) {
	            return like;
	        }
	    }
	    
	    return null;
	}
	
	public LinkedList<Like> getLikeFromArrayListByIdPost(int idPost) {
		LinkedList<Like> listaLikePost = new LinkedList<Like>() ;
		
	    for (Like like : listaLikes) {
	        if (like.getIdPostConLike() == idPost) {
	            	listaLikePost.add(like);
	        }
	    }  
	    
	    return listaLikePost;
	}


}
